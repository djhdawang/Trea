-- ============================================
-- 个人博客系统数据库初始化脚本
-- 数据库: MySQL 8.0+
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE blog;

-- ============================================
-- 1. 用户表
-- ============================================
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码',
    nickname VARCHAR(100) DEFAULT '' COMMENT '昵称',
    avatar VARCHAR(500) DEFAULT '' COMMENT '头像',
    email VARCHAR(100) DEFAULT '' COMMENT '邮箱',
    phone VARCHAR(20) DEFAULT '' COMMENT '手机号',
    role VARCHAR(20) DEFAULT 'user' COMMENT '角色: admin, user',
    status TINYINT DEFAULT 1 COMMENT '状态: 0禁用, 1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除: 0未删除, 1已删除',
    INDEX idx_username (username),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ============================================
-- 2. 分类表
-- ============================================
DROP TABLE IF EXISTS blog_category;
CREATE TABLE blog_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '分类ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    icon VARCHAR(255) DEFAULT '' COMMENT '分类图标',
    sort INT DEFAULT 0 COMMENT '排序',
    article_count INT DEFAULT 0 COMMENT '文章数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='分类表';

-- ============================================
-- 3. 标签表
-- ============================================
DROP TABLE IF EXISTS blog_tag;
CREATE TABLE blog_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    name VARCHAR(50) NOT NULL COMMENT '标签名称',
    color VARCHAR(20) DEFAULT '#409EFF' COMMENT '标签颜色',
    article_count INT DEFAULT 0 COMMENT '文章数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_name (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- ============================================
-- 4. 文章表
-- ============================================
DROP TABLE IF EXISTS blog_article;
CREATE TABLE blog_article (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文章ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    content LONGTEXT NOT NULL COMMENT '内容',
    summary VARCHAR(500) DEFAULT '' COMMENT '摘要',
    cover VARCHAR(500) DEFAULT '' COMMENT '封面图',
    category_id BIGINT COMMENT '分类ID',
    author_id BIGINT COMMENT '作者ID',
    view_count INT DEFAULT 0 COMMENT '浏览量',
    like_count INT DEFAULT 0 COMMENT '点赞数',
    comment_count INT DEFAULT 0 COMMENT '评论数',
    status TINYINT DEFAULT 1 COMMENT '状态: 0草稿, 1发布',
    is_top TINYINT DEFAULT 0 COMMENT '是否置顶: 0否, 1是',
    is_recommend TINYINT DEFAULT 0 COMMENT '是否推荐: 0否, 1是',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_category (category_id),
    INDEX idx_author (author_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time),
    INDEX idx_view_count (view_count),
    INDEX idx_like_count (like_count),
    FULLTEXT idx_title_content (title, content)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章表';

-- ============================================
-- 5. 文章标签关联表
-- ============================================
DROP TABLE IF EXISTS blog_article_tag;
CREATE TABLE blog_article_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    INDEX idx_article (article_id),
    INDEX idx_tag (tag_id),
    UNIQUE KEY uk_article_tag (article_id, tag_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文章标签关联表';

-- ============================================
-- 6. 评论表
-- ============================================
DROP TABLE IF EXISTS blog_comment;
CREATE TABLE blog_comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '评论ID',
    article_id BIGINT NOT NULL COMMENT '文章ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论ID',
    user_id BIGINT COMMENT '用户ID',
    content TEXT NOT NULL COMMENT '评论内容',
    nickname VARCHAR(100) DEFAULT '' COMMENT '评论者昵称',
    email VARCHAR(100) DEFAULT '' COMMENT '评论者邮箱',
    avatar VARCHAR(500) DEFAULT '' COMMENT '评论者头像',
    status TINYINT DEFAULT 0 COMMENT '审核状态: 0待审核, 1通过, 2拒绝',
    reply_count INT DEFAULT 0 COMMENT '回复数量',
    ip_address VARCHAR(50) DEFAULT '' COMMENT 'IP地址',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_article (article_id),
    INDEX idx_parent (parent_id),
    INDEX idx_status (status),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ============================================
-- 7. 轮播图表
-- ============================================
DROP TABLE IF EXISTS blog_carousel;
CREATE TABLE blog_carousel (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '轮播图ID',
    title VARCHAR(100) DEFAULT '' COMMENT '标题',
    image_url VARCHAR(500) NOT NULL COMMENT '图片URL',
    link_url VARCHAR(500) DEFAULT '' COMMENT '跳转链接',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态: 0禁用, 1启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_sort (sort),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='轮播图表';

-- ============================================
-- 8. 友链表
-- ============================================
DROP TABLE IF EXISTS blog_friend;
CREATE TABLE blog_friend (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '友链ID',
    name VARCHAR(100) NOT NULL COMMENT '网站名称',
    description VARCHAR(500) DEFAULT '' COMMENT '网站描述',
    url VARCHAR(500) NOT NULL COMMENT '网站链接',
    logo VARCHAR(500) DEFAULT '' COMMENT '网站图标',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 0 COMMENT '状态: 0待审核, 1通过',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '逻辑删除',
    INDEX idx_status (status),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='友链表';

-- ============================================
-- 初始化数据
-- ============================================

-- 插入管理员账号 (密码: admin123)
INSERT INTO sys_user (username, password, nickname, avatar, email, role, status) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '管理员', '', 'admin@blog.com', 'admin', 1);

-- 插入测试用户 (密码: user123)
INSERT INTO sys_user (username, password, nickname, avatar, email, role, status) VALUES
('user', '$2a$10$XuO2rXK3y4H8QDi8Qb376OuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '普通用户', '', 'user@blog.com', 'user', 1);

-- 插入分类
INSERT INTO blog_category (name, icon, sort) VALUES
('技术分享', 'code', 1),
('生活随笔', 'book', 2),
('前端开发', 'color', 3),
('后端开发', 'server', 4),
('DevOps', 'cloud', 5);

-- 插入标签
INSERT INTO blog_tag (name, color) VALUES
('Vue3', '#42b983'),
('React', '#61dafb'),
('SpringBoot', '#6db33f'),
('Java', '#007396'),
('Python', '#3776ab'),
('Docker', '#2496ed'),
('Kubernetes', '#326ce5'),
('Git', '#f05032');

-- 插入文章
INSERT INTO blog_article (title, content, summary, cover, category_id, author_id, status, is_top, is_recommend) VALUES
('欢迎访问我的个人博客', '<p>欢迎来到我的个人博客！这是一个分享技术与生活的地方。</p><p>在这里，你可以找到关于Web开发、技术分享、生活感悟等各种内容。</p>', '欢迎来到我的个人博客，这是一个分享技术与生活的地方。', '', 2, 1, 1, 1, 1),
('Vue3 Composition API 入门指南', '<p>Vue3引入了Composition API，这是一种新的组织组件逻辑的方式。本文将带你了解如何使用Vue3的Composition API。</p><h2>什么是Composition API？</h2><p>Composition API是Vue3新增的一种编写组件逻辑的方式，它允许我们使用导入的函数来组织组件逻辑，而不是只能通过选项（data、methods、computed等）来组织。</p>', 'Vue3 Composition API是一种新的组织组件逻辑的方式，本文将带你快速入门。', '', 1, 1, 1, 0, 1),
('SpringBoot3 + Mybatis Plus 实战', '<p>本文介绍如何使用SpringBoot3和Mybatis Plus快速开发RESTful API。</p><h2>项目初始化</h2><p>使用Spring Initializr创建项目，添加Mybatis Plus依赖即可快速开始。</p>', 'SpringBoot3与Mybatis Plus的结合使用，让你快速开发RESTful API。', '', 4, 1, 1, 0, 1),
('Docker 容器化部署实战', '<p>Docker已经成为现代应用部署的标准工具。本文介绍如何使用Docker容器化部署你的应用。</p><h2>Dockerfile编写</h2><p>首先，我们需要编写Dockerfile来定义镜像。</p>', 'Docker容器化部署实战，教你如何将应用容器化并部署到生产环境。', '', 5, 1, 1, 0, 0);

-- 插入文章标签关联
INSERT INTO blog_article_tag (article_id, tag_id) VALUES
(2, 1), (2, 4),
(3, 3), (3, 4),
(4, 6), (4, 7);

-- 插入轮播图
INSERT INTO blog_carousel (title, image_url, link_url, sort, status) VALUES
('欢迎来到博客', 'https://picsum.photos/1200/400?random=1', '/', 1, 1),
('Vue3正式发布', 'https://picsum.photos/1200/400?random=2', '/article/2', 2, 1),
('Docker入门教程', 'https://picsum.photos/1200/400?random=3', '/article/4', 3, 1);

-- 插入友链
INSERT INTO blog_friend (name, description, url, logo, sort, status) VALUES
('GitHub', '全球最大的代码托管平台', 'https://github.com', '', 1, 1),
('Stack Overflow', '程序员问答社区', 'https://stackoverflow.com', '', 2, 1),
('掘金', '程序员社区', 'https://juejin.cn', '', 3, 1);

-- 插入评论
INSERT INTO blog_comment (article_id, parent_id, user_id, content, nickname, email, status) VALUES
(1, 0, NULL, '欢迎欢迎！', '访客1', 'visitor1@email.com', 1),
(1, 0, NULL, '博客看起来很不错！', '访客2', 'visitor2@email.com', 1),
(2, 0, NULL, '写得很好，对我帮助很大！', '访客3', 'visitor3@email.com', 1),
(2, 3, NULL, '同意，确实很实用！', '访客4', 'visitor4@email.com', 1);
