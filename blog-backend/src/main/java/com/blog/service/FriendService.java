package com.blog.service;

import com.blog.entity.Friend;
import com.blog.mapper.FriendMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链服务类
 */
@Service
public class FriendService {

    @Autowired
    private FriendMapper friendMapper;

    /**
     * 获取已通过的友链列表
     */
    public List<Friend> getApprovedList() {
        return friendMapper.selectApprovedList();
    }

    /**
     * 获取所有友链
     */
    public List<Friend> getAllList() {
        return friendMapper.selectList(null);
    }

    /**
     * 创建友链申请
     */
    public void apply(Friend friend) {
        friend.setStatus(0);
        friendMapper.insert(friend);
    }

    /**
     * 审核通过友链
     */
    public void approve(Long id) {
        Friend friend = friendMapper.selectById(id);
        if (friend != null) {
            friend.setStatus(1);
            friendMapper.updateById(friend);
        }
    }

    /**
     * 删除友链
     */
    public void delete(Long id) {
        friendMapper.deleteById(id);
    }
}
