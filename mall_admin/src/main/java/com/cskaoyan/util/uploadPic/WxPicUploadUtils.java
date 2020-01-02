package com.cskaoyan.util.uploadPic;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PutObjectResult;
import com.cskaoyan.bean.Storage;
import com.cskaoyan.mapper.StorageMapper;
import com.cskaoyan.service.WxStorageService;
import com.cskaoyan.service.WxStorageServiceImpl;
import com.cskaoyan.service.systemManagement.StorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.SortedMap;
import java.util.UUID;

@Component
public class WxPicUploadUtils {

    private static WxStorageService wxStorageService;
    @Autowired
    public void setWxStorageService(WxStorageService wxStorageService){
        this.wxStorageService = wxStorageService;
    }

    //上传文件并插入数据表中对应位置
    public static Storage fileUpload(MultipartFile file) throws IOException {
        String accessKeyIdF = "LTAI4Fe1LWMQxwKckGtk42A1";
        String accessKeySecretF = "0TAhB8YcB3CP4bmxmcJmGjkJTbbYww";
        String bucketF = "fangzhenhao";
        String endPointF = "oss-cn-hangzhou.aliyuncs.com";
        InputStream inputStream = file.getInputStream();
        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replaceAll("-","")
                        + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));

        OSSClient ossClient = new OSSClient(endPointF, accessKeyIdF, accessKeySecretF);
        PutObjectResult putObjectResult = ossClient.putObject(bucketF, fileName, inputStream);

//        ObjectMapper objectMapper = new ObjectMapper();
//        String s = objectMapper.writeValueAsString(putObjectResult);
//        System.out.println(s);

        //插入数据表中
        //storageMapper.insert(Storage sortage)
        Storage storage = new Storage();
        storage.setKey(fileName);
        storage.setName(originalFilename);
        storage.setType("image/jpeg");
        storage.setSize((int) file.getSize());
        storage.setUrl("https://" + bucketF + "." + endPointF + "/" + fileName);
        Storage data = wxStorageService.addWxPic(storage);
        String s = data.toString();
        return data;
    }
}
