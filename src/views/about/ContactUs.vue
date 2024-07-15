<template>
  <div class="content">
    <div class="relation">
      <h3>联系我们</h3>
      <p>开发者联系：</p>
      <div class="emails">
        <p><a href="mailto:1142013375@qq.com">1142013375@qq.com</a></p>
        <p><a href="mailto:99gelanjingling@gmail.com">99gelanjingling@gmail.com</a></p>
        <p><a href="mailto:1792242434@qq.com">1792242434@qq.com</a></p>
      </div>
    </div>
    <hr class="divider">
  </div>
</template>

<script>
export default {
  name: 'AboutUs'
}
</script>

<style scoped>
@import url('@/styles/about.css');

.content {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  background: linear-gradient(135deg, #f9f9f9, #e0e0e0);
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.relation {
  text-align: center;
  background-color: #fff;
  padding: 30px;
  border-radius: 12px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
  transition: transform 0.3s, box-shadow 0.3s;
}

.relation:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 25px rgba(0, 0, 0, 0.15);
}

.relation h3 {
  margin-bottom: 15px;
  color: #333;
  font-size: 1.8em;
}

.relation p {
  margin: 10px 0;
  color: #555;
  font-size: 1.1em;
}

.emails a {
  color: #1a73e8;
  text-decoration: none;
  font-weight: bold;
}

.emails a:hover {
  text-decoration: underline;
  color: #0f5bb5;
}

.divider {
  width: 60%;
  margin-top: 30px;
  border: none;
  border-top: 2px solid #ddd;
}
</style>