package com.blog.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 友链实体类
 */
@Data
@TableName("blog_friend")
public class Friend {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 网站名称 */
    private String name;

    /** 网站描述 */
    private String description;

    /** 网站链接 */
    private String url;

    /** 网站图标 */
    private String logo;

    /** 排序 */
    private Integer sort;

    /** 状态: 0待审核, 1通过 */
    private Integer status;

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
