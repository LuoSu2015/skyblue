package com.cskaoyan.service;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.WxTopic;
import com.cskaoyan.mapper.TopicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxTopicServiceImpl implements WxTopicService {
    @Autowired
    TopicMapper topicMapper;

    @Override
    public List<Topic> getTopicList() {
        List<Topic> list = topicMapper.getTopicList();
        return list;
    }

    @Override
    public WxTopic getTopicById(Integer topicId) {
        WxTopic topic = topicMapper.getTopicById(topicId);
        return topic;
    }

    @Override
    public List<WxTopic> getRelatedTopicById(Integer topicId) {
        WxTopic topic = topicMapper.getTopicById(topicId);
        List<WxTopic> list = topicMapper.getRelatedTopic(topic);
        return list;
    }
}
