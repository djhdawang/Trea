<template>
  <div class="carousels-admin">
    <div class="toolbar">
      <el-button type="primary" @click="handleCreate">新建轮播图</el-button>
    </div>

    <div class="card">
      <el-table :data="carousels" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="title" label="标题" />
        <el-table-column label="图片" width="200">
          <template #default="{ row }">
            <img :src="row.imageUrl" style="width: 100px; height: 50px; object-fit: cover;" />
          </template>
        </el-table-column>
        <el-table-column prop="linkUrl" label="链接" show-overflow-tooltip />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'info'" size="small">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑轮播图' : '新建轮播图'" width="500px">
      <el-form :model="form" label-width="80px">
        <el-form-item label="标题">
          <el-input v-model="form.title" placeholder="请输入标题" />
        </el-form-item>
        <el-form-item label="图片URL">
          <el-input v-model="form.imageUrl" placeholder="请输入图片URL" />
        </el-form-item>
        <el-form-item label="跳转链接">
          <el-input v-model="form.linkUrl" placeholder="请输入跳转链接" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number v-model="form.sort" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-switch v-model="form.status" :active-value="1" :inactive-value="0" />
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

const carousels = ref([])
const loading = ref(false)
const dialogVisible = ref(false)
const isEdit = ref(false)

const form = ref({ id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 })

const loadCarousels = async () => {
  loading.value = true
  try {
    const res = await adminApi.getCarousels()
    carousels.value = res.data || []
  } catch (error) {
    console.error('加载轮播图失败:', error)
  } finally {
    loading.value = false
  }
}

const handleCreate = () => {
  isEdit.value = false
  form.value = { id: null, title: '', imageUrl: '', linkUrl: '', sort: 0, status: 1 }
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
      await adminApi.updateCarousel(form.value)
      ElMessage.success('更新成功')
    } else {
      await adminApi.createCarousel(form.value)
      ElMessage.success('创建成功')
    }
    dialogVisible.value = false
    loadCarousels()
  } catch (error) {
    console.error('保存失败:', error)
  }
}

const handleDelete = async (id) => {
  try {
    await ElMessageBox.confirm('确定要删除这个轮播图吗?', '提示', { type: 'warning' })
    await adminApi.deleteCarousel(id)
    ElMessage.success('删除成功')
    loadCarousels()
  } catch (error) {
    if (error !== 'cancel') console.error('删除失败:', error)
  }
}

onMounted(() => {
  loadCarousels()
})
</script>
