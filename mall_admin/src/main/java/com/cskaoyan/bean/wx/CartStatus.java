package com.cskaoyan.bean.wx;

import com.cskaoyan.bean.Cart;
import lombok.Data;

import java.util.List;


@Data
public class CartStatus {
    private CartGoodsStatus cartGoodsStatus;
    private List<Cart> checkedCartList;
    private List<Cart> cartList;
}
