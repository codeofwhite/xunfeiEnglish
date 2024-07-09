<template>
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
      <div v-for="(sentence, index) in sentences" :key="index" class="sentence-container">
        <p class="sentence">{{ sentence }}</p>
        <div class="sentence-controls">
          <button @click="playSentence(index)" class="play-sentence">播放</button>
          <button @click="translateSentence(index)" class="translate-sentence">翻译</button>
          <button @click="followReadSentence(index)" class="follow-read">跟读</button>
        </div>
        <p class="translation" v-if="translations[index]">{{ translations[index] }}</p>
      </div>
    </div>
  </div>
</template>

<script>
function loadBookContentById(id) {
  // 从服务器加载书籍内容
  //下面是一个假设的固定数据
  const books = {
    1: {
      id: 1,
      title: 'Book 1',
      bookContent: 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt. Ut labore et dolore magna aliqua.'
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
    };
  },
  methods: {
    // ... 现有的方法 ...
    // 返回页面
    goToAudioBooks() {
      this.$router.push('/readBook');
    },
    // 播放文本的方法，集成TTS服务
    playText(text) {
      // 构建请求TTS服务的URL
      const requestUrl = `${this.ttsUrl}?key=${this.ttsApiKey}&text=${encodeURIComponent(text)}`;
      // 使用fetch API发送请求
      fetch(requestUrl)
          .then(response => response.blob()) // 假设API返回的是音频文件的blob
          .then(blob => {
            // 创建一个URL对象来引用该blob
            const audioUrl = URL.createObjectURL(blob);
            // 创建一个audio元素来播放音频
            const audio = new Audio(audioUrl);
            audio.play();

            // 在音频播放完毕后释放URL对象
            audio.onended = () => {
              URL.revokeObjectURL(audioUrl);
            };
          })
          .catch(error => {
            console.error('播放文本时出错:', error);
            alert('播放文本时出错，请检查网络连接或API密钥。');
          });
    },
    // 翻译文本的方法，集成翻译服务
    translateText(text, targetLanguage = 'en') {
      // 构建请求翻译服务的URL
      const requestUrl = `${this.translateUrl}?key=${this.translateApiKey}&q=${encodeURIComponent(text)}&target=${targetLanguage}`;
      // 使用fetch API发送请求
      fetch(requestUrl)
          .then(response => response.json())
          .then(data => {
            if (data.error) {
              console.error('翻译出错:', data.error);
              alert('翻译出错，请检查网络连接或API密钥。');
            } else {
              // 假设翻译结果存储在data.translatedText中
              // 这里我们将翻译结果存储到translations数组中对应的位置
              const index = this.sentences.findIndex(sentence => sentence.trim() === text.trim());
              if (index !== -1) {
                this.$set(this.translations, index, data.translatedText);
              }
            }
          })
          .catch(error => {
            console.error('请求翻译API时出错:', error);
            alert('请求翻译API时出错，请检查网络连接或API密钥。');
          });
    },
    loadBook() {
      const bookId = this.getBookId; //从导航栏获取到查看的是哪本书籍 （bookId）
      this.book = loadBookContentById(bookId);
      if (this.book) {
        this.bookContent = this.book.bookContent;
        this.splitSentences();
      } else {
        alert('未找到书籍内容！');
      }
    },
    getBookIdFromUrl() {
      const urlParams = new URLSearchParams(window.location.search);
      return urlParams.get('bookId');  //从导航栏获取到查看的是哪本书籍 （bookId）
    },
    splitSentences() {
      // 将书籍内容按英语句号‘.’分割
      this.sentences = this.bookContent.split('.').filter(sentence => sentence.trim() !== '').map(sentence => sentence.trim() + '.');
      this.totalSentences = this.sentences.length;
    },
    //播放句子，调用播放文本功能，用index标识播放哪个句子
    playSentence(index) {
      // 播放句子的逻辑，实现playText函数
      this.playText(this.sentences[index]);
    },
    translateSentence(index) {
      // 翻译句子的逻辑
      this.translateText(this.sentences[index]);
    },
    // 跟读句子的逻辑，现在包括录音和评分
    followReadSentence(index) {
      // 检查浏览器是否支持getUserMedia
      if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        // 请求用户的麦克风权限
        navigator.mediaDevices.getUserMedia({audio: true})
            .then(stream => {
              // 设置录音状态为true
              this.recording = true;
              // 创建MediaRecorder实例
              this.mediaRecorder = new MediaRecorder(stream);
              // 收集录音数据块
              this.mediaRecorder.ondataavailable = e => this.audioChunks.push(e.data);
              // 开始录音
              this.mediaRecorder.start();

              // 设置倒计时
              let timer = setInterval(() => {
                this.countdown--;
                // 倒计时结束时停止录音
                if (this.countdown <= 0) {
                  this.stopRecording();
                  clearInterval(timer);
                }
              }, 1000);
            })
            .catch(error => {
              // 处理错误
              console.error('Error accessing the microphone: ', error);
            });
      } else {
        // 浏览器不支持提示
        alert('Your browser does not support audio recording.');
      }
    },
    // 停止录音的方法
    stopRecording() {
      // 停止MediaRecorder
      this.mediaRecorder.stop();
      // 设置录音状态为false
      this.recording = false;
      // 重置倒计时
      this.countdown = 60;

      // 将录音数据块合成Blob
      const audioBlob = new Blob(this.audioChunks);
      // 创建音频URL
      const audioUrl = URL.createObjectURL(audioBlob);
      // 创建音频元素并播放
      const audio = new Audio(audioUrl);
      audio.play();

      // 发送录音数据到API进行评分
      // 假设API URL为 'https://api.example.com/score-audio'
      const formData = new FormData();
      formData.append('audio', audioBlob);

      fetch('https://api.example.com/score-audio', {
        method: 'POST',
        body: formData
      })
          .then(response => response.json())
          .then(data => {
            // 显示评分弹窗
            alert(`Your score is: ${data.score}`);
          })
          .catch(error => {
            // 处理错误
            console.error('Error sending audio:', error);
          });
    },
    playAllText() {
      // 播放所有句子的逻辑
      this.playText(this.sentences.join('. '));
    },
    goToReadingFollow() {
      // 跳转到原文跟读界面的逻辑
      window.location.href = '原文跟读.html';
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
    // ... 现有的挂载逻辑 ...
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
}

.title {
  color: #333;
  text-align: center;
  font-family: 'Times New Roman', serif;
}

.book-title {
  margin-top: 10px;
  color: #555;
  text-align: center;
}

.text-box {
  width: 100%; /* 宽度设置为100% */
  max-width: 100%; /* 最大宽度设置为100% */
  box-sizing: border-box; /* 边框和内边距包含在宽度内 */
  padding: 10px; /* 内边距 */
  margin: 0; /* 外边距设置为0 */
  border: 1px solid #ccc; /* 边框 */
  resize: none; /* 禁止调整大小 */
  overflow: auto; /* 内容超出时显示滚动条 */
}

.controls {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.progress-bar {
  -webkit-appearance: none; /* 覆盖默认样式 */
  width: 100%; /* 进度条宽度 */
  height: 8px; /* 进度条高度 */
  border-radius: 5px; /* 圆角 */
  background: #ddd; /* 背景色 */
  outline: none; /* 去除轮廓 */
  opacity: 0.7; /* 透明度 */
  transition: opacity .2s; /* 过渡效果 */
}

/* 进度条内部滑块的样式 */
.progress-bar::-webkit-slider-thumb {
  -webkit-appearance: none;
  appearance: none;
  width: 20px; /* 滑块宽度 */
  height: 20px; /* 滑块高度 */
  border-radius: 50%; /* 圆形滑块 */
  background: #4CAF50; /* 滑块颜色 */
  cursor: pointer; /* 鼠标样式 */
  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2); /* 滑块阴影 */
}

/* 进度条内部滑块的样式（兼容Firefox）*/
.progress-bar::-moz-range-thumb {
  width: 20px;
  height: 20px;
  border: none;
  border-radius: 50%;
  background: #4CAF50;
  cursor: pointer;
}

/* 进度条已完成部分的样式 */
.progress-bar::-webkit-slider-runnable-track {
  width: 100%;
  height: 8px;
  background: linear-gradient(to right, #82C91E, #12B886); /* 渐变色 */
  border-radius: 5px;
}

/* 进度条已完成部分的样式（兼容Firefox）*/
.progress-bar::-moz-range-track {
  width: 100%;
  height: 8px;
  background: linear-gradient(to right, #82C91E, #12B886);
  border-radius: 5px;
}

/* 当滑块被悬停或聚焦时，改变透明度 */
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
}

.play-button:hover,
.back-button:hover,
.stop-button:hover,
.play-sentence:hover,
.translate-sentence:hover,
.follow-read:hover {
  background-color: #449d44;
}

.sentence-container {
  padding: 10px;
  background: #fff;
  border-radius: 5px;
  margin-bottom: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
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
}
</style>