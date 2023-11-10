
<template>
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<router-link to="/" class="navbar-brand">
				<img src="@/assets/logo_app-removebg.png" alt="HelpMe&You" class="logo-image">
			</router-link>
			<button class="navbar-toggler" type="button" @click="toggleNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" :class="{ show: isNavOpen }" id="navbarNav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<router-link to="/accedi-registrati/bacheca" class="nav-link">Bacheca</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/accedi-registrati/bacheca/crea-richiesta" class="nav-link">Crea richiesta</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/accedi-registrati/bacheca/mie-attivita" class="nav-link">Le mie attività</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/accedi-registrati/bacheca/profilo" class="nav-link">Profilo</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/" class="nav-link" @click="logout">Logout</router-link>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script>
	export default {
		name: "BachecaNavBar",
		data() {
			return {
				isNavOpen: false // Inizialmente la navbar è chiusa
			};
		},
		methods: {
			toggleNav() {
				this.isNavOpen = !this.isNavOpen;
			},
			logout() {
				// Chiamo la funzione logout
				this.$store.commit('logout');
			}
		}
	};
</script>

<style scoped>
	*{
		font-size: 17px;

	}
	.logo-image {
		max-width: 200px; /* Regola la larghezza massima dell'immagine */
		height: auto; /* L'altezza verrà ridimensionata in base all'aspetto originale */
	}
	.navbar {
		background: linear-gradient(to left, #0f971c, #07f49e, #60efff);

	}
	.navbar-nav {
		--bs-nav-link-color: white;
		--bs-nav-link-hover-color: #07f49e;
	}

	nav a.router-link-exact-active {
		color: white;
		font-weight: bold;
		text-decoration: underline;
	}
</style>
