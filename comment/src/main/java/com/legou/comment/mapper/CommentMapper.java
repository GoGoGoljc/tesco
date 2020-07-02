package com.legou.comment.mapper;

import com.legou.comment.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author SJXY
 */
@Mapper

public interface CommentMapper {

    //根据用户ID获取该用户发表的评论

    @Select("select * from comment where user_id = #{userId}")
    List<Comment> findByUserId(Integer userId);

    //根据商品ID查询该商品所有评论

    @Select("select * from comment where good_id = #{goodId}")
    List<Comment> findByGoodId(Integer goodId);

    //添加评论

    @Insert("insert into comment (good_id,content) values (#{goodId},#{content})")
    Integer addComment(Integer goodId,String content);


    @Delete("delete  from comment where comment_id = #{commentId} ")
    Integer deleteComment( Integer commentId);


}
