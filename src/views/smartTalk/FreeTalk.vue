<template>
  <!-- 语音合成组件  -->
  <SpeechSynthesis v-show="false" ref="speechSynthesis"></SpeechSynthesis>
  <div class="conversation-container">
    <div class="header">
      <button class="back-button" @click="goBack">返回</button>
      <h2 class="scene-title">{{ selectedScene }}</h2>
    </div>
    <div class="messages-container">
      <div v-for="message in messages" :key="message.id"
           :class="['message', message.from === 'ai' ? 'ai-message' : 'user-message']">
        <p>{{ message.text }}</p>
        <div class="message-actions">
          <button @click="translate(message.text)">翻译</button>
          <button @click="speak(message.text)">发音</button>
          <!-- 选择文本按钮 -->
          <button @click="selectMessageForAI(message.text)">选择</button>
        </div>
      </div>
    </div>
    <div class="input-area">
      <input v-model="userInput" placeholder="输入消息..." class="input-field">
      <button class="send-button" @click="sendMessage">发送</button>
      <FreeTalkAI v-show="false" ref="aiComponent"></FreeTalkAI>
    </div>
    <div class="footer">
      <!--      <button class="action-button" @click="startSpeechRecognition">说话</button>-->
      <!--      -->
      <FreeTalkSpeechTranslate v-show="true" @recognition-complete="handleRecognitionComplete"></FreeTalkSpeechTranslate>
      <!--  调用AI接口，ref提供给AI的参数，handleResult提供结果返回    -->
      <FreeTalkAI v-show="false" ref="aiComponent" @result-received="handleResult"></FreeTalkAI>
      <button class="action-button" @result-received="handleResult" @click="aiHelp">AI助答</button>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";
import SpeechTranslate from "@/components/SpeechTranslate.vue";
import FreeTalkAI from "@/components/FreeTalkAI.vue";
import FreeTalkSpeechTranslate from "@/components/FreeTalkSpeechTranslate.vue";

export default {
  components: {FreeTalkSpeechTranslate, FreeTalkAI, SpeechTranslate, SpeechSynthesis},
  data() {
    return {
      selectedScene: '默认场景',
      messages: [], // 显示的消息
      userInput: '',
      aiResponse: '',
      sceneDetails: null, // 初始化为null
    };
  },
  created() {
    const scene = new URLSearchParams(window.location.search).get('freeScene');
    if (scene) {
      this.selectedScene = decodeURIComponent(scene);
    }
  },
  // 这个方created会报错，放mounted就没事
  mounted() {
    const scene = new URLSearchParams(window.location.search).get('freeScene');
    // 这个先不调用先，使用次数都没了就不好了
    // this.sceneStart("和我开始一段英语对话，你先开始，主题为：" +  scene)
  },
  methods: {
    goBack() {
      this.$router.push('/smartTalk');
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
    // AI 翻译， 7-8还没接入接口
    translate(text) {
      axios.post('YOUR_TRANSLATION_API_ENDPOINT', {text})
          .then(response => {
            alert('翻译: ' + response.data.translatedText);
          })
          .catch(error => {
            alert('翻译失败: ' + error);
          });
    },
    // AI朗读
    speak(text) {
      this.$refs.speechSynthesis.play(text);
    },
    selectMessageForAI(text) {
      this.userInput = "帮我解释这个句子：" + text; // 设置用户输入为选中的消息文本
      // this.aiHelp();
    },
    aiHelp() {
      if (this.userInput.trim()) {
        this.messages.push({from: 'user', text: this.userInput});
        // 播放语音
        // this.$refs.speechSynthesis.play(this.userInput);
        this.$refs.aiComponent.startWithText(this.userInput);
        this.userInput = '';
        // 此处省略AI回复实现代码，可根据实际情况添加
      } else {
        alert('请输入消息或说话或选择一条文本');
      }
    },
    // 输入给AI场景
    sceneStart(scene) {
      this.$refs.aiComponent.startWithText(scene);
    },
    // 输入用户input
    sendMessage() {
      if (this.userInput.trim()) {
        this.messages.push({from: 'user', text: this.userInput});
        // this.$refs.speechSynthesis.play(this.userInput);
        this.$refs.aiComponent.startWithText(this.userInput);
        this.userInput = '';
        // 此处省略AI回复实现代码，可根据实际情况添加
      } else {
        alert('请输入消息或说话');
      }
    },
    // 语音识别
    handleRecognitionComplete(recognizedText) {
      // 在这里处理识别结果，例如将其存储在data属性中或传递给其他组件
      console.log('识别的文本:', recognizedText);
      // 例如，如果您有一个聊天组件，您可以这样做：
      this.userInput = recognizedText;
      // 传给AI
      // this.start();
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
  color: #4CAF50;
  font-size: 1.5em;
  text-align: center;
  margin: 0;
}

.user-message {
  background-color: #DCF8C6;
  align-self: flex-end;
}

.ai-message {
  background-color: #E0E0FF;
  align-self: flex-start;
}

.messages-container {
  margin-bottom: 20px;
  overflow-y: auto;
  max-height: 300px;
}

.message {
  max-width: 80%;
  word-wrap: break-word;
  margin-bottom: 10px;
  padding: 10px;
  border-radius: 20px;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
}

.ai-message {
  background-color: #e0e0ff;
}

.message-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 10px;
}

.message-actions button {
  padding: 5px 10px;
  margin-right: 5px;
  border: none;
  border-radius: 4px;
  background-color: #f2f2f2;
  color: #555;
  cursor: pointer;
  transition: all 0.3s ease;
}

.message-actions button:hover {
  background-color: #e6e6e6;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

.message-actions button:active {
  transform: translateY(1px);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.2);
}

.message-actions button:hover {
  background-color: #D3D3D3;
}

.input-area {
  display: flex;
  margin-top: 20px;
}

.input-field {
  border: 2px solid #4CAF50;
}

.send-button {
  background-color: #4CAF50;
}

.send-button:hover {
  background-color: #367C39;
}

.action-button {
  background-color: #FF9800;
}

.action-button:hover {
  background-color: #E68A00;
}

.footer {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
}
</style>