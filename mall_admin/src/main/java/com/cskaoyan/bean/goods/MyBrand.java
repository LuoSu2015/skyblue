package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyBrand {
    Integer value;
    String label;
}
