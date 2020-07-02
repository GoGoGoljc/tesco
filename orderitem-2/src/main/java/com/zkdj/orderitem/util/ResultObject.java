package com.zkdj.orderitem.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SJXY
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObject<T> {

    private  Integer code;

    private String message;

    private T resultData;

}
