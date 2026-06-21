package com.blog.mapper;

import com.blog.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 轮播图Mapper接口
 */
@Mapper
public interface CarouselMapper extends BaseMapper<Carousel> {

    /**
     * 查询启用的轮播图列表
     */
    List<Carousel> selectEnabledList();
}
