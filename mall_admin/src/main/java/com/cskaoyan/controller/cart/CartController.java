package com.cskaoyan.controller.cart;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.wx.CartStatus;
import com.cskaoyan.service.cart.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartController {

    @Autowired
    CartService cartService;

    @RequestMapping("wx/cart/index")
    public BaseRespVo cartIndex() {
        BaseRespVo baseRespVo = new BaseRespVo();
        List<Cart> cartList = cartService.queryGoodsList();
        CartStatus cartTotal = (CartStatus) cartService.queryCartStatus();
        Map map = new HashMap();
        map.put("cartList", cartList);
        /* cartStatus,还没写*/
        map.put("cartTotal", cartTotal);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
