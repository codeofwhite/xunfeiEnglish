import AboutPageNav from "@/views/about/AboutPageNav.vue";
import ContactUs from "@/views/about/ContactUs.vue"
import OurMission from "@/views/about/OurMission.vue"
import TeachApproach from "@/views/about/TeachApproach.vue"

export default [
    {
        path: "/aboutPageNav",
        name: 'AboutPageNav',
        component: AboutPageNav,
        children: [
            {
                path: '/contactUs',
                name: 'ContactUs',
                component: ContactUs,
                props: true
            },
            {
                path: '/ourMission',
                name: 'OurMission',
                component: OurMission,
                props: true
            },
            {
                path: '/teachApproach',
                name: 'TeachApproach',
                component: TeachApproach,
                props: true
            },
        ]

    }
]