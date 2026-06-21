<template>
  <div class="archive-page">
    <h2 class="page-title">文章归档</h2>
    
    <div class="timeline" v-if="archives.length">
      <div v-for="(group, year) in groupedArchives" :key="year" class="year-group">
        <div class="year-header">
          <span class="year">{{ year }}</span>
          <span class="count">{{ group.length }} 篇</span>
        </div>
        <div class="articles">
          <div v-for="article in group" :key="article.id" class="article-item" @click="goArticle(article.id)">
            <span class="date">{{ formatMonthDay(article.createTime) }}</span>
            <span class="title">{{ article.title }}</span>
          </div>
        </div>
      </div>
    </div>
    
    <div v-else class="empty-state">
      <el-empty description="暂无归档文章" />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { articleApi } from '@/api'

const router = useRouter()
const archives = ref([])

const groupedArchives = computed(() => {
  const grouped = {}
  archives.value.forEach(article => {
    const year = new Date(article.createTime).getFullYear()
    if (!grouped[year]) {
      grouped[year] = []
    }
    grouped[year].push(article)
  })
  // 按年份降序排列
  const sortedKeys = Object.keys(grouped).sort((a, b) => b - a)
  const sortedGrouped = {}
  sortedKeys.forEach(key => {
    sortedGrouped[key] = grouped[key].sort((a, b) => new Date(b.createTime) - new Date(a.createTime))
  })
  return sortedGrouped
})

const loadArchives = async () => {
  try {
    const res = await articleApi.getArchive()
    archives.value = res.data
  } catch (error) {
    console.error('加载归档失败:', error)
  }
}

const goArticle = (id) => {
  router.push(`/article/${id}`)
}

const formatMonthDay = (date) => {
  if (!date) return ''
  const d = new Date(date)
  return `${d.getMonth() + 1}月${d.getDate()}日`
}

onMounted(() => {
  loadArchives()
})
</script>

<style lang="scss" scoped>
.archive-page {
  .page-title {
    font-size: 24px;
    font-weight: 700;
    margin-bottom: 30px;
    text-align: center;
  }

  .timeline {
    .year-group {
      margin-bottom: 40px;
    }

    .year-header {
      display: flex;
      align-items: center;
      gap: 15px;
      margin-bottom: 15px;
      padding-bottom: 10px;
      border-bottom: 2px solid var(--primary-color);

      .year {
        font-size: 24px;
        font-weight: 700;
        color: var(--primary-color);
      }

      .count {
        color: var(--text-secondary);
        font-size: 14px;
      }
    }

    .articles {
      .article-item {
        display: flex;
        align-items: center;
        gap: 20px;
        padding: 12px 0;
        cursor: pointer;
        transition: transform 0.3s;

        &:hover {
          transform: translateX(10px);
          .title { color: var(--primary-color); }
        }
      }

      .date {
        color: var(--text-secondary);
        font-size: 14px;
        width: 60px;
      }

      .title {
        flex: 1;
        color: var(--text-color);
      }
    }
  }
}
</style>
