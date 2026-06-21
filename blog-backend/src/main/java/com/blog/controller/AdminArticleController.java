package com.blog.controller;

import com.blog.common.Result;
import com.blog.dto.ArticleDTO;
import com.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/articles")
@io.swagger.v3.oas.annotations.tags.Tag(name = "文章管理接口")
@PreAuthorize("hasRole('ADMIN')")
public class AdminArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    @Operation(summary = "Get article list (admin)")
    public Result<Object> getArticleList() {
        return Result.success(articleService.getArticleList());
    }

    @PostMapping("/create")
    @Operation(summary = "创建文章")
    public Result<String> createArticle(@RequestBody ArticleDTO dto) {
        try {
            articleService.createArticle(dto);
            return Result.success("文章创建成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update")
    @Operation(summary = "更新文章")
    public Result<String> updateArticle(@RequestBody ArticleDTO dto) {
        try {
            articleService.updateArticle(dto);
            return Result.success("文章更新成功");
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除文章")
    public Result<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return Result.success("文章删除成功");
    }
}
