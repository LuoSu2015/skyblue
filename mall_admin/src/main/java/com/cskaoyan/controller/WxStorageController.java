package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.service.WxStorageService;
import com.cskaoyan.util.uploadPic.WxPicUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class WxStorageController {

    @RequestMapping("wx/storage/upload")
    public BaseRespVo wxUploadPic(MultipartFile file) throws IOException {
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        Storage data = WxPicUploadUtils.fileUpload(file);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
