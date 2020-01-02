package com.cskaoyan.service.cart;

import com.cskaoyan.bean.*;
import com.cskaoyan.bean.wx.CartGoodsStatus;
import com.cskaoyan.bean.wx.CartStatus;
import com.cskaoyan.bean.wx.CheckoutWrapper;
import com.cskaoyan.mapper.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CartMapper cartMapper;


    /*查询购物车里的所有商品*/
    @Override
    public List<Cart> queryCartList() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        ArrayList<Cart> realExistCartList = new ArrayList<>();
        for (Cart cart : cartList) {
            if (!cart.getDeleted()) {
                realExistCartList.add(cart);
            }
        }
        return realExistCartList;
    }

    /*更新购物车商品的数量*/
    @Override
    public BaseRespVo updateCartSelective(Map map) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Integer cartId = (Integer) map.get("id");
        short number = (short) map.get("number");
        Cart cart = cartMapper.selectByPrimaryKey(cartId);
        cart.setNumber(number);
        cartMapper.updateByPrimaryKeySelective(cart);
        return baseRespVo;
    }


    /*删除购物车里面的一些商品,逻辑删除 deleted*/
    public void deleteCart(List<Integer> productIds) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        for (Integer productId : productIds) {
            criteria.andProductIdEqualTo(productId);
            Cart cart = cartMapper.selectByExample(cartExample).get(0);
            cart.setDeleted(true);
        }
    }

    @Autowired
    AddressMapper addressMapper;

    /*查询用户的所有地址*/
    @Override
    public List<Address> queryAddressByDefaultUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        AddressExample addressExample = new AddressExample();
        AddressExample.Criteria criteria = addressExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        List<Address> addressList = addressMapper.selectByExample(addressExample);
        return addressList;
    }

    /*更新购物车选中和未选中的状态*/
    @Override
    public void updateCheckedCartStatus(Map map) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        Subject subject = SecurityUtils.getSubject();
        Integer userId = ((User) subject.getPrincipal()).getId();
        Integer isChecked = (Integer) map.get("isChecked");
        Boolean flag;
        if (isChecked == 0) {
            flag = true;
        } else {
            flag = false;
        }
        List<Integer> productIds = (List<Integer>) map.get("productIds");
        for (Integer productId : productIds) {
            criteria.andProductIdEqualTo(productId).andUserIdEqualTo(userId);
            Cart cart = cartMapper.selectByExample(cartExample).get(0);
            cart.setChecked(flag);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
    }

    /*添加购物车*/
    @Override
    public Integer addCart(Map map) {
        Integer goodsId = (Integer) map.get("goodsId");
        int addNumber = (Integer) map.get("number");
        Integer productId = (Integer) map.get("productId");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Integer userId = user.getId();
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andGoodsIdEqualTo(goodsId).andProductIdEqualTo(productId).andUserIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        if (cartList.size() > 0) {
            /*cart存在的话，就只需要改当前的cart*/
            Cart cart = cartList.get(0);
            Short number = cart.getNumber();
            number = (short) (number + addNumber);
            /*显示购物车当前商品的总个数number*/
            GoodsProduct goodsProduct = queryGoodsProductByProductId(productId);
            cart.setNumber(number);
            cart.setDeleted(false);
            cartMapper.updateByPrimaryKey(cart);
        } else {
            /*cart不存在的话，就需要重新创建一个cart*/
            Cart cart = new Cart();
            cart.setUserId(user.getId());
            /*查找goods_id ,goods_sn,goods_name */
            Goods goods = queryGoodsByGoodsId(goodsId);
            if (goods != null) {
                cart.setGoodsId(goodsId);
                cart.setGoodsSn(goods.getGoodsSn());
                cart.setGoodsName(goods.getName());
                cart.setPicUrl(goods.getPicUrl());
            }
            /*显示购物车当前商品的单价price*/
            /*specifications*/
            GoodsProduct goodsProduct = queryGoodsProductByProductId(productId);
            if (goodsProduct != null) {
                double goodsProductPrice = goodsProduct.getPrice().doubleValue();
                cart.setPrice(new BigDecimal(goodsProductPrice));
                cart.setSpecifications(goodsProduct.getSpecifications());
            }
            cart.setNumber((short) addNumber);
            cart.setChecked(false);
            cart.setAddTime(new Date());
            cart.setUpdateTime(new Date());
            cart.setDeleted(false);
            cartMapper.insertSelective(cart);
        }

        int countCart = queryCartList().size();

        return countCart;
    }

    /*返回商品，goodsProduct*/
    private GoodsProduct queryGoodsProductByProductId(Integer productId) {
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
        return goodsProduct;
    }


    /* 查找商品属性 Goods */
    private Goods queryGoodsByGoodsId(Integer goodsId) {
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        return goods;
    }

    @Autowired
    GoodsProductMapper goodsProductMapper;

    /*在商品页购买，不通过购物车*/
    @Override
    public Integer fastAdd(Map map) {
        Integer goodsId = (Integer) map.get("goodsId");
        Integer queryNumber = (Integer) map.get("number");
        Integer productId = (Integer) map.get("productId");
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        Cart cart = new Cart();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        GoodsProduct goodsProduct = goodsProductMapper.selectByPrimaryKey(productId);
        cart.setUserId(user.getId());
        cart.setGoodsId(goodsId);
        cart.setGoodsSn(goods.getGoodsSn());
        cart.setGoodsName(goods.getName());
        cart.setProductId(productId);
        cart.setPrice(goodsProduct.getPrice());
        int actualNumber = goodsProduct.getNumber();
        cart.setNumber((short) actualNumber);
        cart.setSpecifications(goodsProduct.getSpecifications());
        cart.setChecked(false);
        cart.setPicUrl(goods.getPicUrl());
        cart.setAddTime(new Date());
        cart.setUpdateTime(new Date());
        cart.setDeleted(false);
        cartMapper.insertSelective(cart);
        if (actualNumber >= queryNumber) {
            return cart.getId();
        } else {
            return -1;
        }

    }

    /*购物车下单前的确认*/
    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @Override
    public BaseRespVo checketoutCart(CheckoutWrapper checkoutWrapper) {
        BaseRespVo baseRespVo = new BaseRespVo();
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        Integer cartId = checkoutWrapper.getCartId();

        /*AddressId  共用*/
        Map hashMap = new HashMap();
        List<Address> addressList = queryAddressByDefaultUser();
        Integer addressId = checkoutWrapper.getAddressId();
        /*checkedAddress 共用*/
        Address checkedAddress = queryAddressByAddressId(addressId);
        if (checkedAddress == null) {
            for (Address address : addressList) {
                if (address.getIsDefault()) {
                    checkedAddress = address;
                    break;
                }
            }
        }
        /*groupRulesId 共用*/
        Integer grouponRulesId = checkoutWrapper.getGrouponRulesId();
        /*availableCouponLength 共用*/
        List<Coupon> availableCoupon = queryCouponByDefaultUser();
        Integer availableCouponLength = availableCoupon.size();
        /*couponId 共用*/
        Integer couponId = checkoutWrapper.getCouponId();
        /*couponPrice 共用*/
        Double couponPrice = 0.0;
        Coupon coupon = queryCouponByCouponId(couponId);
        if (coupon != null) {
            couponPrice = coupon.getDiscount().doubleValue();
        }
        /*goodsTotalPrice */
        Double goodsTotalPrice;
        /*grouponPrice*/
        Double grouponPrice = 0.0;
        /*freightPrice */
        Double freightPrice;
        /*checkedGoodsList 不共用*/
        List<Goods> checkedGoodsList;
        /*actualPrice*/
        Double actualPrice = 0.0;


        if (cartId == 0) {
            CartStatus cartStatus = currentCartStatusByDefaultUser(); // 购物车状态
            actualPrice = cartStatus.getCartGoodsStatus().getCheckedGoodsAmount();
            checkedGoodsList = cartStatus.getCheckedGoodsList();
        } else {
            Cart cart = cartMapper.selectByPrimaryKey(cartId);
            Integer goodsId = cart.getGoodsId();
            Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
            ArrayList<Goods> list = new ArrayList<>();
            list.add(goods);
            checkedGoodsList=list;
            actualPrice = cart.getPrice().doubleValue();

        }

        if (actualPrice >= 88.0) {
            freightPrice = 0.0;
        } else {
            freightPrice = 10.0;
        }
        goodsTotalPrice=actualPrice;
        actualPrice = actualPrice + freightPrice;

        /*AddressId */
        hashMap.put("addressId", addressId);
        /*groupRulesId*/
        hashMap.put("groupRulesId", grouponRulesId);
        /*availableCouponLength*/
        hashMap.put("availableCouponLength", availableCouponLength);
        /*checkedAddress*/
        hashMap.put("checkedAddress", checkedAddress);
        /*checkedGoodsList*/
        hashMap.put("checkedGoodsList", checkedGoodsList);
        /*couponId*/
        hashMap.put("couponId", couponId);
        /*couponPrice*/
        hashMap.put("couponPrice", couponPrice);
        /*freightPrice*/
        hashMap.put("freightPrice", freightPrice);
        /*goodsTotalPrice*/
        hashMap.put("goodsTotalPrice", goodsTotalPrice);
        /*grouponPrice*/
        hashMap.put("grouponPrice", grouponPrice);
        hashMap.put("actualPrice", actualPrice);
        hashMap.put("orderTotalPrice", goodsTotalPrice);
        baseRespVo.setData(hashMap);
        return baseRespVo;
    }


    /*通过传入地址Id ，更新用户的默认地址*/
    private void updateDefaultAddressByQueryArgAddressId(Integer addressId) {
        List<Address> addressList = queryAddressByDefaultUser();
        Boolean oldDefaultAddress;
        Integer currentAddressId;

        for (Address address : addressList) {
            oldDefaultAddress = address.getIsDefault();
            currentAddressId = address.getId();
            boolean a = currentAddressId == addressId && !oldDefaultAddress;
            boolean b = address.getId() != addressId && address.getIsDefault();
            if (currentAddressId == addressId && oldDefaultAddress) {
                break;
            }
            if (a) {
                address.setIsDefault(true);
            }
            if (b) {
                address.setIsDefault(false);
            }
            if (a && b) {
                break;
            }
        }
    }

    /*查看当前用户的购物车状态*/
    public CartStatus currentCartStatusByDefaultUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
       /* GoodsExample goodsExample = new GoodsExample;
        GoodsExample.Criteria criteria = goodsExample.createCriteria();*/

        CartStatus cartStatus = new CartStatus();
        List<Cart> checkedCartList = new LinkedList();
        CartGoodsStatus cartGoodsStatus = new CartGoodsStatus();

        Double goodsAmount = 0.0;
        Double checkedGoodsAmount = 0.0;
        int checkedGoodsCount = 0;
        int goodsCount = 0;
        List<Cart> cartList = queryCartList();
        List<Goods> checkedGoodsList = null;

        goodsCount = cartList.size();
        for (Cart cart : cartList) {
            /*转成Double类型*/
            goodsAmount += cart.getPrice().doubleValue();
            if (cart.getChecked()) {
                checkedGoodsAmount += cart.getPrice().doubleValue();
                checkedGoodsCount++;
                checkedCartList.add(cart);
                checkedGoodsList.add(goodsMapper.selectByPrimaryKey(cart.getGoodsId()));
            }
        }
        cartGoodsStatus.setGoodsCount(goodsCount);
        cartGoodsStatus.setGoodsAmount(goodsAmount);
        cartGoodsStatus.setCheckedGoodsCount(checkedGoodsCount);
        cartGoodsStatus.setCheckedGoodsAmount(checkedGoodsAmount);
        cartStatus.setCartGoodsStatus(cartGoodsStatus);
        cartStatus.setCartList(cartList);
        cartStatus.setCheckedCartList(checkedCartList);
        cartStatus.setCheckedGoodsList(checkedGoodsList);
        return cartStatus;
    }

    @Autowired
    CouponUserMapper couponUserMapper;
    @Autowired
    CouponMapper couponMapper;

    /*通过用户查询 查询用户和优惠券之间的关系*/
    List<CouponUser> queryCouponUserByDefaultUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        CouponUserExample couponUserExample = new CouponUserExample();
        CouponUserExample.Criteria criteria = couponUserExample.createCriteria();
        criteria.andUserIdEqualTo(user.getId());
        List<CouponUser> couponUserList = couponUserMapper.selectByExample(couponUserExample);
        return couponUserList;
    }


    /*通过用户查询其所拥有的的全部优惠券*/
    List<Coupon> queryCouponByDefaultUser() {
        List<CouponUser> couponUserList = queryCouponUserByDefaultUser();
        List<Coupon> checkedCouponList = new ArrayList<>();
        CouponExample couponExample = new CouponExample();
        CouponExample.Criteria criteria = couponExample.createCriteria();
        for (CouponUser couponUser : couponUserList) {
            criteria.andIdEqualTo(couponUser.getCouponId());
            List<Coupon> couponList = couponMapper.selectByExample(couponExample);
            if (couponList.size() > 0) {
                Coupon coupon = couponList.get(0);
                checkedCouponList.add(coupon);
            }

        }
        return checkedCouponList;
    }

    /*通过优惠券Id来查询优惠券*/
    Coupon queryCouponByCouponId(Integer couponId) {
        Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
        return coupon;
    }

    /*通过团购规则Id来查询团购规则*/
    GrouponRules queryGrouponRulesByGrouponRulesId(Integer grouponRulesId) {
        GrouponRules grouponRules = grouponRulesMapper.selectByPrimaryKey(grouponRulesId);
        return grouponRules;
    }

    Address queryAddressByAddressId(Integer addressId) {
        Address address = addressMapper.selectByPrimaryKey(addressId);
        return address;
    }
}
