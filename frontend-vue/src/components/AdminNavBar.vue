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
						<router-link to="/admin/admin-home" class="nav-link">Approvazione utenti</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/admin/admin-segnalazioni" class="nav-link">Segnalazioni utenti</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/admin/admin-gestione-utenti" class="nav-link">Gestione utenti</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/admin" class="nav-link" @click="logout">Logout</router-link>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script>
	export default {
		name: "AdminNavBar",
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
				this.$store.commit('logoutAdmin');
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
		background: linear-gradient(to left, rgba(0, 97, 255, 0.8), rgba(96, 239, 255, 0.8));
	}

	.navbar-nav {
		--bs-nav-link-color: white;
		--bs-nav-link-hover-color: #bbf7fd;
	}

	nav a.router-link-exact-active {
		color: white;
		font-weight: bold;
		text-decoration: underline;
	}
</style>
