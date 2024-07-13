<template>
  <div class="app-container">
    <header class="app-header">
      <h1>English Mastery</h1>
      <p>Unlock the world with English</p>
    </header>
    <div class="main-content">
      <aside class="sidebar">
        <h2>滚动栏</h2>
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
      </section>
    </div>
  </div>
</template>

<script>
import SwiperComponents from "@/components/SwiperComponents.vue";

export default {
  name: 'App',
  components: {SwiperComponents},
  data() {
    return {
      features: [
        {title: 'Interactive Lessons', description: 'Engage with dynamic exercises and games.'},
        {title: 'Progress Tracking', description: 'Monitor your improvement with our analytics tools.'},
        {title: 'Community Support', description: 'Join discussions and learn together with peers.'}
      ]
    };
  }
};
</script>

<style scoped>
.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  background-color: #f9f9f9;
  padding: 20px;
}

.app-header {
  background-color: #4CAF50;
  color: white;
  padding: 2rem;
  width: 100%;
  text-align: center;
}

.app-header h1 {
  animation: fadeInDown 2s ease;
}

.app-header p {
  animation: fadeInUp 2s ease;
}

.main-content {
  display: flex;
  width: 100%;
  margin-top: 20px;
}

.sidebar {
  flex: 1;
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  margin-right: 20px;
}

.sidebar h2 {
  margin-bottom: 10px;
  color: #333;
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
  color: #4CAF50;
  transition: color 0.3s ease;
}

.sidebar ul li a:hover {
  color: #333;
}

.hot-info {
  flex: 2;
  background-color: #fff;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.hot-info h2 {
  margin-bottom: 20px;
  color: #333;
}

.info-item {
  margin-bottom: 20px;
}

.info-item h3 {
  margin-bottom: 10px;
  color: #4CAF50;
}

.info-item p {
  color: #555;
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

.swiper {
  height: 500px;
  width: 800px;
}
</style>