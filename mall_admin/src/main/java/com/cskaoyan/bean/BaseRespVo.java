package com.cskaoyan.bean;

import lombok.Data;

@Data
public class BaseRespVo<T> {
     T data;
     int errno;
     String errmsg;
}
