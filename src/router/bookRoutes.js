import ReadBook from "@/views/bookTalk/ReadBook.vue";
import ReadBookComponent from "@/components/talkBook/ReadBookComponent.vue";
import BookChapter from "@/components/talkBook/BookChapter.vue";

export default [
    {
        path: '/readBook',
        name: 'readBook',
        component: ReadBook,
        props: true
    },
    {
        path: '/book-chapter/:bookId',
        name: 'BookChapter',
        component: BookChapter,
        props: true // 允许将路由参数作为props传递给组件
    },
    {
        path: '/book-chapter/:bookId/read-book/:chapterId',
        name: 'ReadBookComponent',
        component: ReadBookComponent,
        props: true // 允许将路由参数作为props传递给组件
    }
];