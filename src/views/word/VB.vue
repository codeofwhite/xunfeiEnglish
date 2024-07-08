<template>
  <div class="bookshelf">
    <div class="book" v-for="item in wordIndex" :key="item.id">
      <router-link :to="{ name: 'WordList', params: { wordListId: item.id } }">
        <div class="cover">
          <div class="title">{{ item.name }}</div>
          <div class="word-count">{{ item.length }} words</div>
        </div>
      </router-link>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRouter} from 'vue-router';

const router = useRouter();
const wordIndex = ref([]);

const fetchIndex = async () => {
  try {
    const response = await fetch('../public/VBIndex.json');
    const data = await response.json();
    wordIndex.value = data;
  } catch (error) {
    console.error('读取索引文件时出错:', error);
  }
};

onMounted(() => {
  fetchIndex();
});
</script>

<style scoped>
.bookshelf {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 20px;
  padding: 20px;
  background-color: #e8f0fe; /* 浅蓝色背景，给人一种清新的感觉 */
}

.book {
  width: 150px;
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.book:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2); /* 鼠标悬停时增加阴影效果 */
}

.cover {
  height: 200px;
  background-color: #ffffff; /* 白色封面 */
  border: 1px solid #cfd8dc; /* 浅灰色边框 */
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column; /* 垂直布局 */
  align-items: center;
  justify-content: center;
}

.title {
  font-size: 1.1em;
  text-align: center;
  color: #1a73e8; /* 蓝色标题 */
  font-weight: bold; /* 加粗标题字体 */
}

.word-count {
  margin-top: 10px;
  font-size: 0.9em;
  color: #5f6368; /* 深灰色字体显示单词数 */
}
</style>