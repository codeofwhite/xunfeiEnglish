<template>
  <div class="bookshelf">
    <div class="book" v-for="category in categories" :key="category.id">
      <router-link :to="{ name: 'ChapterList', params: { categoryId: category.id } }">
        <div class="cover">
          <div class="title">{{ category.name }}</div>
          <div class="chapter-count">{{ category.chapters.length }} chapters</div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';

const categories = ref([]);

const fetchCategories = async () => {
  try {
    const response = await fetch('/VBIndex.json');
    const data = await response.json();
    categories.value = data;
  } catch (error) {
    console.error('读取类别文件时出错:', error);
  }
};

onMounted(() => {
  fetchCategories();
});
</script>

<style scoped>
.bookshelf {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 20px;
  padding: 20px;
  background-color: #f5f5f5;
}

.book {
  width: 180px;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  position: relative;
}

.book:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.cover {
  height: 220px;
  background-color: #ffffff;
  border: 1px solid #cfd8dc;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 1;
}

.title {
  font-size: 1.2em;
  text-align: center;
  color: #1a73e8;
  font-weight: bold;
}

.chapter-count {
  margin-top: 10px;
  font-size: 0.9em;
  color: #5f6368;
}

.book::before {
  content: '';
  position: absolute;
  top: 10px;
  left: 10px;
  width: 100%;
  height: 100%;
  background-color: #e0e0e0;
  border-radius: 8px;
  z-index: 0;
  transform: rotate(-2deg);
}

.book::after {
  content: '';
  position: absolute;
  top: 20px;
  left: 20px;
  width: 100%;
  height: 100%;
  background-color: #d0d0d0;
  border-radius: 8px;
  z-index: 0;
  transform: rotate(-4deg);
}
</style>