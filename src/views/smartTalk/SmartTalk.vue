<template>
  <div class="container">
    <button class="button go-back" @click="goBack">返回</button>
    <h1 class="title">魔法口语</h1>
    <button class="button dialog-toggle" @click="showDialog = true">自由对话：每天10分钟，打造流利口语！</button>

    <div v-if="showDialog" class="custom-scene">
      <h2>自定义场景</h2>
      <input v-model="scene" placeholder="请输入或生成场景" class="input-scene">
      <button class="button" @click="generateScene">随机生成</button>
      <button class="button" @click="submitScene">提交</button>
    </div>

    <div class="ai-teacher">
      <h2>AI老师</h2>
      <button class="button accent" @click="setAccent('美音')">美音</button>
      <button class="button accent" @click="setAccent('英音')">英音</button>
    </div>

    <div class="scene-dialogue">
      <h2>场景对话</h2>
      <div class="daily-convo">
        <h3>日常对话</h3>
        <button v-for="scene in dailyScenes" class="button scene" @click="openSceneDialog(scene)">{{ scene }}</button>
      </div>
      <div class="campus-life">
        <h3>校园生活</h3>
        <button v-for="scene in schoolScenes" class="button scene" @click="openSceneDialog(scene)">{{ scene }}</button>
      </div>
      <div class="travel-talk">
        <h3>旅游出行</h3>
        <button v-for="scene in travelScenes" class="button scene" @click="openSceneDialog(scene)">{{ scene }}</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showDialog: false,
      scene: '',
      accent: '',
      dailyScenes: ['问候', '购物', '点餐', '问路', '打电话'],
      schoolScenes: ['课堂讨论', '图书馆', '社团活动', '宿舍生活', '考试'],
      travelScenes: ['订机票', '酒店入住', '租车', '景点问询', '紧急情况']
    };
  },
  methods: {
    goBack() {
      this.$router.push('/'); // 使用Vue Router进行导航
    },
    generateScene() {
      const scenes = [...this.dailyScenes, ...this.schoolScenes, ...this.travelScenes];
      this.scene = scenes[Math.floor(Math.random() * scenes.length)];
    },
    submitScene() {
      // 使用URL参数传递场景信息
      const freeScene = this.scene;
      this.$router.push({path: '/freeTalk', query: {freeScene}});
    },
    setAccent(newAccent) {
      this.accent = newAccent;
      // 实际应用中需实现切换发音的代码
    },
    openSceneDialog(scene) {
      this.$router.push({path: '/scenarioTalk', query: {scene}});
    }
  }
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 20px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.button {
  margin: 10px;
  padding: 10px 20px;
  cursor: pointer;
  background-color: #ffcc00;
  color: #fff;
  border: none;
  border-radius: 30px;
  font-size: 16px;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease;
}

.button:hover {
  transform: translateY(-3px);
}

.go-back {
  background-color: #666;
}

.dialog-toggle {
  background-color: #4CAF50;
}

.accent, .scene {
  background-color: #2196F3;
}

.title {
  color: #E91E63;
  font-family: 'Comic Sans MS', 'Comic Neue', cursive;
  margin-bottom: 20px;
}

.custom-scene, .ai-teacher, .scene-dialogue {
  margin-top: 20px;
}

.input-scene {
  width: 100%;
  padding: 10px;
  margin: 10px 0;
  border: 2px solid #ff9800;
  border-radius: 20px;
}

@media (max-width: 768px) {
  .container {
    padding: 10px;
  }

  .button {
    padding: 8px 16px;
    font-size: 14px;
  }

  .title {
    font-size: 24px;
  }
}
</style>