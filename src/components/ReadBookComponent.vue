<template>
  <!-- 语音合成组件  -->
  <SpeechSynthesis v-show="false" ref="speechSynthesis"></SpeechSynthesis>
  <div id="book-reader" class="book-reader">
    <button @click="goToAudioBooks" class="back-button">返回</button>
    <h1 class="title">书籍阅读</h1>

    <div v-if="book" class="book-content">
      <h2 class="book-title">{{ book.title }}</h2>
      <textarea class="text-box" v-model="bookContent" readonly></textarea>
      <div class="controls">
        <input type="range" class="progress-bar" v-model="playbackProgress" @input="seekTo" :max="totalSentences - 1"/>
        <button @click="playAllText" class="play-button">播放全文</button>
      </div>
      <div v-if="recording" class="recording-controls">
        <p>倒计时: {{ countdown }} 秒</p>
        <button @click="stopRecording" class="stop-button">停止录音</button>
      </div>
      <div v-for="(sentence, index) in sentences" :key="index">
        <p :class="{'highlight': index === currentSentenceIndex}">{{ sentence }}</p>
        <div class="sentence-controls">
          <button @click="playSentence(index)" class="play-sentence">播放</button>
          <button @click="translateSentence(index)" class="translate-sentence">翻译</button>
          <button @click="followReadSentence(index)" class="follow-read">跟读</button>
        </div>
        <p class="translation" v-if="translations[index]">翻译：{{ translations[index] }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";
import axios from "axios";

function loadBookContentById(id) {
  // 从服务器加载书籍内容
  //下面是一个假设的固定数据
  const books = {
    1: {
      id: 1,
      title: 'Book 1',
      bookContent: 'what can i say? man! mamba out. I out, you out, we out!'
    },
    2: {
      id: 2,
      title: 'Book 2',
      bookContent: '这是第二本书的第一句. 这是第二本书的第二句. 这是第二本书的第三句. 这是第二本书的第四句. 这是第二本书的第五句. 这是第二本书的第六句. 这是第二本书的第七句. 这是第二本书的第八句.'
    }
    // ...可以添加更多书籍数据
  };

  return books[id] || null;
}

export default {
  components: {SpeechSynthesis},
  computed: {
    // 使用计算属性来基于bookId计算其他数据
    getBookId() {
      // 假设有一个方法可以根据bookId获取书籍详情
      return this.$route.params.bookId;
    },
  },
  data() {
    return {
      // ... 现有的数据属性 ...
      bookId: '',
      book: null,
      bookContent: '', // 书籍内容字符串
      sentences: [], // 分割后的句子数组
      playbackProgress: 0, // 播放进度
      totalSentences: 0,// 总句子数
      recording: false, // 是否正在录音
      countdown: 60, // 倒计时秒数
      mediaRecorder: null, // MediaRecorder实例
      audioChunks: [], // 录音数据块数组
      translations: [], // 存储翻译结果的数组
      currentSentenceIndex: 0, // 当前播放的句子索引
    };
  },
  methods: {
    // 改了一下调用的格式，传的是纯文本
    translateText(text, index) {
      // 发送请求到Java后端
      axios.post('http://localhost:8001/xunfei/translate', text, {
        headers: {
          'Content-Type': 'text/plain' // 确保发送的是纯文本格式
        }
      })
          .then(response => {
            // 处理响应
            this.translations[index] = response.data;
          })
          .catch(error => {
            console.error('翻译错误: ', error);
          });
    },
    updateProgress(index) {
      this.currentSentenceIndex = index;
      this.playbackProgress = index;
    },
    loadBook() {
      const bookId = this.getBookId; // 从导航栏获取到查看的是哪本书籍 （bookId）
      this.book = loadBookContentById(bookId);
      if (this.book) {
        this.bookContent = this.book.bookContent;
        this.splitSentences();
      } else {
        alert('未找到书籍内容！');
      }
    },
    // 返回页面
    goToAudioBooks() {
      this.$router.push('/readBook');
    },
    splitSentences() {
      // 将书籍内容按英语句号‘.’分割
      this.sentences = this.bookContent.split('.').filter(sentence => sentence.trim() !== '').map(sentence => sentence.trim() + '.');
      this.totalSentences = this.sentences.length;
    },
    //播放句子，调用播放文本功能，用index标识播放哪个句子
    playSentence(index) {
      // 播放句子的逻辑，实现playText函数
      // this.playText(this.sentences[index]);
      this.$refs.speechSynthesis.play(this.sentences[index]);
      this.updateProgress(index);
    },
    // 播放所有句子
    playAllText() {
      // 播放所有句子的逻辑
      // this.$refs.speechSynthesis.play(this.sentences.join('. '));
      // 跟随进度条
      this.sentences.forEach((sentence, index) => {
        setTimeout(() => {
          this.$refs.speechSynthesis.play(sentence);
          this.updateProgress(index);
        }, index * 2000); // 假设每个句子播放时间为2秒
      });
    },
    translateSentence(index) {
      // 翻译句子的逻辑
      this.translateText(this.sentences[index], index);
    },
    // 跟读句子的逻辑，包括录音和评分
    followReadSentence(index) {

    },
    // 停止录音的方法
    stopRecording() {

    },
    seekTo() {
      // 拖动进度条调整播放进度
      // 可选实现，根据进度条位置调整文本框滚动位置
      const textBox = document.querySelector('.text-box');
      const scrollTop = textBox.scrollTop;
      const scrollHeight = textBox.scrollHeight - textBox.clientHeight;

      // 计算当前播放进度
      const currentPlaybackProgress = scrollTop / scrollHeight * (this.totalSentences - 1);
      this.playbackProgress = currentPlaybackProgress;
    }
  },
  mounted() {
    // 加载书本
    this.loadBook();
  }
};
</script>

<style>
.book-reader {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
}

.title {
  color: #333;
  text-align: center;
  font-family: 'Georgia', serif;
  font-size: 2em;
  margin-bottom: 20px;
}

.book-title {
  margin-top: 10px;
  color: #555;
  text-align: center;
  font-size: 1.5em;
}

.text-box {
  width: 100%;
  max-width: 100%;
  box-sizing: border-box;
  padding: 15px;
  margin: 20px 0;
  border: 1px solid #ccc;
  border-radius: 5px;
  resize: none;
  overflow: auto;
  background-color: #fff;
  font-size: 1em;
  line-height: 1.5;
}

.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.progress-bar {
  -webkit-appearance: none;
  width: 100%;
  height: 8px;
  border-radius: 5px;
  background: #ddd;
  outline: none;
  opacity: 0.7;
  transition: opacity .2s;
}

.progress-bar::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2);
}

.progress-bar::-moz-range-thumb {
  width: 20px;
  height: 20px;
  border: none;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
}

.progress-bar::-webkit-slider-runnable-track {
  width: 100%;
  height: 8px;
  background: linear-gradient(to right, #82C91E, #12B886);
  border-radius: 5px;
}

.progress-bar::-moz-range-track {
  width: 100%;
  height: 8px;
  background: linear-gradient(to right, #82C91E, #12B886);
  border-radius: 5px;
}

.progress-bar:hover,
.progress-bar:focus {
  opacity: 1;
}

.play-button,
.back-button,
.stop-button,
.play-sentence,
.translate-sentence,
.follow-read {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: #5cb85c;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 1em;
}

.play-button:hover,
.back-button:hover,
.stop-button:hover,
.play-sentence:hover,
.translate-sentence:hover,
.follow-read:hover {
  background-color: #449d44;
}

.sentence-controls {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
  margin-top: 5px;
}

.translation {
  color: #888;
  font-style: italic;
  margin-top: 5px;
}

.highlight {
  animation: highlightAnimation 1s infinite alternate;
}

@keyframes highlightAnimation {
  0% {
    color: #000;
    transform: scale(1);
  }
  100% {
    color: #ff6347;
    transform: scale(1.1);
  }
}
</style>