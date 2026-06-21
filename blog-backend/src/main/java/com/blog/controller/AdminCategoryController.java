package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Category;
import com.blog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@io.swagger.v3.oas.annotations.tags.Tag(name = "分类管理接口")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @Operation(summary = "获取分类列表（管理）")
    public Result<Object> getCategoryList() {
        return Result.success(categoryService.getCategoryListWithCount());
    }

    @PostMapping("/create")
    @Operation(summary = "创建分类")
    public Result<String> createCategory(@RequestBody Category category) {
        categoryService.create(category);
        return Result.success("分类创建成功");
    }

    @PutMapping("/update")
    @Operation(summary = "更新分类")
    public Result<String> updateCategory(@RequestBody Category category) {
        categoryService.update(category);
        return Result.success("分类更新成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除分类")
    public Result<String> deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success("分类删除成功");
    }
}
