<!--语音打分-->
<template>
  <br/>
  <textarea placeholder="where are you"></textarea>
  <br/>
  <button id="btn_control" @click="startRecording">开始录音</button>
  <div class="output-box" id="result_output">
    <div class="left">
      <p>准确度分：<span id="accuracy_score">0</span></p>
      <p>流畅度分：<span id="fluency_score">0</span></p>
      <p>完整度分：<span id="integrity_score">0</span></p>
      <p>声韵分（仅限中文）：<span id="phone_score">0</span></p>
      <p>调型分（仅限中文）：<span id="tone_score">0</span></p>
      <p>整体印象分（仅限中文）：<span id="emotion_score">0</span></p>
      <p>总分：<span id="total_score">0</span></p>
    </div>
    <div id="right" style="display: none;">
      <p class="title">红色文字代表发音不标准：</p>
      <p id="result"></p>
    </div>
  </div>
</template>

<script setup>
import {ref} from "vue";
import '/src/voice-utils/utilJS/crypto-js.js'; //鉴权的引用地址
import '/src/voice-utils/utilJS/index.umd.js';
import parser from "/src/utils/fast-xml-parser.min.js"; // 调用Web Speech API 的依赖，应该是官方的写的工具类

const btnText = ref("开始录音");
const btnStatus = ref("UNDEFINED"); // "UNDEFINED" "CONNECTING" "OPEN" "CLOSING" "CLOSED"
let resultText = ref(''); // 识别结果
let resultTextTemp = ref('');
const recorder = new RecorderManager('/src/voice-utils/dist')
recorder.onStart = () => {
  changeStatus("OPEN");
}
const APPID = "30fa122e"; // TODO 你的讯飞模型APPID
const API_SECRET = "Mzg2ZDdjODJjYWM1MzE5M2ZkM2E0NGJl"; // TODO 你的讯飞模型API_SECRET
const API_KEY = "96b41233a0974ee46f365d13ef6bea09"; // TODO 你的讯飞模型API_KEY
let iseWS; //监听录音的变量
let countdownInterval;

// 生成 WebSocket URL 生成规则由平台决定
function getWebSocketUrl() {
  // 请求地址根据语种不同变化
  var url = "wss://ise-api.xfyun.cn/v2/open-ise";
  var host = "ise-api.xfyun.cn";
  var apiKey = API_KEY;
  var apiSecret = API_SECRET;
  var date = new Date().toGMTString();
  var algorithm = "hmac-sha256";
  var headers = "host date request-line";
  var signatureOrigin = `host: ${host}\ndate: ${date}\nGET /v2/open-ise HTTP/1.1`;
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

// 录音状态变化函数
function changeStatus(status) {
  btnStatus.value = status;
  if (status === "CONNECTING") {
    btnText.value = "建立连接中";
    resultText.value = '';
    resultTextTemp.value = "";
  } else if (status === "OPEN") {
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
  console.log(jsonData.data);

  if (jsonData.data && jsonData.data.data) {
    let data = window.atob(jsonData.data.data);
    let grade = parser.parse(data, {
      attributeNamePrefix: "",
      ignoreAttributes: false,
    });
    console.log(grade);
    const readSentence =
        grade?.xml_result?.read_sentence?.rec_paper?.read_chapter;
    document.getElementById("accuracy_score").innerText =
        readSentence?.accuracy_score;
    document.getElementById("fluency_score").innerText =
        readSentence?.fluency_score;
    document.getElementById("integrity_score").innerText =
        readSentence?.integrity_score;
    document.getElementById("phone_score").innerText =
        readSentence?.phone_score || 0;
    document.getElementById("tone_score").innerText =
        readSentence?.tone_score || 0;
    document.getElementById("emotion_score").innerText =
        readSentence?.emotion_score || 0;
    document.getElementById("total_score").innerText =
        readSentence?.total_score;
    let sentence = readSentence?.word || [];
    let resultStr = "";
    sentence.forEach((item) => {
      if (item?.word) {
        item.word.forEach((wt) => {
          let flag = false;
          if (wt.syll?.phone) {
            flag = wt.syll.phone.some((pt) => pt?.perr_msg != 0);
          } else {
            wt.syll.forEach((st) => {
              if (Array.isArray(st?.phone)) {
                flag = st.phone.some((pt) => pt?.perr_msg != 0);
              }
            });
          }
          if (flag) {
            resultStr += `<span class="err">${wt.content}</span>`;
          } else {
            resultStr += wt.content;
          }
        });
      } else if (item?.syll) {
        let flag = false;
        if (item.syll?.phone) {
          flag = item.syll.phone.some((pt) => pt?.perr_msg != 0);
        } else {
          item.syll.forEach((st) => {
            if (Array.isArray(st?.phone)) {
              flag = st.phone.some((pt) => pt?.perr_msg != 0);
            }
          });
        }
        if (flag) {
          resultStr += `<span class="err">${item.content}</span>`;
        } else {
          resultStr += item.content;
        }
      }
    });
    if (resultStr) {
      document.getElementById("right").style.display = "block";
      document.getElementById("result").innerHTML = resultStr;
    } else {
      document.getElementById("right").style.display = "none";
    }
  }
  if (jsonData.code === 0 && jsonData.data.status === 2) {
    iseWS.close();
  }
  if (jsonData.code !== 0) {
    iseWS.close();
    console.error(jsonData);
  }
}

// 连接 WebSocket
function connectWebSocket() {
  const websocketUrl = getWebSocketUrl();
  if ("WebSocket" in window) {
    iseWS = new WebSocket(websocketUrl);
  } else if ("MozWebSocket" in window) {
    iseWS = new MozWebSocket(websocketUrl);
  } else {
    alert("浏览器不支持WebSocket");
    return;
  }
  changeStatus("CONNECTING");
  iseWS.onopen = (e) => {
    // 开始录音
    recorder.start({
      sampleRate: 16000,
      frameSize: 1280,
    });
    var params = {
      common: {
        app_id: APPID,
      },
      business: {
        category: "read_sentence", // read_syllable/单字朗读，汉语专有 read_word/词语朗读  read_sentence/句子朗读 https://www.xfyun.cn/doc/Ise/IseAPI.html#%E6%8E%A5%E5%8F%A3%E8%B0%83%E7%94%A8%E6%B5%81%E7%A8%8B
        rstcd: "utf8",
        group: "pupil",
        sub: "ise",
        tte: "utf-8",
        cmd: "ssb",
        auf: "audio/L16;rate=16000",
        ent: "en_vip",
        aus: 1,
        aue: "raw",
        text:
            "\uFEFF" +
            (document.getElementById("text")?.value || "where are you"),
      },
      data: {
        status: 0,
        // data_type: 1,
        // encoding: "raw",
      },
    };
    iseWS.send(JSON.stringify(params));
  };
  iseWS.onmessage = (e) => {
    renderResult(e.data);
  };
  iseWS.onerror = (e) => {
    console.error(e);
    recorder.stop();
    changeStatus("CLOSED");
  };
  iseWS.onclose = (e) => {
    recorder.stop();
    changeStatus("CLOSED");
  };
}

// 定义监听开始的函数
recorder.onStart = () => {
  changeStatus("OPEN");
}
// 处理回调的结果
recorder.onFrameRecorded = ({isLastFrame, frameBuffer}) => {
  if (iseWS.readyState === iseWS.OPEN) {
    iseWS.send(
        JSON.stringify({
          business: {
            aue: "raw",
            cmd: "auw",
            aus: isLastFrame ? 4 : 2,
          },
          data: {
            status: isLastFrame ? 2 : 1,
            data: toBase64(frameBuffer),
            data_type: 1
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
</script>

<style scoped>
/* 文本区域样式 */
textarea {
  width: 95%; /* 设置宽度填满容器 */
  height: 150px; /* 设置高度 */
  padding: 10px; /* 内边距 */
  border: 1px solid #ccc; /* 边框颜色 */
  border-radius: 4px; /* 边框圆角 */
  box-shadow: inset 0 1px 3px rgba(0, 0, 0, 0.1); /* 内阴影 */
  font-size: 16px; /* 文本大小 */
  line-height: 1.5; /* 行高 */
  color: #333; /* 文本颜色 */
  background-color: #f8f8f8; /* 背景色 */
  resize: vertical; /* 允许垂直调整大小 */
  outline: none; /* 移除焦点时的轮廓线 */
}

textarea::placeholder {
  color: #aaa; /* 占位符文本颜色 */
}

textarea:focus {
  border-color: #64b5f6; /* 聚焦时的边框颜色 */
}


/* 按钮样式 */
button {
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
  box-shadow: 0 2px 4px rgba(0, 123, 255, 0.3);
  transition: all 0.2s ease-in-out;
}

button:hover {
  background-color: #0056b3;
  box-shadow: 0 4px 8px rgba(0, 123, 255, 0.5);
}

/* 输出框样式 */
.output-box {
  border: 1px solid #ddd;
  border-radius: 4px;
  padding: 15px;
  background-color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

/* 左侧分数样式 */
.left {
  color: #555;
  font-size: 0.9em;
}

/* 右侧结果样式 */
.right {
  display: block; /* 如果需要显示 */
  margin-top: 20px;
}

/* 错误文本样式 */
.err {
  color: red;
  font-weight: bold;
}
</style>
