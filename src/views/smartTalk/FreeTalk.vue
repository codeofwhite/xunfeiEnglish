<template>
  <div class="conversation-container">
    <div class="header">
      <button class="back-button" @click="goBack">返回</button>
      <h2 class="scene-title">{{ selectedScene }}</h2>
    </div>
    <div class="messages-container">
      <div v-for="message in messages" :key="message.id" :class="['message', { 'ai-message': message.from === 'ai' }]">
        <p>{{ message.text }}</p>
        <div class="message-actions">
          <button @click="translate(message.text)">翻译</button>
          <button @click="speak(message.text)">发音</button>
        </div>
      </div>
    </div>
    <div class="input-area">
      <input v-model="userInput" placeholder="输入消息..." class="input-field">
      <button class="send-button" @click="sendMessage">发送</button>
    </div>
    <div class="footer">
      <button class="action-button" @click="startSpeechRecognition">说话</button>
      <button class="action-button" @click="aiHelp">AI助答</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      selectedScene: '默认场景',
      messages: [],
      userInput: '',
      aiResponse: ''
    };
  },
  created() {
    const scene = new URLSearchParams(window.location.search).get('freeScene');
    if (scene) {
      this.selectedScene = decodeURIComponent(scene);
    }
  },
  methods: {
    goBack() {
      this.$router.push('/smartTalk');
    },
    translate(text) {
      axios.post('YOUR_TRANSLATION_API_ENDPOINT', {text})
          .then(response => {
            alert('翻译: ' + response.data.translatedText);
          })
          .catch(error => {
            alert('翻译失败: ' + error);
          });
    },
    speak(text) {
      axios.post('YOUR_TEXT_TO_SPEECH_API_ENDPOINT', {text})
          .then(response => {
            const audio = new Audio(response.data.audioUrl);
            audio.play();
          })
          .catch(error => {
            alert('朗读失败: ' + error);
          });
    },
    startSpeechRecognition() {
      // 此处省略语音识别实现代码，可根据实际情况添加
    },
    aiHelp() {
      // 此处省略AI助答实现代码，可根据实际情况添加
    },
    sendMessage() {
      if (this.userInput.trim()) {
        this.messages.push({from: 'user', text: this.userInput});
        this.userInput = '';
        // 此处省略AI回复实现代码，可根据实际情况添加
      } else {
        alert('请输入消息或说话');
      }
    }
  }
};
</script>

<style scoped>
.conversation-container {
  max-width: 600px;
  margin: auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.back-button, .action-button {
  padding: 10px 15px;
  border-radius: 20px;
  border: none;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);
}

.scene-title {
  color: #333;
  font-weight: bold;
}

.messages-container {
  margin-bottom: 20px;
  overflow-y: auto;
  max-height: 300px;
}

.message {
  padding: 15px;
  margin: 10px 0;
  border-radius: 15px;
  background-color: #f9f9f9;
  position: relative;
}

.ai-message {
  background-color: #e0e0ff;
}

.message-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.input-area {
  display: flex;
  margin-top: 20px;
}

.input-field {
  flex-grow: 1;
  padding: 10px;
  border-radius: 20px;
  border: 2px solid #ff9800;
}

.send-button {
  padding: 10px 15px;
  margin-left: 10px;
  border-radius: 20px;
  border: none;
  background-color: #2196F3;
  color: white;
  cursor: pointer;
}

.footer {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
</style>