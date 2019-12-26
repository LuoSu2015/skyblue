package com.cskaoyan.service;

import com.cskaoyan.bean.Brand;
import com.cskaoyan.mapper.KeywordMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cskaoyan.bean.Keyword;
import com.cskaoyan.bean.KeywordExample;

import java.util.List;

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
    public List<Keyword> queryKeyword(int page, int limit,String keyword,String url,String sort,String order) {
        //模糊搜索的条件
        KeywordExample keywordExample = showKeyword(keyword,url);
        //排序
        keywordExample.setOrderByClause(sort + " " + order);
        //分页
        PageHelper.startPage(page,limit);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        return keywords;
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
    public Keyword insertKeyword(Keyword keyword) {
        keywordMapper.insert(keyword);
        return keyword;
    }

    /**
     * 更新关键字
     * @param keyword
     */
    @Override
    public void updateKeyword(Keyword keyword) {
        keywordMapper.updateByPrimaryKey(keyword);
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
