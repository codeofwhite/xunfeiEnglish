<template>
  <div class="achievement-container">
    <h2>我的成就</h2>
    <div class="achievement-grid">
      <!-- 假设每个成就勋章是一个div元素 -->
      <div class="achievement-badge">成就1</div>
      <div class="achievement-badge">成就2</div>
      <div class="achievement-badge">成就3</div>
      <!-- 更多成就勋章 -->
    </div>
  </div>
</template>

<script setup>
// JavaScript逻辑（如果需要的话）
</script>

<style scoped>
.achievement-container {
  max-width: 800px;
  margin: auto;
  text-align: center;
}

.achievement-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 10px;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #f7f7f7;
}

.achievement-badge {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>