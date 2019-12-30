package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AddProducts {
    private Integer id;
    private String[] specifications;
    private String price;
    private String number;
    private String url;
}
