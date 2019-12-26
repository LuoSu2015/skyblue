package com.cskaoyan.bean;

import lombok.Data;

@Data
public class BaseRespVo<T> {
     T data;
     int errno;
     String errmsg;

     public T getData() {
          return data;
     }

     public BaseRespVo<T> setData(T data) {
          this.data = data;
          return this;
     }

     public int getErrno() {
          return errno;
     }

     public BaseRespVo<T> setErrno(int errno) {
          this.errno = errno;
          return this;
     }

     public String getErrmsg() {
          return errmsg;
     }

     public BaseRespVo<T> setErrmsg(String errmsg) {
          this.errmsg = errmsg;
          return this;
     }
}
