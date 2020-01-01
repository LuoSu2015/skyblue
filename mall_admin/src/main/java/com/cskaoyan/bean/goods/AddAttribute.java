package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddAttribute {
    private Integer id;
    private Integer goodsId;
    private boolean deleted;
    private String attribute;
    private String value;
}
