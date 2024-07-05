<template>
  <button @click="playVoice('en-GB')">播放英音</button>
  <button @click="playVoice('en-US')">播放美音</button>
</template>
<script>
const synth = window.speechSynthesis;
export default {
  data() {
    return {
      voices: [] // 存储语音列表
    };
  },
  mounted() {
    // 当组件加载完毕，获取可用的语音列表
    this.voices = synth.getVoices();
  },
  methods: {
    playVoice(lang) {
      const msg = new SpeechSynthesisUtterance('mamba out'); // 传入需要播放的文字
      const voice = this.voices.find(v => v.lang === lang); // 根据传入的lang参数找到对应的语音
      if (voice) {
        msg.voice = voice; // 设置语音
      }
      msg.volume = 1; // 声音音量：1
      msg.rate = 1; // 语速：1
      msg.pitch = 1; // 音高：1
      synth.speak(msg); // 播放
    },
  }
};
</script>