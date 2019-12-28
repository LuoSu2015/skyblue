package com.cskaoyan.controller.promote;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.promate.Coupon2;
import com.cskaoyan.bean.promate.PageWrapper;
import com.cskaoyan.service.promote.PromoteService;
import com.cskaoyan.util.promoteUtils.PromoteUtils;
import com.cskaoyan.util.uploadPic.UploadPicUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class PromoteController {

    @Autowired
    PromoteService promoteService;


    /* 图片上传*/
    @RequestMapping("admin/storage/create")
    public BaseRespVo addPic(MultipartFile file) throws IOException {
        BaseRespVo<Storage> storageBaseRespVo = UploadPicUtils.fileUpload(file);
        return storageBaseRespVo;
    }

    /* 广告展示 */
    @RequestMapping("admin/ad/list")
    public BaseRespVo<List<Ad>> queryAdListByNameAndContent(PageWrapper pageWrapper) {

        PromoteUtils<Ad> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Ad> ads = promoteService.queryAdByNameAndContent(pageWrapper.getName(), pageWrapper.getContent());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, ads);

        return baseRespVo;
    }

    /* 添加广告*/
    @RequestMapping("admin/ad/create")
    public BaseRespVo insertAdList(@RequestBody Ad ad) {
        BaseRespVo<Ad> adBaseRespVo = new BaseRespVo<>();
        ArrayList<Ad> adlist = new ArrayList<>();
        adlist.add(ad);
        /* 开启了usegenerateKey，他会自动返回当前对象的Id */
        promoteService.insertAdList(adlist);
        adBaseRespVo.setData(ad);
        adBaseRespVo.setErrno(0);
        adBaseRespVo.setErrmsg("成功");

        return adBaseRespVo;
    }

    /*  更新广告*/
    @RequestMapping("admin/ad/update")
    public BaseRespVo updateAd(Ad ad) {
        ArrayList<Ad> adList = new ArrayList<>();
        promoteService.updateAdList(adList);
        BaseRespVo<Ad> adBaseRespVo = new BaseRespVo<>();
        adBaseRespVo.setErrno(0);
        adBaseRespVo.setErrmsg("成功");
        adBaseRespVo.setData(ad);
        return adBaseRespVo;
    }

    /*删除广告*/
    @RequestMapping("admin/ad/delete")
    public BaseRespVo deleteAdList(@RequestBody Ad ad) {
        ArrayList<Ad> adList = new ArrayList<>();
        adList.add(ad);
        promoteService.deleteAdList(adList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /*  优惠券 */
    @RequestMapping("admin/coupon/list")
    public BaseRespVo queryCouponListByNameAndTypeAndStatus(PageWrapper pageWrapper) {

        PromoteUtils<Coupon> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Coupon> couponList = promoteService.queryCouponListByNameAndTypeAndStatus(pageWrapper.getName(), pageWrapper.getType(), pageWrapper.getStatus());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, couponList);

        return baseRespVo;
    }

    /* 添加优惠券*/
    @RequestMapping("admin/coupon/create")
    public BaseRespVo insertCouponList(@RequestBody Coupon2 coupon2) {
        BaseRespVo<Coupon2> CouponBaseRespVo = new BaseRespVo<>();
        List<Coupon2> coupon2list = new ArrayList<>();
        coupon2list.add(coupon2);
        /* 开启了usegenerateKey，他会自动返回当前对象的Id */
        promoteService.insertCoupon2List(coupon2list);
        CouponBaseRespVo.setData(coupon2);
        CouponBaseRespVo.setErrno(0);
        CouponBaseRespVo.setErrmsg("成功");

        return CouponBaseRespVo;
    }

    /*  更新优惠券*/
    //http://192.168.2.100:8081/Couponmin/Coupon/update
    @RequestMapping("admin/coupon/update")
    public BaseRespVo updateCoupon(@RequestBody Coupon coupon) {
        List<Coupon> couponList = new ArrayList<>();
        couponList.add(coupon);
        promoteService.updateCouponList(couponList);
        BaseRespVo<Coupon> CouponBaseRespVo = new BaseRespVo<>();
        CouponBaseRespVo.setErrno(0);
        CouponBaseRespVo.setErrmsg("成功");
        CouponBaseRespVo.setData(coupon);
        return CouponBaseRespVo;
    }

    /*删除优惠券*/
    @RequestMapping("admin/coupon/delete")
    public BaseRespVo deleteCouponList(@RequestBody Coupon coupon) {
        List<Coupon> couponList = new ArrayList<>();
        couponList.add(coupon);
        promoteService.deleteCouponList(couponList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /*  专题管理  */
    /*admin/topic/list title=sfdf&subtitle=dasd*/
    @RequestMapping("admin/topic/list")
    public BaseRespVo queryTopicListByTitleAndSubtitle(PageWrapper pageWrapper) {
        PromoteUtils<Topic> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Topic> topicList = promoteService.queryTopicListByTitleAndSubtitle(pageWrapper.getTitle(), pageWrapper.getSubtitle());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, topicList);

        return baseRespVo;

    }

    /* 添加专题*/
    @RequestMapping("admin/topic/create")
    public BaseRespVo insertTopicList(@RequestBody Topic topic) {
        BaseRespVo<Topic> topicBaseRespVo = new BaseRespVo<>();
        List<Topic> topiclist = new ArrayList<>();
        topiclist.add(topic);
        /* 开启了usegenerateKey，他会自动返回当前对象的Id */
        promoteService.insertTopicList(topiclist);
        topicBaseRespVo.setData(topic);
        topicBaseRespVo.setErrno(0);
        topicBaseRespVo.setErrmsg("成功");

        return topicBaseRespVo;
    }

    /*  更新专题*/
    //http://192.168.2.100:8081/topicmin/topic/update
    @RequestMapping("admin/topic/update")
    public BaseRespVo updatetopic(@RequestBody Topic topic) {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        promoteService.updateTopicList(topicList);
        BaseRespVo<Topic> topicBaseRespVo = new BaseRespVo<>();
        topicBaseRespVo.setErrno(0);
        topicBaseRespVo.setErrmsg("成功");
        topicBaseRespVo.setData(topic);
        return topicBaseRespVo;
    }

    /*删除专题*/
    @RequestMapping("admin/topic/delete")
    public BaseRespVo deletetopicList(@RequestBody Topic topic) {
        List<Topic> topicList = new ArrayList<>();
        topicList.add(topic);
        promoteService.deleteTopicList(topicList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /*  团购规则 */
//      admin/groupon/list?goodsId=
    @RequestMapping("admin/groupon/list")
    public BaseRespVo queryGrouponRulesListByGoodsId(PageWrapper pageWrapper) {
        PromoteUtils<Groupon_rules> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Groupon_rules> groupon_rulesList = promoteService.queryGrouponRulesListByGoodsId(pageWrapper.getGoodsId());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, groupon_rulesList);

        return baseRespVo;

    }

    /* 添加团购规则*/
    @RequestMapping("admin/groupon/create")
    public BaseRespVo insertGrouponList(@RequestBody Groupon groupon) {
        BaseRespVo<Groupon> grouponBaseRespVo = new BaseRespVo<>();
        List<Groupon> grouponlist = new ArrayList<>();
        grouponlist.add(groupon);
        /* 开启了usegenerateKey，他会自动返回当前对象的Id */
        promoteService.insertGrouponRuleList(grouponlist);
        grouponBaseRespVo.setData(groupon);
        grouponBaseRespVo.setErrno(0);
        grouponBaseRespVo.setErrmsg("成功");

        return grouponBaseRespVo;
    }

    /*  更新团购规则*/
    //http://192.168.2.100:8081/grouponmin/groupon/update
    @RequestMapping("admin/groupon/update")
    public BaseRespVo updateGroupon(@RequestBody Groupon_rules groupon_rules) {
        ArrayList<Groupon_rules> grouponList = new ArrayList<>();
        grouponList.add(groupon_rules);
        promoteService.updateGrouponRuleList(grouponList);
        BaseRespVo<Groupon_rules> grouponBaseRespVo = new BaseRespVo<>();
        grouponBaseRespVo.setErrno(0);
        grouponBaseRespVo.setErrmsg("成功");
        grouponBaseRespVo.setData(groupon_rules);
        return grouponBaseRespVo;
    }

    /*删除团购规则*/
    @RequestMapping("admin/groupon/delete")
    public BaseRespVo deletegrouponList(@RequestBody Groupon groupon) {
        List<Groupon> grouponList = new ArrayList<>();
        grouponList.add(groupon);
        promoteService.deleteGrouponRuleList(grouponList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    /*团购活动*/
//     admin/groupon/listRecord?page=1&limit=20&sort=add_time&order=desc  grouponrules
    @RequestMapping("admin/groupon/listRecord")
    public BaseRespVo queryGrouponListRecordByGoodsId(PageWrapper pageWrapper) {
        PromoteUtils<Groupon> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Groupon> grouponList = promoteService.queryGrouponListRecordByGoodsId(pageWrapper.getGoodsId());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, grouponList);

        return baseRespVo;

    }

    /* *//* 添加团购活动*//*
    @RequestMapping("admin/groupon/create")
    public BaseRespVo insertgrouponListByNameAndContent(@RequestBody groupon groupon) {
        BaseRespVo<groupon> grouponBaseRespVo = new BaseRespVo<>();
        ArrayList<groupon> grouponlist = new ArrayList<>();
        grouponlist.groupond(groupon);
        *//* 开启了usegenerateKey，他会自动返回当前对象的Id *//*
        promoteService.insertgrouponList(grouponlist);
        grouponBaseRespVo.setData(groupon);
        grouponBaseRespVo.setErrno(0);
        grouponBaseRespVo.setErrmsg("成功");

        return grouponBaseRespVo;
    }

    *//*  更新团购活动*//*
    //http://192.168.2.100:8081/grouponmin/groupon/update
    @RequestMapping("grouponmin/groupon/update")
    public BaseRespVo updategroupon(groupon groupon){
        ArrayList<groupon> grouponList = new ArrayList<>();
        promoteService.updategrouponList(grouponList);
        BaseRespVo<groupon> grouponBaseRespVo = new BaseRespVo<>();
        grouponBaseRespVo.setErrno(0);
        grouponBaseRespVo.setErrmsg("成功");
        grouponBaseRespVo.setData(groupon);
        return  grouponBaseRespVo;
    }
    *//*删除团购活动*//*
    @RequestMapping("grouponmin/groupon/delete")
    public BaseRespVo deletegrouponList(groupon groupon){
        ArrayList<groupon> grouponList = new ArrayList<>();
        promoteService.deletegrouponList(grouponList);
        BaseRespVo baseRespVo = new BaseRespVo();
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }*/
}
