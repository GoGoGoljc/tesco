package com.zpark.browse.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResultObject<T>{
    private Integer code;
    private String message;
    private T resultData;


}
