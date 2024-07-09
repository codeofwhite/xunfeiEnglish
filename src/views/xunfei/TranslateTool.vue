<template>
  <div>
    <input v-model="textToTranslate" placeholder="输入要翻译的文本">
    <button @click="translateText">翻译</button>
    <p>翻译结果: {{ translatedText }}</p>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      textToTranslate: '',
      translatedText: ''
    };
  },
  methods: {
    // 改了一下调用的格式，传的是纯文本
    translateText() {
      // 发送请求到Java后端
      axios.post('http://localhost:8001/xunfei/translate', this.textToTranslate, {
        headers: {
          'Content-Type': 'text/plain' // 确保发送的是纯文本格式
        }
      })
          .then(response => {
            // 处理响应
            this.translatedText = response.data;
          })
          .catch(error => {
            console.error('翻译错误: ', error);
          });
    }
  }
};
</script>

<style scoped>
</style>