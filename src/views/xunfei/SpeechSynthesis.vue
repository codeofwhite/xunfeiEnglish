<!--语音合成-->
<template>
  <div class="container">
    <h1 class="title">语音合成</h1>
    <div class="content">
      <button class="btn" @click="play">开始合成</button>
      <button class="btn" @click="pause">停止播放</button>
    </div>
  </div>
</template>

<script>
import TtsRecorder from "../../assets/js/speechSynthesis/audio.js";

const ttsRecorder = new TtsRecorder();
export default {
  props: {
    accent: {
      type: String,
      default: 'henry' // 默认美音
    }
  },
  data() {
    return {
      text: "你好，世界", // 默认文本
      aiResponse: "", // 存储AI回复的文本
    };
  },
  methods: {
    play(textToSpeak) {
      // 如果提供了文本，则使用该文本，否则使用默认文本
      const text = textToSpeak || this.text;
      // 要合成的文本
      ttsRecorder.setParams({
        // 文本内容
        text: text, // 传入的文本
        // 角色
        voiceName: this.accent, // 使用传递的发音人参数
        // 语速
        speed: 50,
        // 音量
        voice: 50,
      });
      ttsRecorder.start();
    },
    pause() {
      ttsRecorder.stop();
    }
  }
}
</script>

<style scoped>
.container {
  text-align: center;
}

.title {
  margin-bottom: 20px;
  animation: fadeInDown 1s;
}

.content {
  display: inline-block;
}

.btn {
  margin: 0 10px;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  background-color: #4CAF50;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.3s;
}

.btn:hover {
  background-color: #45a049;
  transform: translateY(-2px);
}

@keyframes fadeInDown {
  from {
    opacity: 0;
    transform: translate3d(0, -100%, 0);
  }
  to {
    opacity: 1;
    transform: none;
  }
}
</style>