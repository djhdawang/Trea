package com.blog.service;

import com.blog.entity.Category;
import com.blog.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 分类服务类
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有启用的分类
     */
    public List<Category> getEnabledList() {
        return categoryMapper.selectEnabledList();
    }

    /**
     * 获取所有分类（带文章数量）
     */
    public List<Category> getCategoryListWithCount() {
        return categoryMapper.selectCategoryListWithCount();
    }

    /**
     * 根据ID获取分类
     */
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    /**
     * 创建分类
     */
    public void create(Category category) {
        category.setArticleCount(0);
        categoryMapper.insert(category);
    }

    /**
     * 更新分类
     */
    public void update(Category category) {
        categoryMapper.updateById(category);
    }

    /**
     * 删除分类
     */
    public void delete(Long id) {
        categoryMapper.deleteById(id);
    }
}
