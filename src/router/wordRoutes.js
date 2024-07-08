import WordList from "@/views/word/WordList.vue";
import UnknownWord from "@/views/word_book/UnknownWord.vue";
import MasteredWord from "@/views/word_book/MasteredWord.vue";
import FavoriteWord from "@/views/word_book/FavoriteWord.vue";

export default [
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
]