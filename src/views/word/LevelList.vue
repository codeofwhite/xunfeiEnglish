<template>
  <div class="level-list">
    <div class="start-end">Start</div>
    <svg class="line" viewBox="0 0 300 300" preserveAspectRatio="none">
      <path d="M 50 50 Q 100 100, 150 50 T 250 50" fill="none" stroke="#1a73e8" stroke-width="2"/>
    </svg>
    <div class="grid-container">
      <div class="level" v-for="(level, index) in levels" :key="level.id" :class="'level-' + (index % 5)">
        <router-link :to="{ name: 'WordList', params: { categoryId: route.params.categoryId,
        chapterId: route.params.chapterId, levelId: level.id } }">
          <div class="circle">
            <div class="title">{{ level.name }}</div>
            <div class="word-count">{{ level.words.length }} words</div>
          </div>
        </router-link>
      </div>
    </div>
    <svg class="line" viewBox="0 0 300 300" preserveAspectRatio="none">
      <path d="M 50 50 Q 100 100, 150 50 T 250 50" fill="none" stroke="#1a73e8" stroke-width="2"/>
    </svg>
    <div class="start-end">End</div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';

const route = useRoute();
const levels = ref([]);

const fetchLevels = async () => {
  try {
    const response = await fetch('/VBIndex.json');
    const text = await response.text();
    const data = JSON.parse(text);
    const category = data.find(cat => cat.id === parseInt(route.params.categoryId));
    if (category) {
      const chapter = category.chapters.find(ch => ch.id === parseInt(route.params.chapterId));
      if (chapter) {
        levels.value = chapter.levels;
      }
    }
  } catch (error) {
    console.error('读取关卡文件时出错:', error);
  }
};

onMounted(() => {
  fetchLevels();
});
</script>

<style scoped>
.level-list {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  padding: 20px;
  background: linear-gradient(135deg, #e8f0fe, #cfd8dc);
  position: relative;
  border-radius: 15px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.start-end {
  font-size: 1.5em;
  font-weight: bold;
  color: #1a73e8;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
}

.line {
  width: 2px;
  height: 100px;
  background-color: #1a73e8;
  z-index: 0;
  margin-bottom: -20px;
}

.grid-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 20px;
  position: relative;
}

.level {
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  position: relative;
}

.level:hover {
  transform: translateY(-5px);
  border: 2px solid;
  border-radius: 50%;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.circle {
  width: 100px;
  height: 100px;
  background-color: #ffffff;
  border: 2px solid;
  border-radius: 50%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s ease, transform 0.3s ease;
}

.circle:hover {
  background-color: #f1f3f4;
  transform: scale(1.1);
}

.title {
  font-size: 1.2em;
  text-align: center;
  font-weight: bold;
  color: #1a73e8;
}

.word-count {
  margin-top: 5px;
  font-size: 0.9em;
  color: #5f6368;
}

/* Different colors for different levels */
.level-0 .circle {
  border-color: #1a73e8;
  background-color: #e8f0fe;
}

.level-1 .circle {
  border-color: #34a853;
  background-color: #e6f4ea;
}

.level-2 .circle {
  border-color: #fbbc05;
  background-color: #fef7e0;
}

.level-3 .circle {
  border-color: #ea4335;
  background-color: #fdecea;
}

.level-4 .circle {
  border-color: #ff5722;
  background-color: #ffe6e1;
}

/* Line connecting levels */
.level::before {
  content: '';
  position: absolute;
  top: 100%;
  left: 49%;
  width: 2px;
  height: 100%;
  background-color: #1a73e8;
  z-index: 0;
}
</style>