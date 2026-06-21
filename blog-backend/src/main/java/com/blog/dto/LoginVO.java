package com.blog.dto;

import lombok.Data;

/**
 * 登录响应DTO
 */
@Data
public class LoginVO {

    private String token;
    private Long userId;
    private String username;
    private String nickname;
    private String avatar;
    private String role;
}
