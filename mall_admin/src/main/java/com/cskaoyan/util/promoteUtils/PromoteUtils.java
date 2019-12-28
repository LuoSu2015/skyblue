package com.cskaoyan.util.promoteUtils;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.promate.PageWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
        /* 这个和 object.size()不一样，object.size()是当前页的数据个数，pageinfo是总页数*/
        PageInfo<T> tPageInfo = new PageInfo<>();
        hashMap.put("items", object);
        hashMap.put("total", tPageInfo.getSize());
        baseRespVo.setData(hashMap);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
