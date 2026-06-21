package com.blog.controller;

import com.blog.common.PageRequest;
import com.blog.common.PageResult;
import com.blog.common.Result;
import com.blog.dto.ArticleDTO;
import com.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/articles")
@io.swagger.v3.oas.annotations.tags.Tag(name = "前台文章接口")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @Operation(summary = "获取文章列表")
    public Result<PageResult<ArticleDTO>> getArticleList(PageRequest request) {
        PageResult<ArticleDTO> page = articleService.getArticlePage(request);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文章详情")
    public Result<ArticleDTO> getArticleDetail(@PathVariable Long id) {
        ArticleDTO article = articleService.getArticleById(id);
        if (article == null) {
            return Result.error("文章不存在");
        }
        return Result.success(article);
    }

    @GetMapping("/hot")
    @Operation(summary = "获取热门文章")
    public Result<List<ArticleDTO>> getHotArticles(@RequestParam(defaultValue = "5") Integer limit) {
        List<ArticleDTO> articles = articleService.getHotArticles(limit);
        return Result.success(articles);
    }

    @GetMapping("/recommend")
    @Operation(summary = "获取推荐文章")
    public Result<List<ArticleDTO>> getRecommendArticles(@RequestParam(defaultValue = "5") Integer limit) {
        List<ArticleDTO> articles = articleService.getRecommendArticles(limit);
        return Result.success(articles);
    }

    @GetMapping("/archive")
    @Operation(summary = "获取归档列表")
    public Result<List<ArticleDTO>> getArchiveList() {
        List<ArticleDTO> articles = articleService.getArchiveList();
        return Result.success(articles);
    }

    @PostMapping("/{id}/like")
    @Operation(summary = "点赞文章")
    public Result<String> likeArticle(@PathVariable Long id) {
        articleService.likeArticle(id);
        return Result.success("点赞成功");
    }

    @PostMapping("/{id}/unlike")
    @Operation(summary = "取消点赞")
    public Result<String> unlikeArticle(@PathVariable Long id) {
        articleService.unlikeArticle(id);
        return Result.success("取消点赞成功");
    }
}
