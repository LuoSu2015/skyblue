package com.cskaoyan.bean.wx;

import lombok.Data;

@Data
public class CheckoutWrapper {
    private Integer cartId;
    private Integer addressId;
    private Integer couponId;
    private Integer grouponRulesId;
}
