<template>
  <div :class="['app-container', { 'dark-mode': isDark }]">
    <router-view />
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useBlogStore } from './store/blog'

const blogStore = useBlogStore()
const isDark = computed(() => blogStore.theme === 'dark')
</script>

<style lang="scss">
.app-container {
  min-height: 100vh;
  background-color: #f5f7fa;
  transition: background-color 0.3s, color 0.3s;

  &.dark-mode {
    background-color: #1a1a1a;
    color: #e0e0e0;
  }
}
</style>
