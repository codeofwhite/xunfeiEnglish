<template>
  <div class="manual-container">
    <aside class="sidebar">
      <h2>帮助中心</h2>
      <nav>
        <ul>
          <li><a href="#introduction">介绍</a></li>
          <li><a href="#setup">设置</a></li>
          <li><a href="#faq">常见问题</a></li>
          <!-- 更多导航项 -->
        </ul>
      </nav>
    </aside>
    <section class="content">
      <article id="introduction">
        <h3>介绍</h3>
        <p>这里显示介绍的内容。</p>
      </article>
      <article id="setup">
        <h3>设置</h3>
        <p>这里显示设置的内容。</p>
      </article>
      <article id="faq">
        <h3>常见问题</h3>
        <p>这里显示常见问题的内容。</p>
      </article>
      <!-- 更多内容项 -->
    </section>
  </div>
</template>

<script setup>
// JavaScript逻辑（如果需要的话）
</script>

<style scoped>
.manual-container {
  display: flex;
  max-width: 1200px;
  margin: auto;
  background-color: #f0f8ff; /* 添加淡蓝色背景 */
}

.sidebar {
  flex: 0 0 200px;
  border-right: 1px solid #ddd;
  padding: 20px;
  background-color: #fafafa; /* 侧边栏背景色 */
}

.sidebar h2 {
  margin-bottom: 20px;
  color: #1bddff; /* 标题颜色 */
  font-family: 'Comic Sans MS', cursive; /* 卡通字体 */
}

.sidebar nav ul {
  list-style: none;
  padding: 0;
}

.sidebar nav ul li a {
  text-decoration: none;
  color: #333;
  display: block;
  padding: 10px 0;
  transition: color 0.3s ease; /* 颜色过渡效果 */
}

.sidebar nav ul li a:hover {
  color: #90fff1; /* 鼠标悬停颜色 */
}

.content {
  flex: 1;
  padding: 20px;
}

.content article {
  margin-bottom: 40px;
  border-left: 4px solid #59a8ff; /* 文章左侧边框 */
  padding-left: 16px; /* 文章内边距 */
  transition: border-color 0.3s ease; /* 边框颜色过渡效果 */
}

.content article:hover {
  border-color: #90fff1; /* 鼠标悬停边框颜色 */
}

.content h3 {
  margin-bottom: 10px;
  color: #09b46a; /* 标题颜色 */
  font-family: 'Comic Sans MS', cursive; /* 卡通字体 */
}

.content p {
  line-height: 1.6;
  color: #333; /* 段落文字颜色 */
}
</style>