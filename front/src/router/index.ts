import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/join',
      name: 'join',
      component: () => import('../views/auth/JoinView.vue')
    },
    {
      path: '/signIn',
      name: 'signIn',
      component: () => import('../views/auth/SignInView.vue')
    }
  ]
})

export default router
