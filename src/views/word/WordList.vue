<template>
  <div class="word-container">
    <div v-if="wordsList.length > 0">
      <transition name="slide-fade" mode="out-in">
        <section class="word-details" :key="currentIndex">
          <h2>{{ wordsList[currentIndex].name }}</h2>
          <p class="pronunciation">英式发音: {{ wordsList[currentIndex].ukphone }}</p>
          <p class="pronunciation">美式发音: {{ wordsList[currentIndex].usphone }}</p>
          <ul class="translations">
            <li v-for="trans in wordsList[currentIndex].trans" :key="trans">
              {{ trans }}
            </li>
          </ul>
        </section>
      </transition>
      <div class="buttons">
        <button @click="showNextWord">显示下一个</button>
        <button @click="showForwardWord">显示前一个</button>
        <button @click="addToUnknown(word)">加入未掌握词库</button>
      </div>
    </div>
    <div v-else>
      <p>正在加载单词...</p>
    </div>
  </div>
</template>

<script setup>
import {ref, onMounted} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';

const route = useRoute();
const wordListId = route.params.wordListId;
const currentIndex = ref(0);
const wordsList = ref([]);

const fetchWords = async () => {
  try {
    const response = await fetch(`../public/book/${wordListId}.json`);
    const data = await response.json();
    const word = ref('a'); // 假设这是您当前学习的单词
    wordsList.value = data;
  } catch (error) {
    console.error('读取单词文件时出错:', error);
  }
};

const showNextWord = () => {
  if (currentIndex.value < wordsList.value.length - 1) {
    currentIndex.value++;
  } else {
    currentIndex.value = 0; // 如果到达列表末尾，重新开始
  }
};

const showForwardWord = () => {
  if (currentIndex.value <= 0) {
    currentIndex.value = wordsList.value.length - 1; // 如果到达第一个单词之前，跳转到最后一个单词
  } else {
    currentIndex.value--;
  }
};

const addToUnknown = async () => {
  const currentWord = wordsList.value[currentIndex.value];
  try {
// 使用params属性发送Query参数
    const response = await axios({
      method: 'post',
      url: 'http://localhost:8001/word/insertUnknown',
      params: {
        user_email: "99gelanjingling@gmail.com",
        word_name: currentWord.name, // 假设word对象有一个name属性
// 添加其他需要的参数
      }
    });
    if (response.data === "success") {
      console.log('单词已成功加入未掌握词库');
// 这里可以添加其他逻辑，例如清除单词，更新列表等
    } else {
      console.error('加入未掌握词库失败');
    }
  } catch (error) {
    console.error('请求接口时出错:', error);
  }
};

fetchWords();
</script>

<style scoped>
.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.5s ease;
}

.slide-fade-enter-from, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}

.word-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  gap: 20px;
  margin: 20px;
  padding: 20px;
  border-radius: 10px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.word-details {
  width: 200px;
  padding: 15px;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.08);
  transition: transform 0.3s ease;
}

.word-details:hover {
  transform: translateY(-5px);
}

.word-details h2 {
  color: #333;
  text-align: center;
}

.pronunciation {
  font-size: 0.9em;
  color: #666;
}

.translations {
  margin-top: 10px;
}

.translations li {
  background-color: #f0f0f0;
  margin: 5px 0;
  padding: 5px;
  border-radius: 5px;
}

.buttons {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

button {
  padding: 10px 20px;
  margin: 0 10px;
  border: none;
  border-radius: 5px;
  background-color: #5cb85c;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #4cae4c;
  transform: translateY(-2px);
}
</style>