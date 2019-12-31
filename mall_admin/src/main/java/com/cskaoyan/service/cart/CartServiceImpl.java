package com.cskaoyan.service.cart;

import com.cskaoyan.bean.Cart;
import com.cskaoyan.bean.CartExample;
import com.cskaoyan.mapper.CartMapper;
import com.cskaoyan.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    GoodsMapper goodsMapper;

    @Autowired
    CartMapper cartMapper;

    @Override
    public List queryGoodsList(Integer userId) {
        return null;
    }

    @Override
    public Object queryCartStatus(Integer userId) {

        return null;
    }

    @Override
    public List queryCartList(Integer userId) {
        CartExample cartExample = new CartExample();
        CartExample.Criteria criteria = cartExample.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<Cart> cartList = cartMapper.selectByExample(cartExample);
        return cartList;
    }
}
