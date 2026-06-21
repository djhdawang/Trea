package com.blog.common;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;

import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分页工具类
 */
public class PageUtil {

    public static <T> Page<T> buildPage(PageRequest request) {
        return new Page<>(request.getPageNum(), request.getPageSize());
    }

    public static <T, R> PageResult<T> buildPageResult(IPage<R> page, Function<R, T> converter) {
        return PageResult.of(
            page.getTotal(),
            page.getRecords().stream().map(converter).collect(Collectors.toList()),
            (int) page.getCurrent(),
            (int) page.getSize()
        );
    }

    public static <T> PageResult<T> buildPageResult(IPage<T> page) {
        return PageResult.of(
            page.getTotal(),
            page.getRecords(),
            (int) page.getCurrent(),
            (int) page.getSize()
        );
    }

    public static <T> PageResult<T> buildPageResult(Long total, java.util.List<T> list, Integer pageNum, Integer pageSize) {
        return PageResult.of(total, list, pageNum, pageSize);
    }
}
