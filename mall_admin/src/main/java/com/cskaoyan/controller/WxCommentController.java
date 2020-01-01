package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Feedback;
import com.cskaoyan.bean.Region;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.comment.*;
import com.cskaoyan.service.WxCommentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class WxCommentController {
    @Autowired
    WxCommentService wxCommentService;

    /**
     * 显示足迹
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/footprint/list")
    public BaseRespVo showFootprint(Integer page, Integer size){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Map map = wxCommentService.selectFootprint(page, size, user);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(map);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 显示收获地址
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("wx/address/list")
    public BaseRespVo showAddress(Integer page, Integer size){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //获取地址信息
        List<ShowAddress> showAddresses = wxCommentService.selectAddress(user);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(showAddresses);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 新加收获地址
     * @param saveAddress
     * @return
     */
    @RequestMapping("wx/address/save")
    public BaseRespVo createAddress(@RequestBody SaveAddress saveAddress){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //获取地址信息
        wxCommentService.insertAddress(user,saveAddress);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(27);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 显示区域
     * @param pid
     * @return
     */
    @RequestMapping("wx/region/list")
    public BaseRespVo createAddress(Integer pid){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //获取地区域信息
        List<Region> regions = wxCommentService.selectRegion(pid);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(regions);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("wx/address/delete")
    public BaseRespVo deleteAddress(@RequestBody Map id){
        Integer id1 = (Integer) id.get("id");
        wxCommentService.deleteAddress(id1);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /**
     * 显示地址详情信息
     * @param id
     * @return
     */
    @RequestMapping("wx/address/detail")
    public BaseRespVo detailAddress(int id){
        AddressDetail addressDetail = wxCommentService.selectAddressById(id);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setData(addressDetail);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/feedback/submit")
    public BaseRespVo createSubmit(@RequestBody Feedback feedback){
        //获取用户信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        wxCommentService.insertFeedback(feedback,user);
        //返回数据
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }



}
