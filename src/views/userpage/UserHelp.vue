<template>
  <div class="manual-container">
    <aside class="sidebar">
      <h2>帮助中心</h2>
      <nav>
        <ul>
          <li>
            <router-link to="/userHelp/introduction">
              介绍
              <p class="description">了解应用的基本功能和特点。</p>
            </router-link>
          </li>
          <li>
            <router-link to="/userHelp/setup">
              设置
              <p class="description">学习如何配置和使用应用。</p>
            </router-link>
          </li>
          <li>
            <router-link to="/userHelp/faq">
              常见问题
              <p class="description">查找常见问题的解答。</p>
            </router-link>
          </li>
        </ul>
      </nav>
    </aside>
    <section class="content">
      <router-view></router-view>
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
  flex: 0 0 250px; /* 增加侧边栏宽度 */
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

.sidebar nav ul li {
  margin-bottom: 20px; /* 增加列表项间距 */
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

.sidebar .description {
  font-size: 0.9em;
  color: #666;
  margin-top: 5px;
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