package com.zpark.image.controller;


import com.zpark.image.util.FileUtil;
import com.zpark.image.util.ResultObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/goods/file")
public class FileController {

    /**
     * 上传文件
     * @param multipartFile
     * @return
     */

    @GetMapping("/uploadFile")
    public ResultObject uploadFile(@RequestParam("file") MultipartFile multipartFile){
        try {
        if(StringUtils.isEmpty(multipartFile)){
            return new ResultObject(400,"文件名不能为空",null);
        }
        //路径+名称+后缀
       //上传的路径
        String uploadPath="F:\\abc\\";
        //文件名设置
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString().replace("-", "");
           // String fileName=multipartFile.getOriginalFilename();
        String fileExtension = FileUtil.getFileExtension(originalFilename);
        String realPath=uploadPath+fileName+fileExtension;
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        //上传图片
            multipartFile.transferTo(file);
            Map<String,String> map=new HashMap<>();
            map.put("fileName",fileName);
            map.put("filePath",realPath);
            map.put("extension",fileExtension);
            return new ResultObject(200,"上传成功",map);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ResultObject(400,"上传成功",null);
    }

    /**
     * 删除文件
     */

    @PostMapping("/deleteFile")
    public ResultObject deleteFile(@RequestParam String fileName){
        if(StringUtils.isEmpty(fileName)){
            return  new ResultObject(400,"文件名不能为空",null);
        }
        String uploadPath="F:\\abc\\";
        File file = new File(uploadPath + fileName);
        if(file.exists()){
            file.delete();
            return  new ResultObject(200,"删除图片成功",null);
        }
        return  new ResultObject(400,"删除图片失败",null);
    }
}
