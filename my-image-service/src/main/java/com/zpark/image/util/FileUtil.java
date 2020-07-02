package com.zpark.image.util;

public class FileUtil {

    public static String getFileExtension(String fileName){
        if(fileName==null && "".equals(fileName.trim())){
            return null;
        }
        int index=fileName.lastIndexOf(".");
        if(index==-1){
            return null;
        }

        //获取扩展名
        String extension = fileName.substring(index);
        return extension;

    }


    //上传文件
//    public static String uploadFile(String filOriginalName){
//
//    }
}
