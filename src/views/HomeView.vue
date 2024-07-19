<template>
  <div class="app-container">
    <header class="app-header">
      <h1>Mynah</h1>
      <p>Unlock the world with English</p>
    </header>
    <div class="main-content">
      <aside class="sidebar">
        <h2>最新动态</h2>
        <ul>
          <swiper-components class="swiper"></swiper-components>
        </ul>
      </aside>
      <section class="hot-info">
        <h2>热门信息</h2>
        <div class="info-item">
          <h3>最新活动</h3>
          <p>参与我们的最新活动，赢取丰厚奖品！</p>
        </div>
        <div class="info-item">
          <h3>用户故事</h3>
          <p>看看其他用户如何通过我们的平台提升英语水平。</p>
        </div>
        <div class="info-item">
          <h3>学习资源</h3>
          <p>获取最新的学习资源，帮助你更快掌握英语。</p>
        </div>
        <!-- 添加签到按钮 -->
        <div class="sign-in">
          <button @click="signIn">签到</button>
        </div>
        <div class="punch-in-count">
          <p>连续签到次数: {{ punchInCount }}</p>
          <p class="encouragement">继续加油，你正在变得越来越棒！</p>
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import SwiperComponents from "@/components/SwiperComponents.vue";

export default {
  name: 'App',
  components: {SwiperComponents},
  data() {
    return {
      punchInCount: 0,
      userEmail: 'user@example.com' // 假设用户邮箱是固定的
    };
  },
  computed: {
    getUserEmail() {
      return this.$store.state.userEmail;
    }
  },
  methods: {
    async signIn() {
      try {
        const response = await axios.post('http://localhost:8002/userGameResource/punchIn', {userEmail: this.getUserEmail});
        alert(response.data);
        await this.getPunchInCount();
      } catch (error) {
        console.error('签到失败:', error);
      }
    },
    async getPunchInCount() {
      try {
        const response = await axios.get('http://localhost:8002/userGameResource/getPunchInCount', {params: {userEmail: this.getUserEmail}});
        this.punchInCount = response.data.punchInCount;
      } catch (error) {
        console.error('获取连续签到次数失败:', error);
      }
    }
  },
  mounted() {
    this.getPunchInCount();
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background-color: #e0f7fa; /* 柔和的蓝绿色背景 */
  padding: 20px;
}

.app-header {
  background-color: #00796b; /* 深绿色背景 */
  color: white;
  padding: 2rem;
  width: 100%;
  text-align: center;
}

.app-header h1 {
  animation: fadeInDown 2s ease;
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
}

.app-header p {
  animation: fadeInUp 2s ease;
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 使用卡通字体 */
}

.main-content {
  display: flex;
  width: 100%;
  margin-top: 20px;
}

.sidebar {
  flex: 1;
  background-color: #ffffff;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
}

.sidebar h2 {
  margin-bottom: 10px;
  color: #00796b; /* 深绿色 */
}

.sidebar ul {
  list-style: none;
  padding: 0;
}

.sidebar ul li {
  margin-bottom: 10px;
}

.sidebar ul li a {
  text-decoration: none;
  color: #00796b; /* 深绿色 */
  transition: color 0.3s ease;
}

.sidebar ul li a:hover {
  color: #004d40; /* 更深的绿色 */
}

.hot-info {
  flex: 2;
  background-color: #ffffff;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.hot-info h2 {
  margin-bottom: 20px;
  color: #00796b; /* 深绿色 */
}

.info-item {
  margin-bottom: 20px;
}

.info-item h3 {
  margin-bottom: 10px;
  color: #00796b; /* 深绿色 */
}

.info-item p {
  color: #555;
}

.sign-in {
  margin-top: 20px;
}

.sign-in button {
  background-color: #00796b; /* 深绿色 */
  color: white;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.3s ease;
  animation: pulse 1s infinite;
}

.sign-in button:hover {
  background-color: #004d40; /* 更深的绿色 */
  transform: scale(1.05);
}

.punch-in-count {
  margin-top: 20px;
  color: #00796b; /* 深绿色 */
}

.encouragement {
  color: #ff9800; /* 橙色 */
  font-weight: bold;
  animation: fadeInUp 1s ease;
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translate3d(0, -100%, 0);
  }
  to {
    opacity: 1;
    transform: none;
  }
}

@keyframes fadeInUp {
  from {
    opacity: 0;
    transform: translate3d(0, 100%, 0);
  }
  to {
    opacity: 1;
    transform: none;
  }
}

@keyframes pulse {
  0% {
    transform: scale(1);
  }
  50% {
    transform: scale(1.05);
  }
  100% {
    transform: scale(1);
  }
}

.swiper {
  height: 500px;
  width: 800px;
}

.encouragement {
  color: #ff9800; /* 橙色 */
  font-weight: bold;
  animation: fadeInUp 1s ease;
}
</style>