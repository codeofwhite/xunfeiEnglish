<template>
  <div class="app">
    <div class="app-container">
      <header>
        <div class="container">
          <NavigationBar>
          </NavigationBar>
        </div>
      </header>

      <RouterView></RouterView>

      <footer class="app-footer">
        <p>© 2024 English Mastery. All rights reserved.</p>
      </footer>
    </div>
  </div>
</template>

<script setup>
import {RouterView} from 'vue-router'
import NavigationBar from "@/components/NavigationBar.vue";
</script>

<style scoped>
header {
  background-color: #ffffff; /* 深色背景 */
  padding: 5px 0; /* 上下填充 */
}

.container {
  margin: 0 auto; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  justify-content: space-between; /* 元素间隔开 */
}

.app-footer {
  background-color: #00a6be; /* 淡粉色背景 */
  color: #ffffff; /* 粉红色文字 */
  text-align: center;
  padding: 1rem 0;
  font-size: 0.875rem;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
}

.footer {

}
</style>