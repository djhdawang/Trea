package com.blog.service;

import com.blog.entity.Carousel;
import com.blog.mapper.CarouselMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图服务类
 */
@Service
public class CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 获取启用的轮播图列表
     */
    public List<Carousel> getEnabledList() {
        return carouselMapper.selectEnabledList();
    }

    /**
     * 获取所有轮播图
     */
    public List<Carousel> getAllList() {
        return carouselMapper.selectList(null);
    }

    /**
     * 创建轮播图
     */
    public void create(Carousel carousel) {
        carouselMapper.insert(carousel);
    }

    /**
     * 更新轮播图
     */
    public void update(Carousel carousel) {
        carouselMapper.updateById(carousel);
    }

    /**
     * 删除轮播图
     */
    public void delete(Long id) {
        carouselMapper.deleteById(id);
    }
}
