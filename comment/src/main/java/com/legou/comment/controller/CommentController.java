package com.legou.comment.controller;
import com.legou.comment.client.GoodsClient;
import com.legou.comment.client.UserClient;
import com.legou.comment.domain.Comment;
import com.legou.comment.mapper.CommentMapper;
import com.legou.comment.util.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author SJXY
 */
@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {
    @Resource
    private CommentMapper commentMapper;
    @Resource
    private GoodsClient goodsClient;
    @Resource
    private UserClient userClient;


    /**
     * 根据用户ID查看评论，远程拿到用户判断是否存在
     *
     * @param userId
     * @return
     */
   /* @PostMapping("/findAllByUserId")
    public ResultObject findAllByUserId(@RequestParam("userId")   Integer userId) {
        System.out.println("访问了findAllByUserId");
        Map users = userClient.findUserInfoById(userId);
        Map user = (Map) users.get("resultData");
        System.out.println("远程拿到的用户为："+user);
        System.out.println("users"+users);
        if (!StringUtils.isEmpty(user)) {
            //远程拿到用户ID若存在则根据该ID查评论
            List<Comment> comment = commentMapper.findByUserId((Integer) user.get("userId"));
            for (Comment item : comment) {
                System.out.println(item);
                return new ResultObject(200, "找到评论成功", item);
            }
        } else {
            return new ResultObject(400, "查找评论失败", null);
        }
           return new ResultObject(400, "查找评论失败", null);
    }*/
    @PostMapping("/findAllByUserId")
    public ResultObject findAllByUserId(@RequestParam("userId")  Integer userId) {
        System.out.println("------------"+userId);
            List<Comment> comment = commentMapper.findByUserId(userId);
            for (Comment item : comment) {
                System.out.println(item);
            }
            if(comment!=null && comment.size()>0){
                return new ResultObject(200, "找到评论成功", comment);
            }
            return new ResultObject(400, "查找评论失败", null);
    }


    /**
     * 未登录可直接根据商品ID查看商品所有评论，一直产生熔断？
     *
     * @param goodId
     * @return
     */


/*
@PostMapping("/findAllByGoodId")
    public ResultObject findById(@RequestParam("goodId") Integer goodId, HttpServletRequest request){
    System.out.println("访问了findAllByGoodId方法");
    Map goods = goodsClient.adminFindById(goodId,request);
    Map good = (Map) goods.get("resultData");
    System.out.println("远程拿到商品为:"+good);
    if (!StringUtils.isEmpty(good)){
        //远程拿到商品ID若存在则根据该ID查评论
        List<Comment> product= commentMapper.findByGoodId((Integer) good.get("goodId"));
        for(Comment item:product){
            System.out.println(item);
        }
        System.out.println(good);
        return  new ResultObject(200, "查找评论成功", good);
    }
        return  new ResultObject(400, "查找评论失败，商品不存在", good);
}
*/
    @PostMapping("/findAllByGoodId")
    public ResultObject findById(@RequestParam("goodId") Integer goodId, HttpServletRequest request){
        System.out.println("访问了findAllByGoodId");
        try {
            //远程拿到商品ID若存在则根据该ID查评论
            List<Comment> product= commentMapper.findByGoodId(goodId);
            for(Comment item:product){
                System.out.println(item);
            }
            if(product!=null && product.size()>0){
                return  new ResultObject(200, "查找评论成功", product);
            }
            return  new ResultObject(400, "查找评论失败，商品不存在", product);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }



@PostMapping("/addComment")
    public ResultObject addComment( @RequestParam("goodId")Integer goodId,@RequestParam("content")  String content){
    System.out.println(8888);
    //要评论的商品ID不能为空
    if(!StringUtils.isEmpty(goodId) && content!=null ){
        int comment=commentMapper.addComment(goodId,content);
        if (comment>0){
            return new ResultObject(200,"添加评论成功","评论的数量为:"+comment);
        }
    }
    return new ResultObject(400,"添加评论失败商品不存在",null);
}


/**
 * 根据用户名和商品id删除评论
 */
@PostMapping("/deleteComment")
public ResultObject deleteComment(@RequestParam("commentId")  Integer commentId){
    if(StringUtils.isEmpty(commentId)){
        return new ResultObject(400,"服务器发生异常",null);
    }

    Integer i=commentMapper.deleteComment(commentId);
    if(i>0){
        return new ResultObject(200,"删除成功",null);
    }
    return new ResultObject(400,"删除失败",null);
}



}
