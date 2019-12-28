package com.cskaoyan.bean.statistics;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;
@Data
@Component
public class StatisGoods {
    Long amount;
    long orders;
    String day;
    Long products;
}
