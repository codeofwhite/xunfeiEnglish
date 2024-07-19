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
          <button @click="addToMastered(wordsList[currentIndex])">加入已掌握词库</button>
          <button @click="sendMessage(wordsList[currentIndex].name)">请教AI</button>
          <button @click="generateImage(wordsList[currentIndex].name)">生成记忆图片</button>
          <button @click="addToFavorites(wordsList[currentIndex].name)" style="background-color: yellow; color: red">
            收藏
          </button>
        </div>
      </div>
      <div v-else>
        <p>正在加载单词...</p>
      </div>
      <div v-if="imageUrl">
        <img :src="imageUrl" alt="生成的图片" class="generated-image"/>
      </div>
    </div>
    <div class="chat-container">
      <div class="messages-container">
        <div v-for="message in messages" :key="message.id" class="message">
          <p>AI词汇解释：{{ message.text }}</p>
        </div>
      </div>
    </div>
    <button @click="completeLevel">完成关卡</button>
    <p>当前分数: {{ score }}</p>
  </div>
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
import {computed, onMounted, onUnmounted, ref} from 'vue';
import {useRoute} from 'vue-router';
import axios from 'axios';
import FreeTalkAI from "@/components/freeTalk/FreeTalkAI.vue";
import router from "@/router/index.js";
import {useStore} from "vuex";
import {ElMessage} from "element-plus";

export default {
  components: {
    FreeTalkAI
  },
  data() {
    return {
      messages: [
        {
          text: '你好呀！',
        },
      ], // 显示的消息
      imgText: '',
      imageUrl: null
    };
  },
  computed: {
    getUserEmail() {
      return this.$store.state.userEmail;
    },
  },
  methods: {
    // 生成词汇图片
    async generateImage(currentWord) {
      console.log(currentWord);
      try {
        const response = await fetch('http://114.132.52.232:8001/xunfei/generateImage', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          // 初始的时候发参数有带双引号，会造成解析错误，最终修改了Controller，然后调用的参数也修改了
          body: JSON.stringify({text: "生成有关这个单词的语境辅助记忆图片——" + currentWord}) // 确保传递的字符串格式正确
        });

        if (response.ok) {
          const blob = await response.blob();
          this.imageUrl = URL.createObjectURL(blob);
        } else {
          console.error('生成图片失败');
        }
      } catch (error) {
        console.error('请求出错', error);
      }
    },
    // 输入用户input
    sendMessage(text) {
      // this.$refs.speechSynthesis.play(this.userInput);
      this.$refs.aiComponent.startWithText('解释这个单词的意思：' + text);
      // 此处省略AI回复实现代码，可根据实际情况添加
    },
    // 加入收藏
    async addToFavorites(word) {
      try {
        const response = await axios.post('http://114.132.52.232:8002/userFavor/add', {
          user_email: this.getUserEmail, // 替换为实际用户邮箱
          user_favor: word
        });
        if (response.status === 200 && response.data === "success") {
          console.log('单词已成功收藏');
          ElMessage({
            message: '单词已成功收藏',
            type: 'success',
          });
        } else {
          console.error('收藏失败');
          ElMessage({
            message: '收藏失败',
            type: 'error',
          });
        }
      } catch (error) {
        console.error('请求出错:', error);
        ElMessage({
          message: '收藏失败',
          type: 'error',
        });
      }
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
  setup() {
    const route = useRoute();
    const levelId = route.params.levelId;
    const currentIndex = ref(0);
    const wordsList = ref([]);
    const voices = ref([]);
    const synth = window.speechSynthesis;

    const store = useStore();

    const userEmail = computed(() => store.state.userEmail);

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
      try {
        const response = await axios.put('http://114.132.52.232:8002/userGameResource/updateUserCoin', null, {
          params: {
            userEmail: userEmail.value,
            userCoinChange: 50 // 这里传递奖励分数
          }
        });
        // alert(response.data);
      } catch (error) {
        console.error('Error updating user coin:', error);
        // alert('更新金币失败');
      }
    };


    const updateScore = () => {
      const currentWord = wordsList.value[currentIndex.value].name;
      if (!viewedWords.value.has(currentWord)) {
        viewedWords.value.add(currentWord);
        score.value += 10; // 每查看一个新单词加10分
      }
    };

    // 这个之后可能要改路由，定位到level选择那边
    const goToChapterList = () => {
      showModal.value = false;
      clearInterval(interval);
      if (route.params.categoryId === '1') {
        router.push(`/category/${route.params.categoryId}/chapter/${route.params.chapterId}?majorId=${route.query.majorId}`);
      } else {
        router.push(`/category/${route.params.categoryId}/chapter/${route.params.chapterId}`);
      }
    };

    let interval;

    // modal结束倒计时，跳转
    const startCountdown = () => {
      interval = setInterval(() => {
        if (countdown.value > 0) {
          countdown.value--;
        } else {
          clearInterval(interval);
          goToChapterList();
        }
      }, 1000);
    };

    onUnmounted(() => {
      clearInterval(interval);
    });

    // 得到单词
    const fetchWords = async () => {
      try {
        const response = await fetch(`/book/category/${route.params.categoryId}/${levelId}.json`);
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
        const response = await axios.post('http://114.132.52.232:8003/word/insertUnknown', null, {
          params: {
            user_email: userEmail.value,
            word_name: currentWord.name,
          }
        });
        if (response.data === "success") {
          console.log('单词已成功加入未掌握词库');
          ElMessage({
            message: '单词已成功加入未掌握词库',
            type: 'success',
          });
        } else {
          console.error('加入未掌握词库失败');
          ElMessage({
            message: '加入未掌握词库失败',
            type: 'error',
          });
        }
      } catch (error) {
        console.error('请求接口时出错:', error);
        ElMessage({
          message: '请求接口时出错',
          type: 'error',
        });
      }
    };

    // 加入已掌握的词库
    const addToMastered = async (word) => {
      // 加入已掌握单词，就不是unknown单词
      const response = await axios.post('http://114.132.52.232:8003/word/removeUnknown', null, {
        params: {
          user_email: userEmail.value,
          word_name: word.name,
        }
      });
      try {
        const response = await axios.post('http://114.132.52.232:8003/word/insertMastered', null, {
          params: {
            user_email: userEmail.value,
            word_name: word.name,
          }
        });
        if (response.data === "success") {
          console.log('单词已成功加入已掌握词库');
          ElMessage({
            message: '单词已成功加入已掌握词库',
            type: 'success',
          });
        } else {
          console.error('加入已掌握词库失败');
          ElMessage({
            message: '加入已掌握词库失败',
            type: 'error',
          });
        }
      } catch (error) {
        console.error('请求接口时出错:', error);
        ElMessage({
          message: '请求接口时出错',
          type: 'error',
        });
      }
    };

    const playVoice = (text, lang) => {
      const msg = new SpeechSynthesisUtterance(text);
      const voice = voices.value.find(v => v.lang === lang);
      console.log('Selected voice:', voice); // 添加日志
      if (voice) {
        msg.voice = voice;
      } else {
        console.warn('Voice not found for lang:', lang); // 添加警告日志
      }
      msg.volume = 1;
      msg.rate = 1;
      msg.pitch = 1;
      synth.speak(msg);
    };

    onMounted(() => {
      fetchWords();
      synth.onvoiceschanged = () => {
        voices.value = synth.getVoices();
      };
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
      addToMastered,
      userEmail
    };
  },
};
</script>

<style scoped>
.main-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: flex-start;
  gap: 20px;
  margin-top: 20px;
  background-color: #f0f4f8;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.slide-fade-enter-active, .slide-fade-leave-active {
  transition: all 0.5s ease;
}

.slide-fade-enter-from, .slide-fade-leave-to {
  transform: translateX(10px);
  opacity: 0;
}

.word-container, .chat-container {
  flex: 1;
  max-width: 50%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 20px;
  gap: 20px;
  border: 1px solid #ddd;
  border-radius: 10px;
  background-color: #fff;
}

.word-details, .messages-container {
  width: 100%;
  max-width: 300px;
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
  background-color: #e8f0fe;
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
  background-color: #1a73e8;
  color: white;
  font-weight: bold;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

button:hover {
  background-color: #1669c7;
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

.generated-image {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 0 auto;
}
</style>