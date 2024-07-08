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
  background-color: #6affbe; /* 柔和的粉红色背景 */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 2px dashed #09b46a; /* 添加虚线边框 */
}

.collection-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgb(144, 255, 241); /* 粉红色阴影 */
}

.collection-title {
  text-align: center;
  animation: bounce 1s infinite alternate;
  color: #00504a; /* 鲜艳的粉红色文字 */
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
}

.collection-content {
  text-align: center;
  animation: fadeInUp 1s ease both;
  color: #333; /* 柔和的文字颜色 */
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
}

@keyframes bounce {
  from {
    transform: translateY(0);
  }
  to {
    transform: translateY(-10px);
  }
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