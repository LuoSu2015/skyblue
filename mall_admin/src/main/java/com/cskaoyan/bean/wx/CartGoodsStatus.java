package com.cskaoyan.bean.wx;

import com.cskaoyan.bean.Cart;
import lombok.Data;

import java.util.List;


@Data
public class CartGoodsStatus {
    private Double checkedGoodsAmount;
    private Integer checkedGoodsCount;
    private Double goodsAmount;
    private Integer goodsCount;
}
