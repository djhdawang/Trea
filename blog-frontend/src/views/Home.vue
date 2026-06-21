<template>
  <div class="home">
    <!-- 轮播图 -->
    <div class="carousel-section" v-if="carousels.length">
      <el-carousel height="400px" :interval="5000" trigger="click">
        <el-carousel-item v-for="item in carousels" :key="item.id">
          <router-link :to="item.linkUrl || '/'">
            <img :src="item.imageUrl" :alt="item.title" />
          </router-link>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="content-wrapper">
      <!-- 左侧主内容 -->
      <div class="main-content">
        <!-- 搜索栏 -->
        <div class="search-bar">
          <el-input
            v-model="searchKeyword"
            placeholder="搜索文章..."
            @keyup.enter="handleSearch"
            clearable
          >
            <template #prefix>
              <el-icon><search /></el-icon>
            </template>
          </el-input>
        </div>

        <!-- 文章列表 -->
        <div class="article-list">
          <div v-if="loading" class="loading">
            <el-skeleton :rows="5" animated />
          </div>
          <div v-else-if="articles.length === 0" class="empty-state">
            <el-empty description="暂无文章" />
          </div>
          <div v-else>
            <router-link v-for="article in articles" :key="article.id" :to="`/article/${article.id}`" class="article-card card">
              <img v-if="article.cover" :src="article.cover" class="article-cover" />
              <div class="article-info">
                <h3 class="article-title">{{ article.title }}</h3>
                <p class="article-summary">{{ article.summary }}</p>
                <div class="article-meta">
                  <span><el-icon><user /></el-icon> {{ article.authorNickname }}</span>
                  <span><el-icon><calendar /></el-icon> {{ formatDate(article.createTime) }}</span>
                  <span><el-icon><view /></el-icon> {{ article.viewCount }}</span>
                  <span><el-icon><chat-line-square /></el-icon> {{ article.commentCount }}</span>
                </div>
                <div class="article-tags" v-if="article.tags">
                  <el-tag v-for="tag in article.tags.split(',')" :key="tag" size="small">{{ tag }}</el-tag>
                </div>
              </div>
            </router-link>
          </div>
        </div>

        <!-- 分页 -->
        <div class="pagination" v-if="total > 0">
          <el-pagination
            v-model:current-page="pageNum"
            :page-size="pageSize"
            :total="total"
            layout="prev, pager, next"
            @current-change="loadArticles"
          />
        </div>
      </div>

      <!-- 右侧边栏 -->
      <aside class="sidebar">
        <!-- 热门文章 -->
        <div class="card hot-articles">
          <h4 class="sidebar-title">热门文章</h4>
          <div class="hot-list">
            <router-link v-for="(article, index) in hotArticles" :key="article.id" :to="`/article/${article.id}`" class="hot-item">
              <span class="hot-index">{{ index + 1 }}</span>
              <span class="hot-title">{{ article.title }}</span>
            </router-link>
          </div>
        </div>

        <!-- 分类 -->
        <div class="card categories">
          <h4 class="sidebar-title">分类</h4>
          <div class="category-list">
            <div v-for="category in categories" :key="category.id" class="category-item" @click="$router.push(`/category/${category.id}`)">
              <span>{{ category.name }}</span>
              <span class="count">{{ category.articleCount || 0 }}</span>
            </div>
          </div>
        </div>

        <!-- 标签云 -->
        <div class="card tags">
          <h4 class="sidebar-title">标签云</h4>
          <div class="tag-cloud">
            <el-tag v-for="tag in tags" :key="tag.id" :color="tag.color" effect="dark" class="tag-item">
              {{ tag.name }}
            </el-tag>
          </div>
        </div>

        <!-- 友链 -->
        <div class="card friends">
          <h4 class="sidebar-title">友情链接</h4>
          <div class="friend-list">
            <a v-for="friend in friends" :key="friend.id" :href="friend.url" target="_blank" class="friend-item">
              {{ friend.name }}
            </a>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { articleApi, categoryApi, tagApi, friendApi, carouselApi } from '@/api'
import { Search, User, Calendar, View, ChatLineSquare } from '@element-plus/icons-vue'

const articles = ref([])
const hotArticles = ref([])
const categories = ref([])
const tags = ref([])
const friends = ref([])
const carousels = ref([])

const loading = ref(false)
const pageNum = ref(1)
const pageSize = ref(10)
const total = ref(0)
const searchKeyword = ref('')

const loadArticles = async () => {
  loading.value = true
  try {
    const res = await articleApi.getList({
      pageNum: pageNum.value,
      pageSize: pageSize.value,
      keyword: searchKeyword.value
    })
    articles.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
  }
}

const loadHotArticles = async () => {
  try {
    const res = await articleApi.getHot(5)
    hotArticles.value = res.data
  } catch (error) {
    console.error('加载热门文章失败:', error)
  }
}

const loadCategories = async () => {
  try {
    const res = await categoryApi.getList()
    categories.value = res.data
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const loadTags = async () => {
  try {
    const res = await tagApi.getList()
    tags.value = res.data
  } catch (error) {
    console.error('加载标签失败:', error)
  }
}

const loadFriends = async () => {
  try {
    const res = await friendApi.getList()
    friends.value = res.data
  } catch (error) {
    console.error('加载友链失败:', error)
  }
}

const loadCarousels = async () => {
  try {
    const res = await carouselApi.getList()
    carousels.value = res.data
  } catch (error) {
    console.error('加载轮播图失败:', error)
  }
}

const handleSearch = () => {
  pageNum.value = 1
  loadArticles()
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

onMounted(() => {
  loadArticles()
  loadHotArticles()
  loadCategories()
  loadTags()
  loadFriends()
  loadCarousels()
})
</script>

<style lang="scss" scoped>
.home {
  .carousel-section {
    margin-bottom: 30px;
    img {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .content-wrapper {
    display: flex;
    gap: 30px;
  }

  .main-content {
    flex: 1;
    min-width: 0;
  }

  .search-bar {
    margin-bottom: 20px;
  }

  .article-card {
    display: flex;
    gap: 20px;
    cursor: pointer;
    text-decoration: none;
    color: inherit;
    &:hover .article-title { color: var(--primary-color); }
    &:visited { color: inherit; }

    .article-cover {
      width: 200px;
      height: 140px;
      object-fit: cover;
      border-radius: 4px;
      flex-shrink: 0;
    }

    .article-info {
      flex: 1;
      display: flex;
      flex-direction: column;
    }

    .article-title {
      font-size: 18px;
      margin-bottom: 10px;
    }

    .article-summary {
      color: var(--text-secondary);
      flex: 1;
    }

    .article-meta {
      display: flex;
      gap: 15px;
      color: var(--text-secondary);
      font-size: 13px;
      margin-top: 10px;
    }

    .article-tags {
      margin-top: 10px;
    }
  }

  .sidebar {
    width: 300px;
    flex-shrink: 0;

    .sidebar-title {
      font-size: 16px;
      font-weight: 600;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 2px solid var(--primary-color);
    }

    .hot-list {
      .hot-item {
        display: flex;
        align-items: center;
        gap: 10px;
        padding: 8px 0;
        cursor: pointer;
        &:hover .hot-title { color: var(--primary-color); }
      }

      .hot-index {
        width: 20px;
        height: 20px;
        background: var(--primary-color);
        color: #fff;
        border-radius: 50%;
        text-align: center;
        line-height: 20px;
        font-size: 12px;
      }

      .hot-title {
        flex: 1;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
    }

    .category-list, .friend-list {
      .category-item, .friend-item {
        display: flex;
        justify-content: space-between;
        padding: 8px 0;
        cursor: pointer;
        &:hover { color: var(--primary-color); }
      }
    }

    .tag-cloud {
      display: flex;
      flex-wrap: wrap;
      gap: 8px;
      .tag-item { cursor: pointer; }
    }
  }
}

@media (max-width: 768px) {
  .content-wrapper { flex-direction: column; }
  .sidebar { width: 100%; }
  .article-card { flex-direction: column; }
  .article-cover { width: 100% !important; height: 180px !important; }
}
</style>
