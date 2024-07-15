<template>
  <div class="app-container">
    <nav class="vertical-nav">
      <ul>
        <li>
          <router-link to="/ourMission" active-class="active-link">我们的使命</router-link>
        </li>
        <li>
          <router-link to="/teachApproach" active-class="active-link">教学方法</router-link>
        </li>
        <li>
          <router-link to="/contactUs" active-class="active-link">联系我们</router-link>
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
  min-height: 100vh; /* 确保容器至少占满整个视口高度 */
}

.vertical-nav {
  width: 200px;
  background-color: rgba(0, 161, 91, 0.64);
  color: white;
  position: relative; /* 使用相对定位 */
  flex-shrink: 0; /* 防止导航栏缩小 */
}

.content-container {
  flex: 1; /* 使内容容器占据剩余空间 */
  padding: 20px;
  background-color: #f9f9f9;
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

.active-link {
  background-color: #333; /* 选中路由的背景颜色 */
  color: #fff; /* 选中路由的文字颜色 */
}
</style>