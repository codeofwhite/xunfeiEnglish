<template>
  <div class="register">
    <el-container class="container">
      <el-header class="header">欢迎成为APP名用户</el-header>
      <el-form ref="form" :model="form" :rules="rules" label-position="top">
        <el-form-item label="邮箱：" prop="email">
          <el-input v-model="form.email" placeholder="请输入常用邮箱">
            <template #append>
              <el-button @click="sendVerificationCode">获取验证码</el-button>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item label="验证码：" prop="verificationCode">
          <el-input v-model="form.verificationCode" placeholder="请输入验证码"></el-input>
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input type="password" v-model="form.password" placeholder="请输入密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码：" prop="confirmPassword">
          <el-input type="password" v-model="form.confirmPassword" placeholder="请再次输入密码"></el-input>
        </el-form-item>
        <el-button type="primary" @click="goBack">上一步</el-button>
        <el-button type="primary" @click="submitForm">下一步</el-button>
      </el-form>
    </el-container>
  </div>
</template>

<script setup>
  import { reactive, ref } from 'vue';
  import { ElMessage } from 'element-plus';
  import route from "@/router/index.js";

  const form = reactive({
  email: '',
  verificationCode: '',
  password: '',
  confirmPassword: ''
});

  const rules = {
  email: [
{ required: true, message: '请输入邮箱地址', trigger: 'blur' },
{ type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
  ],
  verificationCode: [
{ required: true, message: '请输入验证码', trigger: 'blur' }
  ],
  password: [
{ required: true, message: '请输入密码', trigger: 'blur' },
{ min: 12, message: '密码长度不能少于12位', trigger: 'blur' }
  ],
  confirmPassword: [
{ required: true, message: '请再次输入密码', trigger: 'blur' },
{ validator: (rule, value, callback) => {
  if (value !== form.password) {
  callback(new Error('两次输入的密码不一致'));
} else {
  callback();
}
}, trigger: 'blur' }
  ]
};

  const formRef = ref(null);

  const goBack = () =>{
    route.push("login");
  }

  const sendVerificationCode = () => {
// 实现发送验证码到邮箱的逻辑
  ElMessage({
    message: '验证码已发送，请检查您的邮箱',
    type: 'success'
  });
};

  const submitForm = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      ElMessage({
        message: '验证成功，进行下一步',
        type: 'success'
      });
// 进行下一步操作
    } else {
      ElMessage.error('表单中有错误，请检查后提交');
      return false;
    }
  });
};
</script>

<style scoped>
.register {
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  display: grid; /*网格布局*/
  justify-content: center;
  align-items: center;
  height: 100vh;
  margin: 0;
  background-color: #fad0c4;
  background-image: linear-gradient(to right, #fbc2eb, #a6c1ee);
}

.container {
  background-color: white;
  padding: 40px;
  border-radius: 20px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
  width: 400px;
}

.header {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 30px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: bold;
  margin-bottom: 5px;
}

.form-group .input-group {
  display: flex;
}

.form-group input {
  width: calc(100% - 120px);
  padding: 10px 15px;
  border: 1px solid #ccc;
  border-radius: 20px;
  font-size: 14px;
  color: #333;
  background-color: #f5f5f5;
}

.form-group input.blue-border {
  border-color: #007bff;
}

.form-group .input-group button {
  width: 120px;
  padding: 10px;
  margin-left: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 14px;
  cursor: pointer;
}

.form-group .input-group button:hover {
  background-color: #0056b3;
}

.form-group .error {
  color: red;
  font-size: 12px;
  display: none;
}

.form-group .error.active {
  display: block;
}

.form-group .success {
  color: green;
  font-size: 12px;
  display: none;
}

.form-group .success.active {
  display: block;
}

.btn {
  display: block;
  width: 100%;
  padding: 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px;
  font-size: 16px;
  cursor: pointer;
}

.btn:hover {
  background-color: #0056b3;
}
</style>