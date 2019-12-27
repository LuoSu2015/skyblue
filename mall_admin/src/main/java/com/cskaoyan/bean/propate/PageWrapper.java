package com.cskaoyan.bean.propate;

import lombok.Data;

@Data
public class PageWrapper {
    private Integer page;
    private Integer limit;
    private String name;
    private String sort;
    private String order;
    private String content;
    private Short type;
    private Short status;
    private String subtitle;
    private String title;
    private Integer goodsId;
}