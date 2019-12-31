package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.cskaoyan.util.HandleOptionTool;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxGrouponServiceImpl implements WxGrouponService {
    @Autowired
    GrouponMapper grouponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderGoodsMapper orderGoodsMapper;
    /**
     * 微信小程序团购专区团购列表功能
     * @param page 当前页数
     * @param size 分页条目数
     * @return 全部的团购数据
     */

    @Override
    public List<Map> grouponlist(Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Map> list = new ArrayList<>();
        List<WxGrouponList> wxGrouponLists = goodsMapper.selectGoodsByGroupon();
        for (WxGrouponList wxGroupon : wxGrouponLists) {
            Map map1 = new HashMap();
            BigDecimal discount = wxGroupon.getDiscount();
            Integer discountMember = wxGroupon.getDiscount_member();
            BigDecimal retailPrice = wxGroupon.getRetailPrice();
            BigDecimal subtract = retailPrice.subtract(discount);
            map1.put("groupon_member", discountMember);
            map1.put("groupon_price", subtract);
            map1.put("goods",wxGroupon);
            list.add(map1);
        }
        return list;

    }

    /**
     * 微信小程序个人详情页我的团购模块功能
     * @param showType 确认团购状态的状态码
     * @return user个人的团购信息
     */
    @Override
    public Map<String, Object> wxGrouponMy(Integer showType) {
        GrouponExample grouponExample = new GrouponExample();
        User user = user1();
        grouponExample.createCriteria().andUserIdEqualTo(user.getId());
        //该用户的团购
        List<Groupon> groupons = grouponMapper.selectByExample(grouponExample);
        ArrayList<Object> showType0= new ArrayList<>();
        ArrayList<Object> showType1 = new ArrayList<>();
        int showTypeCount0 = 0;
        int showTypeCount1 = 0;
        for (Groupon groupon : groupons) {
            Map map = new HashMap();
            Order order = orderMapper.selectByPrimaryKey(groupon.getOrderId());

            setOrderStatusText(order,map);//map.put("orderStatusText","未付款");

            User createUser = userMapper.selectByPrimaryKey(groupon.getCreatorUserId());
            map.put("creator", createUser.getNickname());
            map.put("groupon", groupon);
            map.put("orderId", order.getId());
            map.put("orderSn", order.getOrderSn());
            map.put("actualPrice", order.getActualPrice());
            GrouponExample grouponExample1 = new GrouponExample();
            grouponExample1.createCriteria().andGrouponIdEqualTo(groupon.getGrouponId());
            List<Groupon> groupons1 = grouponMapper.selectByExample(grouponExample1);
            map.put("joinerCount",groupons1.size());
            OrderGoodsExample orderGoodsExample = new OrderGoodsExample();
            orderGoodsExample.createCriteria().andOrderIdEqualTo(order.getId());
            List<OrderGoods> orderGoods = orderGoodsMapper.selectByExample(orderGoodsExample);
            List<Goods> goodsList = null;
            for (OrderGoods orderGoods1 : orderGoods) {
                GoodsExample goodsExample = new GoodsExample();
                goodsExample.createCriteria().andIdEqualTo(orderGoods1.getGoodsId());
                goodsList = goodsMapper.selectByExample(goodsExample);
            }
            map.put("goodsList",goodsList);
            GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(groupon.getGrouponId());
            map.put("rules",grouponRules);
            map.put("id",groupon.getId());
            map.put("isCreator",true);
            HandleOptionBean orderHandleOptionBean = HandleOptionTool.getOrderHandleOptionBean(order);
            map.put("handleOption",orderHandleOptionBean);

            if(user.getId().equals(groupon.getCreatorUserId())) {
                showType0.add(map);
                showTypeCount0++;
            }else{
                showType1.add(map);
                showTypeCount1++;
            }
        }
        Map<String,Object> map1 = new HashMap<>();
        if(showType == 0){
            map1.put("data",showType0);
            map1.put("count",showTypeCount0);
            return map1;
        }
        map1.put("data",showType1);
        map1.put("count",showTypeCount1);
        return map1;
    }
    public static User user1(){
        //获取当前用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    public void setOrderStatusText(Order order,Map<String,Object> map){
        switch (order.getOrderStatus()){
            case (short)101:
                map.put("orderStatusText","未付款");
                break;
            case (short)102:
                map.put("orderStatusText","用户取消");
                break;
            case (short)103:
                map.put("orderStatusText","系统取消");
                break;
            case (short)201:
                map.put("orderStatusText","已付款");
                break;
            case (short)202:
                map.put("orderStatusText","申请退款");
                break;
            case (short)203:
                map.put("orderStatusText","已退款");
                break;
            case (short)301:
                map.put("orderStatusText","已发货");
                break;
            case (short)401:
                map.put("orderStatusText","用户收货");
                break;
            case (short)402:
                map.put("orderStatusText","系统收货");
                break;
        }
    }

}

