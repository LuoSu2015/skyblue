package com.cskaoyan.controller;

import com.cskaoyan.bean.BaseRespVo;
import com.cskaoyan.bean.CommittedTopicComment;
import com.cskaoyan.bean.Goods;
import com.cskaoyan.bean.TopicComment;
import com.cskaoyan.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("wx/comment/list")
    public BaseRespVo commentList(String page,String size,String valueId,String type){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();
        int currentPage = Integer.parseInt(page);

        //showtype 不知道干啥的？
        PageHelper.startPage(currentPage,Integer.parseInt(size));
        List<TopicComment> list = commentService.getCommentByIdAndType(Integer.parseInt(valueId),Integer.parseInt(type));
        PageInfo<TopicComment> pageInfo = new PageInfo(list);
        long count = pageInfo.getTotal();

        data.put("count",count);
        data.put("currentPage",currentPage);
        data.put("data",list);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/comment/count")
    public BaseRespVo commentCount(String valueId,String type){
        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();
        Map<String,Object> data = new HashMap<>();

        List<TopicComment> list = commentService.getCommentByIdAndType(Integer.parseInt(valueId),Integer.parseInt(type));
        Integer allCount = list.size();
        Integer hasPicCount = 0;
        for (TopicComment comment : list) {
            if (comment.getPicList().length != 0){
                hasPicCount++;
            }
        }
        data.put("hasPicCount",hasPicCount);
        data.put("allCount",allCount);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }

    @RequestMapping("wx/comment/post")
    public BaseRespVo postComment(@RequestBody CommittedTopicComment comment){
        //先提交用户身份的id认证，再提交
        Integer userId = 1;
        comment.setUserId(userId);

        BaseRespVo<Object> baseRespVo = new BaseRespVo<>();

        CommittedTopicComment data = commentService.commitTopicComment(comment);

        baseRespVo.setData(data);
        baseRespVo.setErrno(0);
        baseRespVo.setErrmsg("成功");
        return baseRespVo;
    }
}
