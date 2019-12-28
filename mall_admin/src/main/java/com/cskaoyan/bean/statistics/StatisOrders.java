package com.cskaoyan.bean.statistics;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StatisOrders {
    Long orders;
    Long amount;
    Long customers;
    String day;
    Long pcr;

}
