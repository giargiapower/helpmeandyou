import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BachecaView from "@/views/BachecaView.vue"

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/accedi-registrati',
    name: 'accedi-registrati',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AccediRegistratiView.vue')
  },
  {
    path: '/accedi-registrati/registrazione',
    name: 'registrazione',
    component: () => import('../views/RegistrazioneView.vue')
  },
  {
    path: '/accedi-registrati/bacheca',
    name: 'bacheca',
    component: BachecaView
  },
  {
    path: '/dona-ora',
    name: 'dona-ora',
    component: () => import('../views/DonazioniView.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
