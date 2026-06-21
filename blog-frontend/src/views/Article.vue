<template>
  <div class="article-page">
    <div v-if="loading" class="loading">
      <el-skeleton :rows="6" animated />
    </div>
    
    <div v-else-if="!article" class="empty-state">
      <el-empty description="文章不存在" />
    </div>
    
    <template v-else>
      <div class="article-header">
        <h1 class="article-title">{{ article.title }}</h1>
        <div class="article-meta">
          <span>作者: {{ article.authorNickname }}</span>
          <span>发布于: {{ formatDate(article.createTime) }}</span>
          <span>阅读: {{ article.viewCount }}</span>
          <span>点赞: {{ article.likeCount }}</span>
        </div>
        <div class="article-tags" v-if="article.tags">
          <el-tag v-for="tag in article.tags.split(',')" :key="tag" size="small">{{ tag }}</el-tag>
        </div>
      </div>

      <div class="article-content card" v-html="renderedContent"></div>

      <div class="article-actions">
        <el-button type="primary" @click="handleLike">点赞 ({{ article?.likeCount || 0 }})</el-button>
        <el-button @click="handleBack">返回</el-button>
      </div>

      <!-- 评论区 -->
      <div class="comment-section card">
        <h3 class="comment-title">评论 ({{ comments.length }})</h3>
        
        <div class="comment-form">
          <el-input
            v-model="commentContent"
            type="textarea"
            :rows="3"
            placeholder="写下你的评论..."
          />
          <el-button type="primary" @click="submitComment" style="margin-top: 10px">发表评论</el-button>
        </div>

        <div class="comment-list">
          <div v-for="comment in comments" :key="comment.id" class="comment-item">
            <div class="comment-avatar">
              <img v-if="comment.avatar" :src="comment.avatar" />
              <el-icon v-else><user /></el-icon>
            </div>
            <div class="comment-info">
              <div class="comment-header">
                <span class="comment-nickname">{{ comment.nickname }}</span>
                <span class="comment-time">{{ formatDate(comment.createTime) }}</span>
              </div>
              <div class="comment-content">{{ comment.content }}</div>
            </div>
          </div>
        </div>
      </div>
    </template>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { articleApi, commentApi } from '@/api'
import { marked } from 'marked'
import hljs from 'highlight.js'
import { User } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'

const route = useRoute()
const router = useRouter()

const article = ref(null)
const comments = ref([])
const commentContent = ref('')
const loading = ref(true)

// 配置marked
marked.setOptions({
  highlight: (code, lang) => {
    if (lang && hljs.getLanguage(lang)) {
      return hljs.highlight(code, { language: lang }).value
    }
    return hljs.highlightAuto(code).value
  }
})

const renderedContent = computed(() => {
  if (!article.value?.content) return ''
  return marked(article.value.content)
})

const loadArticle = async () => {
  loading.value = true
  try {
    const res = await articleApi.getDetail(route.params.id)
    article.value = res.data
  } catch (error) {
    console.error('加载文章失败:', error)
    article.value = null
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    const res = await commentApi.getArticleComments(route.params.id)
    comments.value = res.data
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

const handleLike = async () => {
  try {
    await articleApi.like(route.params.id)
    article.value.likeCount++
    ElMessage.success('点赞成功')
  } catch (error) {
    console.error('点赞失败:', error)
  }
}

const submitComment = async () => {
  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }
  try {
    await commentApi.add({
      articleId: route.params.id,
      content: commentContent.value,
      nickname: '访客',
      email: ''
    })
    commentContent.value = ''
    loadComments()
    ElMessage.success('评论提交成功')
  } catch (error) {
    console.error('提交评论失败:', error)
  }
}

const handleBack = () => {
  router.back()
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

onMounted(() => {
  loadArticle()
  loadComments()
})
</script>

<style lang="scss" scoped>
.article-page {
  max-width: 900px;
  margin: 0 auto;

  .article-header {
    margin-bottom: 30px;
    text-align: center;

    .article-title {
      font-size: 32px;
      font-weight: 700;
      margin-bottom: 15px;
    }

    .article-meta {
      display: flex;
      justify-content: center;
      gap: 20px;
      color: var(--text-secondary);
      margin-bottom: 15px;
    }

    .article-tags {
      display: flex;
      justify-content: center;
      gap: 10px;
    }
  }

  .article-content {
    padding: 30px;
    line-height: 1.8;
    font-size: 16px;

    :deep(img) {
      max-width: 100%;
      border-radius: 4px;
    }

    :deep(pre) {
      background: #f6f8fa;
      padding: 15px;
      border-radius: 4px;
      overflow-x: auto;
    }

    :deep(code) {
      font-family: 'Fira Code', monospace;
    }
  }

  .article-actions {
    display: flex;
    justify-content: center;
    gap: 15px;
    margin: 30px 0;
  }

  .comment-section {
    .comment-title {
      font-size: 18px;
      font-weight: 600;
      margin-bottom: 20px;
    }

    .comment-form {
      margin-bottom: 30px;
    }

    .comment-list {
      .comment-item {
        display: flex;
        gap: 15px;
        padding: 15px 0;
        border-bottom: 1px solid var(--border-color);
      }

      .comment-avatar {
        width: 40px;
        height: 40px;
        border-radius: 50%;
        background: var(--bg-color);
        display: flex;
        align-items: center;
        justify-content: center;
        img { width: 100%; height: 100%; border-radius: 50%; }
      }

      .comment-info {
        flex: 1;
      }

      .comment-header {
        display: flex;
        gap: 10px;
        margin-bottom: 5px;
      }

      .comment-nickname {
        font-weight: 600;
      }

      .comment-time {
        color: var(--text-secondary);
        font-size: 12px;
      }
    }
  }
}
</style>
