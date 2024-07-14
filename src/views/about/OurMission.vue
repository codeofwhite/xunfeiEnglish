<template>
  <div class="mission">
    <h1>我们的使命</h1>
    <p>在这里，我们致力于通过创新的技术和优质的教育资源，帮助每一位学习者实现他们的英语学习目标。</p>
    <p>我们相信，语言是连接世界的桥梁，而英语是通向无限可能的钥匙。</p>
    <p>无论你是学生、职场人士，还是英语爱好者，我们都希望能成为你学习旅程中的伙伴，陪伴你一起成长。</p>
    <p>让我们一起，打破语言的障碍，迎接更加美好的未来。</p>
  </div>
</template>

<script>
export default {
  name: 'Mission'
}
</script>

<style scoped>
.mission {
  max-width: 800px;
  margin: 20px auto;
  padding: 20px;
  background: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  font-family: 'Arial', sans-serif;
  text-align: center;
}

h1 {
  color: #333;
  font-family: 'Georgia', serif;
  font-size: 2em;
  margin-bottom: 20px;
}

p {
  color: #666;
  line-height: 1.6;
  margin-bottom: 20px;
  font-size: 1.1em;
}

.mission p:nth-child(2) {
  font-style: italic;
  color: #555;
}

.mission p:nth-child(4) {
  font-weight: bold;
  color: #444;
}
</style>