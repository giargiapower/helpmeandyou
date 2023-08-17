<template>
	<nav class="navbar navbar-expand-lg" style="background-color: #e9faf5">
		<div class="container">
			<router-link to="/" class="navbar-brand">
				<img src="@/assets/logo_app-removebg.png" alt="HelpMe&You" class="logo-image">
			</router-link>
			<button
				class="navbar-toggler"
				type="button"
				data-bs-toggle="collapse"
				data-bs-target="#navbarNav"
				aria-controls="navbarNav"
				aria-expanded="false"
				aria-label="Toggle navigation"
			>
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item">
						<router-link to="/" class="nav-link">Home</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/accedi-registrati" class="nav-link">Accedi/Registrati</router-link>  <!-- Collega HomeView a AccediRegistratiView -->
					</li>
					<li class="nav-item">
						<router-link to="/dona-ora" class="nav-link">Donazione</router-link>
					</li>
				</ul>
			</div>
		</div>
	</nav>

</template>

<script>
	export default {
		name: "NavBar"
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
</style>
