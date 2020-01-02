package com.cskaoyan.service;

import com.cskaoyan.bean.CommittedTopicComment;
import com.cskaoyan.bean.TopicComment;

import java.util.List;

public interface CommentService {
    List<TopicComment> getCommentByIdAndType(int valueId, int type);

    CommittedTopicComment commitTopicComment(CommittedTopicComment comment);
}
