package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;
import com.cskaoyan.mapper.KeywordMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    KeywordMapper keywordMapper;

    /**
     * 查找关键字,并且分页
     * @param page
     * @param limit
     * @return
     */
    @Override
    public Map queryKeyword(int page, int limit, String keyword, String url, String sort, String order) {
        Map map = new HashMap();
        //模糊搜索的条件
        KeywordExample keywordExample = showKeyword(keyword,url);
        //排序
        keywordExample.setOrderByClause(sort + " " + order);
        //分页
        PageHelper.startPage(page,limit);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        //获取总条目数
        PageInfo<Keyword> pageInfo = new PageInfo<>(keywords);
        int total = (int)pageInfo.getTotal();
        map.put("keywords",keywords);
        map.put("total",total);
        return map;
    }

    /**
     * 模糊搜索的分类讨论
     * @param keyword
     * @param url
     * @return
     */
    private KeywordExample showKeyword(String keyword, String url) {
        //判断keyword和url的搜索是否为空,拼接sql语句
        KeywordExample keywordExample = new KeywordExample();
        if(keyword != null && url != null){
            keywordExample.createCriteria().andKeywordLike("%" + keyword + "%").andUrlLike("%" + url + "%");
        }else if(keyword != null && url == null){
            keywordExample.createCriteria().andKeywordLike("%" + keyword + "%");
        }else if(keyword == null && url != null){
            keywordExample.createCriteria().andKeywordLike("%" + url + "%");
        }
        return keywordExample;
    }

    /**
     * 插入关键字
     * @param keyword
     * @return
     */
    @Override
    @Transactional
    public Keyword insertKeyword(Keyword keyword) {
        keywordMapper.insert(keyword);
        return keyword;
    }

    /**
     * 更新关键字
     * @param keyword
     */
    @Override
    public Keyword updateKeyword(Keyword keyword) {
        int id = keywordMapper.updateByPrimaryKey(keyword);
        Keyword keyword1 = keywordMapper.selectByPrimaryKey(id);
        return keyword1;
    }

    /**
     * 删除关键字
     * @param id
     */
    @Override
    public void deleteKeywordById(int id) {
        keywordMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Brand> queryBrand(Integer page, Integer limit, String sort, String order) {
        return null;
    }
}
