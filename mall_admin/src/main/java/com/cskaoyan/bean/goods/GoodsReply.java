package com.cskaoyan.bean.goods;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class GoodsReply {
    private Integer id;
    private Integer commentId;
    private String content;
    private Date addTime;
    private Integer type;
}
