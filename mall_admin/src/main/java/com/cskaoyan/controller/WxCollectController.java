package com.cskaoyan.controller;
import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.WxMyCollect;
import com.cskaoyan.service.WxCollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WxCollectController {
    @Autowired
    WxCollectService wxCollectService;

    @RequestMapping("wx/collect/list")
    public BaseRespVo showCollect(Integer type,Integer page,Integer size){
        //获取shiro认证后的用户id（待修改）
        Session session = SecurityUtils.getSubject().getSession();
        System.out.println(session.getId());
        Integer userId = 1;


        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        int totalPages = 0;
        List<WxMyCollect> collectList = null;
        //商品收藏(假设现在获取到了用户id)
        if (type == 0){
            PageHelper.startPage(page,size);
            collectList = wxCollectService.getGoodsCollectByUserId(userId);
            PageInfo<WxMyCollect> pageInfo = new PageInfo(collectList);
            totalPages = pageInfo.getPages();
            int pageNum = pageInfo.getPageNum();
        }

        //专题收藏
        if (type == 1){

        }

        data.put("collectList",collectList);
        data.put("totalPages",totalPages);
        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/collect/addordelete")
    public BaseRespVo addOrDeleteCollect(@RequestParam Integer type,Integer valueId){
        //获取shiro认证后的用户id（待修改）
        Integer userId = 1;

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        String opType = wxCollectService.addOrDeleteCollect(type,valueId,userId);
        data.put("type",opType);

        baseRespVo.setErrno(0);
        baseRespVo.setData(data);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
