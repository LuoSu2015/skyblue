package com.cskaoyan.mapper;

import com.cskaoyan.bean.Comment;
import com.cskaoyan.bean.CommentExample;
import java.util.List;

import com.cskaoyan.bean.CommittedTopicComment;
import com.cskaoyan.bean.TopicComment;
import org.apache.ibatis.annotations.Param;

public interface CommentMapper {
    long countByExample(CommentExample example);

    int deleteByExample(CommentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExample(CommentExample example);

    Comment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<TopicComment> getCommentByIdAndType(@Param("valueId") int valueId, @Param("type") int type);

    Integer addTopicComment(@Param("comment") CommittedTopicComment comment);

    CommittedTopicComment selectCommentById(@Param("id") Integer id);
}
