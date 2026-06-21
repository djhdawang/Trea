package com.blog.service;

import com.blog.common.PageRequest;
import com.blog.common.PageResult;
import com.blog.common.PageUtil;
import com.blog.entity.Comment;
import com.blog.entity.User;
import com.blog.mapper.CommentMapper;
import com.blog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 评论服务类
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取文章的评论列表
     */
    public List<Comment> getArticleComments(Long articleId) {
        return commentMapper.selectArticleComments(articleId);
    }

    /**
     * 获取评论的回复列表
     */
    public List<Comment> getReplies(Long parentId) {
        return commentMapper.selectReplies(parentId);
    }

    /**
     * 添加评论
     */
    public void addComment(Comment comment) {
        comment.setStatus(1); // 直接通过审核
        comment.setReplyCount(0);
        commentMapper.insert(comment);
    }

    /**
     * 审核评论
     */
    public void approveComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment != null) {
            comment.setStatus(1);
            commentMapper.updateById(comment);
        }
    }

    /**
     * 拒绝评论
     */
    public void rejectComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment != null) {
            comment.setStatus(2);
            commentMapper.updateById(comment);
        }
    }

    /**
     * 删除评论
     */
    public void deleteComment(Long id) {
        commentMapper.deleteById(id);
    }

    /**
     * 获取待审核评论数量
     */
    public Long getPendingCount() {
        return commentMapper.selectPendingCount();
    }

    /**
     * 分页获取所有评论
     */
    public PageResult<Comment> getCommentPage(PageRequest request) {
        // 待实现：后台评论管理分页
        return null;
    }
}
