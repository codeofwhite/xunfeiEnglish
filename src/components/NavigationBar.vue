<template>
  <div class="header">
    <div class="logo">
      <!-- Logo图片路径根据实际情况替换 -->
      <router-link to="/">
        <img src="@/assets/images/mynah.jpg" alt="Logo"/>
      </router-link>
    </div>
    <nav class="nav">
      <ul>
        <li>
          <RouterLink to="/" class="nav-link"><span class="nav-title">首页</span><span class="nav-subtitle">HOME</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/vbIndex" class="nav-link"><span class="nav-title">单词记忆</span><span class="nav-subtitle">WORD</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/smartTalk" class="nav-link"><span class="nav-title">智能语音</span><span
              class="nav-subtitle">SPEECH</span></RouterLink>
        </li>
        <li>
          <RouterLink to="/userProfile" class="nav-link"><span class="nav-title">个人中心</span><span
              class="nav-subtitle">CENTER</span></RouterLink>
        </li>
        <li>
          <RouterLink to="/readBook" class="nav-link"><span class="nav-title">有声阅读</span><span class="nav-subtitle">CENTER</span>
          </RouterLink>
        </li>
        <li>
          <RouterLink to="/test" class="nav-link"><span class="nav-title">测试</span><span
              class="nav-subtitle">CENTER</span></RouterLink>
        </li>
      </ul>
    </nav>
    <!-- ...其他代码... -->
    <div class="user">
      <RouterLink to="/login" class="user-link">
        <img src="@/assets/images/codeofwhite.jpg" alt="User" class="user-image"/>
      </RouterLink>
      <div class="coin-spark">
        <span>金币: {{ coin }}</span>
        <br>
        <span>火花: {{ spark }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import {RouterLink} from 'vue-router';
import UserPage from "@/views/userpage/UserPageNav.vue";
import {useStore} from "vuex";
import axios from "axios";
import {computed, onMounted, ref} from "vue";

const store = useStore();

const userEmail = computed(() => store.state.userEmail);

const coin = ref(0);
const spark = ref(0);

const fetchUserResources = async () => {
  try {
    const response = await axios.get('http://localhost:8002/userGameResource/get', {
      params: {
        userEmail: userEmail.value
      }
    });
    coin.value = response.data.user_coin;
    spark.value = response.data.user_spark;
  } catch (error) {
    console.error('获取用户资源失败:', error);
  }
};

onMounted(() => {
  fetchUserResources()
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  background-color: #f3f5f7;
  font-family: Arial, sans-serif;
}

a {
  text-decoration: none;
  color: #1c1919;
}

/* Header */
.header {
  width: 100%;
  height: 160px;
  background-color: #ffffff;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.logo img {
  height: 120px;
  width: 300px;
  border-radius: 15px;
}

/* Navigation */
.nav {
  float: left;
  margin-left: 20px;
}

.nav ul {
  list-style: none;
}

.nav ul li {
  display: inline-block;
  margin-right: 20px;
}

.nav ul li a {
  display: block;
  padding: 10px 15px;
  color: #1c1919;
  text-transform: uppercase;
  font-size: 16px;
  transition: all 0.3s ease;
}

.nav ul li a:hover {
  color: #007bff;
}

.nav-title {
  font-size: 18px;
  font-weight: bold;
}

.nav-subtitle {
  font-size: 12px;
  color: #aeaeae;
}

/* User section */
.user {
  float: right;
  margin-top: 20px;
  margin-right: 20px;
  font-size: 14px;
  color: #1c1919;
}

.coin-spark {
  margin-right: 20px;
  font-size: 14px;
  color: #1c1919;
}

.coin-spark span {
  margin-right: 10px;
}

.user img {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  margin-right: 10px;
  vertical-align: middle;
}

.nav-link {
  color: #000000; /* 白色文字 */
  text-decoration: none; /* 去除下划线 */
  margin: 0 15px; /* 左右间距 */
  padding: 5px 10px; /* 内填充 */
  border-radius: 5px; /* 圆角边框 */
  transition: background-color 0.3s; /* 背景色过渡效果 */
}

/* 父菜单项，当鼠标悬停时显示子菜单 */
.nav-item {
  position: relative; /* 设置相对定位，作为子菜单的定位基准 */
}

/*子菜单*/
.submenu {
  display: none; /*默认不显示*/
  flex-direction: column;
  position: absolute;
  background-color: #333;
  padding: 10px;
  border-radius: 5px;
  top: 100%; /* 子菜单的顶部紧贴父菜单项的底部 */
  left: 0;
  transform: translateY(20px); /* 初始状态向下移动，准备“拉出”效果 */
  transition: all 0.3s ease; /* 平滑过渡效果 */
  z-index: 1000; /* 确保子菜单在其他内容上方 */
}

/*选择器*/
.nav-link:hover + .submenu,
.submenu:hover {
  display: flex;
  opacity: 1; /* 鼠标悬停时变为不透明 */
  visibility: visible; /* 鼠标悬停时变为可见 */
  transform: translateY(0); /* 鼠标悬停时移动到原始位置 */
}
</style>