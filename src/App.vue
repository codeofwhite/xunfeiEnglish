<template>
  <header>
    <NavigationBar></NavigationBar>
  </header>

  <RouterView/>
</template>

<script setup>
import {RouterView} from 'vue-router'
import NavigationBar from "@/components/NavigationBar.vue";
</script>

<style scoped>
header {
  background-color: #333; /* 深色背景 */
  padding: 10px 0; /* 上下填充 */
}
</style>