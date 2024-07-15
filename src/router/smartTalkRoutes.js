import SmartTalk from '@/views/smartTalk/SmartTalk.vue';
import ScenarioTalk from "@/views/smartTalk/ScenarioTalk.vue";
import FreeTalk from "@/views/smartTalk/FreeTalk.vue";

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
    // 其他与智能对话相关的路由...
];