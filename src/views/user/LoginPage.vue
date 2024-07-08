<template>
  <div class="container">
    <div class="login-wrapper">
      <div class="header">登录</div>
      <div class="form-wrapper">
        <input type="text" name="邮箱" placeholder="邮箱" class="input-item">
        <input type="password" name="密码" placeholder="密码" class="input-item">
        <div class="msg">
          忘记密码？ <a href="forgot-password.html">点击这里</a>
        </div>
        <button class="btn">登录</button>
        <button class="register-btn" @click="goToRegister">注册</button>
      </div>
    </div>
  </div>
</template>

<script setup>

import router from "@/router/index.js";

function goToRegister() {
  router.push('/register')
}

</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html, body {
  height: 100%;
  font-family: 'Arial', sans-serif;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0;
  padding: 0;
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}

.login-wrapper {
  background-color: #fff;
  padding: 2rem;
  border-radius: 10px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
  width: auto;
  max-width: 400px;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
}

.header {
  font-size: 2rem;
  color: #333;
  margin-bottom: 1.5rem;
  text-align: center;
}

.input-item {
  width: calc(100% - 2rem);
  margin-bottom: 1rem;
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 1rem;
}

.btn {
  width: calc(100% - 2rem);
  padding: 0.5rem;
  margin-top: 1rem;
  background-color: #a6c1ee;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 1rem;
  transition: all 0.3s ease;
}

.btn:hover {
  background-color: #fbc2eb;
}

.msg {
  font-size: 0.9rem;
  margin-top: 1rem;
}

.msg a {
  color: #a6c1ee;
  text-decoration: underline;
}

.register-btn {
  display: block;
  margin-top: 1rem;
  padding: 0.5rem 1rem;
  background-color: #fff;
  color: #a6c1ee;
  border: 2px solid #a6c1ee;
  border-radius: 5px;
  cursor: pointer;
  font-size: 0.9rem;
  transition: all 0.3s ease;
  text-align: center;
  text-decoration: none;
}

.register-btn:hover {
  background-color: #a6c1ee;
  color: #fff;
}
</style>