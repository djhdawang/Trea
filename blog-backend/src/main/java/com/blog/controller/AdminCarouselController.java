package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Carousel;
import com.blog.service.CarouselService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/carousels")
@io.swagger.v3.oas.annotations.tags.Tag(name = "轮播图管理接口")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCarouselController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/list")
    @Operation(summary = "获取轮播图列表（管理）")
    public Result<List<Carousel>> getCarouselList() {
        return Result.success(carouselService.getAllList());
    }

    @PostMapping("/create")
    @Operation(summary = "创建轮播图")
    public Result<String> createCarousel(@RequestBody Carousel carousel) {
        carouselService.create(carousel);
        return Result.success("轮播图创建成功");
    }

    @PutMapping("/update")
    @Operation(summary = "更新轮播图")
    public Result<String> updateCarousel(@RequestBody Carousel carousel) {
        carouselService.update(carousel);
        return Result.success("轮播图更新成功");
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除轮播图")
    public Result<String> deleteCarousel(@PathVariable Long id) {
        carouselService.delete(id);
        return Result.success("轮播图删除成功");
    }
}
