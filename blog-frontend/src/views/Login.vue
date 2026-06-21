<template>
  <div class="login-page">
    <div class="login-card card">
      <h2 class="login-title">管理员登录</h2>
      <el-form :model="loginForm" :rules="rules" ref="formRef">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="用户名" prefix-icon="user" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="密码" prefix-icon="lock" @keyup.enter="handleLogin" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="handleLogin" :loading="loading">登录</el-button>
        </el-form-item>
      </el-form>
      <div class="login-tip">
        <p>测试账号: admin / admin123</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useBlogStore } from '@/store/blog'
import { authApi } from '@/api'
import { ElMessage } from 'element-plus'

const router = useRouter()
const blogStore = useBlogStore()

const formRef = ref()
const loading = ref(false)
const loginForm = ref({
  username: '',
  password: ''
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const handleLogin = async () => {
  const valid = await formRef.value.validate().catch(() => false)
  if (!valid) return

  loading.value = true
  try {
    const res = await authApi.login(loginForm.value)
    blogStore.setToken(res.data.token)
    blogStore.setUser(res.data)
    ElMessage.success('登录成功')
    router.push('/admin')
  } catch (error) {
    console.error('登录失败:', error)
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.login-page {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);

  .login-card {
    width: 400px;
    padding: 40px;
  }

  .login-title {
    text-align: center;
    margin-bottom: 30px;
    font-size: 24px;
  }

  .login-tip {
    text-align: center;
    color: var(--text-secondary);
    font-size: 12px;
    margin-top: 20px;
  }
}
</style>
