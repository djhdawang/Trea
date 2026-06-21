<template>
  <div class="admin-layout">
    <aside class="sidebar">
      <div class="logo">
        <router-link to="/admin">博客管理</router-link>
      </div>
      <nav class="nav">
        <router-link to="/admin" class="nav-item" :class="{ active: route.path === '/admin' }">
          <el-icon><data-board /></el-icon>
          <span>数据看板</span>
        </router-link>
        <router-link to="/admin/articles" class="nav-item">
          <el-icon><document /></el-icon>
          <span>文章管理</span>
        </router-link>
        <router-link to="/admin/categories" class="nav-item">
          <el-icon><folder /></el-icon>
          <span>分类管理</span>
        </router-link>
        <router-link to="/admin/tags" class="nav-item">
          <el-icon><price-tag /></el-icon>
          <span>标签管理</span>
        </router-link>
        <router-link to="/admin/comments" class="nav-item">
          <el-icon><chat-dot-square /></el-icon>
          <span>评论管理</span>
        </router-link>
        <router-link to="/admin/carousels" class="nav-item">
          <el-icon><picture /></el-icon>
          <span>轮播图</span>
        </router-link>
        <router-link to="/admin/friends" class="nav-item">
          <el-icon><link /></el-icon>
          <span>友链管理</span>
        </router-link>
      </nav>
    </aside>

    <div class="main">
      <header class="header">
        <div class="header-left">
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{ path: '/admin' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>{{ currentRoute }}</el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        <div class="header-right">
          <el-button type="primary" text @click="$router.push('/')">
            <el-icon><view /></el-icon> 查看前台
          </el-button>
          <el-dropdown>
            <span class="user-info">
              {{ user?.nickname || user?.username }}
              <el-icon><arrow-down /></el-icon>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <main class="content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useBlogStore } from '@/store/blog'
import {
  DataBoard, Document, Folder, PriceTag,
  ChatDotSquare, Picture, Link, View, ArrowDown
} from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const blogStore = useBlogStore()

const user = computed(() => blogStore.user)

const currentRoute = computed(() => {
  const name = route.name
  if (name === 'Dashboard') return '数据看板'
  if (name === 'AdminArticles') return '文章管理'
  if (name === 'AdminCategories') return '分类管理'
  if (name === 'AdminTags') return '标签管理'
  if (name === 'AdminComments') return '评论管理'
  if (name === 'AdminCarousels') return '轮播图'
  if (name === 'AdminFriends') return '友链管理'
  return ''
})

const logout = () => {
  blogStore.logout()
  router.push('/login')
}
</script>

<style lang="scss" scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;

  .sidebar {
    width: 220px;
    background: #304156;
    position: fixed;
    height: 100vh;

    .logo {
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      background: #263445;

      a {
        color: #fff;
        font-size: 18px;
        font-weight: 700;
      }
    }

    .nav {
      padding: 10px 0;

      .nav-item {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 12px 20px;
        color: #bfcbd9;
        transition: all 0.3s;

        &:hover, &.active {
          background: #263445;
          color: #409eff;
        }
      }
    }
  }

  .main {
    flex: 1;
    margin-left: 220px;
  }

  .header {
    height: 60px;
    background: #fff;
    border-bottom: 1px solid #e4e7ed;
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 0 20px;
    position: sticky;
    top: 0;
    z-index: 10;

    .header-right {
      display: flex;
      align-items: center;
      gap: 20px;
    }

    .user-info {
      cursor: pointer;
      display: flex;
      align-items: center;
      gap: 5px;
    }
  }

  .content {
    padding: 20px;
    background: #f5f7fa;
    min-height: calc(100vh - 60px);
  }
}
</style>
