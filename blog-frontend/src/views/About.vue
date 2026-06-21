<template>
  <div class="about-page">
    <div class="card about-content">
      <h2 class="section-title">关于我</h2>
      <div class="about-text">
        <p>你好！我是一名热爱技术的程序员。</p>
        <p>这个博客用于记录我在学习和工作中的技术心得、生活感悟。</p>
        <p>如果你有任何问题或建议，欢迎通过以下方式联系我：</p>
      </div>
    </div>

    <div class="card contact-section">
      <h2 class="section-title">联系方式</h2>
      <div class="contact-list">
        <div class="contact-item">
          <el-icon><message /></el-icon>
          <span>邮箱: admin@blog.com</span>
        </div>
        <div class="contact-item">
          <el-icon><location /></el-icon>
          <span>地址: 中国</span>
        </div>
      </div>
    </div>

    <div class="card skill-section">
      <h2 class="section-title">技能标签</h2>
      <div class="skill-tags">
        <el-tag v-for="tag in skills" :key="tag" type="info">{{ tag }}</el-tag>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { Message, Location } from '@element-plus/icons-vue'

const skills = ref([
  'Java', 'SpringBoot', 'Vue3', 'React',
  'MySQL', 'Redis', 'Docker', 'Git',
  'Linux', 'Kubernetes', 'Python'
])
</script>

<style lang="scss" scoped>
.about-page {
  max-width: 800px;
  margin: 0 auto;

  .section-title {
    font-size: 20px;
    font-weight: 600;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 2px solid var(--primary-color);
  }

  .about-text {
    line-height: 2;
    p { margin-bottom: 10px; }
  }

  .contact-list {
    .contact-item {
      display: flex;
      align-items: center;
      gap: 10px;
      padding: 10px 0;
    }
  }

  .skill-tags {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
  }
}
</style>
