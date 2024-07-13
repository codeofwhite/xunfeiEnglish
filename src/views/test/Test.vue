<template>
  <div>
    <button @click="startRecording">开始录音</button>
    <button @click="stopRecording">停止录音</button>
    <button @click="saveAudio">保存音频</button>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      audioContext: null,
      mediaRecorder: null,
      audioChunks: [],
      audioBlob: null,
    };
  },
  methods: {
    async startRecording() {
      const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
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
    },
    stopRecording() {
      this.mediaRecorder.disconnect();
      this.audioContext.close();
      const audioBuffer = new Blob(this.audioChunks, { type: 'audio/pcm' });
      this.audioBlob = audioBuffer;
      this.audioChunks = [];
    },
    saveAudio() {
      const url = URL.createObjectURL(this.audioBlob);
      const a = document.createElement('a');
      a.style.display = 'none';
      a.href = url;
      a.download = 'audio.pcm';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
    },
    async uploadAudio() {
      const formData = new FormData();
      formData.append('audio', this.audioBlob, 'audio.pcm');
      const response = await axios.post('/api/upload', formData);
      console.log('上传结果:', response.data);
    },
  },
};
</script>