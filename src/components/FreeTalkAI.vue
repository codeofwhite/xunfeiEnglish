<!--星火大模型-->
<template>
  <div class="chat-container">
    <el-row :gutter="20" class="input-row">
      <el-col :span="24">
        <el-input
            type="textarea"
            placeholder="请输入内容"
            v-model="userInput"
            maxlength="500"
            show-word-limit
            :autosize="{ minRows: 2, maxRows: 4}"
            class="input-textarea"
        ></el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <el-button @click="start" type="primary" class="submit-button" :loading="loading">
          {{ loading ? 'ai在拼命思考啦' : '提问' }}
        </el-button>
      </el-col>
    </el-row>
    <div class="chat-content" ref="chatContent">
      <div v-for="chatContent in finalChat" :key="chatContent.id" class="chat-message">
        <div class="message user-message">
          <div class="message-content">我: {{ chatContent.user }}</div>
        </div>
        <div class="message ai-message">
          <div class="message-content">chatGPT: {{ chatContent.ai }}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CryptoJS from 'crypto-js';
import * as base64 from 'base-64'
import SpeechTranslate from "@/components/SpeechTranslate.vue";
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";

export default {
  components: {SpeechTranslate, SpeechSynthesis},
  data() {
    return {
      appId: '30fa122e',
      status: 'init',
      ttsWS: null,
      totalRes: '',
      userInput: '',
      aiContentRequest: '',
      finalChat: [],
      loading: false
    };
  },
  methods: {
    getWebsocketUrl() {
      return new Promise((resolve, reject) => {
        const apiKey = '96b41233a0974ee46f365d13ef6bea09';
        const apiSecret = 'Mzg2ZDdjODJjYWM1MzE5M2ZkM2E0NGJl';
        const url = 'wss://spark-api.xf-yun.com/v3.5/chat'; //这里使用的是星火大模型1.x版本
        const host = "spark-api.xf-yun.com";
        const date = new Date().toGMTString();
        const algorithm = 'hmac-sha256';
        const headers = 'host date request-line';
        const signatureOrigin = `host: ${host}\ndate: ${date}\nGET /v3.5/chat HTTP/1.1`;
        const signatureSha = CryptoJS.HmacSHA256(signatureOrigin, apiSecret);
        const signature = CryptoJS.enc.Base64.stringify(signatureSha);
        const authorizationOrigin = `api_key="${apiKey}", algorithm="${algorithm}", headers="${headers}", signature="${signature}"`;
        const authorization = base64.encode(authorizationOrigin);
        const finalUrl = `${url}?authorization=${authorization}&date=${date}&host=${host}`;
        console.log(finalUrl)
        resolve(finalUrl);
      });
    },
    setStatus(status) {
      this.status = status;
    },
    connectWebSocket() {
      this.setStatus('ttsing');
      this.getWebsocketUrl().then((url) => {
        let ttsWS;
        if ('WebSocket' in window) {
          ttsWS = new WebSocket(url);
        } else if ('MozWebSocket' in window) {
          ttsWS = new MozWebSocket(url);
        } else {
          alert('浏览器不支持WebSocket');
          return;
        }
        this.ttsWS = ttsWS;
        ttsWS.onopen = (e) => {
          this.webSocketSend();
        };
        ttsWS.onmessage = (e) => {
          this.result(e.data);
        };
        ttsWS.onerror = (e) => {
          clearTimeout(this.playTimeout);
          this.setStatus('error');
          alert('WebSocket报错，请f12查看详情');
          console.error(`详情查看：${encodeURI(url.replace('wss:', 'https:'))}`);
        };
        ttsWS.onclose = (e) => {
          console.log(e);
        };
      });
    },
    webSocketSend() {
      let that = this
      const params = {
        header: {
          app_id: this.appId,
          uid: '123456',
        },
        parameter: {
          chat: {
            domain: 'generalv3.5',//如果是chat2这里也需要进行相应修改
            temperature: 0.5,
            max_tokens: 1024,
          },
        },
        payload: {
          message: {
            text: [
              {"role": "user", "content": that.userInput}
            ]
          },
        },
      };
      console.log(JSON.stringify(params));
      this.ttsWS.send(JSON.stringify(params));
    },
    start() {
      this.loading = true
      this.totalRes = '';
      this.aiContentRequest = ''
      this.connectWebSocket();
    },
    requestHandle(requestData) {//处理request
      this.aiContentRequest = this.aiContentRequest + requestData.payload.choices.text[0].content
    },
    result(resultData) {
      let jsonData = JSON.parse(resultData);
      //console.log(jsonData)
      this.totalRes += resultData;
      //this.$refs.outputText.value = this.totalRes;
      //加入到ai回答中
      if (jsonData.header.status !== 2) {//不为结束就进行添加
        this.requestHandle(jsonData)
      } else {
        let contentSomething = {
          ai: this.aiContentRequest,
          user: this.userInput
        }
        this.finalChat.push(contentSomething)
        this.userInput = ''
        this.loading = false
        // 发射自定义事件，传递结果
        this.$emit('result-received', contentSomething);
      }
      if (jsonData.header.code !== 0) {
        alert(`提问失败: ${jsonData.header.code}:${jsonData.header.message}`);
        console.error(`${jsonData.header.code}:${jsonData.header.message}`);
        return;
      }
      if (jsonData.header.code === 0 && jsonData.header.status === 2) {
        // AI回复完毕，将回复内容传递给SpeechSynthesis组件
        // this.$refs.speechSynthesis.play(this.aiContentRequest);
        this.ttsWS.close();
        this.setStatus('init');
      }
    },
    // handleRecognitionComplete(recognizedText) {
    //   // 在这里处理识别结果，例如将其存储在data属性中或传递给其他组件
    //   console.log('识别的文本:', recognizedText);
    //   // 例如，如果您有一个聊天组件，您可以这样做：
    //   this.userInput = recognizedText;
    //   // 传给AI
    //   // this.start();
    // },
    startWithText(text) {
      this.userInput = text;
      this.start();
    },
  },
};
</script>

<style scoped>
.chat-container {
  margin-top: 30px;
  padding: 0 10px;
  background-color: #f5f5f5;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.input-row {
  margin-bottom: 20px;
}

.input-textarea {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}

.submit-button {
  margin-top: 10px;
  width: 100%;
}

.chat-content {
  margin-top: 20px;
  max-height: 300px;
  overflow-y: auto;
}

.chat-message {
  display: flex;
  flex-direction: column;
}

.message {
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 8px;
  max-width: 80%;
}

.user-message .message-content {
  background-color: #dff0d8;
  color: #3c763d;
  align-self: flex-end;
}

.ai-message .message-content {
  background-color: #d9edf7;
  color: #31708f;
  align-self: flex-start;
}

.message-content {
  padding: 10px;
  border-radius: 8px;
}
</style>