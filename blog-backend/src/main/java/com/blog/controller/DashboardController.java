package com.blog.controller;

import com.blog.common.Result;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@io.swagger.v3.oas.annotations.tags.Tag(name = "数据看板接口")
public class DashboardController {

    @GetMapping("/statistics")
    @Operation(summary = "获取统计数据")
    public Result<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("articleCount", 4);
        stats.put("commentCount", 0);
        stats.put("categoryCount", 5);
        stats.put("tagCount", 8);
        stats.put("pendingCommentCount", 0);
        stats.put("todayViewCount", 6);
        stats.put("todayLikeCount", 5);
        return Result.success(stats);
    }

    @GetMapping("/chart/article/views")
    @Operation(summary = "获取文章浏览量图表数据")
    public Result<Object> getArticleViewChart() {
        List<Map<String, Object>> chartData = new ArrayList<>();
        // 示例数据：最近7天的浏览量趋势
        String[] dates = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
        int[] views = {120, 200, 150, 300, 280, 420, 380};
        for (int i = 0; i < dates.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("date", dates[i]);
            item.put("views", views[i]);
            chartData.add(item);
        }
        return Result.success(chartData);
    }

    @GetMapping("/chart/article/categories")
    @Operation(summary = "获取文章分类分布数据")
    public Result<Object> getCategoryChart() {
        List<Map<String, Object>> chartData = new ArrayList<>();
        // 示例数据：各分类的文章数量
        String[] categories = {"Java", "前端", "数据库", "架构", "工具"};
        int[] counts = {12, 8, 5, 3, 6};
        for (int i = 0; i < categories.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("name", categories[i]);
            item.put("value", counts[i]);
            chartData.add(item);
        }
        return Result.success(chartData);
    }
}
