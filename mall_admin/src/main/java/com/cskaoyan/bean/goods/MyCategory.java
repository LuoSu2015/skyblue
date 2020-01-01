package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class MyCategory {
    Integer value;
    String label;
    CategoryChild[] children;
}
