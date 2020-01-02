package com.cskaoyan.exception;

import lombok.Data;

@Data
public class AdEx extends Exception{
    String msg;

    public AdEx() {
    }

    public AdEx(String msg) {
        this.msg = msg;
    }

}
