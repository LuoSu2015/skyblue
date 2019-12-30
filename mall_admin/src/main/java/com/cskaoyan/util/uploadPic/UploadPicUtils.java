package com.cskaoyan.util.uploadPic;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Storage;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;


public class UploadPicUtils {

  /*  @Value("${prop.upload-folder}")
    static String filePath;

    @Value("${prop.url}")
    static String pathUrl;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:SS", timezone = "GMT+8")
    static Date date;*/

    /*  图片上传 */
//    @PostMapping("admin/storage/create")
    public static BaseRespVo<Storage> fileUpload(MultipartFile file) throws IOException {

        Storage storage = new Storage();
        BaseRespVo<Storage> baseRespVo = new BaseRespVo<>();
         Date date = new Date();
        //获取文件后缀
        String originalFilename = file.getOriginalFilename();
        /*取. 后面的字符*/
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
        String savePath =  "D://pic//";//filePath
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
        storage.setUrl("http://localhost:8081/" + filename);
        storage.setAddTime(date);
        storage.setUpdateTime(date);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(storage);

        return baseRespVo;
    }
}
