# 个人博客系统

一款轻量化、高颜值的现代化个人博客系统，基于 Vue3 + SpringBoot3 技术栈开发。

## 技术栈

### 前端
- Vue 3.4
- Element Plus 2.5
- Vite 5.0
- Vue Router 4
- Pinia 状态管理
- Axios
- Marked + Highlight.js

### 后端
- SpringBoot 3.2
- Mybatis Plus 3.5
- MySQL 8.0
- Druid 连接池
- JWT 认证
- Knife4j API文档

## 功能特性

### 前台展示
- 首页轮播图
- 文章列表（分页、搜索）
- 文章详情（Markdown渲染）
- 分类/标签筛选
- 评论区
- 归档时间线
- 暗色/亮色主题切换
- 响应式布局

### 后台管理
- 仪表盘统计
- 文章管理（富文本编辑）
- 分类管理
- 标签管理
- 评论审核
- 轮播图管理
- 友链管理
- 用户权限管理

## 项目结构

```
blog/
├── blog-backend/          # SpringBoot后端
│   ├── src/main/java/com/blog/
│   │   ├── config/       # 配置类
│   │   ├── controller/   # 控制器
│   │   ├── service/       # 服务层
│   │   ├── mapper/       # 数据层
│   │   ├── entity/       # 实体类
│   │   ├── dto/          # 数据传输对象
│   │   ├── common/       # 公共组件
│   │   └── util/         # 工具类
│   └── src/main/resources/
│       └── application.yml
├── blog-frontend/         # Vue3前端
│   ├── src/
│   │   ├── views/        # 页面组件
│   │   ├── components/   # 通用组件
│   │   ├── router/       # 路由配置
│   │   ├── store/        # 状态管理
│   │   ├── api/          # API接口
│   │   └── assets/       # 静态资源
│   └── package.json
├── sql/                   # 数据库脚本
│   └── blog.sql
└── scripts/               # 启动脚本
```

## 快速开始

### 环境要求
- JDK 17+
- Node.js 18+
- MySQL 8.0+
- Maven 3.8+

### 1. 数据库配置

```bash
# 登录MySQL
mysql -u root -p

# 创建数据库
CREATE DATABASE blog DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 导入SQL脚本
USE blog;
SOURCE sql/blog.sql;
```

### 2. 后端启动

```bash
# 进入后端目录
cd blog-backend

# 使用Maven构建
mvn clean package -DskipTests

# 运行
java -jar target/blog-backend-1.0.0.jar
```

或使用IDEA直接运行 `BlogApplication.java`

### 3. 前端启动

```bash
# 进入前端目录
cd blog-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

访问 http://localhost:3000

### 4. 一键启动（Windows）

```bash
# 在项目根目录执行
scripts\start-all.bat
```

## 默认账号

| 角色 | 用户名 | 密码 |
|------|--------|------|
| 管理员 | admin | admin123 |
| 普通用户 | user | user123 |

## API文档

启动后端后访问: http://localhost:8080/doc.html

## 配置说明

### 后端配置 (blog-backend/src/main/resources/application.yml)

```yaml
server:
  port: 8080  # 后端端口

spring:
  datasource:
    url: jdbc:mysql://localhost:3306/blog
    username: root      # 数据库用户名
    password: root     # 数据库密码

jwt:
  secret: your-secret-key    # JWT密钥
  expiration: 86400000        # 过期时间(毫秒)

blog:
  upload:
    path: D:/blog/uploads/    # 上传文件存储路径
    url: /api/upload/         # 上传访问路径
```

### 前端配置 (blog-frontend/vite.config.js)

```javascript
server: {
  port: 3000,                 # 前端端口
  proxy: {
    '/api': {
      target: 'http://localhost:8080',  # 后端地址
      changeOrigin: true
    }
  }
}
```

## Git提交规范

```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式
refactor: 重构
perf: 性能优化
test: 测试
chore: 构建/工具
```

示例：
```bash
git commit -m "feat: 添加文章点赞功能"
git commit -m "fix: 修复评论分页问题"
```

## 部署到GitHub

### 1. 创建GitHub仓库

```bash
# 初始化git
git init

# 添加远程仓库
git remote add origin https://github.com/yourusername/blog.git

# 添加文件
git add .

# 提交
git commit -m "feat: initial blog system"

# 推送
git push -u origin main
```

### 2. 克隆到服务器

```bash
git clone https://github.com/yourusername/blog.git
cd blog
```

### 3. 服务器部署

```bash
# 后端部署
cd blog-backend
nohup java -jar target/blog-backend-1.0.0.jar &

# 前端构建
cd blog-frontend
npm install
npm run build
# 将dist目录部署到nginx
```

## 项目截图

[待添加]

## License

MIT License
