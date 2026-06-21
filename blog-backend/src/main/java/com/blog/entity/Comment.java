package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 评论实体类
 */
@Data
@TableName("blog_comment")
public class Comment {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 文章ID */
    private Long articleId;

    /** 父评论ID */
    private Long parentId;

    /** 用户ID */
    private Long userId;

    /** 评论内容 */
    private String content;

    /** 评论者昵称 */
    private String nickname;

    /** 评论者邮箱 */
    private String email;

    /** 评论者头像 */
    private String avatar;

    /** 审核状态: 0待审核, 1通过, 2拒绝 */
    private Integer status;

    /** 回复数量 */
    private Integer replyCount;

    /** IP地址 */
    private String ipAddress;

    /** 创建时间 */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /** 更新时间 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;
}
