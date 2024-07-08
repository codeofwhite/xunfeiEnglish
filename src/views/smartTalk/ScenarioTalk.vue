<template>
  <div class="container">
    <button class="button" @click="goBack">返回</button>
    <h2>{{ selectedScene }}</h2>
    <hr>
    <div v-if="sceneDetails" class="scene-details">
      <h3>背景信息:</h3>
      <p>{{ sceneDetails.background }}</p>
      <h3>围绕话题:</h3>
      <ul>
        <li v-for="(topic, index) in sceneDetails.topics" :key="index">{{ topic }}</li>
      </ul>
    </div>
    <br>
    <div v-for="message in messages" :class="['message', { 'ai': message.from === 'ai' }]">
      <p>{{ message.text }}</p>
      <button @click="translate(message.text)">翻译</button>
      <button @click="speak(message.text)">发音</button>
    </div>
    <button class="button" @click="startSpeechRecognition">说话</button>
    <button class="button" @click="aiHelp">AI助答</button>
    <input v-model="userInput" placeholder="输入消息...">
    <button @click="sendMessage">发送</button>
  </div>
</template>

<script>
export default {
  data() {
    return {
      selectedScene: localStorage.getItem('scene') || '默认场景',
      messages: [],
      userInput: '',
      aiResponse: '',
      sceneDetails: null // 初始化为null
    };
  },
  created() {
    this.fetchSceneDetails();
    const scene = new URLSearchParams(window.location.search).get('scene');
    if (scene) {
      this.selectedScene = decodeURIComponent(scene);
    }
  },
  methods: {
    goBack() {
      // 实现返回逻辑
      this.$router.push('/smartTalk')
    },
    fetchSceneDetails() {
      // 实现获取场景详情逻辑
    },
    translate(text) {
      // 实现翻译逻辑
    },
    speak(text) {
      // 实现文本朗读逻辑
    },
    startSpeechRecognition() {
      // 实现语音识别逻辑
    },
    aiHelp() {
      // 实现AI助答逻辑
    },
    sendMessage() {
      // 实现发送消息逻辑
    }
  }
};
</script>

<style scoped>
.container {
  padding: 20px;
  background-color: #fff0f6; /* 浅粉色背景 */
  border-radius: 15px; /* 圆角边框 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 轻微的阴影 */
  font-family: 'Comic Sans MS', 'Comic Neue', cursive; /* 卡通风格的字体 */
}

.message {
  margin: 10px 0;
  padding: 10px;
  border-radius: 10px;
  background-color: #e7f5ff; /* 浅蓝色背景 */
  border: 2px dashed #ffec99; /* 虚线边框 */
  position: relative;
}

.ai {
  background-color: #e0e0ff; /* 更深的蓝色背景 */
}

.button {
  padding: 10px 20px;
  cursor: pointer;
  background-color: #ffadd2; /* 粉红色按钮 */
  border: none;
  border-radius: 20px; /* 圆润的按钮 */
  color: white;
  font-size: 16px;
  margin: 5px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2); /* 按钮阴影 */
  transition: all 0.3s ease;
}

.button:hover {
  background-color: #ff85c0; /* 鼠标悬停时的颜色变化 */
  transform: translateY(-2px); /* 轻微的弹跳效果 */
}

input {
  padding: 10px;
  border-radius: 20px;
  border: 2px solid #ffadd2; /* 输入框边框 */
  margin: 10px 0;
  width: calc(100% - 24px); /* 减去padding和border的宽度 */
}

hr {
  border-color: #ffadd2; /* 分割线颜色 */
  margin-top: 20px;
}

h2 {
  color: #ff69b4; /* 标题颜色 */
  text-align: center;
}

h3 {
  color: #ff85c0; /* 小标题颜色 */
}

ul {
  list-style-type: none; /* 去除列表前的圆点 */
}

li {
  padding: 5px 0;
}

/* 添加一些动画效果 */
@keyframes bounce {
  0%, 100% {
    transform: translateY(0);
  }
  50% {
    transform: translateY(-5px);
  }
}

.message:hover {
  animation: bounce 0.5s;
}
</style>