<template>
  <div class="collection-container">
    <h2 class="collection-title">我的收藏</h2>

    <section class="collection-section">
      <h3>单词收藏</h3>
      <p>这里显示你收藏的单词。你可以随时查看和复习这些单词，帮助你更好地记忆。</p>
    </section>

    <section class="collection-section">
      <h3>书本收藏</h3>
      <p>这里显示你收藏的书本。你可以随时访问这些书本，继续你的阅读之旅。</p>
    </section>

    <section class="collection-section">
      <h3>语音对话的句子收藏</h3>
      <p>这里显示你收藏的语音对话句子。你可以随时查看这些句子，提升你的口语能力。</p>
    </section>
  </div>
</template>

<script setup>
// 在这里你可以添加JavaScript逻辑，例如获取收藏的内容
</script>

<style scoped>
.collection-container {
  padding: 20px;
  border-radius: 10px;
  background-color: #f0f8ff; /* 柔和的蓝色背景 */
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  border: 2px dashed #09b46a; /* 添加虚线边框 */
}

.collection-container:hover {
  transform: translateY(-5px);
  box-shadow: 0 10px 20px rgba(144, 255, 241, 0.5); /* 蓝色阴影 */
}

.collection-title {
  text-align: center;
  animation: bounce 1s infinite alternate;
  color: #00504a; /* 鲜艳的绿色文字 */
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
}

.collection-section {
  margin-bottom: 20px;
}

.collection-section h3 {
  color: #1bddff; /* 标题颜色 */
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
  margin-bottom: 10px;
}

.collection-section p {
  line-height: 1.6;
  color: #333; /* 段落文字颜色 */
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