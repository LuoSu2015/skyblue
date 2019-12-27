package com.cskaoyan.controller.promote;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.propate.PageWrapper;
import com.cskaoyan.service.promote.PromoteService;
import com.cskaoyan.util.PromoteUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
public class PromoteController {

    @Autowired
    PromoteService promoteService;

    @Value("${prop.upload-folder}")
    String filePath;

    @Value("${prop.url}")
    String pathUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS",timezone = "GMT+8")
    Date date;

    /*  图片上传 */
    @PostMapping("admin/storage/create")
    public BaseRespVo<Storage> fileUpload(MultipartFile file, HttpServletRequest request) throws IOException {

        Storage storage = new Storage();
        BaseRespVo<Storage> baseRespVo = new BaseRespVo<>();
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        /*取. 后面的字符*/
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        String savePath = filePath;
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        //将文件保存指定目录
        file.transferTo(new File(savePath + filename));
        storage.setKey(filename);
        storage.setName(originalFilename);
        storage.setType("image/jpeg");
        storage.setSize((int) file.getSize());
        storage.setUrl(pathUrl+filename);
        storage.setAddTime(date);
        storage.setUpdateTime(null);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(storage);

        return baseRespVo;
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
