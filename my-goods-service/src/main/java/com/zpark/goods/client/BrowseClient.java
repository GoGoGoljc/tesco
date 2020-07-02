package com.zpark.goods.client;

import com.zpark.goods.domain.Goods;
import com.zpark.goods.fallback.BrowseClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "my-browse-service",fallback = BrowseClientFallback.class)
public interface BrowseClient {

   /* @PostMapping("/browse/addBrowseInfo")
    String addBrowseInfo(Goods goods,Integer userId);*/

    @PostMapping("/browse/addBrowseInfo")
    Map addBrowseInfo(@RequestParam("goodsName") String goodsName,
                      @RequestParam("goodsImage") String goodsImage,
                      @RequestParam("goodsId") Integer goodsId,
                      @RequestParam("userId") Integer userId);


}
