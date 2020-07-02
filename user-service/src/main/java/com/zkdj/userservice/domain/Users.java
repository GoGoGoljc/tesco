package com.zkdj.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Integer userId;
    private String userName;
    private String userRealName;
    private String userPwd;
    private Integer userSex;
    private String userEmail;
    private String userPhone;
    private String userImage;
    private Integer userState;
    private Date userTime;
    private String userAddress;
}
