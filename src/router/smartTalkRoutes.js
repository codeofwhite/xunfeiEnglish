import SmartTalk from '@/views/smartTalk/SmartTalk.vue';
import ScenarioTalk from "@/views/smartTalk/ScenarioTalk.vue";
import FreeTalk from "@/views/smartTalk/FreeTalk.vue";
import ReadBook from "@/views/smartTalk/ReadBook.vue";
import ReadBookComponent from "@/components/ReadBookComponent.vue";

export default [
    {
        path: '/smartTalk',
        name: 'SmartTalk',
        component: SmartTalk,
        props: true
    },
    {
        path: '/scenarioTalk',
        name: 'scenarioTalk',
        component: ScenarioTalk,
        props: true
    },
    {
        path: '/freeTalk',
        name: 'freeTalk',
        component: FreeTalk,
        props: true
    },
    {
        path: '/readBook',
        name: 'readBook',
        component: ReadBook,
        props: true
    },
    {
        path: '/read-book/:bookId',
        name: 'ReadBook',
        component: ReadBookComponent,
        props: true // 允许将路由参数作为props传递给组件
    }
    // 其他与智能对话相关的路由...
];