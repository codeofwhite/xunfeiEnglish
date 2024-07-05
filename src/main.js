import './assets/styles/main.css'

import {createApp} from 'vue'
import {createPinia} from 'pinia'

import App from './App.vue'
import router from './router'
// UI组件
import ElementPlus from 'element-plus'
// 导入element-plus
import "element-plus/dist/index.css"

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(ElementPlus)

app.mount('#app')
