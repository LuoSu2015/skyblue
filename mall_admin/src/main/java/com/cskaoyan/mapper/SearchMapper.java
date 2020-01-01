package com.cskaoyan.mapper;

import com.cskaoyan.bean.Keyword;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SearchMapper {

    Keyword getDefaultKeyword();

    List<Keyword> getHotKeywordList();

    List<Keyword> getHistoryKeywordList(@Param("userId") Integer userId);

    String[] getHotKeywordContainKey(@Param("keyword") String keyword);

    void deleteHistory();

}
