<template>
  <div class="profile-container">
    <h2 class="profile-title">个人资料</h2>
    <div class="profile-section">
      <label for="avatar-upload" class="profile-label">头像：</label>
      <img :src="avatarUrl" alt="Avatar" class="avatar">
      <input type="file" id="avatar-upload" @change="changeAvatar" class="avatar-upload-input">
      <label for="avatar-upload" class="avatar-upload-btn">
        <i class="fas fa-upload"></i> 更改头像
      </label>
    </div>
    <div class="profile-section">
      <label class="profile-label">名称：</label>
      <span class="profile-info">{{ username }}</span>
      <button class="btn-primary" @click="changeUsername">修改名称</button>
    </div>
    <div class="profile-section">
      <label class="profile-label">账号：</label>
      <span class="profile-info">{{ account }}</span>
    </div>
    <hr class="profile-divider">
    <div class="profile-section">
      <button class="logout-btn" @click="logout">退出登录</button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
const avatarUrl = ref('src/assets/images/codeofwhite.jpg');
const username = ref('Your Name');
const account = ref('your.account@example.com');

function changeAvatar(event) {
// Handle avatar change
}

function changeUsername() {
// Handle username change
}

function logout() {
// Handle logout
}
</script>

<style scoped>
.profile-container {
  max-width: 600px;
  margin: auto;
  background-color: #ffffff; /* 更亮的背景色 */
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05); /* 更轻的阴影 */
}

.profile-title {
  color: #5D647B; /* 柔和的标题颜色 */
  text-align: center;
  margin-bottom: 20px;
}

.profile-section {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.profile-label {
  margin-right: 10px;
  font-weight: bold;
  color: #5D647B; /* 标签颜色与标题颜色一致 */
}

.avatar {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
  margin-right: 10px;
  border: 2px solid #EAEAEA; /* 头像边框 */
}

.avatar-upload-btn {
  background-color: #4CAF50; /* 按钮背景颜色 */
  color: white;
  padding: 10px 20px;
  text-align: center;
  display: inline-block;
  font-size: 16px;
  cursor: pointer;
  border: none;
  border-radius: 5px;
  transition: background-color 0.3s ease; /* 添加过渡效果 */
}

.btn-primary {
  background-color: #4D96FF; /* 鲜艳的主按钮颜色 */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.avatar-upload-btn:hover {
  background-color: #45a049; /* 鼠标悬停时的背景颜色 */
}

.avatar-upload-input {
  display: none; /* 保持不显示，显示的话不好看，多了一个东西 */
}

.logout-btn {
  background-color: #FF6B6B; /* 鲜艳的退出按钮颜色 */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  display: block;
  width: 100%;
}

.profile-divider {
  margin-top: 20px;
  margin-bottom: 20px;
  border-color: #EAEAEA; /* 分割线颜色 */
}

.profile-info {
  flex-grow: 1;
  color: #5D647B; /* 信息文本颜色 */
}
</style>