package com.cskaoyan.bean.wx.order;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

@Data
public class HandleOption {
    @Value("false")
    boolean cancel;
    @Value("false")
    boolean delete;
    @Value("false")
    boolean pay;
    @Value("false")
    boolean comment;
    @Value("false")
    boolean confirm;
    @Value("false")
    boolean refund;
    @Value("false")
    boolean rebuy;
}
