package com.blog.service;

import com.blog.common.PageRequest;
import com.blog.common.PageResult;
import com.blog.common.PageUtil;
import com.blog.entity.Article;
import com.blog.entity.ArticleTag;
import com.blog.entity.Category;
import com.blog.entity.Tag;
import com.blog.mapper.*;
import com.blog.dto.ArticleDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章服务类
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private ArticleTagMapper articleTagMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取所有文章列表（后台管理）
     */
    public List<ArticleDTO> getArticleList() {
        List<Article> articles = articleMapper.selectAllList();
        return convertToDTOList(articles);
    }

    /**
     * 分页查询文章列表
     */
    public PageResult<ArticleDTO> getArticlePage(PageRequest request) {
        Page<Article> page = new Page<>(request.getPageNum(), request.getPageSize());
        List<Article> articles = articleMapper.selectPublishedList(
            request.getKeyword(), request.getCategoryId(), request.getTagId(), request.getOrderBy());
        Long total = articleMapper.selectPublishedCount(
            request.getKeyword(), request.getCategoryId(), request.getTagId());
        
        List<ArticleDTO> dtos = convertToDTOList(articles);
        return PageUtil.buildPageResult(total, dtos, request.getPageNum(), request.getPageSize());
    }

    /**
     * 获取文章详情
     */
    public ArticleDTO getArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return null;
        }
        // 增加浏览量
        articleMapper.incrementViewCount(id);
        return convertToDTO(article);
    }

    /**
     * 获取热门文章
     */
    public List<ArticleDTO> getHotArticles(Integer limit) {
        List<Article> articles = articleMapper.selectHotArticles(limit);
        return convertToDTOList(articles);
    }

    /**
     * 获取推荐文章
     */
    public List<ArticleDTO> getRecommendArticles(Integer limit) {
        List<Article> articles = articleMapper.selectRecommendArticles(limit);
        return convertToDTOList(articles);
    }

    /**
     * 获取归档列表
     */
    public List<ArticleDTO> getArchiveList() {
        List<Article> articles = articleMapper.selectArchiveList();
        return convertToDTOList(articles);
    }

    /**
     * 获取所有文章（后台管理）
     */
    public PageResult<ArticleDTO> getAllArticlePage(PageRequest request) {
        Page<Article> page = PageUtil.buildPage(request);
        IPage<Article> result = articleMapper.selectPublishedList(
            request.getKeyword(), request.getCategoryId(), request.getTagId(), request.getOrderBy()
        ) == null ? new Page<>() : null;
        
        if (result == null) {
            result = new Page<>(request.getPageNum(), request.getPageSize());
        }
        
        List<ArticleDTO> dtos = convertToDTOList(result.getRecords());
        return PageUtil.buildPageResult(result.getTotal(), dtos, request.getPageNum(), request.getPageSize());
    }

    /**
     * 创建文章
     */
    @Transactional
    public void createArticle(ArticleDTO dto) {
        Article article = new Article();
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setSummary(dto.getSummary());
        article.setCover(dto.getCover());
        article.setCategoryId(dto.getCategoryId());
        article.setAuthorId(dto.getAuthorId());
        article.setStatus(dto.getStatus() != null ? dto.getStatus() : 1);
        article.setIsTop(dto.getIsTop() != null ? dto.getIsTop() : 0);
        article.setIsRecommend(dto.getIsRecommend() != null ? dto.getIsRecommend() : 0);
        article.setViewCount(0);
        article.setLikeCount(0);
        article.setCommentCount(0);
        
        articleMapper.insert(article);
        
        // 保存标签关联
        if (dto.getTagIds() != null && dto.getTagIds().length > 0) {
            saveArticleTags(article.getId(), dto.getTagIds());
        }
    }

    /**
     * 更新文章
     */
    @Transactional
    public void updateArticle(ArticleDTO dto) {
        Article article = articleMapper.selectById(dto.getId());
        if (article == null) {
            throw new RuntimeException("文章不存在");
        }
        
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setSummary(dto.getSummary());
        article.setCover(dto.getCover());
        article.setCategoryId(dto.getCategoryId());
        article.setStatus(dto.getStatus() != null ? dto.getStatus() : article.getStatus());
        article.setIsTop(dto.getIsTop() != null ? dto.getIsTop() : article.getIsTop());
        article.setIsRecommend(dto.getIsRecommend() != null ? dto.getIsRecommend() : article.getIsRecommend());
        
        articleMapper.updateById(article);
        
        // 更新标签关联
        if (dto.getTagIds() != null) {
            articleTagMapper.deleteByArticleId(dto.getId());
            if (dto.getTagIds().length > 0) {
                saveArticleTags(dto.getId(), dto.getTagIds());
            }
        }
    }

    /**
     * 删除文章
     */
    @Transactional
    public void deleteArticle(Long id) {
        articleMapper.deleteById(id);
        articleTagMapper.deleteByArticleId(id);
    }

    /**
     * 点赞文章
     */
    public void likeArticle(Long id) {
        articleMapper.incrementLikeCount(id);
    }

    /**
     * 取消点赞
     */
    public void unlikeArticle(Long id) {
        articleMapper.decrementLikeCount(id);
    }

    /**
     * 保存文章标签关联
     */
    private void saveArticleTags(Long articleId, Long[] tagIds) {
        List<Long> tagIdList = new ArrayList<>();
        for (Long tagId : tagIds) {
            if (tagId != null) {
                tagIdList.add(tagId);
            }
        }
        if (!tagIdList.isEmpty()) {
            articleTagMapper.batchInsert(articleId, tagIdList);
        }
    }

    /**
     * 转换Article为ArticleDTO
     */
    private ArticleDTO convertToDTO(Article article) {
        if (article == null) {
            return null;
        }
        ArticleDTO dto = new ArticleDTO();
        dto.setId(article.getId());
        dto.setTitle(article.getTitle());
        dto.setContent(article.getContent());
        dto.setSummary(article.getSummary());
        dto.setCover(article.getCover());
        dto.setCategoryId(article.getCategoryId());
        dto.setAuthorId(article.getAuthorId());
        dto.setViewCount(article.getViewCount());
        dto.setLikeCount(article.getLikeCount());
        dto.setCommentCount(article.getCommentCount());
        dto.setStatus(article.getStatus());
        dto.setIsTop(article.getIsTop());
        dto.setIsRecommend(article.getIsRecommend());
        dto.setCreateTime(article.getCreateTime());
        dto.setUpdateTime(article.getUpdateTime());

        // 设置分类名称
        if (article.getCategoryId() != null) {
            Category category = categoryMapper.selectById(article.getCategoryId());
            if (category != null) {
                dto.setCategoryName(category.getName());
            }
        }

        // 设置作者昵称
        if (article.getAuthorId() != null) {
            var user = userMapper.selectById(article.getAuthorId());
            if (user != null) {
                dto.setAuthorNickname(user.getNickname());
            }
        }

        // 设置标签列表
        List<Long> tagIds = articleTagMapper.selectTagIdsByArticleId(article.getId());
        if (!tagIds.isEmpty()) {
            List<Tag> tags = tagMapper.selectBatchIds(tagIds);
            dto.setTags(tags.stream().map(Tag::getName).reduce((a, b) -> a + "," + b).orElse(""));
        }

        return dto;
    }

    /**
     * 转换Article列表为ArticleDTO列表
     */
    private List<ArticleDTO> convertToDTOList(List<Article> articles) {
        List<ArticleDTO> dtos = new ArrayList<>();
        for (Article article : articles) {
            dtos.add(convertToDTO(article));
        }
        return dtos;
    }
}
