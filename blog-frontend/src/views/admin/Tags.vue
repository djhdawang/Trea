<template>
  <div class="tags-admin">
    <div class="toolbar">
      <el-button type="primary" @click="handleCreate">新建标签</el-button>
    </div>

    <div class="card">
      <el-table :data="tags" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="name" label="标签名称" />
        <el-table-column prop="color" label="颜色" width="120">
          <template #default="{ row }">
            <span :style="{ color: row.color }">{{ row.color }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="articleCount" label="文章数量" width="100" />
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑标签' : '新建标签'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="名称">
          <el-input v-model="form.name" placeholder="请输入标签名称" />
        </el-form-item>
        <el-form-item label="颜色">
          <el-color-picker v-model="form.color" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { adminApi } from '@/api'

const tags = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)

const form = ref({ id: null, name: '', color: '#409eff' })

const loadTags = async () => {
  loading.value = true
  try {
    const res = await adminApi.getTags()
    tags.value = res.data || []
  } catch (error) {
    console.error('加载标签失败:', error)
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  isEdit.value = false
  form.value = { id: null, name: '', color: '#409eff' }
  dialogVisible.value = true
}

const handleEdit = (row) => {
  isEdit.value = true
  form.value = { ...row }
  dialogVisible.value = true
}

const handleSave = async () => {
  try {
    if (isEdit.value) {
      await adminApi.updateTag(form.value)
      ElMessage.success('更新成功')
    } else {
      await adminApi.createTag(form.value)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadTags()
  } catch (error) {
    console.error('保存失败:', error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个标签吗?', '提示', { type: 'warning' })
    await adminApi.deleteTag(id)
    ElMessage.success('删除成功')
    loadTags()
  } catch (error) {
    if (error !== 'cancel') console.error('删除失败:', error)
  }
}

onMounted(() => {
  loadTags()
})
</script>
