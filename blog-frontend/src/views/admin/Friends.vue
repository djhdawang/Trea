<template>
  <div class="friends-admin">
    <div class="toolbar">
      <el-button @click="loadFriends">刷新</el-button>
    </div>

    <div class="card">
      <el-table :data="friends" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="网站名称" />
        <el-table-column prop="description" label="描述" show-overflow-tooltip />
        <el-table-column prop="url" label="链接" show-overflow-tooltip />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'warning'" size="small">
              {{ row.status === 1 ? '已通过' : '待审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button v-if="row.status === 0" type="success" size="small" @click="handleApprove(row.id)">通过</el-button>
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

const friends = ref([])
const loading = ref(false)

const loadFriends = async () => {
  loading.value = true
  try {
    const res = await adminApi.getFriends()
    friends.value = res.data || []
  } catch (error) {
    console.error('加载友链失败:', error)
  } finally {
    loading.value = false
  }
}

const handleApprove = async (id) => {
  try {
    await adminApi.approveFriend(id)
    ElMessage.success('审核通过')
    loadFriends()
  } catch (error) {
    console.error('审核失败:', error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个友链吗?', '提示', { type: 'warning' })
    await adminApi.deleteFriend(id)
    ElMessage.success('删除成功')
    loadFriends()
  } catch (error) {
    if (error !== 'cancel') console.error('删除失败:', error)
  }
}

onMounted(() => {
  loadFriends()
})
</script>
