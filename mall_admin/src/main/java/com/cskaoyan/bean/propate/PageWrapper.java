package com.cskaoyan.bean.propate;

import lombok.Data;

@Data
public class PageWrapper {
    private int page;
    private int limit;
    private String name;
    private String sort;
    private String order;
}
