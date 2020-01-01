package com.cskaoyan.mapper;

import com.cskaoyan.bean.Topic;
import com.cskaoyan.bean.TopicExample;
import java.util.List;

import com.cskaoyan.bean.WxTopic;
import org.apache.ibatis.annotations.Param;

public interface TopicMapper {
    long countByExample(TopicExample example);

    int deleteByExample(TopicExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Topic record);

    int insertSelective(Topic record);

    List<Topic> selectByExampleWithBLOBs(TopicExample example);

    List<Topic> selectByExample(TopicExample example);

    Topic selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExampleWithBLOBs(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    int updateByPrimaryKeySelective(Topic record);

    int updateByPrimaryKeyWithBLOBs(Topic record);

    int updateByPrimaryKey(Topic record);

    List<Topic> getTopicList();

    WxTopic getTopicById(@Param("topicId") Integer topicId);

    List<WxTopic> getRelatedTopic(@Param("topic") WxTopic topic);
}
