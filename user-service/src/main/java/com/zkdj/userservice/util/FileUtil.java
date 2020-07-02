package com.zkdj.userservice.util;

import java.util.Date;

public class FileUtil {


    public static String getFileName() {
        Date date = new Date();
        long time = date.getTime();
        return String.valueOf(time);
    }

    public static String getExtension(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        if(originalFilename==null || "".equals(originalFilename.trim())){
            return null;
        }
        //没有找到扩展名
        if(index==-1){
            return null;
        }
        //获得扩展名
        String extension = originalFilename.substring(index);
        return extension;

    }
}
