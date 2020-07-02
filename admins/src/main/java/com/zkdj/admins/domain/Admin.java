package com.zkdj.admins.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SJXY
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Admin {
    @JsonProperty(value = "adminId")
    private Integer adminId;
    @JsonProperty(value = "adminName")
    private String adminName;
    @JsonProperty(value = "adminPhone")
    private String adminPhone;
    @JsonProperty(value = "adminEmail")
    private String adminEmail;
//    @JsonProperty(value = "adminPwd")
    private String adminPwd;
    @JsonProperty(value = "adminState")
    private  Integer adminState;
    @JsonProperty(value = "adminImage")
    private  String adminImage;
}
