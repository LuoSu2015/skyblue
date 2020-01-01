package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.comment.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class WxCommentServiceImpl implements WxCommentService {

    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    FootprintMapper footprintMapper;
    @Autowired
    AddressMapper addressMapper;
    @Autowired
    RegionMapper regionMapper;
    @Autowired
    FeetbackMapper feetbackMapper;

    /**
     * 显示我的足迹
     * @param page
     * @param size
     * @param user
     * @return
     */
    @Override
    public Map selectFootprint(Integer page, Integer size, User user) {
        //根据user_id查询footprint信息
        FootprintExample footprintExample = new FootprintExample();
        footprintExample.createCriteria().andUserIdEqualTo(user.getId()).andDeletedEqualTo(false);
        List<Footprint> footprints = footprintMapper.selectByExample(footprintExample);
        //分页
        PageHelper.startPage(page,size);
        //List接收footprint信息
        List<ShowFootprint> showFootprints = new ArrayList<>();
        GoodsExample goodsExample = new GoodsExample();
        for (Footprint footprint : footprints) {
            //根据user_id查询goods信息
            goodsExample.createCriteria().andIdEqualTo(footprint.getGoodsId());
            List<Goods> goods = goodsMapper.selectByExample(goodsExample);
            for (Goods good : goods) {
                //封装ShowFootprint
                ShowFootprint showFootprint = new ShowFootprint(footprint.getId(),good.getName(),
                        good.getBrief(),good.getPicUrl(),footprint.getAddTime(),footprint.getGoodsId(),good.getRetailPrice());
                showFootprints.add(showFootprint);
            }
        }
        PageInfo<ShowFootprint> showFootprintPageInfo = new PageInfo<>(showFootprints);
        int total = (int) showFootprintPageInfo.getTotal();
        //将数据封装到map中
        Map map = new HashMap();
        map.put("footprintList",showFootprints);
        map.put("totalPages",total);
        return map;
    }

    /**
     * 显示收货地址
     * @param user
     * @return
     */
    @Override
    public List<ShowAddress> selectAddress(User user) {
        //查询地址信息
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andUserIdEqualTo(user.getId()).andDeletedEqualTo(false);
        List<Address> addresses = addressMapper.selectByExample(addressExample);
        //将地址信息封装
        List<ShowAddress> showAddresses = new ArrayList<>();
        for (Address address : addresses) {
            ShowAddress showAddress = new ShowAddress(address.getId(),address.getName(),address.getAddress(),
                    address.getMobile(),address.getIsDefault());
            showAddresses.add(showAddress);
        }
        return showAddresses;
    }

    /**
     * 新加收获地址
     * @param user
     * @return
     */
    @Override
    @Transactional
    public int insertAddress(User user, SaveAddress saveAddress) {
        Date nowTime = new Date();
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andIsDefaultEqualTo(true);
        //如果设为了默认值则将其他的默认值设为0
        if(saveAddress.getIsDefault()){
            List<Address> addresses = addressMapper.selectByExample(addressExample);
            for (Address address : addresses) {
                address.setIsDefault(false);
                addressMapper.updateByPrimaryKeySelective(address);
            }
        }
        //构建地址信息
        Address address = new Address(null,saveAddress.getName(),user.getId(),saveAddress.getProvinceId(),saveAddress.getCityId(),
                saveAddress.getAreaId(),saveAddress.getAddress(),saveAddress.getMobile(),saveAddress.getIsDefault(),nowTime,nowTime,false);
        //插入地址信息
        int insert = addressMapper.insert(address);

        return insert;
    }

    /**
     * 查询商品信息
     * @param pid
     * @return
     */
    @Override
    public List<Region> selectRegion(Integer pid) {
        //查询区域信息
        RegionExample regionExample = new RegionExample();
        regionExample.createCriteria().andPidEqualTo(pid);
        List<Region> regions = regionMapper.selectByExample(regionExample);
        return regions;
    }

    /**
     * 删除
     * @param
     * @param
     * @return
     */
    @Override
    public int deleteAddress(Integer id) {
        //查询地址信息
        Address address = addressMapper.selectByPrimaryKey(id);
        address.setDeleted(true);
        //删除逻辑
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andIdEqualTo(id);
        int i = addressMapper.updateByPrimaryKeySelective(address);
        return i;
    }

    /**
     * 显示地址详情信息
     * @param id
     * @return
     */
    @Override
    public AddressDetail selectAddressById(Integer id) {

        Address address = addressMapper.selectByPrimaryKey(id);
        //根据地区Id查询地区信息
        Integer provinceId = address.getProvinceId();
        Integer cityId = address.getCityId();
        Integer areaId = address.getAreaId();
        //查询城市name
        Region region1 = regionMapper.selectByPrimaryKey(provinceId);
        String provinceName = region1.getName();
        //查询区域name
        Region region2 = regionMapper.selectByPrimaryKey(cityId);
        String cityName = region2.getName();
        //查询县城name
        Region region3 = regionMapper.selectByPrimaryKey(areaId);
        String areaName = region3.getName();
        //封装返回结果
        AddressDetail addressDetail = new AddressDetail(address.getIsDefault(),areaId,address.getAddress(),cityName,
                areaName,address.getName(),address.getMobile(),address.getId(),cityId,provinceName,provinceId);
        return addressDetail;
    }

    /**
     * 插入意见反馈
     * @param feedback
     * @param user
     * @return
     */
    @Override
    public int insertFeedback(Feedback feedback, User user) {
        //将信息保存到数据库
        Date nowTime = new Date();
        feedback.setUserId(user.getId());
        feedback.setUsername(user.getUsername());
        feedback.setStatus(1);
        feedback.setAddTime(nowTime);
        feedback.setUpdateTime(nowTime);
        feedback.setDeleted(false);
        int insert = feetbackMapper.insert(feedback);
        return insert;
    }
}
