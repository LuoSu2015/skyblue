package com.cskaoyan.service.cart;


import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.wx.CartStatus;

import java.util.List;

public interface CartService<T> {

    List<T> queryGoodsList();

    T queryCartStatus();
}
