<template>
  <div class="collection-container">
    <h2 class="collection-title">我的收藏</h2>
    <p class="collection-content">这里显示我的收藏的内容。</p>
  </div>
</template>

<script setup>
// 在这里你可以添加JavaScript逻辑，例如获取收藏的内容
</script>

<style scoped>
.collection-container {
  padding: 20px;
  border-radius: 10px;
  background-color: #f0f0f0;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.collection-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
}

.collection-title {
  text-align: center;
  animation: fadeInDown 1s ease both;
}

.collection-content {
  text-align: center;
  animation: fadeInUp 1s ease both;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>