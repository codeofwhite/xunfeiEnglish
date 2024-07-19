import {fileURLToPath, URL} from 'node:url'

import {defineConfig} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [
        vue(),
        vueJsx(),
    ],
    resolve: {
        alias: {
            '@': fileURLToPath(new URL('./src', import.meta.url))
        }
    },
    server: {
        hmr: true, // 热更新Hot Module Replacement，效率更高
        port: 80,
        proxy: {
            '/api': {
                target: 'http://114.132.52.232:8003',
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, ''),
            },
            '/bpi': {
                target: 'http://spark-api.cn-huabei-1.xf-yun.com', // 目标API
                changeOrigin: true, // 必须设置为true
                rewrite: (path) => path.replace(/^\/bpi/, '')
            },
            '/v2': {
                target: 'https://itrans.xfyun.cn/', // 目标API
                changeOrigin: true, // 必须设置为true
            },
        }
    },
})
