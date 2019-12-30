package com.cskaoyan.bean.systemManagement;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class Roles {
    int value;
    String label;
}
