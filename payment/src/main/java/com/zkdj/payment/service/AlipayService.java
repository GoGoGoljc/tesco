package com.zkdj.payment.service;

import com.zkdj.payment.domain.pay;

public interface AlipayService {



pay getOrderItem(Integer orderItemId);
}
