<template>
  <div class="app-container">
    <nav class="vertical-nav">
      <ul>
        <li>
          <router-link to="/vbIndex">背单词</router-link>
        </li>
        <li>
          <router-link to="/unKnownWord">不认识的单词</router-link>
        </li>
        <li>
          <router-link to="/masteredWord">已掌握的单词</router-link>
        </li>
        <li>
          <router-link to="/favoriteWord">我收藏的单词</router-link>
        </li>
        <li>
          <router-link to="/statisticPage">个性化分析</router-link>
        </li>
      </ul>
    </nav>
    <div class="content-container">
      <router-view></router-view> <!-- 子路由将在这里渲染 -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
}

.vertical-nav {
  width: 200px;
  background: linear-gradient(135deg, rgba(0, 161, 91, 0.64), rgba(0, 161, 91, 0.8)); /* 渐变效果 */
  color: white;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 20px;
}

.vertical-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
}

.vertical-nav li {
  width: 100%;
}

.vertical-nav li a {
  display: block;
  padding: 15px 20px;
  color: #f9eeff;
  text-decoration: none;
  transition: background-color 0.3s, transform 0.3s;
}

.vertical-nav li a:hover {
  background-color: #004d40; /* 更深的绿色 */
  transform: scale(1.05);
}

.content-container {
  flex: 1; /* 使内容容器占据剩余空间 */
  padding: 20px; /* 或者根据您的设计需要调整 */
  background-color: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  backdrop-filter: blur(10px); /* 背景模糊效果 */
  transition: opacity 0.5s ease;
}

.content-container:hover {
  opacity: 0.9;
}
</style>