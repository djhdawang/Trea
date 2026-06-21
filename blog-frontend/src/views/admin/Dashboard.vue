<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <div class="card stat-card">
          <div class="stat-icon" style="background: #409eff">
            <el-icon><document /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.articleCount }}</div>
            <div class="stat-label">文章数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card stat-card">
          <div class="stat-icon" style="background: #67c23a">
            <el-icon><chat-dot-square /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.commentCount }}</div>
            <div class="stat-label">评论数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card stat-card">
          <div class="stat-icon" style="background: #e6a23c">
            <el-icon><folder /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.categoryCount }}</div>
            <div class="stat-label">分类数量</div>
          </div>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="card stat-card">
          <div class="stat-icon" style="background: #f56c6c">
            <el-icon><warning /></el-icon>
          </div>
          <div class="stat-info">
            <div class="stat-value">{{ stats.pendingCommentCount }}</div>
            <div class="stat-label">待审核评论</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <div class="card chart-card">
          <h4 class="card-title">文章浏览量统计</h4>
          <div ref="viewsChartRef" class="chart-container"></div>
        </div>
      </el-col>
      <el-col :span="12">
        <div class="card chart-card">
          <h4 class="card-title">分类文章分布</h4>
          <div ref="categoryChartRef" class="chart-container"></div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { adminApi } from '@/api'
import { Document, ChatDotSquare, Folder, Warning } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

const stats = ref({
  articleCount: 0,
  commentCount: 0,
  categoryCount: 0,
  pendingCommentCount: 0
})

const viewsChartRef = ref(null)
const categoryChartRef = ref(null)
let viewsChart = null
let categoryChart = null

const loadStatistics = async () => {
  try {
    const res = await adminApi.getStatistics()
    stats.value = res.data
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

const loadViewsChart = async () => {
  try {
    const res = await adminApi.getArticleViewsChart()
    const data = res.data || []
    
    if (!viewsChart) {
      viewsChart = echarts.init(viewsChartRef.value)
    }
    
    const option = {
      tooltip: {
        trigger: 'axis'
      },
      xAxis: {
        type: 'category',
        data: data.map(item => item.date)
      },
      yAxis: {
        type: 'value'
      },
      series: [{
        data: data.map(item => item.views),
        type: 'line',
        smooth: true,
        areaStyle: {
          color: 'rgba(64, 158, 255, 0.2)'
        },
        itemStyle: {
          color: '#409eff'
        }
      }]
    }
    
    viewsChart.setOption(option)
  } catch (error) {
    console.error('加载浏览量图表失败:', error)
  }
}

const loadCategoryChart = async () => {
  try {
    const res = await adminApi.getCategoryChart()
    const data = res.data || []
    
    if (!categoryChart) {
      categoryChart = echarts.init(categoryChartRef.value)
    }
    
    const option = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        bottom: '0%',
        left: 'center'
      },
      series: [{
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        itemStyle: {
          borderRadius: 10,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: true,
          formatter: '{b}: {c}'
        },
        data: data.map((item, index) => ({
          name: item.name,
          value: item.value,
          itemStyle: {
            color: ['#409eff', '#67c23a', '#e6a23c', '#f56c6c', '#909399'][index % 5]
          }
        }))
      }]
    }
    
    categoryChart.setOption(option)
  } catch (error) {
    console.error('加载分类图表失败:', error)
  }
}

const handleResize = () => {
  viewsChart?.resize()
  categoryChart?.resize()
}

onMounted(() => {
  loadStatistics()
  loadViewsChart()
  loadCategoryChart()
  window.addEventListener('resize', handleResize)
})

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  viewsChart?.dispose()
  categoryChart?.dispose()
})
</script>

<style lang="scss" scoped>
.dashboard {
  .stat-card {
    display: flex;
    align-items: center;
    gap: 20px;
  }

  .stat-icon {
    width: 60px;
    height: 60px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 24px;
    color: #fff;
  }

  .stat-info {
    .stat-value {
      font-size: 28px;
      font-weight: 700;
    }
    .stat-label {
      color: var(--text-secondary);
      font-size: 14px;
    }
  }

  .chart-card {
    .card-title {
      font-size: 16px;
      font-weight: 600;
      margin-bottom: 20px;
    }

    .chart-container {
      height: 300px;
    }
  }
}
</style>
