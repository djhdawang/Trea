<template>
  <div class="comments-admin">
    <div class="toolbar">
      <el-button @click="loadComments">刷新</el-button>
    </div>

    <div class="card">
      <el-table :data="comments" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="content" label="内容" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusType(row.status)" size="small">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="时间" width="120">
          <template #default="{ row }">
            {{ formatDate(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="success" size="small" @click="handleApprove(row.id)">通过</el-button>
            <el-button v-if="row.status === 0" type="warning" size="small" @click="handleReject(row.id)">拒绝</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi } from '@/api'

const comments = ref([])
const loading = ref(false)

const loadComments = async () => {
  loading.value = true
  try {
    // 待实现：获取所有评论列表
    comments.value = []
  } catch (error) {
    console.error('加载评论失败:', error)
  } finally {
    loading.value = false
  }
}

const handleApprove = async (id) => {
  try {
    await adminApi.approveComment(id)
    ElMessage.success('审核通过')
    loadComments()
  } catch (error) {
    console.error('审核失败:', error)
  }
}

const handleReject = async (id) => {
  try {
    await adminApi.rejectComment(id)
    ElMessage.success('已拒绝')
    loadComments()
  } catch (error) {
    console.error('操作失败:', error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这条评论吗?', '提示', { type: 'warning' })
    await adminApi.deleteComment(id)
    ElMessage.success('删除成功')
    loadComments()
  } catch (error) {
    if (error !== 'cancel') console.error('删除失败:', error)
  }
}

const getStatusType = (status) => {
  if (status === 0) return 'warning'
  if (status === 1) return 'success'
  return 'info'
}

const getStatusText = (status) => {
  if (status === 0) return '待审核'
  if (status === 1) return '已通过'
  return '已拒绝'
}

const formatDate = (date) => {
  if (!date) return ''
  return new Date(date).toLocaleDateString('zh-CN')
}

onMounted(() => {
  loadComments()
})
</script>
