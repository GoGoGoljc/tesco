package com.zpark.collect.controller;

import com.github.pagehelper.PageInfo;
import com.zpark.collect.domain.Collect;
import com.zpark.collect.service.CollectService;
import com.zpark.collect.util.CheckUtil;
import com.zpark.collect.util.PageUtil;
import com.zpark.collect.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;

    /**
     * 根据用户id  查看我的收藏
     *
     */

    @GetMapping(value = "/findAll")
    public ResultObject findAllByUserId(@RequestParam("user_id") Integer userId, @RequestParam("pageNum") Integer pageNum){

        ResultObject result = new ResultObject();
        try {
            System.out.println("查询收藏");
            Boolean aBoolean = CheckUtil.checkIntNum(userId);
            if(!aBoolean.booleanValue()){
                return  new ResultObject(400,"抱歉，出故障了",null);
            }
            Boolean bBoolean = CheckUtil.checkIntNum(pageNum);
            if(!bBoolean.booleanValue()){
                return  new ResultObject(400,"抱歉，出故障了",null);
            }

            //多超页的情况
            Integer record=collectService.recordTotal(userId);
            int page=record/ PageUtil.collectPageSize;
            if(record%PageUtil.collectPageSize!=0){
                page++;
            }
            if(pageNum>page){
                return new ResultObject(400,"没有更多商品了",null);
            }

            PageInfo<Collect> collectList= collectService.findAllByUserId(userId,pageNum);
            List<Collect> list = collectList.getList();
            if(list!=null && list.size()>0){
                result.setCode(200);
                result.setMessage("查询收藏成功");
                result.setResultData(collectList);
                return result;
            }
            result.setCode(400);
            result.setMessage("查询收藏失败");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }

    }


    /**
     * insert 添加用户收藏
     */

    /**
      此处远程调用my-goods-service的findById方法的重试机制可以了 但熔断感觉有问题（哪错了呢）ok
     * 还要验证是否收藏
     * @param collect
     * @return
     */
    @PostMapping("/insert")
    public ResultObject addCollection(Collect collect){
       //collect 要验证 有useId collectGoodsId
        ResultObject result = new ResultObject();
        System.out.println("添加商品收藏传过来的参数"+collect);

        //验证收藏的属性字段
        //也可以验证用户的id是否存在  商品的id是否真的存在  防止出错（前台可弄）

      //  try {
            Boolean aBoolean = CheckUtil.checkIntNum(collect.getUserId());
            if(!aBoolean.booleanValue()){
                return  new ResultObject(400,"抱歉，出故障了",null);
            }
            Boolean bBoolean = CheckUtil.checkIntNum(collect.getCollectGoodsId());
            if(!bBoolean.booleanValue()){
                return  new ResultObject(400,"抱歉，出故障了",null);
            }

            //看是否已经收藏
            Collect isExist= collectService.findCollectByUserIdAndGoodsId(collect.getUserId(),collect.getCollectGoodsId());
            if(isExist!=null){
                System.out.println("you had collected");
                return  new ResultObject(400,"已经收藏了！",null);
            }

            System.out.println("ready to collect");
            Integer i=collectService.addCollection(collect);
            if(i>0){
                result.setCode(200);
                result.setMessage("收藏成功");
                return result;
            }
            result.setCode(400);
            result.setMessage("收藏失败");
            return result;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException();
//        }

    }


    /**
     * 可以写一个校验的工具类
     * 取消收藏
     */

    @PostMapping("/delete")
    public ResultObject delete(Collect collect){
        ResultObject<Object> result= new ResultObject<>();
        try {
            System.out.println("删除商品，传过来的数据"+collect);
        //先校验 用户id  收藏商品的id
           /* Boolean aBoolean = CheckUtil.checkIntNum(collect.getUserId());
            if(!aBoolean){
                System.out.println("sorry a");
                return  new ResultObject(400,"抱歉，出故障了",null);
            }

            Boolean bBoolean = CheckUtil.checkIntNum(collect.getCollectGoodsId());
            if(!bBoolean){
                System.out.println("sorry b");
                return  new ResultObject(400,"抱歉，出故障了",null);
            }*/
            //验证是否已经收藏
            Collect collect1=collectService.findCollectByUserIdAndGoodsId(collect.getUserId(),collect.getCollectGoodsId());
            if(collect1==null){
                System.out.println("you had,t collected");
                return new ResultObject(400,"你还没有收藏哦！",null);
            }
            //删除
            Integer i=collectService.cancelCollect(collect);
            if(i>0){
                result.setCode(200);
                result.setMessage("取消收藏成功");
                return result;
            }
            result.setCode(400);
            result.setMessage("取消收藏失败");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

    /**
     * 根据主键删除收藏的商品信息
     * @param collectId
     * @return
     */

    @PostMapping("/deleteByPrimaryKey")
    public ResultObject deleteByPrimaryKey(@RequestParam("collect_id") Integer collectId){
        ResultObject<Object> result= new ResultObject<>();
        try {
            //先校验
            Boolean aBoolean = CheckUtil.checkIntNum(collectId);
            if(!aBoolean){
                System.out.println("sorry c");
                return  new ResultObject(400,"抱歉，出故障了",null);
            }

            //万一给的collect_id 不在数据库表里面呢 （前台可弄）

            //验证是否已经收藏
            Collect collect1=collectService.findById(collectId);
            if(collect1==null){
                System.out.println("you had,t collected");
                return new ResultObject(400,"你还没有收藏哦！",null);
            }

            Integer i=collectService.deleteByPrimaryKey(collectId);
            if(i>0){
                result.setCode(200);
                result.setMessage("取消收藏成功");
                return result;
            }
            result.setCode(400);
            result.setMessage("取消收藏失败");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();
        }
    }

}
