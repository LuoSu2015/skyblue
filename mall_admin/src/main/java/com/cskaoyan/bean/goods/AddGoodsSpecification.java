package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class AddGoodsSpecification {
    private Integer id;
    private Integer goodsId;
    private String specification;
    private String value;
    private String picUrl;
    private boolean deleted;
}
