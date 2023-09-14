<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<div class="hero-section">

			<div class="hero-content row">
				<h1 class="hero-title typed-title"></h1>
			</div>

			<div class="container">
				<form ref="form" class="row">
					<fieldset class="row align-items-center">
						<div class="col-md-2">
							<legend class="custom-legend">Filtra ricerca per:</legend>
						</div>

						<div class="col-md-8">
							<div class="row">
								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Regione:</span>
										<input type="text" class="form-control">
									</div>
								</div>

								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Provincia:</span>
										<input type="text" class="form-control">
									</div>
								</div>

								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Città:</span>
										<input type="text" class="form-control">
									</div>
								</div>

								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Data:</span>
										<input type="date" class="form-control" :min="minDate" :max="maxDate" v-model="startDate">
										<span class="input-group-text"> - </span>
										<input type="date" class="form-control" :min="startDate" v-model="maxDate">
									</div>
								</div>
							</div>
						</div>

						<div class="col-md-2">
							<button class="btn btn-primary btn-block" type="button" @click="filterRequests">Cerca</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>

		<div v-if="richieste.length !== 0" class = "card-container">
			<div class="row row-cols-1 row-cols-md-3 g-4">
				<richiesta-item v-for="ric in richieste" :key="ric.id" :richiesta="ric"></richiesta-item>
			</div>
		</div>
		<div v-else>
			<p>Non è attualmente presente alcuna richiesta!</p>
		</div>
	</div>

</template>

<script>
	import BachecaNavBar from "@/components/BachecaNavBar";
	import RichiestaItem from "@/components/RichiestaItem";
	import axios from 'axios';
	import Typed from 'typed.js';

	export default {
		name: "BachecaView",
		components: {
			RichiestaItem,
			BachecaNavBar
		},
		data() {
			return {
				richieste: [],
				typedTitle: null,
				minDate: this.calculateMinDate(),
				maxDate: null,
				startDate: this.calculateMinDate(),
				idUtente: parseInt(this.$store.state.userId)
			}
		},
		methods: {
			async fetchRichieste() {
				await axios.get('/api/richiesteaiuto/richieste')
					.then(response => {
						this.richieste = response.data;
						console.log('Richieste aggiungete con successo');
						console.log(this.richieste);
					})
					.catch(error => {
						console.log(error)
					})
			},
			async filterRequests() {
				// console.log(this.$store.state.userId);
			},
			startTypedEffects() {
				this.typedTitle = new Typed('.typed-title', {
					strings: ["Bacheca"],		//&amp; equivale a &
					typeSpeed: 50,
					showCursor: false
				});
			},
			stopTypedEffects() {
				if (this.typedTitle) {
					this.typedTitle.destroy();
					this.typedTitle = null;
				}
			},
			calculateMinDate() {
				const currentDate = new Date();
				const minYear = currentDate.getFullYear();
				const minMonth = currentDate.getMonth() + 1;
				const minDay = currentDate.getDate();

				return `${minYear}-${minMonth.toString().padStart(2, '0')}-${minDay.toString().padStart(2, '0')}`;
			}
		},
		beforeRouteEnter(to, from, next) {
			next(vm => {
				vm.startTypedEffects(); // Avvia gli effetti Typed all'ingresso nella pagina
			});
		},
		beforeRouteLeave(to, from, next) {
			this.stopTypedEffects(); // Interrompe gli effetti Typed all'uscita dalla pagina
			next();
		},
		mounted() {
			this.fetchRichieste();
		},
		unmounted() {
			this.stopTypedEffects(); // Interrompe gli effetti Typed quando il componente viene distrutto
		},
		computed() {
			this.fetchRichieste();
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		animation: fadeInUp 1s ease;
		background: linear-gradient(to bottom, #0f971c, #0f971c, #07f49e, #60efff);
	}

	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
		height: 65vh;
		align-items: center;
		justify-content: center;
	}

	.hero-content {
		text-align: center;
		color: white;
		margin-top: 60px;
		margin-bottom: 30px;
		display: inline-block;
		text-shadow: 4px 4px 8px rgba(0, 0, 0, 0.5); /* ombra al testo */
	}

	.hero-title {
		font-size: 65px;
		margin-bottom: 20px;
	}

	/* Stile per le scritte a schermo intero */
	@media (min-width: 767.5px){
		.container {
			margin-top: 100px;
			margin-bottom: 0;
		}
	}

	@media (max-width: 767.5px) {
		.container {
			margin-top: 5px;
			margin-bottom: 0;
		}
		.hero-section {
			height: 85vh;
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

	button {
		margin: 20px 0; /* Aggiunge spazio verticale prima e dopo il bottone */
		padding: 10px 20px;
		font-size: 18px;
		background-color: #5c6df8;
		border: none;
		color: white;
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	button:hover {
		background-color: #0d17c4ff;
	}

	.custom-legend{
		display: flex;
		align-items: center;
		color: white;
	}

	.card-container {
		margin-top: 30px;
		margin-right: 50px;
		margin-left: 50px;
	}

</style>
