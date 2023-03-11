import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/members/welcome',
      name: 'welcome',
      component: HomeView
    },
    {
      path: '/auth/join',
      name: 'join',
      component: () => import('../views/auth/JoinView.vue')
    },
    {
      path: '/auth/signIn',
      name: 'signIn',
      component: () => import('../views/auth/SignInView.vue')
    }
  ]
})

export default router
