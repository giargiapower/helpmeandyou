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
		magLoggedIn: localStorage.getItem('magLoggedIn') || false,
		adminLoggedIn: localStorage.getItem('adminLoggedIn') || false
	},
	mutations: {
		setUserId(state, userId) {
			state.userId = userId;
			localStorage.setItem('userId', userId);
		},
		logout(state) {
			state.userId = null;
			localStorage.removeItem('userId');
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
	}
});

export default createApp(App)
    .use(router)
	.use(store)
    .use(vue3GoogleLogin, {
		clientId: '944204629673-grd6fhq4g1ehpmmkjpvl3rgn04gntoe8.apps.googleusercontent.com',
	})
    .mount('#app')
