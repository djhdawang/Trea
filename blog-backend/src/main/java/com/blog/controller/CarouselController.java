package com.blog.controller;

import com.blog.common.Result;
import com.blog.entity.Carousel;
import com.blog.service.CarouselService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/carousels")
@io.swagger.v3.oas.annotations.tags.Tag(name = "前台轮播图接口")
public class CarouselController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/list")
    @Operation(summary = "获取轮播图列表")
    public Result<List<Carousel>> getCarouselList() {
        List<Carousel> carousels = carouselService.getEnabledList();
        return Result.success(carousels);
    }
}
