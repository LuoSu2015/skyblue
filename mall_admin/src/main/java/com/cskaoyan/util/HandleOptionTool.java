package com.cskaoyan.util;


import com.cskaoyan.bean.HandleOptionBean;
import com.cskaoyan.bean.Order;

public class HandleOptionTool {
    public static HandleOptionBean getOrderHandleOptionBean(Order order) {
        Short orderStatus = order.getOrderStatus();
        HandleOptionBean handleOptionBean = new HandleOptionBean();
        if (orderStatus == 101) {
            handleOptionBean.setCancel(true);
        } else {
            handleOptionBean.setCancel(false);
        }
        if (orderStatus == 402 || orderStatus == 203 || orderStatus == 102) {
            handleOptionBean.setDelete(true);
        } else {
            handleOptionBean.setDelete(false);
        }
        if (orderStatus == 101) {
            handleOptionBean.setPay(true);
        } else {
            handleOptionBean.setPay(false);
        }
        Short comments = order.getComments();
        if (comments == 0) {
            handleOptionBean.setComment(true);
        } else {
            handleOptionBean.setComment(false);
        }
        if (orderStatus == 301) {
            handleOptionBean.setConfirm(true);
        } else {
            handleOptionBean.setConfirm(false);
        }
        if (orderStatus == 201) {
            handleOptionBean.setRefund(true);
        } else {
            handleOptionBean.setRefund(false);
        }
        if (orderStatus == 401) {
            handleOptionBean.setRebuy(true);
        } else {
            handleOptionBean.setRebuy(false);
        }
        return handleOptionBean;
    }
}
