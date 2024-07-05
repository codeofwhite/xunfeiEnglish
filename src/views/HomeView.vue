<!--主页面-->
<template>
  <div class="word-container">
    <div v-if="wordsList.length > 0">
      <transition name="fade" mode="out-in">
        <section class="word-details" :key="currentIndex">
          <h2>{{ wordsList[currentIndex].name }}</h2>
          <p>英式发音: {{ wordsList[currentIndex].ukphone }}</p>
          <p>美式发音: {{ wordsList[currentIndex].usphone }}</p>
          <button @click="playVoice(wordsList[currentIndex].name,'en-GB')">播放英音</button>
          <button @click="playVoice(wordsList[currentIndex].name,'en-US')">播放美音</button>
          <ul>
            <li v-for="trans in wordsList[currentIndex].trans" :key="trans">
              {{ trans }}
            </li>
          </ul>
        </section>
      </transition>
      <div class="buttons">
        <button @click="showNextWord">显示下一个</button>
        <button @click="showForwardWord">显示前一个</button>
      </div>
    </div>
    <div v-else>
      <p>正在加载单词...</p>
    </div>
  </div>
</template>

<script setup>
import {onMounted, ref} from 'vue';

const currentIndex = ref(0);
const wordsList = ref([]);
const voices = ref([]);

const synth = window.speechSynthesis;

onMounted(() => {
// 监听语音列表变化事件
  synth.onvoiceschanged = () => {
    voices.value = synth.getVoices();
  };
});

const playVoice = (text,lang) => {
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

const fetchWords = async () => {
  try {
    const response = await fetch('../public/book/en2zh_CN-min.json');
    const data = await response.json();
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

fetchWords();
</script>

<style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

.word-container {
  margin: 20px;
  font-family: 'Helvetica Neue', Arial, sans-serif;
}

.word-details h2 {
  margin: 0;
  color: #2c3e50;
  text-align: center;
}

.word-details ul {
  list-style-type: none;
  padding: 0;
  text-align: center;
}

.word-details li {
  margin: 10px 0;
  transition: transform 0.2s ease;
}

.word-details li:hover {
  transform: scale(1.05);
}

.buttons {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}

button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 5px;
  background-color: #3498db;
  color: white;
  cursor: pointer;
  transition: background-color 0.2s, transform 0.2s;
}

button:hover {
  background-color: #2980b9;
  transform: translateY(-2px);
}
</style>