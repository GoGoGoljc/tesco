package com.zpark.browse.controller;

import com.netflix.client.http.HttpRequest;
import com.zpark.browse.domian.Browse;
import com.zpark.browse.service.BrowseService;
import com.zpark.browse.util.CheckBrowseUtil;
import com.zpark.browse.util.ResultObject;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/browse")
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    /**
     * 添加商品浏览记录
     */

    @PostMapping("/addBrowseInfo")
    public ResultObject addBrowseInfo(@RequestParam("goodsName") String goodsName,
                                      @RequestParam("goodsImage") String goodsImage,
                                      @RequestParam("goodsId") Integer goodsId,
                                      @RequestParam("userId") Integer userId){
        try {
            System.out.println("调用了browse服务的addBrowseInfo");
            Browse browse = new Browse();
            browse.setGoodsId(goodsId);
            browse.setGoodsName(goodsName);
            browse.setGoodsImage(goodsImage);
            browse.setBrowseTime(new Date());
            browse.setUserId(userId);

            Boolean aBoolean = CheckBrowseUtil.checkBrowseAttr(browse);
            if(!aBoolean){
                return new ResultObject(400,"服务器或网络异常",null);
            }
            Integer i=browseService.addBrowseInfo(browse);
            if(i>0){
                return new ResultObject(200,"添加成功",null);
            }
            return new ResultObject(400,"添加失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    /*@PostMapping("/addBrowseInfo")
    public ResultObject addBrowseInfo(Map browseInfo){
        // public ResultObject addBrowseInfo(Browse browseInfo){
        System.out.println("接受到的参数是："+browseInfo);
        try {
            if(StringUtils.isEmpty(browseInfo)){
                return new ResultObject(400,"请求发生了异常",null);
            }

            Object goodsImage = browseInfo.get("goodsImage");
            Object goodsName=browseInfo.get("goodsName");
            Object goodsId=browseInfo.get("goodsId");
            Browse browse = new Browse();
            browse.setBrowseTime(new Date());
            browse.setGoodsImage((String) goodsImage);
            browse.setGoodsName((String) goodsName);
            browse.setGoodsId((Integer) goodsId);

            Integer i=browseService.addBrowseInfo(browse);
            if(i>0){
                return new ResultObject(200,"添加成功",null);
            }
            return new ResultObject(400,"添加失败",null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
*/

    /**
     * 根据用户id查询浏览记录
     */
    @GetMapping("/findBrowsebyUserId")
    public ResultObject findBrowsebyUserId(@RequestParam("user_id") Integer userId){
        try {
            Boolean aBoolean = CheckBrowseUtil.checkIntNum(userId);
            if(!aBoolean){
                return new ResultObject(400,"网络异常或者服务异常",null);
            }

            List<Browse> browseList= browseService.findBrowsebyUserId(userId);
            if(browseList!=null && browseList.size()>0){
                return new ResultObject(200,"查询成功",browseList);
            }
            return new ResultObject(400,"暂时没有记录哦",browseList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

/**
 * 根据用户id查询浏览记录 使用token获取用户id
 */
@GetMapping("/findBrowsebyToken")
public ResultObject findBrowsebyToken(HttpServletRequest request){
    try {
        String token = request.getHeader("token");
        Integer userId= (Integer) redisTemplate.opsForHash().get("userToken:"+token,"userId");
        System.out.println("-----------"+token+"+===="+userId);
        if(!CheckBrowseUtil.checkIntNum(userId)){
            //用户id为空
            System.out.println("=======");
            return new ResultObject(400,"你还没有登录，请登录",null);
        }

        List<Browse> browseList= browseService.findBrowsebyUserId(userId);
        if(browseList!=null && browseList.size()>0){
            return new ResultObject(200,"查询成功",browseList);
        }
        return new ResultObject(400,"暂时没有记录哦",browseList);
    } catch (Exception e) {
        e.printStackTrace();
        throw new RuntimeException();
    }

}
}
