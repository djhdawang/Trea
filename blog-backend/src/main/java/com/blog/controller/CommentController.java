package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/comments")
@io.swagger.v3.oas.annotations.tags.Tag(name = "前台评论接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{articleId}")
    @Operation(summary = "获取文章评论列表")
    public Result<List<Comment>> getArticleComments(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getArticleComments(articleId);
        return Result.success(comments);
    }

    @GetMapping("/replies/{parentId}")
    @Operation(summary = "获取评论回复")
    public Result<List<Comment>> getReplies(@PathVariable Long parentId) {
        List<Comment> replies = commentService.getReplies(parentId);
        return Result.success(replies);
    }

    @PostMapping("/add")
    @Operation(summary = "添加评论")
    public Result<String> addComment(@RequestBody Comment comment) {
        try {
            commentService.addComment(comment);
            return Result.success("评论提交成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
