<template>
  <div class="container">
    <nav class="top-nav">
      <button class="nav-button go-back" @click="goBack">返回</button>
      <h1 class="title">魔法口语</h1>
      <button class="nav-button dialog-toggle" @click="changeDialog">自由对话</button>
    </nav>

    <div v-if="showDialog" class="custom-scene">
      <h2>创建你的场景</h2>
      <input v-model="scene" placeholder="请输入或生成场景" class="input-scene">
      <div class="scene-buttons">
        <button class="button generate" @click="generateScene">随机生成</button>
        <button class="button submit" @click="submitScene">开始对话</button>
      </div>
    </div>

    <div class="ai-teacher">
      <h2>选择你的AI教练</h2>
      <div class="accents">
        <button class="button accent" @click="setAccent('美音')">美音</button>
        <button class="button accent" @click="setAccent('英音')">英音</button>
      </div>
    </div>

    <div class="scene-dialogue">
      <h2>选择对话场景</h2>
      <div class="scenes">
        <div class="scene-category">
          <h3>日常对话</h3>
          <div class="scene-buttons">
            <button v-for="scene in dailyScenes" class="button scene" @click="openSceneDialog(scene)">{{
                scene
              }}
            </button>
          </div>
        </div>
        <div class="scene-category">
          <h3>校园生活</h3>
          <div class="scene-buttons">
            <button v-for="scene in schoolScenes" class="button scene" @click="openSceneDialog(scene)">{{
                scene
              }}
            </button>
          </div>
        </div>
        <div class="scene-category">
          <h3>旅游出行</h3>
          <div class="scene-buttons">
            <button v-for="scene in travelScenes" class="button scene" @click="openSceneDialog(scene)">{{
                scene
              }}
            </button>
          </div>
        </div>
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
    changeDialog() {
      this.showDialog = !this.showDialog;
    },
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
  font-family: 'Roboto', sans-serif; /* 更专业的字体 */
  color: #333; /* 更柔和的文字颜色 */
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

.nav-button {
  background-color: transparent;
  color: #ffffff;
  border: 2px solid white;
  border-radius: 2px;
  width: 100px;
  height: 50px;
  font-size: large;
}

.top-nav {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 10px 20px;
  background-color: #00e1ff; /* 深蓝色背景 */
  color: white;
  border-radius: 5px;
}

.button:hover {
  transform: translateY(-3px);
}

.go-back {
  background-color: #6affbe;
}

.dialog-toggle {
  background-color: #ffce87;
}

.accent, .scene {
  background-color: #2196F3;
}

.title {
  color: #ffffff;
  margin-bottom: 20px;
  font-family: 'Fredoka One', cursive; /* 更有趣的标题字体 */
}

.custom-scene, .ai-teacher, .scene-dialogue {
  background-color: white;
  padding: 20px;
  margin-top: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.scene-buttons {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
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

.generate {
  background-color: #FFCA28; /* 金色按钮 */
}

.submit {
  background-color: #66BB6A; /* 绿色按钮 */
}

.accents {
  display: flex;
  justify-content: center;
}

.scenes {
  display: flex;
  flex-direction: column;
}

.scene-category {
  margin-bottom: 20px;
}
</style>