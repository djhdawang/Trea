package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.User;
import com.blog.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Test API")
public class TestController {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public TestController(PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping("/ping")
    public Result<String> ping() {
        return Result.success("pong");
    }

    @GetMapping("/hash/{password}")
    public Result<String> hashPassword(@PathVariable String password) {
        String hash = passwordEncoder.encode(password);
        return Result.success(hash);
    }

    @PostMapping("/update-password")
    public Result<String> updatePassword(@RequestParam String username, @RequestParam String password) {
        User user = userMapper.selectByUsername(username);
        if (user == null) {
            return Result.error("用户不存在");
        }
        String hash = passwordEncoder.encode(password);
        user.setPassword(hash);
        userMapper.updateById(user);
        return Result.success("密码更新成功");
    }
}
