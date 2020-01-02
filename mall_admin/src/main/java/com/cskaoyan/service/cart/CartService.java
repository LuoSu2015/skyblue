package com.cskaoyan.service.cart;


import com.cskaoyan.bean.Address;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.wx.CartStatus;
import com.cskaoyan.bean.wx.CheckoutWrapper;

import java.util.List;
import java.util.Map;

public interface CartService {


    List<Address> queryAddressByDefaultUser();

    void updateCheckedCartStatus(Map map);

    List<Cart> queryCartList();

    CartStatus currentCartStatusByDefaultUser();

    BaseRespVo updateCartSelective(Map map);

    void deleteCart(List<Integer> productIds);

    Integer addCart(Map map);

    Integer fastAdd(Map map);

    BaseRespVo checketoutCart(CheckoutWrapper checkoutWrapper);

}
