package com.cskaoyan.service;

import com.cskaoyan.bean.CommittedTopicComment;
import com.cskaoyan.bean.TopicComment;
import com.cskaoyan.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentMapper commentMapper;

    @Override
    public List<TopicComment> getCommentByIdAndType(int valueId, int type) {
        List<TopicComment> list = commentMapper.getCommentByIdAndType(valueId,type);
        return list;
    }

    @Override
    public CommittedTopicComment commitTopicComment(CommittedTopicComment comment) {
        Integer id = commentMapper.addTopicComment(comment);
        CommittedTopicComment result = commentMapper.selectCommentById(id);
        return result;
    }
}
