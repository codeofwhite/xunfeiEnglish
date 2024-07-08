<template>
  <div class="word-container">
    <div v-if="wordsList.length > 0">
      <transition name="slide-fade" mode="out-in">
        <section class="word-details" :key="currentIndex">
          <h2>{{ wordsList[currentIndex].name }}</h2>
          <p class="pronunciation">英式发音: {{ wordsList[currentIndex].ukphone }}</p>
          <p class="pronunciation">美式发音: {{ wordsList[currentIndex].usphone }}</p>
          <button @click="playVoice(wordsList[currentIndex].name,'en-GB')">播放英音</button>
          <button @click="playVoice(wordsList[currentIndex].name,'en-US')">播放美音</button>
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
import {ref} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';

const route = useRoute();
const wordListId = route.params.wordListId;
const currentIndex = ref(0);
const wordsList = ref([]);
const voices = ref([]);

const synth = window.speechSynthesis;

const fetchWords = async () => {
  try {
    const response = await fetch(`../public/book/${wordListId}.json`);
    wordsList.value = await response.json();
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

const playVoice = (text, lang) => {
  const msg = new SpeechSynthesisUtterance(text); // 传入需要播放的文字
  const voice = voices.value.find(v => v.lang === lang); // 移除 this 关键字
  if (voice) {
    msg.voice = voice; // 设置语音
  }
  msg.volume = 1; // 声音音量：1
  msg.rate = 1; // 语速：1
  msg.pitch = 1; // 音高：1
  synth.speak(msg); // 播放
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
  flex-direction: column;
  align-items: center;
  padding: 20px;
  background-color: #f0f7ff; /* 浅蓝色背景 */
}

.word-details {
  width: 100%;
  max-width: 300px;
  margin: 10px 0;
  padding: 20px;
  border-radius: 10px;
  background-color: #ffffff;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.word-details h2 {
  color: #333;
  font-size: 1.5em;
  margin-bottom: 10px;
}

.pronunciation {
  font-size: 0.9em;
  color: #666;
  margin: 5px 0;
}

.translations li {
  background-color: #e8f0fe; /* 浅蓝色背景的翻译列表项 */
  margin: 5px 0;
  padding: 5px;
  border-radius: 5px;
  font-size: 0.9em;
}

.buttons {
  display: flex;
  justify-content: space-around;
  width: 100%;
  max-width: 300px;
}

button {
  padding: 10px 15px;
  margin: 0 5px;
  border: none;
  border-radius: 5px;
  background-color: #1a73e8; /* 蓝色按钮 */
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #1669c7; /* 按钮悬停时的深蓝色 */
  transform: translateY(-2px);
}
</style>