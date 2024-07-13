<template>
  <div class="app-container">
    <nav class="vertical-nav">
      <ul>
        <li>
          <router-link to="/userProfile">用户主页</router-link>
        </li>
        <li>
          <router-link to="/userAchievements">成就</router-link>
        </li>
        <li>
          <router-link to="/userHelp">帮助</router-link>
        </li>
        <li>
          <router-link to="/userFavorites">收藏</router-link>
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
  height: 105vh;
  background-color: rgba(0, 161, 91, 0.64);
  color: white;
  position: absolute;
  left: 1vh;
  top: 25%;
  /* 其他样式保持不变 */
}

.content-container {
  margin-left: 200px; /* 与侧边导航栏的宽度相同 */
  width: calc(100% - 200px); /* 减去侧边导航栏的宽度 */
  min-height: 100vh;
  padding: 20px; /* 或者根据您的设计需要调整 */
}

.vertical-nav ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.vertical-nav li a {
  display: block;
  padding: 15px 20px;
  color: #f9eeff;
  text-decoration: none;
  transition: background-color 0.3s;
}

.vertical-nav li a:hover {
  background-color: #555;
}
</style>