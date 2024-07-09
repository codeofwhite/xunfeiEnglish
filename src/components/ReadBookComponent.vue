<template>
  <div id="book-reader" class="book-reader">
    <button @click="goToAudioBooks">返回</button>
    <h1>书籍阅读</h1>

    <div v-if="book">
      <h2>{{ book.title }}</h2>
      <textarea class="text-box" v-model="bookContent" readonly></textarea>
      <p><input type="range" class="progress-bar" v-model="playbackProgress" @input="seekTo" :max="totalSentences - 1"/>
      </p>
      <p>
        <button @click="playAllText">播放全文</button>
        <!-- <button @click="goToReadingFollow">原文跟读</button> -->
      </p>
      <!-- 当正在录音时，显示倒计时、暂停录音按钮和进度条 -->
      <div v-if="recording" class="recording-controls">
        <p>倒计时: {{ countdown }} 秒</p>
        <button @click="stopRecording">停止录音</button>
        <!-- 你可以在这里添加一个进度条，但在这个示例中我们省略了它 -->
      </div>
      <div v-for="(sentence, index) in sentences" :key="index" class="text-container">
        <p>{{ sentence }}</p>
        <button @click="playSentence(index)">播放</button>
        <button @click="translateSentence(index)">翻译</button>
        <button @click="followReadSentence(index)">跟读</button>
        <p class="translation" v-if="translations[index]">{{ translations[index] }}</p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: ['bookId'],
  data() {
    return {
      // ... 现有的数据属性 ...
    };
  },
  methods: {
    // ... 现有的方法 ...
    // 返回页面
    goToAudioBooks() {
      window.location.href = '有声读物.html';
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
      const bookId = this.getBookIdFromUrl(); //从导航栏获取到查看的是哪本书籍 （bookId）
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
  }
};
</script>

<style>
/* 样式设置 */
.book-reader {
  max-width: 600px;
  margin: 0 auto;
}

.text-box {
  height: 300px;
  width: 100%;
  border: 1px solid #ccc;
  padding: 10px;
  resize: none;
  overflow: auto;
}

.progress-bar {
  width: 100%;
}

.text-container {
  margin-bottom: 10px;
}

.recording-controls {
  margin-top: 20px;
  text-align: center;
}

.recording-controls p,
.recording-controls button {
  margin: 5px;
}
</style>