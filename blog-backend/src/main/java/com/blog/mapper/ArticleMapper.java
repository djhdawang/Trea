package com.blog.mapper;

import com.blog.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 文章Mapper接口
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 查询已发布文章列表
     */
    List<Article> selectPublishedList(@Param("keyword") String keyword, 
                                       @Param("categoryId") Long categoryId,
                                       @Param("tagId") Long tagId,
                                       @Param("orderBy") String orderBy);

    /**
     * 查询所有文章列表（后台管理）
     */
    List<Article> selectAllList();

    /**
     * 查询已发布文章数量
     */
    Long selectPublishedCount(@Param("keyword") String keyword,
                               @Param("categoryId") Long categoryId,
                               @Param("tagId") Long tagId);

    /**
     * 查询热门文章
     */
    List<Article> selectHotArticles(@Param("limit") Integer limit);

    /**
     * 查询推荐文章
     */
    List<Article> selectRecommendArticles(@Param("limit") Integer limit);

    /**
     * 查询文章归档列表
     */
    List<Article> selectArchiveList();

    /**
     * 增加浏览量
     */
    void incrementViewCount(@Param("id") Long id);

    /**
     * 增加点赞数
     */
    void incrementLikeCount(@Param("id") Long id);

    /**
     * 减少点赞数
     */
    void decrementLikeCount(@Param("id") Long id);

    /**
     * 统计今日浏览量
     */
    Long sumTodayViews(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    /**
     * 统计今日点赞数
     */
    Long sumTodayLikes(@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);
}
