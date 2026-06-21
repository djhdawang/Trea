package com.blog.mapper;

import com.blog.entity.Friend;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 友链Mapper接口
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {

    /**
     * 查询已通过的友链列表
     */
    List<Friend> selectApprovedList();
}
