import UserHelp from "@/views/userpage/UserHelp.vue";
import UserAchievements from "@/views/userpage/UserAchievements.vue";
import UserFavorites from "@/views/userpage/UserFavorites.vue";
import UserProfile from "@/views/userpage/UserProfile.vue";
import LoginPage from "@/views/user/LoginPage.vue";
import RegisterPage from "@/views/user/RegisterPage.vue";
import Test from "@/views/test/Test.vue";
import UserPage from "@/views/userpage/UserPageNav.vue";
import Introduction from "@/views/userpage/help/Introduction.vue";
import Setup from "@/views/userpage/help/Setup.vue";
import FAQ from "@/views/userpage/help/FAQ.vue";

export default [
    {
        path: '/userPage',
        name: 'UserPage',
        component: UserPage,
        children: [
            {
                path: '/userHelp',
                name: 'UserHelp',
                component: UserHelp,
                props: true,
                children: [
                    {
                        path: 'introduction',
                        component: Introduction
                    },
                    {
                        path: 'setup',
                        component: Setup
                    },
                    {
                        path: 'faq',
                        component: FAQ
                    }
                ]
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
    },
    {
        path: '/login',
        name: 'Login',
        component: LoginPage,
        props: true
    },
    {
        path: '/register',
        name: 'Register',
        component: RegisterPage,
        props: true
    }
]