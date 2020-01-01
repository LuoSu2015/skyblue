package com.cskaoyan.bean.wxgroupon;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class WxGroupon {
    private BigDecimal groupon_price;
    private WxGrouponGoods goods;
    private Integer groupon_member;
}
