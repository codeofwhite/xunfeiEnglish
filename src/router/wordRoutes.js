import WordList from "@/views/word/WordList.vue";
import UnknownWord from "@/views/word_book/UnknownWord.vue";
import MasteredWord from "@/views/word_book/MasteredWord.vue";
import FavoriteWord from "@/views/word_book/FavoriteWord.vue";
import WordPageNav from "@/views/word/WordPageNav.vue";

export default [
    {
        path: '/word-list/:wordListId',
        name: 'WordList',
        component: WordList,
        props: true
    },
    {
        path: '/wordPageNav',
        name: 'WordPageNav',
        component: WordPageNav,
        props: true,
        children: [
            {
                path: '/vbIndex',
                name: 'vbIndex',
                component: () => import('@/views/word/VB.vue')
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
    },

]