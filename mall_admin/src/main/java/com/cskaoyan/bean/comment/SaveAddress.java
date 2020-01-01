package com.cskaoyan.bean.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveAddress {
    private Integer id;
    private String name;
    private String mobile;
    private Integer provinceId;
    private Integer cityId;
    private Integer areaId;
    private String address;
    private Boolean isDefault;
}
