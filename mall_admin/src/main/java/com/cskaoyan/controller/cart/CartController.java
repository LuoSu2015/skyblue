package com.cskaoyan.controller.cart;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.User;
import com.cskaoyan.bean.wx.CartStatus;
import com.cskaoyan.service.cart.CartService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Cart> cartList = cartService.queryCartList(user.getId());
        /*这个还没写，联合查询*/
        CartStatus cartTotal = (CartStatus) cartService.queryCartStatus(user.getId());
        Map map = new HashMap();
        map.put("cartList", cartList);
        map.put("cartTotal", cartTotal);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        baseRespVo.setData(map);
        return baseRespVo;
    }

  /*  @RequestMapping("wx/cart/update")
    public BaseRespVo updateCart(@RequestBody ) {

    }*/


}
