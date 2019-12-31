package com.cskaoyan.service;

import com.cskaoyan.bean.*;
import com.cskaoyan.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class MarketServiceImpl implements MarketService {
    @Autowired
    KeywordMapper keywordMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    StorageMapper storageMapper;
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CategoryMapper categoryMapper;

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
        keywordExample.createCriteria().andDeletedEqualTo(false);
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
        keywordMapper.selectLastInsert(keyword);
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
     * @param keyword
     */
    @Override
    public Keyword deleteKeyword(Keyword keyword) {
        keyword.setDeleted(true);
        keyword.setUpdateTime(new Date());
        keywordMapper.updateByPrimaryKey(keyword);
        return keyword;
    }

    /**
     * 显示所有商标及其分页
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @Override
    public Map queryBrand(Integer page, Integer limit, String sort, String order,Integer id,String name) {
        Map map = new HashMap();
        //模糊搜索的条件
        BrandExample brandExample = showBrands(id,name);
        //排序
        brandExample.setOrderByClause(sort + " " + order);
        //分页
        PageHelper.startPage(page,limit);
        List<Brand> brands = brandMapper.selectByExample(brandExample);
        //获取总条目数
        PageInfo<Brand> pageInfo = new PageInfo<>(brands);
        int total = (int)pageInfo.getTotal();
        map.put("brands",brands);
        map.put("total",total);
        return map;
    }

    /**
     * 商标搜索的条件判断
     * @param id
     * @param name
     * @return
     */
    private BrandExample showBrands(Integer id, String name) {
        BrandExample brandExample = new BrandExample();
        brandExample.createCriteria().andDeletedEqualTo(false);
        if(id != null && name != null){
            brandExample.createCriteria().andIdEqualTo(id).andNameLike("%" + name + "%");
        }else if(id == null && name != null){
            brandExample.createCriteria().andNameLike("%" + name + "%");
        }else if(id != null && name == null){
            brandExample.createCriteria().andIdEqualTo(id);
        }
        return brandExample;
    }

    /**
     * 图片上传,将图片信息保存到数据库
     * @param storage
     * @return
     */
    @Override
    public int  insertStorage(Storage storage) {
        int insert = storageMapper.insert(storage);
        return insert;
    }

    /**
     * 新建商标
     * @param brand
     * @return
     */
    @Override
    public Brand insertBrand(Brand brand) {
        brandMapper.selectLastId(brand);
        return brand;
    }

    /**
     * 删除商标
     * @param
     * @return
     */
    @Override
    public Brand deleteBrand(Brand brand) {
        brand.setDeleted(true);
        brand.setUpdateTime(new Date());
        brandMapper.updateByPrimaryKey(brand);
        return brand;
    }

    /**
     * 更新商标
     * @param brand
     * @return
     */
    @Override
    public int updateBreand(Brand brand) {
        int i = brandMapper.updateByPrimaryKey(brand);
        return i;
    }

    /**
     * 问题的显示积分页
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @param question
     * @return
     */
    @Override
    public Map queryIssue(Integer page, Integer limit, String sort, String order, String question) {
        Map map = new HashMap();
        //模糊搜索的条件
        IssueExample issueExample = showIssus(question);
        //排序
        issueExample.setOrderByClause(sort + " " + order);
        //分页
        PageHelper.startPage(page,limit);
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        //获取总条目数
        PageInfo<Issue> pageInfo = new PageInfo<>(issues);
        int total = (int)pageInfo.getTotal();
        map.put("issues",issues);
        map.put("total",total);
        return map;
    }

    /**
     * 问题的搜索逻辑
     * @param question
     * @return
     */
    private IssueExample showIssus(String question) {
        IssueExample issueExample = new IssueExample();
        issueExample.createCriteria().andDeletedEqualTo(false);
        if(question != null){
            issueExample.createCriteria().andQuestionLike("%" + question +"%");
        }
        return issueExample;
    }

    /**
     * 新建问题
     * @param issue
     * @return
     */
    @Override
    public Issue insertIssue(Issue issue) {
        issueMapper.selectLastId(issue);
        return issue;
    }

    /**
     * 更新问题
     * @param issue
     * @return
     */
    @Override
    public int updateIssue(Issue issue) {
        int i = issueMapper.updateByPrimaryKey(issue);
        return i;
    }

    /**
     * 删除问题
     * @param
     * @return
     */
    @Override
    public Issue delectIssue(Issue issue) {
        issue.setDeleted(true);
        issue.setUpdateTime(new Date());
        issueMapper.updateByPrimaryKey(issue);
        return issue;
    }

    /**
     * 查询所有商品类目
     * @return
     */
    @Override
    public List<Category2> queryCategory() {
        List<Category2> category2 = categoryMapper.selectCategory2();
        CategoryExample categoryExample = new CategoryExample();
        for (Category2 category21 : category2) {
            categoryExample.createCriteria().andPidEqualTo(category21.getId());
            List<Category> categories = categoryMapper.selectByExample(categoryExample);
            category21.setChildren(categories);
        }
        return category2;
    }

    /**
     * 查询商品类目为L1的类目
     * @return
     */
    @Override
    public List<Map> queryCategoryByL1() {
        CategoryExample categoryExample = new CategoryExample();
        //查询L1的类目
        categoryExample.createCriteria().andDeletedEqualTo(false).andLevelEqualTo("L1");
        List<Map> maps = new ArrayList<>();
        //放到list中
        List<Category> categories = categoryMapper.selectByExample(categoryExample);
        for (Category category : categories) {
            Map map = new HashMap();
            map.put("value",category.getId());
            map.put("label",category.getName());
            maps.add(map);
        }
        return maps;
    }

    /**
     * 新加商品类目
     * @param category
     * @return
     */
    @Override
    public Category insertCategory(Category category) {
        categoryMapper.insert(category);
        return category;
    }

    /**
     * 更新商品类目
     * @param category
     * @return
     */
    @Override
    public int updateCategroy(Category category) {
        int i = categoryMapper.updateByPrimaryKeySelective(category);
        return i;
    }

    /**
     * 删除商品类目
     * @param category
     * @return
     */
    @Override
    public Category delectCategroy(Category category) {
        category.setUpdateTime(new Date());
        category.setDeleted(true);
        categoryMapper.updateByPrimaryKeySelective(category);
        return category;
    }
}
