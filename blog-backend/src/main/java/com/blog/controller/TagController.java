package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Tag;
import com.blog.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/tags")
@io.swagger.v3.oas.annotations.tags.Tag(name = "Front-end Tag API")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    @Operation(summary = "Get tag list")
    public Result<List<Tag>> getTagList() {
        List<Tag> tags = tagService.getEnabledList();
        return Result.success(tags);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get tag detail")
    public Result<Tag> getTagDetail(@PathVariable Long id) {
        Tag tag = tagService.getById(id);
        if (tag == null) {
            return Result.error("Tag not found");
        }
        return Result.success(tag);
    }
}
