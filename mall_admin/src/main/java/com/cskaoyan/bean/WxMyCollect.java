package com.cskaoyan.bean;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Data
@Component
public class WxMyCollect {
    private String brief;
    private String picUrl;
    private Integer valueId;
    private String name;
    private Integer id;
    private Integer type;
    private BigDecimal retailPrice;
}
