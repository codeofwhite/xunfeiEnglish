<template>
  <div class="profile-container">
    <h2 class="profile-title">个人资料</h2>
    <div class="profile-section">
      <label for="avatar-upload" class="profile-label">头像：</label>
      <img :src="user.userAvatar" alt="Avatar" class="avatar">
      <input type="file" id="avatar-upload" @change="changeAvatar" class="avatar-upload-input">
      <label for="avatar-upload" class="avatar-upload-btn">
        <i class="fas fa-upload"></i> 更改头像
      </label>
    </div>
    <div class="profile-section">
      <label class="profile-label">名称：</label>
      <span class="profile-info">{{ user.user_name }}</span>
      <button class="btn-primary" @click="changeUsername">修改名称</button>
    </div>
    <div class="profile-section">
      <label class="profile-label">账号：</label>
      <span class="profile-info">{{ userEmail }}</span>
    </div>
    <div class="profile-section">
      <label class="profile-label">金币：</label>
      <span class="profile-info">
        <i class="fas fa-coins coin-icon">{{ userGame.user_coin }}</i>
      </span>
    </div>
    <div class="profile-section">
      <label class="profile-label">火花：</label>
      <span class="profile-info">
        <i class="fas fa-bolt spark-icon">{{ userGame.user_spark }}</i>
      </span>
    </div>
    <div class="profile-section">
      <label class="profile-label">等级：</label>
      <span class="profile-info">
        <i class="fas fa-level-up-alt level-icon"> {{ userGame.user_level }}</i>
      </span>
    </div>
    <hr class="profile-divider">
    <div class="profile-section">
      <button class="logout-btn" @click="onLogout">退出登录</button>
    </div>
    <div class="experience-bar-container">
      <label class="profile-label">经验值：</label>
      <div class="experience-bar">
        <div class="experience-progress" :style="{ width: experiencePercentage + '%' }"></div>
      </div>
    </div>
    <div class="online-days-container">
      <label class="profile-label">在线天数：</label>
      <div class="online-days">{{ onlineDays }}</div>
    </div>
  </div>
</template>

<script setup>
import {computed, onMounted, ref} from 'vue';
import {useStore} from "vuex";
import axios from "axios";

const user = ref({});
const userGame = ref({});
const experiencePercentage = ref(50); // 假设经验值为50%
const onlineDays = ref(120); // 假设在线天数为120天

// 更新用户头像
const changeAvatar = async (event) => {
  const file = event.target.files[0];
  if (!file) return;

  const formData = new FormData();
  formData.append('file', file);
  formData.append('userEmail', userEmail.value);

  try {
    const response = await axios.post('http://localhost:8002/api/user/updateAvatar', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    alert(response.data);
    await fetchUserData(); // 更新用户数据
  } catch (error) {
    console.error('Error updating avatar:', error);
    alert('更新头像失败');
  }
};

const store = useStore();
const isLoggedIn = computed(() => store.state.isLoggedIn);
const userEmail = computed(() => store.state.userEmail);

// 登出操作
const onLogout = () => {
  // 清除 localStorage 或 sessionStorage 中的登录信息
  localStorage.removeItem('isLoggedIn');
  localStorage.removeItem('userEmail');
  sessionStorage.removeItem('isLoggedIn');
  sessionStorage.removeItem('userEmail');

  // 更新 Vuex 状态
  store.commit('setLoggedIn', false);
  store.commit('setUserEmail', null);

  // 可以重定向到登录页面或其他操作
};

// 获取用户的基本资料
const fetchUserData = async () => {
  try {
    const response = await axios.post('http://localhost:8002/user/getUserByEmail', {}, {
      params: {userEmail: userEmail.value}
    });
    // 打印
    console.log(response.data);
    user.value = response.data;
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

// 拿到用户的游戏元素资料
const fetchUserGameData = async () => {
  try {
    const response = await axios.get('http://localhost:8002/userGameResource/get', {
      params: {userEmail: userEmail.value}
    });
    // 打印
    console.log(response.data);
    userGame.value = response.data;
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

onMounted(() => {
  fetchUserData();
  fetchUserGameData();
});

// 更新用户名
function changeUsername() {
  const newUsername = prompt("请输入新的用户名：");
  if (newUsername) {
    axios.put('http://localhost:8002/user/updateUsername', null, {
      params: {
        userEmail: userEmail.value,
        newUsername: newUsername
      }
    })
        .then(response => {
          alert(response.data);
          fetchUserData(); // 更新用户数据
        })
        .catch(error => {
          console.error('Error updating username:', error);
          alert('修改用户名失败');
        });
  }
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
  display: flex;
  align-items: center;
}

.coin-icon {
  color: #FFD700; /* 金币颜色 */
  margin-right: 5px;
}

.spark-icon {
  color: #FF4500; /* 火花颜色 */
  margin-right: 5px;
}

.level-icon {
  color: #4B0082; /* 等级颜色 */
  margin-right: 5px;
}

.experience-bar-container {
  margin-bottom: 10px;
}

.experience-bar {
  background-color: #e0e0e0;
  border-radius: 5px;
  overflow: hidden;
}

.experience-progress {
  height: 20px;
  background-color: #4caf50;
  transition: width 0.5s ease;
}

.online-days-container {
  display: flex;
  align-items: center;
}

.online-days {
  background-color: #ffd700;
  color: #fff;
  padding: 5px 10px;
  border-radius: 5px;
  margin-left: 10px;
  font-weight: bold;
}
</style>