import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import WordList from '@/views/word/WordList.vue'
import UnknownWord from "@/views/word_book/UnknownWord.vue";
import ChatAI from "@/views/xunfei/ChatAI.vue"
import SpeechScore from "@/views/xunfei/SpeechScore.vue";
import VoiceToWord from "@/views/xunfei/SpeechToWord.vue";
import SpeechSynthesis from "@/views/xunfei/SpeechSynthesis.vue";
import FavoriteWord from "@/views/word_book/FavoriteWord.vue";
import MasteredWord from "@/views/word_book/MasteredWord.vue";
import UserFavorites from "@/views/userpage/UserFavorites.vue";
import UserHelp from "@/views/userpage/UserHelp.vue";
import UserAchievements from "@/views/userpage/UserAchievements.vue";
import UserProfile from "@/views/userpage/UserProfile.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/vbIndex',
      name: 'vbIndex',
      component: () => import('@/views/word/VB.vue')
    },
    {
      path: '/word-list/:wordListId',
      name: 'WordList',
      component: WordList,
      props: true
    },
    {
      path: '/unKnownWord',
      name: 'UnknownWord',
      component: UnknownWord,
      props: true
    },
    {
      path: '/masteredWord',
      name: 'MasteredWord',
      component: MasteredWord,
      props: true
    },
    {
      path: '/favoriteWord',
      name: 'FavoriteWord',
      component: FavoriteWord,
      props: true
    },
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
    {
      path: '/userHelp',
      name: 'UserHelp',
      component: UserHelp,
      props: true
    },
    {
      path: '/userAchievements',
      name: 'UserAchievements',
      component: UserAchievements,
      props: true
    },
    {
      path: '/userFavorites',
      name: 'UserFavorites',
      component: UserFavorites,
      props: true
    },
    {
      path: '/userProfile',
      name: 'UserProfile',
      component: UserProfile,
      props: true
    },
  ]
})

export default router
