<template>
  <div class="word-collection">
    <h1>不认识的单词</h1>
    <div class="word-list">
      <div v-for="(words, letter) in sortedWords" :key="letter" class="letter-section">
        <h2>{{ letter }}</h2>
        <div class="word-item" v-for="word in words" :key="word">
          <div class="word">{{ word }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted, computed} from 'vue';
import axios from 'axios';
import {useStore} from "vuex";

const unknownWords = ref([]);

// 获取状态
const store = useStore();

const userEmail = computed(() => store.state.userEmail);

// 在组件挂载时调用后端接口
onMounted(async () => {
  try {
    const response = await axios({
      method: 'post',
      url: '/api/word/selectUnknown',
      params: {
        user_email: userEmail.value // 替换为实际的用户邮箱
      }
    });
    unknownWords.value = response.data; // 假设后端返回的是一个单词数组
    console.log('调用成功', response);
  } catch (error) {
    console.error('获取未掌握单词时出错:', error);
  }
});

// 按字母顺序排序单词
const sortedWords = computed(() => {
  const grouped = unknownWords.value.reduce((acc, word) => {
    const firstLetter = word[0].toUpperCase();
    if (!acc[firstLetter]) {
      acc[firstLetter] = [];
    }
    acc[firstLetter].push(word);
    return acc;
  }, {});
  return Object.keys(grouped).sort().reduce((acc, letter) => {
    acc[letter] = grouped[letter];
    return acc;
  }, {});
});
</script>

<style scoped>
.word-collection {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
  background-color: #f0f0f0;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

h1 {
  text-align: center;
  color: #333;
}

.word-list {
  margin-top: 20px;
}

.letter-section {
  margin-bottom: 20px;
}

.letter-section h2 {
  margin-bottom: 10px;
  color: #5b8bf7;
}

.word-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
}

.word {
  font-size: 1.1em;
}
</style>