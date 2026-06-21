package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Friend;
import com.blog.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/friends")
@io.swagger.v3.oas.annotations.tags.Tag(name = "友链管理接口")
@PreAuthorize("hasRole('ADMIN')")
public class AdminFriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/list")
    @Operation(summary = "获取友链列表（管理）")
    public Result<List<Friend>> getFriendList() {
        return Result.success(friendService.getAllList());
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "审核通过友链")
    public Result<String> approveFriend(@PathVariable Long id) {
        friendService.approve(id);
        return Result.success("友链审核通过");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除友链")
    public Result<String> deleteFriend(@PathVariable Long id) {
        friendService.delete(id);
        return Result.success("友链删除成功");
    }
}
