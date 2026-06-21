package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/comments")
@io.swagger.v3.oas.annotations.tags.Tag(name = "评论管理接口")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/pending/count")
    @Operation(summary = "获取待审核评论数量")
    public Result<Long> getPendingCount() {
        Long count = commentService.getPendingCount();
        return Result.success(count);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审核通过评论")
    public Result<String> approveComment(@PathVariable Long id) {
        commentService.approveComment(id);
        return Result.success("评论审核通过");
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "拒绝评论")
    public Result<String> rejectComment(@PathVariable Long id) {
        commentService.rejectComment(id);
        return Result.success("评论已拒绝");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除评论")
    public Result<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return Result.success("评论删除成功");
    }
}
