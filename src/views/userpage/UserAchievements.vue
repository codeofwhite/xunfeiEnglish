<template>
  <div class="achievement-container">
    <h2>我的成就</h2>
    <div class="achievement-grid">
      <div v-for="achievement in achievements" :key="achievement.id" class="achievement-badge">
        {{ achievement.user_achievement }}
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';
import {useStore} from "vuex";

const store = useStore();

const achievements = ref([]);
const userEmail = store.state.userEmail; // 替换为实际用户的email

const fetchAchievements = async () => {
  try {
    const response = await axios.get(`http://localhost:8002/api/user/achievements/${userEmail}`);
    achievements.value = response.data;
  } catch (error) {
    console.error('Error fetching achievements:', error);
  }
};

onMounted(() => {
  fetchAchievements();
});
</script>

<style scoped>
.achievement-container {
  max-width: 800px;
  margin: auto;
  text-align: center;
}

.achievement-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
  gap: 10px;
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: #5eb3ff;
}

.achievement-badge {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #afffdd;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s ease; /* 添加过渡效果 */
}

.achievement-badge:hover {
  transform: scale(1.1); /* 鼠标悬停时放大 */
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3); /* 鼠标悬停时增加阴影 */
}
</style>