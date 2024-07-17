<template>
  <!-- 语音合成组件  -->
  <SpeechSynthesis v-show="false" ref="speechSynthesis"></SpeechSynthesis>
  <div id="book-reader" class="book-reader">
    <button @click="goToAudioBooks" class="back-button">返回</button>
    <button @click="endConversation" class="end-button">结束阅读</button>
    <h1 class="title">书籍阅读</h1>

    <div v-if="book" class="book-content">
      <h2 class="book-title">{{ book.title }}</h2>
      <textarea class="text-box" v-model="bookContent" readonly></textarea>
      <div class="controls">
        <input type="range" class="progress-bar" v-model="playbackProgress" @input="seekTo" :max="totalSentences - 1"/>
        <button @click="playAllText" class="play-button">播放全文</button>
      </div>
      <div v-for="(sentence, index) in sentences" :key="index" class="sentence-block">
        <p :class="{'highlight': index === currentSentenceIndex}" class="sentence">{{ sentence }}</p>
        <div class="sentence-controls">
          <button @click="playSentence(index)" class="play-sentence">播放</button>
          <button @click="translateSentence(index)" class="translate-sentence">翻译</button>
        </div>
        <div>
          <button @click="toggleRecordingArea(index)" class="toggle-button">
            {{ recordingAreaVisible[index] ? '收起朗读' : '展开朗读' }}
          </button>
          <div v-if="recordingAreaVisible[index]">
            <button v-if="recordingIndex !== index" @click="startRecordingPcm(index)" class="record-button">开始录音
            </button>
            <button v-if="recordingIndex === index" @click="stopRecordingPcm" class="stop-button">停止录音</button>
            <button @click="uploadAudio(sentence, index)" class="score-button">音频评分</button>
            <div v-if="recordingIndex === index" class="recording-controls">
              <p v-if="recording" class="countdown">倒计时: {{ countdown }} 秒</p>
            </div>
          </div>
        </div>
        <div v-if="recordingAreaVisible[index]" class="result-data">
          <h3>评分结果</h3>
          <table class="score-table">
            <tr>
              <th>评分项</th>
              <th>分数</th>
            </tr>
            <tr>
              <td>Accuracy Score</td>
              <td>{{ accuracyScores[index] }}</td>
            </tr>
            <tr>
              <td>Fluency Score</td>
              <td>{{ fluencyScores[index] }}</td>
            </tr>
            <tr>
              <td>Integrity Score</td>
              <td>{{ integrityScores[index] }}</td>
            </tr>
            <tr>
              <td>Total Score</td>
              <td>{{ totalScores[index] }}</td>
            </tr>
          </table>
          <div class="average-score">
            <h4>平均分: {{ averageScore }}</h4>
          </div>
        </div>
        <p class="translation" v-if="translations[index]">翻译：{{ translations[index] }}</p>
      </div>
    </div>
  </div>
  <!-- Modal -->
  <div v-if="showModal" class="modal">
    <div class="modal-content">
      <h3>对话完成</h3>
      <p>总分数: {{ averageScore }}</p>
      <p>获得的金币: {{ coin }}</p>
      <button @click="closeModal">关闭</button>
    </div>
  </div>
</template>

<script>
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";
import axios from "axios";
import {ref} from "vue";
import {useStore} from "vuex";

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
      return this.$route.params.chapterId;
    },
    getUserEmail() {
      return this.$store.state.userEmail;
    }
  },
  data() {
    return {
      // ... 现有的数据属性 ...
      bookId: '',
      chapterId: '',
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
      accuracyScores: [],
      fluencyScores: [],
      integrityScores: [],
      totalScores: [],
      audioContext: null,
      audioBlob: null,
      resultData: {}, // 新增一个对象来存储返回的数据
      scores: [], // 新增一个数组来存储每个句子的得分
      averageScore: 0, // 新增一个变量来存储平均分
      recordingIndex: null, // 当前正在录音的句子索引
      recordingAreaVisible: [], // 控制录音区域显示的数组
      showModal: false,
      coin: 0,
    };
  },
  methods: {
    toggleRecordingArea(index) {
      this.recordingAreaVisible = {
        ...this.recordingAreaVisible,
        [index]: !this.recordingAreaVisible[index]
      };
    },
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
    seekTo() {
      // 拖动进度条调整播放进度
      // 可选实现，根据进度条位置调整文本框滚动位置
      const textBox = document.querySelector('.text-box');
      const scrollTop = textBox.scrollTop;
      const scrollHeight = textBox.scrollHeight - textBox.clientHeight;

      // 计算当前播放进度
      const currentPlaybackProgress = scrollTop / scrollHeight * (this.totalSentences - 1);
      this.playbackProgress = currentPlaybackProgress;
    },
    async startRecordingPcm(index) {
      alert('开始录音');
      this.recordingIndex = index;
      const stream = await navigator.mediaDevices.getUserMedia({audio: true});
      this.audioContext = new (window.AudioContext || window.webkitAudioContext)();
      const source = this.audioContext.createMediaStreamSource(stream);
      const processor = this.audioContext.createScriptProcessor(4096, 1, 1);

      source.connect(processor);
      processor.connect(this.audioContext.destination);

      processor.onaudioprocess = (e) => {
        const inputData = e.inputBuffer.getChannelData(0);
        const buffer = new ArrayBuffer(inputData.length * 2);
        const outputData = new DataView(buffer);
        for (let i = 0; i < inputData.length; i++) {
          const s = Math.max(-1, Math.min(1, inputData[i]));
          outputData.setInt16(i * 2, s < 0 ? s * 0x8000 : s * 0x7FFF, true);
        }
        this.audioChunks.push(buffer);
      };

      this.mediaRecorder = processor;
      this.recording = true;
      this.startCountdown();
    },
    stopRecordingPcm() {
      alert('录音结束');
      this.mediaRecorder.disconnect();
      this.audioContext.close();
      const audioBuffer = new Blob(this.audioChunks, {type: 'audio/pcm'});
      this.audioBlob = audioBuffer;
      this.audioChunks = [];
      this.recording = false;
      this.countdown = 0; // 清零计时器
      this.recordingIndex = null; // 重置recordingIndex
    },
    async uploadAudio(sentence, index) {
      alert('上传音频评分');
      const formData = new FormData();
      formData.append('audioFile', this.audioBlob, 'audio.pcm');
      formData.append('text', sentence);
      try {
        const response = await axios.post('http://localhost:8001/xunfei/score', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        });
        console.log('上传结果:', response.data);
        // 解析返回的数据
        this.resultData = response.data; // 将返回的数据存储在resultData对象中
        const totalScore = parseFloat(response.data.TotalScore); // 确保TotalScore是一个数字
        // 避免按顺序存的情况，导致显示出现问题
        this.accuracyScores[index] = response.data.AccuracyScore; // 记录Accuracy得分
        this.fluencyScores[index] = response.data.FluencyScore; // 记录Fluency得分
        this.integrityScores[index] = response.data.IntegrityScore; // 记录Integrity得分
        this.totalScores[index] = response.data.TotalScore;
        if (!isNaN(totalScore)) {
          this.scores[index] = totalScore; // 将每个句子的总分存储在scores数组中
          this.calculateAverageScore(); // 计算平均分
        } else {
          console.error('TotalScore 不是一个有效的数字:', response.data.TotalScore);
        }
      } catch (error) {
        console.error('上传失败:', error);
      }
    },
    startCountdown() {
      this.countdown = 60;
      const interval = setInterval(() => {
        if (this.countdown > 0) {
          this.countdown--;
        } else {
          clearInterval(interval);
          this.stopRecordingPcm();
        }
      }, 1000);
    },
    calculateAverageScore() {
      if (this.scores.length > 0) {
        const sum = this.scores.reduce((a, b) => a + b, 0);
        this.averageScore = (sum / this.scores.length).toFixed(2);
      } else {
        this.averageScore = 0;
      }
    },
    async endConversation() {
      this.coin = Math.floor(this.averageScore * 100);

      // 调用更新user_coin的接口
      const response = await axios.put('http://localhost:8002/userGameResource/updateUserCoin', null, {
        params: {
          userEmail: this.getUserEmail,
          userCoinChange: this.coin
        }
      });

      try {
        const response = await axios.post('http://localhost:8005/api/bookTalk', {
          userEmail: this.getUserEmail,
          accuracy: this.accuracyScores,
          fluency: this.fluencyScores,
          integrity: this.integrityScores,
          totalScore: this.averageScore
          // 其他需要传递的数据
        });
        // 显示modal
        this.showModal = true;
        // 5秒后自动关闭modal
        setTimeout(this.closeModal, 5000);
        console.log('阅读信息上传成功:', response.data);
      } catch (error) {
        console.error('阅读信息上传失败:', error);
      }
    },
    closeModal() {
      this.showModal = false;
      this.$router.push('/readBook')
    },
  },
  mounted() {
    // 加载书本
    this.loadBook();
  },
}
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

.progress-bar::-webkit-slider-thumb,
.progress-bar::-moz-range-thumb {
  width: 20px;
  height: 20px;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2);
}

.progress-bar::-webkit-slider-runnable-track,
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

button {
  padding: 10px 15px;
  border: none;
  border-radius: 5px;
  background-color: #5cb85c;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
  font-size: 1em;
  margin: 10px
}

.toggle-button {
  padding: 2px 10px;
  width: 500px;
  height: 25px;
  text-align: center;
  background-color: #1bffc4;
  color: white;
  margin: 1px;
}

.end-button {
  margin-left: 76%;
  background-color: #5eb3ff;
  color: white;
}

button:hover {
  background-color: #449d44;
}

.end-button:hover {
  background-color: red;
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

.recording-controls {
  margin-bottom: 10px;
}

.countdown {
  font-size: 16px;
  color: red;
}

.result-data {
  margin-top: 10px;
}

.score-table {
  width: 100%;
  border-collapse: collapse;
}

.score-table th, .score-table td {
  border: 1px solid #ddd;
  padding: 8px;
}

.score-table th {
  background-color: #f2f2f2;
  text-align: left;
}

.average-score {
  margin-top: 10px;
  font-size: 18px;
  font-weight: bold;
}

/*modal样式*/
.modal {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-color: white;
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  z-index: 1000;
}

.modal-content {
  text-align: center;
}

.modal-content p {
  margin: 10px 0;
}

.modal-content button {
  padding: 8px 16px;
  font-size: 14px;
  color: #fff;
  background-color: #007BFF;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.modal-content button:hover {
  background-color: #0056b3;
}
</style>