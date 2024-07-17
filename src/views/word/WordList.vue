<template>
  <div class="main-container">
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
          <button @click="sendMessage(wordsList[currentIndex].name)">请教AI</button>
        </div>
      </div>
      <div v-else>
        <p>正在加载单词...</p>
      </div>
    </div>
    <div class="chat-container">
      <!-- AI文字对话窗口的内容 -->
      <div class="messages-container">
        <div v-for="message in messages" :key="message.id"
             class="message">
          <p>AI词汇解释：{{ message.text }}</p>
        </div>
      </div>
    </div>
    <button @click="completeLevel">完成关卡</button>
    <p>当前分数: {{ score }}</p>
  </div>
  <!--  调用AI接口，ref提供给AI的参数，handleResult提供结果返回    -->
  <FreeTalkAI v-show="false" ref="aiComponent" @result-received="handleResult"></FreeTalkAI>
  <modal class="modal" v-if="showModal" @close="showModal = false">
    <h3 slot="header">关卡完成</h3>
    <p slot="body">恭喜你完成了本关卡！</p>
    <p slot="body">总得分: {{ score }}</p>
    <p slot="body">将在 {{ countdown }} 秒后跳转...</p>
    <div slot="footer">
      <button @click="goToChapterList">返回章节列表</button>
    </div>
  </modal>
</template>

<script>
import {onMounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';
import FreeTalkAI from "@/components/freeTalk/FreeTalkAI.vue";
import router from "@/router/index.js";

export default {
  components: {
    FreeTalkAI
  },
  setup() {
    const route = useRoute();
    const levelId = route.params.levelId;
    const currentIndex = ref(0);
    const wordsList = ref([]);
    const voices = ref([]);
    const synth = window.speechSynthesis;

    const showModal = ref(false); // 控制显示完成关卡
    const countdown = ref(5); // 显示倒计时

    // 记分
    const score = ref(0); // 分数
    const viewedWords = ref(new Set()); // 记录已查看的单词

    // 点击完成关卡
    const completeLevel = async () => {
      score.value += 50; // 完成关卡奖励分数

      showModal.value = true;
      setTimeout(goToChapterList, 5000); // 5秒后自动跳转
      startCountdown(); // 开始计时
    };

    const updateScore = () => {
      const currentWord = wordsList.value[currentIndex.value].name;
      if (!viewedWords.value.has(currentWord)) {
        viewedWords.value.add(currentWord);
        score.value += 10; // 每查看一个新单词加10分
      }
    };

    // 这个之后可能要改路由
    const goToChapterList = () => {
      showModal.value = false;
      router.push(`/category/${route.params.categoryId}/chapter/${route.params.chapterId}`);
    };

    // modal结束倒计时，跳转
    const startCountdown = () => {
      const interval = setInterval(() => {
        if (countdown.value > 0) {
          countdown.value--;
        } else {
          clearInterval(interval);
          goToChapterList();
        }
      }, 1000);
    };

    // 得到单词
    const fetchWords = async () => {
      try {
        const response = await fetch(`/book/${levelId}.json`);
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
      updateScore(); // 每次显示新单词时更新分数
    };

    const showForwardWord = () => {
      if (currentIndex.value <= 0) {
        currentIndex.value = wordsList.value.length - 1; // 如果到达第一个单词之前，跳转到最后一个单词
      } else {
        currentIndex.value--;
      }
      updateScore(); // 每次显示新单词时更新分数
    };

    const addToUnknown = async () => {
      const currentWord = wordsList.value[currentIndex.value];
      try {
        const response = await axios.post('http://localhost:8001/word/insertUnknown', null, {
          params: {
            user_email: '99gelanjingling@gmail.com',
            word_name: currentWord.name,
          }
        });
        if (response.data === "success") {
          console.log('单词已成功加入未掌握词库');
        } else {
          console.error('加入未掌握词库失败');
        }
      } catch (error) {
        console.error('请求接口时出错:', error);
      }
    };

    const playVoice = (text, lang) => {
      const msg = new SpeechSynthesisUtterance(text);
      const voice = voices.value.find(v => v.lang === lang);
      if (voice) {
        msg.voice = voice;
      }
      msg.volume = 1;
      msg.rate = 1;
      msg.pitch = 1;
      synth.speak(msg);
    };

    onMounted(() => {
      fetchWords();
    });

    return {
      currentIndex,
      wordsList,
      voices,
      showNextWord,
      showForwardWord,
      addToUnknown,
      playVoice,
      showModal,
      completeLevel,
      goToChapterList,
      countdown,
      score,
    };
  },
  data() {
    return {
      messages: [
        {
          text: '你好呀！'
        },
      ], // 显示的消息
    };
  },
  methods: {
    // 输入用户input
    sendMessage(text) {
      // this.$refs.speechSynthesis.play(this.userInput);
      this.$refs.aiComponent.startWithText(text);
      // 此处省略AI回复实现代码，可根据实际情况添加
    },
    handleResult(result) {
      // 在这里处理结果，例如将其显示在界面上
      console.log('AI结果:', result);
      this.messages.push({
        id: Date.now(), // 使用时间戳作为唯一ID
        from: 'ai',
        text: result.ai // 假设result对象有一个ai属性，包含AI的回复文本
      });
    },
  },
};
</script>

<style scoped>
.main-container {
  display: flex; /* 使用flex布局 */
  flex-direction: row; /* 子元素横向排列 */
  justify-content: center; /* 水平居中 */
  align-items: flex-start; /* 子元素顶部对齐 */
  gap: 20px; /* 子元素之间的间隙 */
  margin-top: 20px;
}

.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.5s ease;
}

.slide-fade-enter-from, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}

.word-container, .chat-container {
  flex: 1; /* 子容器占据等量空间 */
  max-width: 50%; /* 子容器最大宽度为父容器的一半 */
  display: flex;
  flex-direction: row; /* 改为横向布局 */
  justify-content: center;
  align-items: flex-start; /* 顶部对齐 */
  padding: 20px;
  gap: 20px; /* 窗口之间的间隙 */
  border: 1px solid #ddd;
  border-radius: 8px;
}

.word-container {
  max-width: 550px;
}

.word-details, .messages-container {
  width: 100%;
  max-width: 300px; /* 控制最大宽度 */
  /* 其他样式保持不变 */
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
  max-width: 400px;
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

.chat-container {
  background-color: #f9f9f9;
  padding: 10px;
  max-width: 300px;
}

.messages-container {
  margin: 0;
  padding: 0;
}

.message {
  list-style: none;
  margin-bottom: 10px;
  padding: 5px;
  border-radius: 4px;
  font-family: "Times New Roman";
}

p {
  margin: 0;
  padding: 0;
}

.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  z-index: 1000;
}

.modal h3 {
  margin-top: 0;
}

.modal p {
  margin: 10px 0;
}

.modal button {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: #1a73e8;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

.modal button:hover {
  background-color: #1669c7;
  transform: translateY(-2px);
}
</style>