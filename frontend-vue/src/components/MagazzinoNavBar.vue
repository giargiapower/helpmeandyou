<template>
	<nav class="navbar navbar-expand-lg">
		<div class="container-fluid">
			<router-link to="/" class="navbar-brand">
				<img src="@/assets/logo_app-removebg.png" alt="HelpMe&You" class="logo-image">
			</router-link>
			<ul class="navbar-nav ml-auto">
				<li class="nav-item">
					<router-link to="/magazzino" class="nav-link" @click="logout">Logout</router-link>
				</li>
			</ul>
		</div>
	</nav>
</template>

<script>
	export default {
		name: "MagazzinoNavBar",
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
				this.$store.commit('logoutMag');
			}
		}
	}
</script>

<style scoped>
	* {
		font-size: 17px;
	}

	.logo-image {
		max-width: 200px; /* Regola la larghezza massima dell'immagine */
		height: auto; /* L'altezza verrà ridimensionata in base all'aspetto originale */
	}

	.navbar {
		background: linear-gradient(to left, rgba(230, 11, 9, 0.8), rgba(237, 227, 66, 0.8));
	}

	.navbar-nav {
		--bs-nav-link-color: white;
		--bs-nav-link-hover-color: black;
	}

	nav a.router-link-exact-active {
		color: white;
		font-weight: bold;
		text-decoration: underline;
	}

	.nav-link {
		margin-right: 20px;
	}
</style>
