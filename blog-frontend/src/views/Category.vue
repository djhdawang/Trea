<template>
  <div class="category-page">
    <h2 class="page-title">分类: {{ categoryName }}</h2>
    
    <div class="article-list">
      <div v-if="loading" class="loading">
        <el-skeleton :rows="5" animated />
      </div>
      <div v-else-if="articles.length === 0" class="empty-state">
        <el-empty description="该分类下暂无文章" />
      </div>
      <div v-else>
        <div class="article-card card" v-for="article in articles" :key="article.id" @click="goArticle(article.id)">
          <h3 class="article-title">{{ article.title }}</h3>
          <p class="article-summary">{{ article.summary }}</p>
          <div class="article-meta">
            <span>{{ formatDate(article.createTime) }}</span>
            <span>阅读 {{ article.viewCount }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { articleApi, categoryApi } from '@/api'

const route = useRoute()
const router = useRouter()

const categoryName = ref('')
const articles = ref([])
const loading = ref(false)

const loadCategory = async () => {
  try {
    const res = await categoryApi.getDetail(route.params.id)
    categoryName.value = res.data.name
  } catch (error) {
    console.error('加载分类失败:', error)
  }
}

const loadArticles = async () => {
  loading.value = true
  try {
    const res = await articleApi.getList({
      pageNum: 1,
      pageSize: 100,
      categoryId: route.params.id
    })
    articles.value = res.data.records
  } catch (error) {
    console.error('加载文章失败:', error)
  } finally {
    loading.value = false
  }
}

const goArticle = (id) => {
  router.push(`/article/${id}`)
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

onMounted(() => {
  loadCategory()
  loadArticles()
})
</script>

<style lang="scss" scoped>
.category-page {
  .page-title {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 30px;
  }

  .article-card {
    cursor: pointer;
    &:hover .article-title { color: var(--primary-color); }

    .article-title {
      font-size: 18px;
      margin-bottom: 10px;
    }

    .article-summary {
      color: var(--text-secondary);
      margin-bottom: 10px;
    }

    .article-meta {
      display: flex;
      gap: 20px;
      color: var(--text-secondary);
      font-size: 13px;
    }
  }
}
</style>
