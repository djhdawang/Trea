package com.blog.service;

import com.blog.entity.Tag;
import com.blog.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签服务类
 */
@Service
public class TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 获取所有启用的标签
     */
    public List<Tag> getEnabledList() {
        return tagMapper.selectEnabledList();
    }

    /**
     * 获取所有标签（带文章数量）
     */
    public List<Tag> getTagListWithCount() {
        return tagMapper.selectTagListWithCount();
    }

    /**
     * 根据ID获取标签
     */
    public Tag getById(Long id) {
        return tagMapper.selectById(id);
    }

    /**
     * 创建标签
     */
    public void create(Tag tag) {
        tag.setArticleCount(0);
        tagMapper.insert(tag);
    }

    /**
     * 更新标签
     */
    public void update(Tag tag) {
        tagMapper.updateById(tag);
    }

    /**
     * 删除标签
     */
    public void delete(Long id) {
        tagMapper.deleteById(id);
    }
}
