<!--TODO: DA TOGLIERE. Secondo me sarebbe meglio usare un'unica navbar e visualizzare cose diverse a seconda del ruolo dell'utente (loggato o meno)-->

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
						<router-link :to="creationLinkBacheca" class="nav-link">Bacheca</router-link>
					</li>
					<li class="nav-item">
						<router-link :to="creationLinkCreaRichiesta" class="nav-link">Crea richiesta</router-link>
					</li>
					<li class="nav-item">
						<router-link :to="creationLinkLeMieAttivita" class="nav-link">Le mie attività</router-link>
					</li>
					<li class="nav-item">
						<router-link :to="creationLinkProfilo" class="nav-link">Profilo</router-link>
					</li>
					<li class="nav-item">
						<router-link to="/" class="nav-link">Logout</router-link>
					</li>
				</ul>
			</div>
		</div>
	</nav>
</template>

<script>
	export default {
		name: "BachecaNavBar",
		props: ['idUtente'],
		data() {
			return {
				isNavOpen: false // Inizialmente la navbar è chiusa
			};
		},
		methods: {
			toggleNav() {
				this.isNavOpen = !this.isNavOpen;
			}
		},
		computed: {
			creationLinkBacheca() {
				// Generate the router link with the idUtente parameter
				return `/accedi-registrati/bacheca/${this.idUtente}`;
			},
			creationLinkCreaRichiesta() {
				// Generate the router link with the idUtente parameter
				return `/accedi-registrati/bacheca/crea-richiesta/${this.idUtente}`;
			},
			creationLinkLeMieAttivita() {
				// Generate the router link with the idUtente parameter
				return `/accedi-registrati/bacheca/mie-attivita/${this.idUtente}`;
			},
			creationLinkProfilo() {
				// Generate the router link with the idUtente parameter
				return `/accedi-registrati/bacheca/profilo/${this.idUtente}`;
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
		background: linear-gradient(to right, rgba(118, 236, 183, 0.8), rgb(233, 250, 245));
	}
</style>
