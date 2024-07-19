<template>
  <!-- 语音合成组件 -->
  <SpeechSynthesis v-show="false" ref="speechSynthesis" :accent="accent"></SpeechSynthesis>
  <div class="container">
    <div class="left-section">
      <!-- 输入和按钮区域 -->
      <div class="input-area">
        <input v-model="userInput" placeholder="输入消息..." class="input-field">
        <button class="send-button" @click="sendMessage">发送</button>
        <FreeTalkAI v-show="false" ref="aiComponent"></FreeTalkAI>
      </div>
      <div class="footer">
        <FreeTalkSpeechTranslate v-show="true"
                                 @recognition-complete="handleRecognitionComplete"></FreeTalkSpeechTranslate>
        <FreeTalkAI v-show="false" ref="aiComponent" @result-received="handleResult"></FreeTalkAI>
        <button class="action-button" @result-received="handleResult" @click="aiHelp">AI助答</button>
      </div>
    </div>
    <div class="right-section">
      <!-- 聊天框 -->
      <div class="conversation-container">
        <div class="header">
          <button class="back-button" @click="goBack">返回</button>
          <h2 class="scene-title">{{ selectedScene }}</h2>
        </div>
        <div class="messages-container">
          <div v-for="message in messages" :key="message.id"
               :class="['message', message.from === 'ai' ? 'ai-message' : 'user-message']">
            <p>{{ message.from === 'ai' ? 'AI：' : 'User：' }}{{ message.text }}</p>
            <div class="message-actions">
              <button @click="translate(message.text)">翻译</button>
              <button @click="speak(message.text)">发音</button>
              <!-- 选择文本按钮 -->
              <button @click="selectMessageForAI(message.text)">选择</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";
import FreeTalkAI from "@/components/freeTalk/FreeTalkAI.vue";
import FreeTalkSpeechTranslate from "@/components/freeTalk/FreeTalkSpeechTranslate.vue";

export default {
  components: {FreeTalkSpeechTranslate, FreeTalkAI, SpeechSynthesis},
  data() {
    return {
      selectedScene: '默认场景',
      messages: [], // 显示的消息
      userInput: '',
      aiResponse: '',
      sceneDetails: null, // 初始化为null
      accent: this.$route.query.accent || 'henry' // 默认美音
    };
  },
  created() {
    const scene = new URLSearchParams(window.location.search).get('scene');
    if (scene) {
      this.selectedScene = decodeURIComponent(scene);
    }
  },
  mounted() {
    const scene = new URLSearchParams(window.location.search).get('scene');
    // 这个先不调用先，使用次数都没了就不好了
    this.sceneStart("和我开始一段英语对话，你先开始，主题为：" + scene)
  },
  methods: {
    goBack() {
      this.$router.push('/smartTalk');
    },
    handleResult(result) {
      console.log('AI结果:', result);
      this.messages.push({
        id: Date.now(), // 使用时间戳作为唯一ID
        from: 'ai',
        text: result.ai // 假设result对象有一个ai属性，包含AI的回复文本
      });
    },
    translate(text) {
      axios.post('http://114.132.52.232:8001/xunfei/translate', {text})
          .then(response => {
            alert('翻译: ' + response.data);
          })
          .catch(error => {
            alert('翻译失败: ' + error);
          });
    },
    // 输入给AI场景
    sceneStart(scene) {
      this.$refs.aiComponent.startWithText(scene);
    },
    speak(text) {
      this.$refs.speechSynthesis.play(text);
    },
    selectMessageForAI(text) {
      this.userInput = "帮我解释这个句子：" + text; // 设置用户输入为选中的消息文本
    },
    aiHelp() {
      if (this.userInput.trim()) {
        this.messages.push({from: 'user', text: this.userInput});
        this.$refs.aiComponent.startWithText(this.userInput);
        this.userInput = '';
      } else {
        alert('请输入消息或说话或选择一条文本');
      }
    },
    sendMessage() {
      if (this.userInput.trim()) {
        this.messages.push({from: 'user', text: this.userInput});
        this.$refs.aiComponent.startWithText(this.userInput);
        this.userInput = '';
      } else {
        alert('请输入消息或说话');
      }
    },
    handleRecognitionComplete(recognizedText) {
      console.log('识别的文本:', recognizedText);
      this.userInput = recognizedText;
    },
  }
};
</script>

<style scoped>
.container {
  display: flex;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.left-section {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.right-section {
  flex: 2;
  display: flex;
  flex-direction: column;
  padding: 20px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  margin-left: 20px;
}

.input-area {
  display: flex;
  width: 100%;
  margin-bottom: 20px;
}

.input-field {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  margin-right: 10px;
}

.send-button {
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.send-button:hover {
  background-color: #45a049;
}

.footer {
  justify-content: space-between;
  width: 100%;
  margin-top: 20px;
}

.footer button {
  margin: 10px;
  width: 45%;
}

.action-button {
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
  flex: 1;
  margin: 0 5px;
}

.action-button:hover {
  background-color: #45a049;
}

.conversation-container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.back-button {
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.back-button:hover {
  background-color: #45a049;
}

.scene-title {
  color: #333;
  font-size: 1.5em;
  text-align: center;
  margin: 0;
}

.messages-container {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: #f1f1f1;
  border-radius: 10px;
  box-shadow: inset 0 2px 5px rgba(0, 0, 0, 0.1);
}

.message {
  max-width: 80%;
  word-wrap: break-word;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 10px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
  position: relative;
}

.user-message {
  background-color: #DCF8C6;
  align-self: flex-start;
}

.ai-message {
  background-color: #E0E0FF;
  align-self: flex-end;
}

.message-actions {
  display: flex;
  justify-content: flex-end;
}

.message-actions button {
  padding: 5px 10px;
  margin-right: 5px;
  border: none;
  border-radius: 5px;
  background-color: #f2f2f2;
  color: #555;
  cursor: pointer;
  transition: background-color 0.3s;
}

.message-actions button:hover {
  background-color: #e6e6e6;
}
</style>