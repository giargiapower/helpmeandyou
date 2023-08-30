import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BachecaView from "@/views/BachecaView.vue"
import AdminView from '@/views/AdminView.vue'
import AdminHomeView from "@/views/AdminHomeView";
import MagazzinoView from "@/views/MagazzinoView";
import MagazzinoHomeView from "@/views/MagazzinoHomeView";

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView
  },
  {
    path: '/magazzino',
    name: 'magazzino',
    component: MagazzinoView
  },
  {
    path: '/magazzino/magazzino-home',
    name: 'magazzino-home',
    component: MagazzinoHomeView
  },
  {
    path: '/admin/admin-home',
    name: 'admin-home',
    component: AdminHomeView
  },
  {
    path: '/admin/admin-segnalazioni',
    name: 'admin-segnalazioni',
    component: () => import('../views/AdminSegnalazioniView.vue')
  },
  {
    path: '/admin/admin-gestione-utenti',
    name: 'admin-gestione-utenti',
    component: () => import('../views/AdminGestioneUtentiView.vue')
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
    path:'/accedi-registrati/bacheca/crea-richiesta',
    name: 'crea-richiesta',
    component: () => import('../views/CreaRichiestaView.vue')
  },
  {
    path: '/accedi-registrati/bacheca/mie-attivita',
    name: 'mie-attivita',
    component: () => import('../views/MieAttivitaView.vue')
  },
  {
    path: '/accedi-registrati/bacheca/profilo',
    name: 'profilo',
    component: () => import('../views/ProfiloView.vue')
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
