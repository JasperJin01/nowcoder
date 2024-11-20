package com.nowcoder.community.dao;

import com.nowcoder.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// FIXME 这个评论到底是怎么组织数据结构的啊？

@Mapper
public interface CommentMapper {
    // FIXME 为啥报错啊？？不是声明class是interface！


    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType, int entityId);

    int insertComment(Comment comment);

}
