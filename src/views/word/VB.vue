<template>
  <div class="bookshelf">
    <div class="book" v-for="item in wordIndex" :key="item.id">
      <router-link :to="{ name: 'WordList', params: { wordListId: item.id } }">
        <div class="cover">
          <div class="title">{{ item.name }}</div>
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
}

.book {
  width: 150px;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.book:hover {
  transform: translateY(-5px);
}

.cover {
  height: 200px;
  background-color: #f7f7f7;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: center;
}

.title {
  font-size: 1.1em;
  text-align: center;
}
</style>