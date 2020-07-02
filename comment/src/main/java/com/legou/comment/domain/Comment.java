package com.legou.comment.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author SJXY
 */
@Data
public class Comment {
    private Integer commentId;
    private Integer userId;
    private Integer goodId;
    private String userName;
    private String content;
    private Integer check;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private String image;
}
