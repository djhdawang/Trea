package com.blog.mapper;

import com.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 评论Mapper接口
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

    /**
     * 查询文章评论列表
     */
    List<Comment> selectArticleComments(@Param("articleId") Long articleId);

    /**
     * 查询评论的回复列表
     */
    List<Comment> selectReplies(@Param("parentId") Long parentId);

    /**
     * 查询待审核评论数量
     */
    Long selectPendingCount();
}
