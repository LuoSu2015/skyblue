package com.cskaoyan.controller.cart;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.wx.CartGoodsStatus;
import com.cskaoyan.bean.wx.CartStatus;
import com.cskaoyan.bean.wx.CheckoutWrapper;
import com.cskaoyan.service.cart.CartService;
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

    // CartList: WxApiRoot + 'cart/index',获取购物车的数据
    @RequestMapping("wx/cart/index")
    public BaseRespVo cartIndex() {
        BaseRespVo baseRespVo = new BaseRespVo();
        Map hashMap = new HashMap();
        CartStatus cartStatus = cartService.currentCartStatusByDefaultUser();
        List<Cart> cartList = cartStatus.getCartList();
        CartGoodsStatus cartGoodsStatus = cartStatus.getCartGoodsStatus();
        hashMap.put("cartList",cartList);
        hashMap.put("cartTotal",cartGoodsStatus);
        baseRespVo.setData(hashMap);
        return baseRespVo;
    }

    // CartAdd: WxApiRoot + 'cart/add', 添加商品到购物车
    @RequestMapping("wx/cart/add")
    public BaseRespVo selectList(@RequestBody Map map) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Integer totalCartCount = cartService.addCart(map);
        baseRespVo.setData(totalCartCount);
        return baseRespVo;
    }

    //    CartFastAdd: WxApiRoot + 'cart/fastadd', 立即购买商品
    @RequestMapping("wx/cart/fastadd")
    public BaseRespVo fastAdd(@RequestBody Map map) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Integer left_count = cartService.fastAdd(map);
        if(left_count>=0){
            baseRespVo.setData(left_count);
        }else {
            baseRespVo.setErrno(711);
            baseRespVo.setErrmsg("库存不足");
        }

        return baseRespVo;
    }

    //  CartUpdate: WxApiRoot + 'cart/update', 更新购物车的商品
    @RequestMapping("wx/cart/update")
    public BaseRespVo updateCart(@RequestBody Map map) {
        BaseRespVo baseRespVo = cartService.updateCartSelective(map);
        return baseRespVo;
    }

    //CartDelete: WxApiRoot + 'cart/delete', 删除购物车的商品
    @RequestMapping("wx/cart/delete")
    public BaseRespVo deleteCart(@RequestBody Map map) {
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        cartService.deleteCart(productIds);
        BaseRespVo baseRespVo = cartIndex();
        return baseRespVo;
    }

    //  CartChecked: WxApiRoot + 'cart/checked', 选择或取消选择商品
    @RequestMapping("wx/cart/checked")
    public BaseRespVo updateCheckedCart(@RequestBody Map map) {
        cartService.updateCheckedCartStatus(map);
        BaseRespVo baseRespVo = cartIndex();
        return baseRespVo;
    }

    //CartCheckout: WxApiRoot + 'cart/checkout', 下单前信息确认
    /*还没有实现，因为不了解表结构,把优惠券和团购屏蔽了*/
    @RequestMapping("wx/cart/checkout")
    public BaseRespVo checkoutCart(CheckoutWrapper checkoutWrapper) {
        BaseRespVo baseRespVo =cartService.checketoutCart(checkoutWrapper);
        return baseRespVo;
    }

    // CartGoodsCount: WxApiRoot + 'cart/goodscount' 获取购物车商品件数
    @RequestMapping("wx/cart/goodscount")
    public BaseRespVo goodsCount() {
        BaseRespVo baseRespVo = new BaseRespVo();
        List list = cartService.queryCartList();
        baseRespVo.setData(list.size());
        return baseRespVo;
    }
}
