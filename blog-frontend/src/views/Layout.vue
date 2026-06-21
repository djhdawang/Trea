<template>
  <div class="layout">
    <header class="header">
      <div class="container header-inner">
        <div class="logo">
          <router-link to="/">我的博客</router-link>
        </div>
        <nav class="nav">
          <router-link to="/" class="nav-item">首页</router-link>
          <router-link to="/archive" class="nav-item">归档</router-link>
          <router-link to="/about" class="nav-item">关于</router-link>
        </nav>
        <div class="actions">
          <el-switch
            v-model="isDark"
            @change="toggleTheme"
            active-text="暗"
            inactive-text="亮"
          />
          <el-button v-if="!user" type="primary" size="small" @click="$router.push('/login')">
            登录
          </el-button>
          <el-dropdown v-else>
            <el-button type="primary" size="small">
              {{ user.nickname }}<el-icon class="el-icon--right"><arrow-down /></el-icon>
            </el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="$router.push('/admin')">后台管理</el-dropdown-item>
                <el-dropdown-item divided @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
    </header>

    <main class="main">
      <div class="container">
        <router-view />
      </div>
    </main>

    <footer class="footer">
      <div class="container">
        <p>&copy; 2024 我的个人博客. All rights reserved.</p>
        <p>
          <a href="https://github.com" target="_blank">GitHub</a> |
          <a href="https://gitee.com" target="_blank">Gitee</a>
        </p>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useBlogStore } from '@/store/blog'
import { useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'

const blogStore = useBlogStore()
const router = useRouter()

const isDark = computed({
  get: () => blogStore.theme === 'dark',
  set: (val) => blogStore.setTheme(val ? 'dark' : 'light')
})

const user = computed(() => blogStore.user)

const toggleTheme = (val) => {
  blogStore.setTheme(val ? 'dark' : 'light')
}

const logout = () => {
  blogStore.logout()
  router.push('/')
}
</script>

<style lang="scss" scoped>
.layout {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
}

.header {
  background: var(--card-bg);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;

  .header-inner {
    display: flex;
    align-items: center;
    justify-content: space-between;
    height: 60px;
  }

  .logo a {
    font-size: 20px;
    font-weight: 700;
    color: var(--primary-color);
  }

  .nav {
    display: flex;
    gap: 30px;

    .nav-item {
      color: var(--text-color);
      &:hover, &.router-link-active {
        color: var(--primary-color);
      }
    }
  }

  .actions {
    display: flex;
    align-items: center;
    gap: 15px;
  }
}

.main {
  flex: 1;
  padding: 30px 0;
}

.footer {
  background: var(--card-bg);
  padding: 30px 0;
  text-align: center;
  color: var(--text-secondary);
  margin-top: auto;

  p { margin: 5px 0; }
  a { color: var(--primary-color); &:hover { text-decoration: underline; } }
}
</style>
