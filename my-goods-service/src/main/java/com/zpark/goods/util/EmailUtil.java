package com.zpark.goods.util;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailUtil {
    public static void sendMessage(String title, String content, String to, JavaMailSender javaMailSender){
        new Thread(()->{
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setFrom("CC1316886229@163.com");
            simpleMailMessage.setFrom("mgr15878571440@163.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(title);
            simpleMailMessage.setText(content);
            javaMailSender.send(simpleMailMessage);
        }).start();
    }
}
