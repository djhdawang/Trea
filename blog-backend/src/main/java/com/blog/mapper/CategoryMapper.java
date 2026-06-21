package com.blog.mapper;

import com.blog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 分类Mapper接口
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 查询所有启用的分类
     */
    List<Category> selectEnabledList();

    /**
     * 查询分类列表（带文章数量）
     */
    List<Category> selectCategoryListWithCount();
}
