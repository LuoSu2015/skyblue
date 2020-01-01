package com.cskaoyan.bean.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowAddress {
    private Integer id;
    private String name;
    private String detailedAddress;
    private String mobile;
    private Boolean isDefault;
}
