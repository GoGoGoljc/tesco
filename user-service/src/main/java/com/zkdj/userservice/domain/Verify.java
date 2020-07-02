package com.zkdj.userservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Verify {
   public Integer userId;
   public String userEmail;
   public String userPwd;
   public String code;
}
