import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('../views/Layout.vue'),
    children: [
      { path: '', name: 'Home', component: () => import('../views/Home.vue') },
      { path: 'article/:id', name: 'Article', component: () => import('../views/Article.vue') },
      { path: 'category/:id', name: 'Category', component: () => import('../views/Category.vue') },
      { path: 'archive', name: 'Archive', component: () => import('../views/Archive.vue') },
      { path: 'about', name: 'About', component: () => import('../views/About.vue') },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/admin',
    component: () => import('../views/admin/Layout.vue'),
    meta: { requiresAuth: true },
    children: [
      { path: '', name: 'Dashboard', component: () => import('../views/admin/Dashboard.vue') },
      { path: 'articles', name: 'AdminArticles', component: () => import('../views/admin/Articles.vue') },
      { path: 'categories', name: 'AdminCategories', component: () => import('../views/admin/Categories.vue') },
      { path: 'tags', name: 'AdminTags', component: () => import('../views/admin/Tags.vue') },
      { path: 'comments', name: 'AdminComments', component: () => import('../views/admin/Comments.vue') },
      { path: 'carousels', name: 'AdminCarousels', component: () => import('../views/admin/Carousels.vue') },
      { path: 'friends', name: 'AdminFriends', component: () => import('../views/admin/Friends.vue') },
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { top: 0 }
    }
  }
})

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
