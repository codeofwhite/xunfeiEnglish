<template>
  <div>
    <input v-model="text" placeholder="输入文本"/>
    <button @click="generateImage">生成图片</button>
    <div v-if="imageUrl">
      <img :src="imageUrl" alt="生成的图片"/>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      text: '',
      imageUrl: null
    };
  },
  methods: {
    async generateImage() {
      try {
        const response = await fetch('http://localhost:8001/xunfei/generateImage', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          // 初始的时候发参数有带双引号，会造成解析错误，最终修改了Controller，然后调用的参数也修改了
          body: JSON.stringify({text: this.text}) // 确保传递的字符串格式正确
        });

        if (response.ok) {
          const blob = await response.blob();
          this.imageUrl = URL.createObjectURL(blob);
        } else {
          console.error('生成图片失败');
        }
      } catch (error) {
        console.error('请求出错', error);
      }
    }
  }
};
</script>

<style scoped>
/* 添加一些样式 */
</style>
