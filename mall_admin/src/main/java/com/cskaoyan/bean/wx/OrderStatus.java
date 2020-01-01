package com.cskaoyan.bean.wx;

import lombok.Data;

@Data
public class OrderStatus {
    private Integer uncomment;
    private Integer unpaid;
    private Integer unrecv;
    private Integer unship;
}
