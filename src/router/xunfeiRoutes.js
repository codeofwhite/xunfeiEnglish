import ChatAI from "@/views/xunfei/ChatAI.vue";
import SpeechScore from "@/views/xunfei/SpeechScore.vue";
import VoiceToWord from "@/views/xunfei/SpeechToWord.vue";
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";

export default [
    {
        path: '/chatAI',
        name: 'ChatAI',
        component: ChatAI,
        props: true
    },
    {
        path: '/speechScore',
        name: 'SpeechScore',
        component: SpeechScore,
        props: true
    },
    {
        path: '/speechToWord',
        name: 'VoiceToWord',
        component: VoiceToWord,
        props: true
    },
    {
        path: '/speechSynthesis',
        name: 'SpeechSynthesis',
        component: SpeechSynthesis,
        props: true
    },
]