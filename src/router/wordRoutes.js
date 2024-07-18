import WordList from "@/views/word/WordList.vue";
import UnknownWord from "@/views/word_book/UnknownWord.vue";
import MasteredWord from "@/views/word_book/MasteredWord.vue";
import FavoriteWord from "@/views/word_book/FavoriteWord.vue";
import WordPageNav from "@/views/word/WordPageNav.vue";
import LevelList from "@/views/word/LevelList.vue";
import ChapterList from "@/views/word/ChapterList.vue";
import CategoryList from "@/views/word/CategoryList.vue";

export default [
    {
        path: '/wordPageNav',
        name: 'WordPageNav',
        component: WordPageNav,
        props: true,
        children: [
            {
                path: '/vbIndex',
                name: 'vbIndex',
                component: () => import('@/views/word/CategoryList.vue')
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
            {path: '/', name: 'CategoryList', component: CategoryList, props: true},
            {path: '/category/:categoryId', name: 'ChapterList', component: ChapterList, props: true},
            {path: '/category/:categoryId/chapter/:chapterId', name: 'LevelList', component: LevelList, props: true},
            {
                path: '/category/:categoryId/chapter/:chapterId/level/:levelId',
                name: 'WordList',
                component: WordList,
                props: true
            }
        ]
    },

]