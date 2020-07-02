package com.zkdj.orderitem.controller;

import com.zkdj.orderitem.domain.OrderItem;
import com.zkdj.orderitem.service.OrderItemService;
import com.zkdj.orderitem.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author SJXY
 */
@RestController
@RequestMapping("/api/v1/orderitem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;
    @PostMapping("/selectByAll")
    public ResultObject selectByAll(){
     ResultObject result=new ResultObject<>();
     result.setCode(200);
     result.setMessage("查询所有信息成功");
     result.setResultData(orderItemService.selectByAll());
        return  result;
    }
    @GetMapping("/orderNumber")
    public ResultObject orderNumber(@RequestParam String orderNumber){
        System.out.println(orderNumber);
        ResultObject result =new ResultObject<>();
        OrderItem orderItem=orderItemService.selectByNum(orderNumber);
        System.out.println(orderNumber);
            if (orderItem!=null){
                result.setCode(200);
                result.setMessage("查询了id为"+orderNumber+"的数据");
                result.setResultData(orderItem);
                return result;
            } else {
                result.setCode(400);
                result.setMessage("查询失败");
                return result;
            }
    }
    @GetMapping("/selectById")
    public ResultObject selectById(@RequestParam Integer orderItemId){
        System.out.println(orderItemId);
        ResultObject result =new ResultObject<>();
        OrderItem orderItem=orderItemService.selectById(orderItemId);
        System.out.println(orderItemId);
        if (orderItem!=null){
            result.setCode(200);
            result.setMessage("查询了id为"+orderItemId+"的数据");
            result.setResultData(orderItem);
            return result;
        } else {
            result.setCode(400);
            result.setMessage("查询失败");
            return result;
        }
    }
    @PostMapping("/update")
    public ResultObject update(OrderItem orderItem){
        ResultObject result =new ResultObject<>();
        if (!StringUtils.isEmpty(orderItem.getOrderNumber())) {
            try {

                Integer m = orderItemService.update(orderItem);
                if (m > 0) {
                    result.setCode(200);
                    result.setMessage("更新了id为" + orderItem.getOrderItemId() + "的数据");
                    result.setResultData(orderItem);
                    return result;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        result.setCode(400);
        result.setMessage("数据更新失败");
        return result;
    }
    @PostMapping("/insert")
    public  ResultObject insert(OrderItem orderItem){
        ResultObject result=new ResultObject<>();
//        String uuid = UUID.randomUUID().toString().replace("-", "");
//        String substring = uuid.substring(0, 5);
//        orderItem.setOrderNumber(substring);

        System.out.println(orderItem.getOrderNumber()+"-------------------------");
        try {
            if(!StringUtils.isEmpty(orderItem.getOrderNumber())) {
                Integer m =orderItemService.insert(orderItem);
                if (m > 0) {
                    result.setCode(200);
                    System.out.println(orderItem.getOrderNumber());
                    result.setMessage("插入了的数据");
                    result.setResultData(orderItem);
                    return result;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("插入数据失败");
        return  result;
    }
    @PostMapping("/deleteById")
    public  ResultObject deleteById(Integer orderItemId){
        ResultObject result =new ResultObject<>();
        Integer m =orderItemService.deleteById(orderItemId);
        try {
        if (m>0){
            result.setCode(200);
            result.setMessage("数据删除成功");
            result.setResultData(null);
            return result;
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.setCode(400);
        result.setMessage("数据删除失败");
        return  result;
    }
    @PostMapping("/selectState")
    public ResultObject selectState(OrderItem orderItem){
         OrderItem orderItem1=orderItemService.selectByState(orderItem);
        System.out.println(orderItem1);
        System.out.println(orderItem1.getOrderState()+"----------------+++");
         try {
             if (orderItem1!=null){
                 return  new ResultObject(200,"查询成功",orderItem1.getOrderState());
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
        return new ResultObject(400,"查询失败",null);
    }

    @GetMapping("/insertOrderItem")
    public ResultObject insertOrderItem(OrderItem orderItem,@RequestParam("userId") Integer userId){
        System.out.println(orderItem);
        System.out.println(userId);
        if(StringUtils.isEmpty(orderItem.getGoodsId()+"")){
            return new ResultObject(400,"服务器错误",null);

        }
        Integer i=orderItemService.insertOrderItem(orderItem,userId);
        if(i>0){
            return new ResultObject(200,"插入成功",null);
        }
        return new ResultObject(400,"插入失败",null);
    }



}
