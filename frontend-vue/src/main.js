import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Vuex from 'vuex';
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap/dist/js/bootstrap.js'
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import 'bootstrap-icons/font/bootstrap-icons.css';
import vue3GoogleLogin from 'vue3-google-login'
import * as bootstrap from 'bootstrap';
window.bootstrap = bootstrap;

const store = new Vuex.Store({
	state: {
		userId: localStorage.getItem('userId') || null,
		lastLoginTime: localStorage.getItem('lastLoginTime') || null,
		magLoggedIn: localStorage.getItem('magLoggedIn') || false,
		adminLoggedIn: localStorage.getItem('adminLoggedIn') || false
	},
	mutations: {
		setUserId(state, userId) {
			state.userId = userId;
			state.lastLoginTime = new Date().getTime().toString();
			localStorage.setItem('lastLoginTime', new Date().getTime().toString());
			localStorage.setItem('userId', userId);
		},
		logout(state) {
			state.userId = null;
			state.lastLoginTime = null;
			localStorage.removeItem('userId');
			localStorage.removeItem('lastLoginTime');
		},
		setMagLoggedIn(state) {
			state.magLoggedIn = 'true';
			localStorage.setItem('magLoggedIn', 'true');
		},
		logoutMag(state) {
			state.magLoggedIn = false;
			localStorage.removeItem('magLoggedIn');
		},
		setAdminLoggedIn(state) {
			state.adminLoggedIn = 'true';
			localStorage.setItem('adminLoggedIn', 'true');
		},
		logoutAdmin(state) {
			state.adminLoggedIn = false;
			localStorage.removeItem('adminLoggedIn');
		}
	},
});

// Imposta un timer per il logout automatico dopo 30 minuti
setInterval(() => {
	if (store.state.userId && store.state.lastLoginTime) {
		console.log('Sono passati 10 minuti dal login');
		const lastLoginTime = store.state.lastLoginTime;
		const currentTime = new Date().getTime();
		const sessionTimeout = 30 * 60 * 1000; // 30 minuti in millisecondi (30 * 60 * 1000)

		if (currentTime - parseInt(lastLoginTime, 10) > sessionTimeout) {
			store.commit('logout');
			window.location.href = '/accedi-registrati';
		}
	}
	else {
		console.log('Non è stato effettuato il login');
	}
}, 300000); // Ogni 5 minuti (300000 millisecondi)


export default createApp(App)
    .use(router)
	.use(store)
    .use(vue3GoogleLogin, {
		clientId: '944204629673-grd6fhq4g1ehpmmkjpvl3rgn04gntoe8.apps.googleusercontent.com',
	})
    .mount('#app')
