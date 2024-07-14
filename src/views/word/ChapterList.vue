<template>
  <div class="game-chapters">
    <div class="chapter" v-for="chapter in chapters" :key="chapter.id">
      <router-link :to="{ name: 'LevelList', params: { categoryId: route.params.categoryId, chapterId: chapter.id } }">
        <div class="chapter-cover">
          <div class="chapter-title">{{ chapter.name }}</div>
          <div class="chapter-levels">{{ chapter.levels.length }} levels</div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';

const route = useRoute();
const chapters = ref([]);

const fetchChapters = async () => {
  try {
    const response = await fetch('/VBIndex.json');
    const data = await response.json();
    const category = data.find(cat => cat.id === parseInt(route.params.categoryId));
    if (category) {
      chapters.value = category.chapters;
    }
  } catch (error) {
    console.error('读取章节文件时出错:', error);
  }
};

onMounted(() => {
  fetchChapters();
});
</script>

<style scoped>
.game-chapters {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 20px;
  padding: 20px;
  background-color: #f0f0f0;
}

.chapter {
  width: 200px;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  position: relative;
}

.chapter:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.chapter-cover {
  height: 250px;
  background-color: #ffffff;
  border: 2px solid #cfd8dc;
  border-radius: 12px;
  padding: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 1;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.chapter-cover:hover {
  background-color: #ffebee;
  transform: scale(1.05);
}

.chapter-title {
  font-size: 1.3em;
  text-align: center;
  color: #ff5722;
  font-weight: bold;
}

.chapter-levels {
  margin-top: 15px;
  font-size: 1em;
  color: #757575;
}

.chapter::before {
  content: '';
  position: absolute;
  top: 15px;
  left: 15px;
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
  border-radius: 12px;
  z-index: 0;
  transform: rotate(-2deg);
}

.chapter::after {
  content: '';
  position: absolute;
  top: 30px;
  left: 30px;
  width: 100%;
  height: 100%;
  background-color: #d0d0d0;
  border-radius: 12px;
  z-index: 0;
  transform: rotate(-4deg);
}
</style>