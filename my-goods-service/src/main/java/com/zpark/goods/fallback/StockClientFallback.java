package com.zpark.goods.fallback;

import com.zpark.goods.client.StockClient;
import com.zpark.goods.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class StockClientFallback implements StockClient {


    @Resource
    private JavaMailSender javaMailSender;

    @Override
    public Map addStock(Integer stockNum, Integer goodsId, double stockPrice) {
        //给维护人员发送邮件，叫他赶紧回来
        EmailUtil.sendMessage("紧急通知","商品服务调用出现问题啦，请尽快回来解决bug","1316886229@qq.com",javaMailSender);
          Map data= new HashMap<>();
          data.put("fallbackInfo","服务器繁忙请重试");
         return data;
    }

    @Override
    public String updateStockByGoodsId(Integer goodsId, Integer goodsNum) {
        //给维护人员发送邮件，叫他赶紧回来
        EmailUtil.sendMessage("紧急通知","商品服务调用出现问题啦，请尽快回来解决bug","1316886229@qq.com",javaMailSender);
        return null;
    }

    @Override
    public String deleteStockByGoodsId(Integer goodsId) {
        EmailUtil.sendMessage("紧急通知","商品服务调用出现问题啦，请尽快回来解决bug","1316886229@qq.com",javaMailSender);
        return null;
    }
}
