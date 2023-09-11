import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import BachecaView from "@/views/BachecaView.vue"
import AdminView from '@/views/AdminView.vue'
import AdminHomeView from "@/views/AdminHomeView";
import MagazzinoView from "@/views/MagazzinoView";
import MagazzinoHomeView from "@/views/MagazzinoHomeView";
import app from "@/main";

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
		component: MagazzinoHomeView,
		beforeEnter(to, from, next) {
			if (app.$store.state.mapLoggedIn === false)
				next({name: 'magazzino'});
			else
				next();
		}
	},
	{
		path: '/admin/admin-home',
		name: 'admin-home',
		component: AdminHomeView,
		beforeEnter(to, from, next) {
			if (app.$store.state.adminLoggedIn === false)
				next({name: 'admin'});
			else
				next();
		}
	},
	{
		path: '/admin/admin-segnalazioni',
		name: 'admin-segnalazioni',
		component: () => import('../views/AdminSegnalazioniView.vue'),
		beforeEnter(to, from, next) {
			if (app.$store.state.adminLoggedIn === false)
				next({name: 'admin'});
			else
				next();
		}
	},
	{
		path: '/admin/admin-gestione-utenti',
		name: 'admin-gestione-utenti',
		component: () => import('../views/AdminGestioneUtentiView.vue'),
		beforeEnter(to, from, next) {
			if (app.$store.state.adminLoggedIn === false)
				next({name: 'admin'});
			else
				next();
		}
	},
	{
		path: '/accedi-registrati',
		name: 'accedi-registrati',
		component: () => import('../views/AccediRegistratiView.vue')
	},
	{
		path: '/accedi-registrati/registrazione',
		name: 'registrazione',
		component: () => import('../views/RegistrazioneView.vue')
	},
	{
		path: '/dona-ora',
		name: 'dona-ora',
		component: () => import('../views/DonazioniView.vue')
	},
	{
		path: '/accedi-registrati/bacheca',
		name: 'bacheca',
		component: BachecaView,
		beforeEnter(to, from, next) {
			if (app.$store.state.userId === null)
				next({name: 'accedi-registrati'});
			else
				next();
		}
	},
	{
		path: '/accedi-registrati/bacheca/crea-richiesta',
		name: 'crea-richiesta',
		component: () => import('../views/CreaRichiestaView.vue'),
		beforeEnter(to, from, next) {
			if (app.$store.state.userId === null)
				next({name: 'accedi-registrati'});
			else
				next();
		}
	},
	{
		path: '/accedi-registrati/bacheca/mie-attivita',
		name: 'mie-attivita',
		component: () => import('../views/MieAttivitaView.vue'),
		beforeEnter(to, from, next) {
			if (app.$store.state.userId === null)
				next({name: 'accedi-registrati'});
			else
				next();
		}
	},
	{
		path: '/accedi-registrati/bacheca/profilo',
		name: 'profilo',
		component: () => import('../views/ProfiloView.vue'),
		beforeEnter(to, from, next) {
			if (app.$store.state.userId === null)
				next({name: 'accedi-registrati'});
			else
				next();
		}
	}
]

const router = createRouter({
	history: createWebHistory(process.env.BASE_URL),
	routes
})

export default router
