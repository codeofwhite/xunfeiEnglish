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
}

.sidebar {
  flex: 0 0 200px;
  border-right: 1px solid #ddd;
  padding: 20px;
}

.sidebar h2 {
  margin-bottom: 20px;
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
}

.content {
  flex: 1;
  padding: 20px;
}

.content article {
  margin-bottom: 40px;
}

.content h3 {
  margin-bottom: 10px;
}

.content p {
  line-height: 1.6;
}
</style>