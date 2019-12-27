package com.cskaoyan.service.promote;

import com.cskaoyan.bean.Ad;

import java.util.List;

public interface PromoteService<T> {
    List<T> queryAdList();

    List<T> queryAdByNameAndContent(String name, String content);

    int insertAdList(List<T> adList);

    List<T> queryAdListByName(String name);
}
