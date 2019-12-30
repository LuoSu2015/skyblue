package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class MyGoods{
    private MyAddGoods goods;
    private List<AddGoodsSpecification> specifications;
    private List<AddProducts> products;
    private List<AddAttribute> attributes;
}
