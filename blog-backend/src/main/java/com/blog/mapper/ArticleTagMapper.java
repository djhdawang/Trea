package com.blog.mapper;

import com.blog.entity.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 文章标签关联Mapper接口
 */
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

    /**
     * 查询文章的所有标签ID
     */
    List<Long> selectTagIdsByArticleId(@Param("articleId") Long articleId);

    /**
     * 根据文章ID删除关联记录
     */
    void deleteByArticleId(@Param("articleId") Long articleId);

    /**
     * 批量插入文章标签关联
     */
    void batchInsert(@Param("articleId") Long articleId, @Param("tagIds") List<Long> tagIds);
}
