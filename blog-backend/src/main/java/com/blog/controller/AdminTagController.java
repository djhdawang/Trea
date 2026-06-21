package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag Management API")
@PreAuthorize("hasRole('ADMIN')")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @Operation(summary = "Get tag list (admin)")
    public Result<Object> getTagList() {
        return Result.success(tagService.getTagListWithCount());
    }

    @PostMapping("/create")
    @Operation(summary = "Create tag")
    public Result<String> createTag(@RequestBody Tag tag) {
        tagService.create(tag);
        return Result.success("Tag created successfully");
    }

    @PutMapping("/update")
    @Operation(summary = "Update tag")
    public Result<String> updateTag(@RequestBody Tag tag) {
        tagService.update(tag);
        return Result.success("Tag updated successfully");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete tag")
    public Result<String> deleteTag(@PathVariable Long id) {
        tagService.delete(id);
        return Result.success("Tag deleted successfully");
    }
}
