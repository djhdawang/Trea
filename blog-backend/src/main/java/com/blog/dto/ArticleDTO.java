package com.blog.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * 文章数据传输对象
 */
@Data
public class ArticleDTO {

    private Long id;

    @NotBlank(message = "标题不能为空")
    private String title;

    @NotBlank(message = "内容不能为空")
    private String content;

    private String summary;

    private String cover;

    private Long categoryId;

    private Long authorId;

    private Integer viewCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer status;

    private Integer isTop;

    private Integer isRecommend;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /** 标签ID列表 */
    private Long[] tagIds;

    /** 分类名称 */
    private String categoryName;

    /** 作者昵称 */
    private String authorNickname;

    /** 标签列表 */
    private String tags;
}
