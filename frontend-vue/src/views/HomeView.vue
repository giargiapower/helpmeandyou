<template>
	<div class="home-container">
		<home-nav-bar/>
		<div class="hero-section">
			<div class="hero-content">
				<div class="logo">
					<img src="@/assets/logo_app-removebg.png" alt="HelpMe&You Logo" class="img-fluid">
				</div>
				<h1 class="hero-title typed-title"></h1>
				<p class="hero-slogan typed-text"></p>
			</div>
		</div>
		<div class="section">
			<h2>Obiettivo</h2>
			<p>
				Aiutiamo a connettere coloro che vogliono aiutare con chi ha bisogno di aiuto.
				Il nostro sito offre una piattaforma per la condivisione di risorse, conoscenze
				e supporto reciproco.
			</p>
			<p>
				Unisciti a noi nella nostra missione di rendere il mondo un posto migliore!
			</p>
		</div>
		<hr class="divider"> <!-- Linea di separazione tra le sezioni -->
		<div class="section">
			<h2>Dona ora</h2>
			<p>Per sostenere il nostro progetto e contribuire alla nostra missione, effettua una donazione.</p>
			<router-link to="/dona-ora">
				<button class="hero-button">Dona ora</button>
			</router-link>
		</div>
	</div>
</template>

<script>
	// @ is an alias to /src
	import HomeNavBar from "@/components/HomeNavBar";
	import Typed from 'typed.js';

	export default {
		name: 'HomeView',
		components: {
			HomeNavBar
		},
		data() {
			return {
				typedTitle: null,
				typedText: null
			};
		},
		beforeRouteEnter(to, from, next) {
			next(vm => {
				vm.startTypedEffects(); // Avvia gli effetti Typed all'ingresso nella pagina
			});
		},
		beforeRouteLeave(to, from, next) {
			this.stopTypedEffects(); // Interrompi gli effetti Typed all'uscita dalla pagina
			next();
		},
		methods: {
			startTypedEffects() {
				this.typedTitle = new Typed('.typed-title', {
					strings: ["Benvenuti su HelpMe&amp;You!"],		//&amp; equivale a &
					typeSpeed: 50,
					showCursor: false
				});

				this.typedText = new Typed('.typed-text', {
					strings: ["Non possiamo aiutare tutti, ma tutti possono aiutare e richiedere aiuto!"],
					typeSpeed: 40,
					showCursor: false
				});
			},
			stopTypedEffects() {
				if (this.typedTitle) {
					this.typedTitle.destroy();
					this.typedTitle = null;
				}

				if (this.typedText) {
					this.typedText.destroy();
					this.typedText = null;
				}
			}
		},
		unmounted() {
			this.stopTypedEffects(); // Interrompi gli effetti Typed quando il componente viene distrutto
		}
	}
</script>


<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background-color: #e9faf5;	  /* Aggiunge stile di sfondo per tutta la pagina */
		animation: fadeInUp 1s ease;	/* Applica l'animazione a tutta la pagina */
	}
	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.hero-content {
		text-align: center;
		color: #ffffff;
		background-color: rgba(118, 236, 183, 0.6); /* colore #76ecb7 con opacità al 50% */
		border-radius: 45px;
		padding: 0.5em 3em;
		display: inline-block;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* ombra al testo */
	}

	.hero-title {
		font-family: 'Great Vibes', cursive;
		font-size: 36px;
		margin-bottom: 20px;
	}

	.hero-slogan {
		font-style: italic;
		font-size: 20px;
		margin-bottom: 10px;
	}

	.hero-button {
		padding: 10px 20px;
		font-size: 18px;
		background-color: #76ecb7;
		border: none;
		color: white;
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* ombra al bottone */
	}

	.hero-button:hover {
		background-color: #008664;
	}

	.logo {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin-bottom: 10px;
	}

	.img-fluid {
		width: 40em;
	}

	h2 {
		font-family: "Droid Sans Mono Dotted", serif;
		font-size: 2.5em;
		margin-bottom: 20px;
	}

	.section {
		padding: 40px;
	}

	.section p {
		font-size: 1.2em;
	}

	@media (max-width: 768px) {
		/* Stili per la visualizzazione su dispositivi mobili */
		.logo-image {
			max-width: 400px;
		}
	}

	@keyframes fadeInUp {
		from {
			transform: translateY(20px);
			opacity: 0;
		}
		to {
			transform: translateY(0);
			opacity: 1;
		}
	}

	.divider {
		border: none;
		border-top: 3px solid #008664;
		margin: 20px 0;
	}
</style>
