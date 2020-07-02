package com.zkdj.admins.util;

/**
 * @author SJXY
 */
public class VerifyUtil {
    public  static String generateVerify(int len){
        String letters ="23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
        String verify ="";
        for (int i=0;i<len;i++){
            verify +=letters.charAt((int)(Math.random()*letters.length()));
        }
        return verify;
    }

    public static void main(String[] args) {

        System.out.println(generateVerify(6));
    }
}


