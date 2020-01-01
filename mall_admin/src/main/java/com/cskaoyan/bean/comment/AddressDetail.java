package com.cskaoyan.bean.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDetail {
    private Boolean isDefault;
    private Integer areaId;
    private String address;
    private String cityName;
    private String areaName;
    private String name;
    private String mobile;
    private Integer id;
    private Integer cityId;
    private String provinceName;
    private Integer provinceId;

}
