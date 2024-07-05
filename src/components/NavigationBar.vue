<template>
  <header>
    <nav>
      <RouterLink to="/" class="nav-link">Home</RouterLink>
      <RouterLink to="/vbIndex" class="nav-link">VBIndex</RouterLink>
      <RouterLink to="/chatAI" class="nav-link">ChatAI</RouterLink>
      <RouterLink to="/speechScore" class="nav-link">SpeechScore</RouterLink>
      <RouterLink to="/speechToWord" class="nav-link">SpeechToWord</RouterLink>
      <RouterLink to="/speechSynthesis" class="nav-link">SpeechSynthesis</RouterLink>
      <!-- 父菜单项 -->
      <div class="nav-item">
        <div class="nav-link">MyVocBooks</div>
        <!-- 子菜单 -->
        <div class="submenu">
          <RouterLink to="/unKnownWord" class="nav-link">UnKnownWord</RouterLink>
          <RouterLink to="/masteredWord" class="nav-link">MasteredWord</RouterLink>
          <RouterLink to="/favoriteWord" class="nav-link">FavoriteWord</RouterLink>
          <!-- 更多子菜单项 -->
        </div>
      </div>
      <!--  User父菜单    -->
      <div class="nav-item">
        <div class="nav-link">User</div>
        <!-- 子菜单 -->
        <div class="submenu">
          <RouterLink to="/userProfile" class="nav-link">userProfile</RouterLink>
          <RouterLink to="/userFavorites" class="nav-link">userFavorites</RouterLink>
          <RouterLink to="/userAchievements" class="nav-link">userAchievements</RouterLink>
          <RouterLink to="/userHelp" class="nav-link">userHelp</RouterLink>
          <!-- 更多子菜单项 -->
        </div>
      </div>
    </nav>
  </header>
</template>

<script setup>
import {RouterLink} from 'vue-router';

</script>

<style scoped>
header {
  background-color: #333; /* 深色背景 */
  padding: 10px 0; /* 上下填充 */
}

nav {
  position: relative; /* 确保子菜单相对于导航栏定位 */
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
}

.nav-link {
  color: #fff; /* 白色文字 */
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