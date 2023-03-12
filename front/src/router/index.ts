import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import { VueElement } from 'vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/auth/join',
      name: 'join',
      component: () => import('../views/auth/JoinView.vue'),
      meta: {unauthorized: true}
    },
    {
      path: '/auth/signIn',
      name: 'signIn',
      component: () => import('../views/auth/SignInView.vue'),
      meta: {unauthorized: true}
    },
    {
      path: '/auth/reissue',
      name: 'reissue',
      component: () => import('../views/auth/Reissue.vue')
    },

    {
      path: '/members/welcome',
      name: 'welcome',
      component: () => import('../views/member/WelcomeView.vue')
    },
    {
      path: '/members/info',
      name: 'memberInfo',
      component: () => import('../views/member/MemberInfoView.vue')
    }
  ]
})

export default router
