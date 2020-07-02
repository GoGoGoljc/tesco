package com.zkdj.order.controller;

import com.github.pagehelper.PageInfo;
import com.zkdj.order.domain.Order;
import com.zkdj.order.service.OrderService;
import com.zkdj.order.util.CheckUtil;
import com.zkdj.order.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SJXY
 */
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    private Order order;

//    查询所有订单的信息

    @GetMapping("/selectByAll")
    public ResultObject selectByAll() {
        return new ResultObject(200,"查询所有信息成功",orderService.selectByAll());
    }
//根据Id查询订单信息
@GetMapping("/selectById")
public  ResultObject  selectById(Integer orderId){
        if (orderId!=null){
            Integer m= orderService.selectById(orderId);
            if (m>0){
                return  new ResultObject(200,"查询成功",orderId);
            }
        }

        return new ResultObject(400,"查询失败",null);
}
    @PostMapping("/selectByNum")
    public ResultObject selectByNum(@RequestParam("orderNumber") String orderNumber) {

        if (orderNumber!=null) {
            Order order1 = orderService.selectNum(orderNumber);

            if (order1!=null) {
                System.out.println(orderNumber+"+++++++");
                System.out.println(order1+"------");
                return new ResultObject(200,"查询成功",order1);

            } else {
                return  new ResultObject(400,"查询失败",null);
            }
        }
        return  new ResultObject(400,"查询失败",null);
    }


    @PostMapping("/insert")
    public ResultObject insert(Order order){
        System.out.println("=================");
        if (!StringUtils.isEmpty(order.getOrderNumber())) {
            try {
                int m = orderService.insert(order);
                if (m > 0) {
                    System.out.println(order);
                    return new ResultObject(200,"添加成功",order);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResultObject(400,"添加失败",null);
    }
//    更新订单数据

  @PostMapping("/update")
    public ResultObject update(Order order){
        Order order1 =orderService.selectNum(order.getOrderNumber());
      System.out.println(order);
      if (!StringUtils.isEmpty(order.getOrderNumber())){
   try {
       int m =orderService.update(order);
       if (m>0){
           return new ResultObject(200,"更新成功",order);
       }

   } catch (Exception e) {
       e.printStackTrace();
   }
   }

      return new ResultObject(400,"更新失败",null);
  }
    @GetMapping("/update1")
    public ResultObject update1(Order order){
        Order order1 =orderService.selectNum(order.getOrderNumber());
        System.out.println(order);
        if (!StringUtils.isEmpty(order.getOrderNumber())){
            try {
                int m =orderService.update(order);
                if (m>0){
                    return new ResultObject(200,"更新成功",order);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ResultObject(400,"更新失败",null);
    }
//  根据订单id删除订单

  @GetMapping("/deleteByNum1")
    public ResultObject deleteByNum1(String orderNumber){

        try {
            int m =orderService.deleteByNum(orderNumber);
            if (m>0){
                return  new ResultObject(200,"删除了orderNumber为"+orderNumber+"的数据",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return new ResultObject(400,"删除失败没有这个orderNumber的数据",null);
  }
    @PostMapping("/deleteByNum")
    public ResultObject deleteByNum(String orderNumber){

        try {
            int m =orderService.deleteByNum(orderNumber);
            if (m>0){
                return  new ResultObject(200,"删除了orderNumber为"+orderNumber+"的数据",null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObject(400,"删除失败没有这个orderNumber的数据",null);
    }
//  订单取消
//
//@PostMapping("/CancelOrder")
//    public ResultObject CancelOrder(Integer orderId,String ){
//
//        Integer m=orderService.selectById(orderId);
//        try {
//            if (m>0){
////                Integer n =orderService.orderNumber(orderId);
//                 if(n>0){
//                    return new ResultObject(200,"成功取消订单",null);
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return  new ResultObject(400,"没有这个订单，取消订单失败",null);
//}
 @PostMapping("/selectPage")
    public  ResultObject selectPage( @RequestParam("pageNum")  Integer pageNum){
     System.out.println("分页查询order");
        ResultObject result=new ResultObject();
        try {
            if (!CheckUtil.checkIntNum(pageNum)){
                return  new ResultObject(400,"操作过快，请稍后再试",null);
            }
            PageInfo<Order> pageInOrder=orderService.selectPage(pageNum);
            List<Order> list=pageInOrder.getList();
            if (list!=null&&list.size()>0){
                return  new ResultObject(200,"查询成功",pageInOrder);
            }
            return new ResultObject(400,"查询成功",pageInOrder);
        } catch (Exception e) {
            e.printStackTrace();
            throw  new RuntimeException();

        }

 }
     @PostMapping("/orderSelectByLike")
    public  ResultObject orderSelectByLike(Order order){
         System.out.println("模糊查询");

        try {
            List<Order> orders =orderService.selectByLike(order);
            System.out.println("--------------------"+orders);

            if (orders!=null && orders.size()>0){
                return  new ResultObject(200,"模糊查询成功",orders);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResultObject(400,"模糊查询失败",null);
     }


    /**
     * 根据用户id查询订单
     *
     */
    @GetMapping("/findOrderByUserId")
    public  ResultObject findOrderByUserId(@RequestParam("userId") Integer userId){

        if(StringUtils.isEmpty(userId+"")){
            return new ResultObject(400,"出现异常",null);
        }
        List<Order> orderList=orderService.findOrderByUserId(userId);
        if(orderList!=null && orderList.size()>0){
            return new ResultObject(200,"查询订单成功",orderList);
        }

        return new ResultObject(400,"查询订单失败",null);
    }

}
