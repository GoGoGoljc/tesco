package com.zkdj.orderitem.util;//package com.zkdj.orderitem.util;
//
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
///**
// * @author SJXY
// */
//public class EmailUtil {
//    public static void  sendMessage(String title, String content, String to, JavaMailSender sender){
//        //开启分线程发送邮件
//        new Thread(()->{
//            SimpleMailMessage msg = new SimpleMailMessage();
//            msg.setFrom("mgr15878571440@163.com");
//            msg.setTo(to);
//            msg.setSubject(title);
//            msg.setText(content);
//            sender.send(msg);
//            System.out.println("发送邮件！");
//        }).start();
//    }
//
//}
