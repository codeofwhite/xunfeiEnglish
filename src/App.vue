<template>
  <div class="app">
    <div class="app-container">
      <header>
        <div class="container">
          <NavigationBar></NavigationBar>
        </div>
      </header>

      <RouterView></RouterView>

      <footer class="app-footer">
        <p>© 2024 English Mastery. All rights reserved.</p>
        <router-link to="/ourMission">关于我们</router-link>
      </footer>

      <button @click="scrollToTop" class="scroll-to-top" v-show="showScrollButton">⬆️</button>
    </div>
  </div>
</template>

<script>
import NavigationBar from "@/components/NavigationBar.vue";
import {ref, onMounted, onUnmounted} from "vue";
import {mapActions, mapGetters} from "vuex";

export default {
  components: {NavigationBar},
  setup() {
    const showScrollButton = ref(false);

    const handleScroll = () => {
      showScrollButton.value = window.scrollY > 200;
    };

    const scrollToTop = () => {
      window.scrollTo({top: 0, behavior: "smooth"});
    };

    onMounted(() => {
      document.body.removeChild(document.getElementById('Loading')) // 加载页面完后移除加载动画
      window.addEventListener("scroll", handleScroll);
    });

    onUnmounted(() => {
      window.removeEventListener("scroll", handleScroll);
    });

    return {
      showScrollButton,
      scrollToTop,
    };
  },
  computed: {
    ...mapGetters(['loginDays']),
  },
  methods: {
    ...mapActions(['fetchLoginDays', 'incrementLoginDays']),
    checkLogin() {
      const userEmail = this.$store.state.userEmail; // 假设用户邮箱存储在Vuex状态中
      this.fetchLoginDays(userEmail);
      this.incrementLoginDays(userEmail);
    },
  },
  mounted() {
    this.checkLogin();
  },
};
</script>

<style scoped>
header {
  background-color: #ffffff; /* 深色背景 */
  padding: 5px 0; /* 上下填充 */
}

.container {
  margin: 0 auto; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  justify-content: space-between; /* 元素间隔开 */
}

.app-footer {
  background-color: #00a6be; /* 淡粉色背景 */
  color: #ffffff; /* 粉红色文字 */
  text-align: center;
  padding: 1rem 0;
  font-size: 0.875rem;
  box-shadow: 0 -2px 4px rgba(0, 0, 0, 0.1);
}

.scroll-to-top {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  padding: 10px;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s;
}

.scroll-to-top:hover {
  background-color: #45a049;
}
</style>