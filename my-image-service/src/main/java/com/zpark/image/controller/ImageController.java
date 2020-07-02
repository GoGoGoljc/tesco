package com.zpark.image.controller;

import com.zpark.image.domain.Image;
import com.zpark.image.service.ImageService;
import com.zpark.image.util.CheckUtil;
import com.zpark.image.util.ResultObject;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    private ImageService imageService;


    /**
     * 添加图片
     */

    @PostMapping("/addImage")
    public ResultObject addImage(Image image){

        try {
            if (!CheckUtil.checkIntNum(image.getGoodsId())){
                return new ResultObject(400,"信息有误",null);
            }

            if (StringUtils.isEmpty(image.getImageAddress())){
                return new ResultObject(400,"信息有误",null);
            }
            Integer i=imageService.addImage(image);
            if(i>0){
                return new ResultObject(200,"添加图片成功",null);

            }
            return new ResultObject(400,"添加图片失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /**
     * 删除图片  根据商品id
     */
    @PostMapping("/deleteByGoodsId")
    public ResultObject deleteByGoodsId(@RequestParam("goods_id") Integer goodsId){
        try {
            System.out.println("删除图片----");
            if(!CheckUtil.checkIntNum(goodsId)){
                return new ResultObject(400,"信息有误",null);
            }

            Integer i=imageService.deleteByGoodsId(goodsId);
            System.err.println("删除数量："+i);
            if(i>0){
                System.err.println("删除成功");
                return new ResultObject(200,"删除图片成功",null);
            }
            System.err.println("删除失败");
            return new ResultObject(400,"删除图片失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
