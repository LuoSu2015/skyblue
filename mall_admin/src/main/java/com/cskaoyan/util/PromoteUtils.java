package com.cskaoyan.util;

import com.cskaoyan.bean.Ad;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.propate.PageWrapper;
import com.github.pagehelper.PageHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromoteUtils<T> {

    public static BaseRespVo preSetBaseRespVo(PageWrapper pageWrapper) {

        int limit = pageWrapper.getLimit();
        String name = pageWrapper.getName();
        String order = pageWrapper.getOrder();
        int page = pageWrapper.getPage();
        String sort = pageWrapper.getSort();

        BaseRespVo adBaseRespVo = new BaseRespVo<>();
        String orderBySort = sort + " " + order;
        PageHelper.startPage(page, limit, orderBySort);

        return adBaseRespVo;
    }


    public  BaseRespVo postSetBaseRespVo(BaseRespVo baseRespVo,List<T> object) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("items", object);
        hashMap.put("total", object.size());
        baseRespVo.setData(hashMap);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
