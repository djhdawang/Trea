package com.blog.mapper;

import com.blog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 标签Mapper接口
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 查询所有启用的标签
     */
    List<Tag> selectEnabledList();

    /**
     * 查询标签列表（带文章数量）
     */
    List<Tag> selectTagListWithCount();
}
