package com.cskaoyan.util.uploadPic;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Storage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

public class AliyunOssUtils {
    /*图片存在云端*/
    public static BaseRespVo uploadFile(MultipartFile file) throws IOException {

        String accessKeyId = "LTAI4Fr5gfYhcVjLMqeRGbuT";
        String accessKeySecret = "IrkcHu6dZyrjPZRushgO76P5392HJ1";
        String bucket = "cskaoyan";
        String endPoint = "oss-cn-beijing.aliyuncs.com";

        InputStream inputStream = file.getInputStream();

        Storage storage = new Storage();
        BaseRespVo<Storage> baseRespVo = new BaseRespVo<>();
        Date date = new Date();
        String originalFilename = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-", "");
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        String filename = prefix + "." + suffix;

        OSSClient ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
        PutObjectResult putObjectResult = ossClient.putObject(bucket, filename, inputStream);

        storage.setKey(filename);
        storage.setName(originalFilename);
        storage.setType("image/jpeg");
        storage.setSize((int) file.getSize());
        storage.setUrl("https://cskaoyan.oss-cn-beijing.aliyuncs.com/" + filename);
        storage.setAddTime(date);
        storage.setUpdateTime(date);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(storage);
        return baseRespVo;
    }
}
