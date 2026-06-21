import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

const request = axios.create({
  baseURL: '/api',
  timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      ElMessage.error(res.message || '请求失败')
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      localStorage.removeItem('user')
      router.push('/login')
    }
    ElMessage.error(error.message || '网络错误')
    return Promise.reject(error)
  }
)

export default request

// 文章相关API
export const articleApi = {
  getList: (params) => request.get('/public/articles/list', { params }),
  getDetail: (id) => request.get(`/public/articles/${id}`),
  getHot: (limit) => request.get('/public/articles/hot', { params: { limit } }),
  getRecommend: (limit) => request.get('/public/articles/recommend', { params: { limit } }),
  getArchive: () => request.get('/public/articles/archive'),
  like: (id) => request.post(`/public/articles/${id}/like`),
  unlike: (id) => request.post(`/public/articles/${id}/unlike`)
}

// 分类相关API
export const categoryApi = {
  getList: () => request.get('/public/categories/list'),
  getDetail: (id) => request.get(`/public/categories/${id}`)
}

// 标签相关API
export const tagApi = {
  getList: () => request.get('/public/tags/list'),
  getDetail: (id) => request.get(`/public/tags/${id}`)
}

// 评论相关API
export const commentApi = {
  getArticleComments: (articleId) => request.get(`/public/comments/article/${articleId}`),
  getReplies: (parentId) => request.get(`/public/comments/replies/${parentId}`),
  add: (data) => request.post('/public/comments/add', data)
}

// 友链相关API
export const friendApi = {
  getList: () => request.get('/public/friends/list'),
  apply: (data) => request.post('/public/friends/apply', data)
}

// 轮播图相关API
export const carouselApi = {
  getList: () => request.get('/public/carousels/list')
}

// 认证相关API
export const authApi = {
  login: (data) => request.post('/auth/login', data),
  register: (data) => request.post('/auth/register', data)
}

// 后台管理API
export const adminApi = {
  // 文章管理
  getArticles: (params) => request.get('/admin/articles/list', { params }),
  createArticle: (data) => request.post('/admin/articles/create', data),
  updateArticle: (data) => request.put('/admin/articles/update', data),
  deleteArticle: (id) => request.delete(`/admin/articles/${id}`),

  // 分类管理
  getCategories: () => request.get('/admin/categories/list'),
  createCategory: (data) => request.post('/admin/categories/create', data),
  updateCategory: (data) => request.put('/admin/categories/update', data),
  deleteCategory: (id) => request.delete(`/admin/categories/${id}`),

  // 标签管理
  getTags: () => request.get('/admin/tags/list'),
  createTag: (data) => request.post('/admin/tags/create', data),
  updateTag: (data) => request.put('/admin/tags/update', data),
  deleteTag: (id) => request.delete(`/admin/tags/${id}`),

  // 评论管理
  getPendingCount: () => request.get('/admin/comments/pending/count'),
  approveComment: (id) => request.put(`/admin/comments/${id}/approve`),
  rejectComment: (id) => request.put(`/admin/comments/${id}/reject`),
  deleteComment: (id) => request.delete(`/admin/comments/${id}`),

  // 轮播图管理
  getCarousels: () => request.get('/admin/carousels/list'),
  createCarousel: (data) => request.post('/admin/carousels/create', data),
  updateCarousel: (data) => request.put('/admin/carousels/update', data),
  deleteCarousel: (id) => request.delete(`/admin/carousels/${id}`),

  // 友链管理
  getFriends: () => request.get('/admin/friends/list'),
  approveFriend: (id) => request.put(`/admin/friends/${id}/approve`),
  deleteFriend: (id) => request.delete(`/admin/friends/${id}`),

  // 数据看板
  getStatistics: () => request.get('/admin/dashboard/statistics'),
  getArticleViewsChart: () => request.get('/admin/dashboard/chart/article/views'),
  getCategoryChart: () => request.get('/admin/dashboard/chart/article/categories')
}

// 文件上传API
export const uploadApi = {
  uploadImage: (file) => {
    const formData = new FormData()
    formData.append('file', file)
    return request.post('/upload/image', formData, {
      headers: { 'Content-Type': 'multipart/form-data' }
    })
  }
}
