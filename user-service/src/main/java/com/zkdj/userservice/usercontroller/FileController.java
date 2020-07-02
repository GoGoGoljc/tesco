package com.zkdj.userservice.usercontroller;

import com.zkdj.userservice.util.FileUtil;
import com.zkdj.userservice.util.ResultObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.swing.StringUIClientPropertyKey;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/file")
@RestController
public class FileController {

    /**
     * 文件上传
     */
    @PostMapping("/uploadUserImage")
    public ResultObject uploadFile(@RequestParam("userImage") MultipartFile multipartFile){
        try {
            String filePath="F:\\user-image\\";
            //获取到图片的新的名称
            String fileName= FileUtil.getFileName();
            //获取图片的后缀名称
            String extension=FileUtil.getExtension(multipartFile.getOriginalFilename());

            File file=new File(filePath+fileName+extension);
            if(!file.exists()){
                file.mkdirs();
            }
            multipartFile.transferTo(file);
            Map<String,String> map=new HashMap<>();
            map.put("fileName",fileName);
            map.put("filePath",filePath);
            map.put("extension",extension);
           return new ResultObject(400,"上传成功",map);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传失败
        return new ResultObject(400,"上传失败",null);
    }


    /**
     * 删除文件
     * @param userFileName
     * @return
     */
    @PostMapping("/deleteFile")
    public ResultObject deleteFile(@RequestParam("userFileName") String userFileName){
        try {
            if(StringUtils.isEmpty(userFileName)){
                return new ResultObject(400,"文件名不能为空",null);
            }
            File file = new File(userFileName);
            if(file.exists()){
                boolean delete = file.delete();
                if(delete){
                    return new ResultObject(200,"删除成功",null);
                }

            }
            return new ResultObject(200,"删除失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
