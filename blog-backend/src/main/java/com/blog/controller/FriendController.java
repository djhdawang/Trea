package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Friend;
import com.blog.service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/friends")
@io.swagger.v3.oas.annotations.tags.Tag(name = "前台友链接口")
public class FriendController {

    @Autowired
    private FriendService friendService;

    @GetMapping("/list")
    @Operation(summary = "获取友链列表")
    public Result<List<Friend>> getFriendList() {
        List<Friend> friends = friendService.getApprovedList();
        return Result.success(friends);
    }

    @PostMapping("/apply")
    @Operation(summary = "申请友链")
    public Result<String> applyFriend(@RequestBody Friend friend) {
        try {
            friendService.apply(friend);
            return Result.success("友链申请已提交");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}
