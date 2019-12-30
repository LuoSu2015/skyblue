package com.cskaoyan.bean.statistics;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class StatisUser {
    int users;
    String day;
}
