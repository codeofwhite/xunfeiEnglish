<!--登录界面-->
<template>
  <div v-if="!isLoggedIn" class="login-body">
    <div class="login-panel">
      <div class="login-title">用户登录</div>
      <el-form :model="formData" ref="loginForm">
        <!-- 账号输入框 -->
        <el-form-item label="" prop="account"
                      :rules="[
          { required: true, message: '请输入账号', trigger: 'blur' },
          /*email格式检查*/
          { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ]">
          <el-input placeholder="请输入账号" v-model="formData.account"></el-input>
        </el-form-item>
        <!-- 密码输入框 -->
        <el-form-item label="" prop="password"
                      :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <el-input placeholder="请输入密码" type="password" v-model="formData.password"></el-input>
        </el-form-item>
        <!-- 记住我复选框 -->
        <el-form-item label="">
          <el-checkbox v-model="formData.rememberMe">记住登录状态</el-checkbox>
        </el-form-item>
        <!-- 验证码按钮 -->
        <el-form-item label="">
          <el-button type="primary" @click="onShow">开始验证</el-button>
          <Vcode :show="isShow" @success="onSuccess" @close="onClose"/>
        </el-form-item>
        <!-- 登录按钮 -->
        <el-form-item label="">
          <el-button type="primary" style="width:100%" @click="onLogin">登录</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="text" @click="onRegister">没有账号？注册一个！</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
  <div v-else>
    <UserProfile></UserProfile>
  </div>
</template>

<!--这个不加setup，上面的找不到formData-->
<script setup>
import {computed, reactive, ref} from "vue"
import {useStore} from 'vuex';
import Vcode from "vue3-puzzle-vcode";
import axios from 'axios'; // 引入axios
import router from '@/router/index.js'
import {ElMessage} from 'element-plus';
import UserProfile from "@/views/userpage/UserProfile.vue";

// 用户资料
const formData = reactive({
  account: '',
  password: '',
  rememberMe: false
});

const isShow = ref(false);
const isVerified = ref(false); // 验证码验证状态
const loginForm = ref(null);
const isLoggedIn = computed(() => store.state.isLoggedIn);
// 状态管理
const store = useStore();

// 调整注册操作页面
const onRegister = () => {
// 重定向到注册页面
  router.push({name: "Register"});
};

const onShow = () => {
  isShow.value = true;
};

// 关闭验证码
const onClose = () => {
  isShow.value = false;
};

// 验证码状态
const onSuccess = () => {
  isVerified.value = true; // 设置验证码验证状态为成功
  onClose(); // 验证成功，需要手动关闭模态框
};

const onLogin = async () => {
  loginForm.value.validate(async (valid) => {
    if (valid && isVerified.value) {
      // 构建登录请求的数据
      const loginData = {
        userEmail: formData.account,
        userPassword: formData.password,
      };

      try {
        // 发送登录请求到后端
        const response = await axios({
          method: 'post',
          url: 'http://localhost:8002/user/login',
          data: loginData,
          headers: {'Content-Type': 'application/json'}
        });
        // 检查后端返回的是否成功标识
        if (response.data.success) {
          // 登录成功
          // 如果用户选择了“记住我”，则将登录状态保存到 localStorage
          if (formData.rememberMe) {
            localStorage.setItem('isLoggedIn', true);
            localStorage.setItem('uemail', response.data.uemail);
          } else {
            sessionStorage.setItem('isLoggedIn', true);
            sessionStorage.setItem('uemail', response.data.uemail);
          }
          // 更新 Vuex 状态
          store.commit('setLoggedIn', true);
          store.commit('setUserEmail', response.data.uemail);
          console.log('登录成功', response);
          ElMessage({
            message: '登录成功！欢迎回来。',
            type: 'success',
            duration: 3000 // 消息显示时间，单位毫秒
          });
          // 刷新
          // location.reload();
          // 登录成功后的操作，比如页面跳转
          // 重定向到主页或其他页面
          await router.push({name: 'home'});
        } else {
          // 登录失败处理
          alert('登录失败，请检查账号密码是否正确');
        }
      } catch (error) {
        // 网络错误处理
        if (!error.response) {
          // 网络错误（服务宕机或无法连接到服务器）
          alert('无法连接到服务器，请稍后再试');
        } else {
          // 登录失败处理
          console.error('登录失败', error);
          alert('登录失败，请检查账号密码是否正确');
        }
      }
    } else {
      // 验证失败处理
      if (!isVerified.value) {
        alert('请先完成验证码验证');
      }
    }
  });
};

// 在 Vue 实例创建时检查存储的登录状态
const checkLoginStatus = () => {
  const isLoggedIn = localStorage.getItem('isLoggedIn') || sessionStorage.getItem('isLoggedIn');
  const uemail = localStorage.getItem('uemail') || sessionStorage.getItem('uemail');
  if (isLoggedIn) {
    store.commit('setLoggedIn', true);
    store.commit('setUserEmail', uemail);
  }
};

// 调用 checkLoginStatus 函数来初始化登录状态
checkLoginStatus();
</script>

<style lang="scss" scoped>
.login-body {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(to right, #83a4d4, #b6fbff);
}

.login-panel {
  padding: 40px;
  width: 400px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  animation: fadeIn 1s ease-in-out;
}

.login-title {
  font-size: 24px;
  color: #333;
  margin-bottom: 30px;
  text-align: center;
}

.el-form-item {
  margin-bottom: 20px;
}

.el-button {
  transition: background-color 0.3s, transform 0.3s;
}

.el-button:hover {
  transform: translateY(-2px);
  background-color: #5c6bc0;
}

@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(-20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>