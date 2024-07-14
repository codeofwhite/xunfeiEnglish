<template>
  <div class="about-us">
    <h1>关于我们</h1>
    <p>欢迎使用我们的英语教学软件！我们致力于通过先进的讯飞技术，为用户提供高效、互动、有趣的英语学习体验。</p>
    <h2>我们的使命</h2>
    <p>我们的使命是帮助每一位学习者提升英语水平，打破语言障碍，开拓更广阔的世界。</p>
    <h2>我们的团队</h2>
    <p>我们拥有一支由语言学专家、教育学家和技术开发人员组成的团队，致力于为用户提供最优质的学习资源和服务。</p>
    <h2>联系我们</h2>
    <p>如果您有任何问题或建议，请随时通过以下方式联系我们：</p>
    <ul>
      <li>邮箱: support@englishlearning.com</li>
      <li>电话: 123-456-7890</li>
      <li>地址: 重庆市沙坪坝区</li>
    </ul>
  </div>
</template>

<script>
export default {
  name: 'AboutUs'
}
</script>

<style scoped>
.about-us {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
}

h1 {
  color: #333;
  text-align: center;
  font-family: 'Georgia', serif;
  font-size: 2em;
  margin-bottom: 20px;
}

h2 {
  color: #555;
  font-size: 1.5em;
  margin-top: 20px;
}

p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 20px;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  color: #666;
  line-height: 1.6;
  margin-bottom: 10px;
}
</style>