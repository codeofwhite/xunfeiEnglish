<!--语音转文字组件-->
<template>
  <div class="container">
    <button class="record-btn" @click="startRecording">
      {{ btnText }}
    </button>
    <div class="result-text">实时识别结果：{{ resultText }}</div>
    <!-- 新增识别记录列表 -->
    <div class="recognition-list">
      <div v-for="(record, index) in recognitionRecords" :key="index">
        {{ index + 1 }}. {{ record }}
      </div>
    </div>
    <!-- 新增返回数据显示 -->
    <div class="result-data">
      <h3>评分结果</h3>
      <table>
        <tr>
          <th>评分项</th>
          <th>分数</th>
        </tr>
        <tr>
          <td>Accuracy Score</td>
          <td>{{ resultData.AccuracyScore }}</td>
        </tr>
        <tr>
          <td>Fluency Score</td>
          <td>{{ resultData.FluencyScore }}</td>
        </tr>
        <tr>
          <td>Integrity Score</td>
          <td>{{ resultData.IntegrityScore }}</td>
        </tr>
        <tr>
          <td>Total Score</td>
          <td>{{ resultData.TotalScore }}</td>
        </tr>
      </table>
      <div>
        <h4>平均分: {{ averageScore }}</h4>
      </div>
    </div>
    <button class="end-btn" @click="endConversation">结束对话</button>
  </div>
</template>

<script setup>
import {onMounted, ref} from "vue";
import '/src/voice-utils/utilJS/crypto-js.js'; //鉴权的引用地址
import '/src/voice-utils/utilJS/index.umd.js'; // 调用Web Speech API 的依赖，应该是官方的写的工具类
import {defineEmits} from 'vue';
import axios from "axios";

const recognitionRecords = ref([]); // 新增一个数组来存储识别记录
const emit = defineEmits(['recognition-complete']);

const accuracyScores = ref([]);
const fluencyScores = ref([]);
const integrityScores = ref([]);

const btnText = ref("开始录音");
const btnStatus = ref("UNDEFINED"); // "UNDEFINED" "CONNECTING" "OPEN" "CLOSING" "CLOSED"
const recorder = new RecorderManager('/src/voice-utils/dist')
const APPID = "30fa122e"; // TODO 你的讯飞模型APPID
const API_SECRET = "Mzg2ZDdjODJjYWM1MzE5M2ZkM2E0NGJl"; // TODO 你的讯飞模型API_SECRET
const API_KEY = "96b41233a0974ee46f365d13ef6bea09"; // TODO 你的讯飞模型API_KEY
let iatWS; //监听录音的变量
let resultText = ref(''); // 识别结果
let resultTextTemp = ref('');
let countdownInterval;
let audioContext = ref('');
let audioChunks = ref([]);
let audioBlob = ref('');
let resultData = ref({}); // 新增一个对象来存储返回的数据
let scores = ref([]); // 新增一个数组来存储每个句子的得分

// 生成 WebSocket URL 生成规则由平台决定
function getWebSocketUrl() {
  // 请求地址根据语种不同变化
  var url = "wss://iat-api.xfyun.cn/v2/iat";
  var host = "iat-api.xfyun.cn";
  var apiKey = API_KEY;
  var apiSecret = API_SECRET;
  var date = new Date().toGMTString();
  var algorithm = "hmac-sha256";
  var headers = "host date request-line";
  var signatureOrigin = `host: ${host}\ndate: ${date}\nGET /v2/iat HTTP/1.1`;
  var signatureSha = CryptoJS.HmacSHA256(signatureOrigin, apiSecret);
  var signature = CryptoJS.enc.Base64.stringify(signatureSha);
  var authorizationOrigin = `api_key="${apiKey}", algorithm="${algorithm}", headers="${headers}", signature="${signature}"`;
  var authorization = btoa(authorizationOrigin);
  url = `${url}?authorization=${authorization}&date=${date}&host=${host}`;
  console.log(url)
  return url;
}

// 加密工具函数
function toBase64(buffer) {
  var binary = "";
  var bytes = new Uint8Array(buffer);
  var len = bytes.byteLength;
  for (var i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
}

// 计数函数
function countdown() {
  let seconds = 60;
  btnText.value = `录音中（${seconds}s）`;
  countdownInterval = setInterval(() => {
    seconds = seconds - 1;
    if (seconds <= 0) {
      clearInterval(countdownInterval);
      recorder.stop();
    } else {
      btnText.value = `录音中（${seconds}s）`;
    }
  }, 1000);
}

// 录音状态变化函数
function changeStatus(status) {
  btnStatus.value = status;
  if (status === "CONNECTING") {
    btnText.value = "建立连接中";
    resultText.value = '';
    resultTextTemp.value = "";
  } else if (status === "OPEN") {
    countdown();
  } else if (status === "CLOSING") {
    btnText.value = "关闭连接中";
  } else if (status === "CLOSED") {
    btnText.value = "开始录音";
  }
}

// 结果解析函数
function renderResult(resultData) {
  // 识别结束
  let jsonData = JSON.parse(resultData);
  if (jsonData.data && jsonData.data.result) {
    let data = jsonData.data.result;
    let str = "";
    let ws = data.ws;
    for (let i = 0; i < ws.length; i++) {
      str = str + ws[i].cw[0].w;
    }
    // 开启wpgs会有此字段(前提：在控制台开通动态修正功能)
    // 取值为 "apd"时表示该片结果是追加到前面的最终结果；取值为"rpl" 时表示替换前面的部分结果，替换范围为rg字段
    if (data.pgs) {
      if (data.pgs === "apd") {
        // 将resultTextTemp同步给resultText
        resultText.value = resultTextTemp;
        // 在renderResult函数中，当识别结束并且结果准备好时
        emit('recognition-complete', resultText.value);
      }
      // 将结果存储在resultTextTemp中
      resultTextTemp.value = resultText.value + str;
    } else {
      resultText.value = resultText.value + str;
      // 在renderResult函数中，当识别结束并且结果准备好时
      emit('recognition-complete', resultText.value);
    }
  }
  if (jsonData.code === 0 && jsonData.data.status === 2) {
    iatWS.close();
    recognitionRecords.value.push(resultText.value); // 将识别结果添加到记录数组中
  }
  if (jsonData.code !== 0) {
    iatWS.close();
    console.error(jsonData);
  }
}

// 连接 WebSocket
function connectWebSocket() {
  const websocketUrl = getWebSocketUrl();
  if ("WebSocket" in window) {
    iatWS = new WebSocket(websocketUrl);
  } else if ("MozWebSocket" in window) {
    iatWS = new MozWebSocket(websocketUrl);
  } else {
    alert("浏览器不支持WebSocket");
    return;
  }
  changeStatus("CONNECTING");
  iatWS.onopen = (e) => {
    // 开始录音
    startRecordingPcm()
    recorder.start({
      sampleRate: 16000,
      frameSize: 1280,
    });
    var params = {
      common: {
        app_id: APPID,
      },
      business: {
        language: "zh_cn",
        domain: "iat",
        accent: "mandarin",
        vad_eos: 5000,
        dwa: "wpgs",
      },
      data: {
        status: 0,
        format: "audio/L16;rate=16000",
        encoding: "raw",
      },
    };
    iatWS.send(JSON.stringify(params));
  };
  iatWS.onmessage = (e) => {
    renderResult(e.data);
  };
  iatWS.onerror = (e) => {
    console.error(e);
    recorder.stop();
    stopRecordingPcm()
    saveAudioPcm()
    changeStatus("CLOSED");
  };
  iatWS.onclose = (e) => {
    recorder.stop();
    stopRecordingPcm()
    saveAudioPcm()
    changeStatus("CLOSED");
    uploadAudio();
  };
}

// 定义监听开始的函数
recorder.onStart = () => {
  changeStatus("OPEN");
}
// 处理回调的结果
recorder.onFrameRecorded = ({isLastFrame, frameBuffer}) => {
  if (iatWS.readyState === iatWS.OPEN) {
    iatWS.send(
        JSON.stringify({
          data: {
            status: isLastFrame ? 2 : 1,
            format: "audio/L16;rate=16000",
            encoding: "raw",
            audio: toBase64(frameBuffer),
          },
        })
    );
    if (isLastFrame) {
      changeStatus("CLOSING");
    }
  }
};
// 停止录音的处理
recorder.onStop = () => {
  clearInterval(countdownInterval);
};
// 按钮点击的启动 | 结束函数
const startRecording = () => {
  if (btnStatus.value === "UNDEFINED" || btnStatus.value === "CLOSED") {
    connectWebSocket();
  } else if (btnStatus.value === "CONNECTING" || btnStatus.value === "OPEN") {
    // 结束录音
    recorder.stop();
  }
}

async function startRecordingPcm() {
  const stream = await navigator.mediaDevices.getUserMedia({audio: true});
  audioContext.value = new (window.AudioContext || window.webkitAudioContext)();
  const source = audioContext.value.createMediaStreamSource(stream);
  const processor = audioContext.value.createScriptProcessor(4096, 1, 1);

  source.connect(processor);
  processor.connect(audioContext.value.destination);

  processor.onaudioprocess = (e) => {
    const inputData = e.inputBuffer.getChannelData(0);
    const buffer = new ArrayBuffer(inputData.length * 2);
    const outputData = new DataView(buffer);
    for (let i = 0; i < inputData.length; i++) {
      const s = Math.max(-1, Math.min(1, inputData[i]));
      outputData.setInt16(i * 2, s < 0 ? s * 0x8000 : s * 0x7FFF, true);
    }
    audioChunks.value.push(buffer);
  };

  recorder.value = processor;
  // changeStatus("OPEN");
}

function stopRecordingPcm() {
  recorder.value.disconnect();
  audioContext.value.close();
  const audioBuffer = new Blob(audioChunks.value, {type: 'audio/pcm'});
  audioBlob.value = audioBuffer;
  audioChunks.value = [];
  // changeStatus("CLOSED");
}

function saveAudioPcm() {
  const url = URL.createObjectURL(audioBlob.value);
  const a = document.createElement('a');
  a.style.display = 'none';
  a.href = url;
  a.download = 'audio.pcm';
  document.body.appendChild(a);
  a.click();
  window.URL.revokeObjectURL(url);
}

async function uploadAudio() {
  const formData = new FormData();
  formData.append('audioFile', audioBlob.value, 'audio.pcm');
  formData.append('text', resultText.value);
  try {
    const response = await axios.post('http://localhost:8001/xunfei/score', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    });
    console.log('上传结果:', response.data);
    // 解析返回的数据
    resultData.value = response.data; // 将返回的数据存储在resultData对象中
    const totalScore = parseFloat(response.data.TotalScore); // 确保TotalScore是一个数字
    if (!isNaN(totalScore)) {
      scores.value.push(totalScore); // 将每个句子的总分存储在scores数组中
      accuracyScores.value.push(response.data.AccuracyScore); // 记录Accuracy得分
      fluencyScores.value.push(response.data.FluencyScore); // 记录Fluency得分
      integrityScores.value.push(response.data.IntegrityScore); // 记录Integrity得分
      calculateAverageScore(); // 计算平均分
    } else {
      console.error('TotalScore 不是一个有效的数字:', response.data.TotalScore);
    }
  } catch (error) {
    console.error('上传失败:', error);
  }
}

function calculateAverageScore() {
  if (scores.value.length > 0) {
    const sum = scores.value.reduce((a, b) => a + b, 0);
    averageScore.value = (sum / scores.value.length).toFixed(2);
  } else {
    averageScore.value = 0;
  }
}

const endConversation = async () => {
  try {
    const response = await axios.post('http://localhost:8004/api/talk', {
      userEmail: 'example@example.com',
      accuracy: accuracyScores.value,
      fluency: fluencyScores.value,
      integrity: integrityScores.value,
      totalScore: averageScore.value
    });
    console.log('对话信息上传成功:', response.data);
  } catch (error) {
    console.error('对话信息上传失败:', error);
  }
};

let averageScore = ref(0); // 新增一个变量来存储平均分
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start; /* 更紧凑的布局 */
  padding: 10px; /* 减少留白 */
}

.record-btn {
  padding: 8px 16px; /* 按钮更紧凑 */
  margin-bottom: 8px; /* 减少按钮和结果文本之间的空间 */
  font-size: 14px; /* 调整字体大小 */
  color: #fff;
  background-color: #007BFF; /* SmarkEng风格的蓝色 */
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.record-btn:hover {
  background-color: #0056b3; /* 鼠标悬停时的颜色变化 */
}

.result-text {
  font-size: 16px; /* 调整结果文本的字体大小 */
  color: #333;
  width: 100%; /* 宽度调整为100%，以填充容器 */
  text-align: center; /* 文本居中 */
}

/*识别记录列表*/
.recognition-list {
  margin-top: 10px; /* 减少顶部空间 */
  font-size: 14px; /* 调整字体大小 */
}

/* 新增返回数据显示样式 */
.result-data {
  margin-top: 10px; /* 减少顶部空间 */
  font-size: 16px; /* 调整字体大小 */
  color: #333;
  text-align: center;
}

.result-data table {
  width: 100%;
  border-collapse: collapse;
}

.result-data th, .result-data td {
  border: 1px solid #ddd;
  padding: 6px; /* 减少单元格内边距 */
}

.result-data th {
  background-color: #f2f2f2;
  text-align: left;
}

.result-data h4 {
  margin-top: 10px; /* 减少顶部空间 */
  font-size: 18px; /* 调整字体大小 */
  color: #333;
}

.end-btn {
  padding: 8px 16px;
  margin-top: 10px;
  font-size: 14px;
  color: #fff;
  background-color: #28a745;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s;
}

.end-btn:hover {
  background-color: #218838;
}
</style>
