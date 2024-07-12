<template>
  <div class="word-collection">
    <h1>不认识的单词</h1>
    <div class="word-list">
      <div class="word-item" v-for="item in unknownWords" :key="item.word_name">
        <div class="word">{{ item }}</div>
        <!--        <div class="meaning">{{ item.meaning }}</div>-->
      </div>
    </div>
    <!-- 添加单词的表单 -->
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import axios from 'axios';

const unknownWords = ref([]);

// 在组件挂载时调用后端接口
onMounted(async () => {
  try {
    // 使用POST方法并传入user_email
    const response = await axios({
      method: 'post',
      url: '/api/word/selectUnknown',
      params: {
        user_email: '99gelanjingling@gmail.com' // 替换为实际的用户邮箱
      }
    });
    unknownWords.value = response.data; // 假设后端返回的是一个单词数组
    console.log('调用成功', response)
  } catch (error) {
    console.error('获取未掌握单词时出错:', error);
  }
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

.word-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
  padding: 10px;
  background-color: #fff;
  border-radius: 5px;
}

.word, .meaning {
  font-size: 1.1em;
}

.add-word-form {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.add-word-form input[type="text"] {
  flex: 1;
  margin-right: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.add-word-form button {
  padding: 10px 20px;
  background-color: #5b8bf7;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.add-word-form button:hover {
  background-color: #4a7bd7;
}
</style>