package com.cskaoyan.service;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.WxTopic;

import java.util.List;

public interface WxTopicService {
    List<Topic> getTopicList();

    WxTopic getTopicById(Integer topicId);

    List<WxTopic> getRelatedTopicById(Integer topicId);
}
