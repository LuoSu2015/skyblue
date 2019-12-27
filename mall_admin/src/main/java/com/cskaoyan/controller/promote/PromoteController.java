package com.cskaoyan.controller.promote;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.propate.PageWrapper;
import com.cskaoyan.service.promote.PromoteService;
import com.cskaoyan.util.promoteUtils.PromoteUtils;
import com.cskaoyan.util.uploadPic.UploadPicUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    public BaseRespVo insertAdListByNameAndContent(@RequestBody Ad ad) {
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
    //http://192.168.2.100:8081/admin/ad/update
   /* @RequestMapping("admin/ad/update")
    public BaseRespVo*/





    /*  优惠券 */
    @RequestMapping("admin/coupon/list")
    public BaseRespVo queryCouponListByNameAndTypeAndStatus(PageWrapper pageWrapper) {

        PromoteUtils<Coupon> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Coupon> couponList = promoteService.queryCouponListByNameAndTypeAndStatus(pageWrapper.getName(), pageWrapper.getType(), pageWrapper.getStatus());
        baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, couponList);

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
}
