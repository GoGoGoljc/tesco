package com.zkdj.payment.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AlipayConfig {
    //    收款账号的APP_ID（沙箱环境）
    public static String APP_ID = "2016102100734305";
    //  PKCS8格式RSA2私钥  商户私钥
    public static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCl0iWe1tevGGHs6iZTs/bjc7OFNuVnKBqaNmZPspNuFAfRGx9kyLwtcTo+aZCM2VLsjhe5saYWo1oUAHGe9nbplXft1VHSmTHSv7mAi2EWSiktu4rLYktT2Mct0c+4O2LvJjvdtrfXzwv5GSDFwLJ5NEh0CZbY3aJ4dIX8WsHYOn+mr7AkiJIyT0cRHwxCxEWmGcD6x66XEb1h5qf7rVUCQ5vPOJSGLyAKkpDBdeoYMkXRVHo+ql9SJikamBJYlgALQApsIC/eUsmOEq7bR3dVHKeCcm8lS7cWG/KTfdlpIHptc0EIID1O7YXts520AVsLV4daBir7h/I81qDb9bAbAgMBAAECggEAP6Y+ko3dFHTDgfEO7SozJnOPAR9lRwd5zp/6/bSKKsK9jWJf1ovGz5qaxMI4nhf9YJfaO4kZO69QMEVLV707P8YD1uL/GVt8aGz3g4P8rFWjIM7QI+mDCJg5PDlBBnKlMy7149DzGH99FQy0K7Qfza5n8UKNCIlqh9oSZB9iMOEEtGcqjc41Nq8VcxyoRkQ5IFeKqvFGOyefpPnOLyA9RE9LwcErwnEDOzgELAI6kiXKsd8XMaBc+hGTqyfUsDNtyJJZ/Ma636xlLRY5yhrRhYAyKXPeBF4Dol+FKZnKdDGqrCdJDjKGfkhH/udlIjETRSfc7/dXCe43hXwy0u/jAQKBgQDhj6HU4tTpc/QnYOGenc0o8itBIOkGXciOs0CqESixHWypkgRGwz4BHxzEGlOGLJsmYO13hTXGjNapy+mrdZWZdoQ8PUHrhwySfxKOMlUY7ybMyk7nm6pN2dX6oU294iUFYP80xHKBE5Pu3tMHCLuDoTL+RzgRESKnGNo3I3a14QKBgQC8MrHfHSPa1/T9t6CDT5mJBAjDzgZfza5ZVgRGhq6vfVGNUXdpm7vxFRwryFN0mxxiusEKnTXCy5gO84uuaCZRiTZixNJiZe+Vhaogkz6IkQKEck6u5d6225rtOp1SN0dYQECK4rijMH5yTxOsl+o89BA+GEHB0J2yfwHntuvtewKBgHxHFCZkmGTMU5DCy20GivxtebGXvWyZjaA6WX2amK4FhVJktmB6wJYDm2jdDHoX56ActZot1jRAcyH4zRWAYt28NB2wrrpjnWS2CPY5+zMSyXD6YsEvvoTIaiJ+b4QYrsco61QajPBbZh2tvd3ZTN+4mI0LlnOo2kvw4IKS0pxhAoGAenUyME+xyPDExWL62vasnO83FNG2bBhV3mOOHKX+fMo6io37+YgQXuqxjfun8jQL4GJMwi9RkBMSOohW14GFjYCPFu2jR8u0fcKf1Np60jopPS1z7XI93RZAVSTSwXCex9nnJXgpVGZ1oMO7tY6UKF1XRFBcWVT5h/AEuVXZZokCgYAZEnaKhfW8PUqvzwFKakyhp0NIhY6IPiK7krcM7GyLCSHhrd7hYg8Vkr0Y3YCoXkM7ECsCN/tpBppkUf90Jp9z1eroMnclJeotuvLQC46DPnsYGbZM44pDLJqhkPi9fM+8MwQjv3uAucY8vHDUpBBBYFevInBGd+V4y3+Ex5Vv5g==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String PAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAi/r/xrZMjsDDImDETBh+dNsXkPukzgDr8pCDehPIxOcIbBpuuALD0txBVzupe41unLEXCN/N1FqVZ3qPCTb25/DiaAZtERcvtXfMdMXi23LYn8tS+cPpfokwyjH3eOlVdUqR0XV1S3VSk5oAXircWEA2tWeKVW2vwETQ5H/FLx+QVAIJHZx+olB5vuGy/7XUzvAa2nYuwwL/x8D1VOq+/7KhQ5U7eRsOSuf5Sj98u1ajYNWA9ExLE+F6gzjPQAK1nw73W6oy+w7t57pp7hLjp+LZAsW4W+IO+0IndOqHWurip6QOCl12ijYKvTpYuAyIMZZa20jLv5KazA72FCHyUwIDAQAB";
    //服务器异步通知页面路径
    public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";
    // 页面跳转同步通知页面路径 (其实就是支付成功后返回的页面)
    public static String return_url = "http://172.18.84.43:8848/moban3833/pay-success.html";
    //签名方式
    public static String sign_type = "RSA2";
    //字符编码格式
    public static String CHARSET = "utf-8";
    // 支付宝网关，这是沙箱的网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
    // 支付宝网关
    public static String log_path = "C:\\";

}
