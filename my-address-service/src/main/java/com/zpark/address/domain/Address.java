package com.zpark.address.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
    private Integer addressId;
    private Integer userId;
    private String receiverName;
    private String receiverAddress;
    private String receiverPhone;
}
