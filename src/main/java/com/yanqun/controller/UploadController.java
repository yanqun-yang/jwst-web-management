package com.yanqun.controller;

import com.yanqun.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@Slf4j
@RestController
public class UploadController {

    @PostMapping("/upload")
    public Result upload(String username, Integer age, ArrayList<MultipartFile> images) throws Exception {
        if (images == null || images.get(0).getOriginalFilename().isEmpty()) {
            log.info("文件上传: {}, {}, images-没该参数或有该参数但没上传文件", username, age);
        } else {
            log.info("文件上传: {}, {}, {}", username, age, images);
            for (MultipartFile image : images) {
                //获取原始文件名 - 1.jpg 123.0.0.jpg
                String originalFilename = image.getOriginalFilename();
                //构造唯一的文件名（不能重复） - uuid（通用唯一识别码） cf96fb65-f8c3-4060-8fdc-43608783e3a1
                int index = originalFilename.lastIndexOf(".");
                String extname = originalFilename.substring(index);
                String newFileName = UUID.randomUUID().toString() + extname;
                log.info("新的文件名：{}", newFileName);

                //将文件存储在服务器的磁盘目录中 D:\Java进修之路\code\jwst-web-management\images
                image.transferTo(new File("D:\\Java进修之路\\code\\jwst-web-management\\images\\" + newFileName));
            }
        }

        return Result.success();
    }
}
