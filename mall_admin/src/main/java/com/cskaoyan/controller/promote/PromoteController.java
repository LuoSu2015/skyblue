package com.cskaoyan.controller.promote;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.bean.propate.PageWrapper;
import com.cskaoyan.service.promote.PromoteService;
import com.cskaoyan.util.PromoteUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class PromoteController {

    @Autowired
    PromoteService promoteService;

    /*  图片上传 */
    @PostMapping("admin/storage/create")
    public BaseRespVo<Storage> fileUpload(@RequestParam(name = "file", required = false) MultipartFile file, HttpServletRequest request) throws IOException {

        Storage storage = new Storage();
        BaseRespVo<Storage> baseRespVo = new BaseRespVo<>();
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        String savePath = "D://pic";
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
        storage.setUrl(null);
        storage.setAddTime(null);
        storage.setUpdateTime(null);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(storage);

        return baseRespVo;
    }


    /* 广告展示 */
    @RequestMapping("admin/ad/list")
    public BaseRespVo<List<Ad>> queryAdListByNameAnd(PageWrapper pageWrapper) {

        PromoteUtils<Ad> adPromoteUtils = new PromoteUtils<>();
        BaseRespVo baseRespVo = PromoteUtils.preSetBaseRespVo(pageWrapper);
        List<Ad> ads = promoteService.queryAdListByName(pageWrapper.getName());
         baseRespVo = adPromoteUtils.postSetBaseRespVo(baseRespVo, ads);

        return baseRespVo;
    }


    /*
    *
    *
    * http://192.168.2.100:8081/admin/coupon/list?page=1&limit=20&sort=add_time&order=desc
    * */

}
