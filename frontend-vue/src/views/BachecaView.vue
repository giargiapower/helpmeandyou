<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<div class="hero-section">

			<div class="hero-content row">
				<h1 class="hero-title typed-title"></h1>
			</div>
			<img src="@/assets/reload-white.png" alt="reload" id="reload" @click="fetchRichieste">

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
										<input type="text" class="form-control" v-model="regioneFilter">
									</div>
								</div>

								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Provincia:</span>
										<input type="text" class="form-control" v-model="provinciaFilter">
									</div>
								</div>

								<div class="col-md-6 mb-3">
									<div class="input-group">
										<span class="input-group-text">Città:</span>
										<input type="text" class="form-control" v-model="cittaFilter">
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
							<button class="btn btn-primary btn-block" type="submit" @click.prevent="filterRequests">Cerca</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>


		<div v-if="richieste.length === 0">
			<p id="no-richieste">Nessuna richiesta trovata!</p>
		</div>
		<div v-else class = "card-container">
			<div class="row row-cols-1 row-cols-md-3 g-4">
				<div class="col" v-for="richiesta in richieste" :key="richiesta.id">
					<div class="card">
						<div class="card-body">
							<h5 class="card-title">
								{{ richiesta.giorno }}
								<div v-if="richiesta.categoria !== null">
									{{ richiesta.categoria.tipo }}
								</div>
							</h5>
							<hr class="line-separator">
							<p class="card-text">{{ richiesta.descrizione }}</p>
							<button class="btn btn-primary" type="button" data-bs-toggle="modal" :data-bs-target="'#btn-verifica-' + richiesta.id">Aiuta</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<richiesta-item v-for="richiesta in richieste" :key="'modal-' + richiesta.id" :richiesta="richiesta"></richiesta-item>
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
				loggedUser: this.$store.state.userId,
				regioneFilter: '',
				provinciaFilter: '',
				cittaFilter: '',
				tutteRichieste: []
			}
		},
		mounted() {
			this.fetchRichieste();
		},
		unmounted() {
			this.stopTypedEffects(); // Interrompe gli effetti Typed quando il componente viene distrutto
		},
		computed() {
			this.fetchRichieste();
		},
		methods: {
			async fetchRichieste() {
				await axios.get(`/api/richiesteaiuto/richiestepubblicate/not/${this.loggedUser}`)
					.then(async response => {
						this.richieste = response.data;
						for (let i = 0; i < this.richieste.length; i++) {
							this.richieste[i].nomeMateriale = await this.findName(this.richieste[i].idMateriale);
						}
						this.tutteRichieste = this.richieste;
						console.log('Richieste aggiungete con successo');
						console.log(this.richieste);
					})
					.catch(error => {
						console.log(error)
					})
			},
			// Funzione che recupera il nome del materiale dato il suo id
			async findName(idMateriale) {
				let nomeMateriale = '';
				await axios.get(`/api/magazzini/nome/1/${idMateriale}`)
					.then(response => {
						nomeMateriale = response.data;
					})
					.catch(error => {
						console.log(error);
					})
				return nomeMateriale;
			},
			// Funzione che si occupa di filtrare le richieste in base ai parametri inseriti dall'utente
			async filterRequests() {
				let richiesteFiltrate = [];
				for (let i = 0; i < this.tutteRichieste.length; i++) {
					if ((this.regioneFilter === '' || this.tutteRichieste[i].indirizzo.regione === this.regioneFilter)
						&& (this.provinciaFilter === '' || this.tutteRichieste[i].indirizzo.provincia === this.provinciaFilter)
						&& (this.cittaFilter === '' || this.tutteRichieste[i].indirizzo.citta === this.cittaFilter)
						&& (this.startDate <= this.tutteRichieste[i].giorno)
						&& (this.maxDate === null || this.maxDate >= this.tutteRichieste[i].giorno))
						richiesteFiltrate.push(JSON.parse(JSON.stringify(this.tutteRichieste[i])));
				}
				this.richieste = richiesteFiltrate;
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
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		animation: fadeInUp 1s ease;
		background: linear-gradient(to top, #0f971c, #07f49e, #60efff, #60efff);
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

	.card {
		margin-bottom: 50px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
		border-width: 1px;
		border-radius: 20px;
	}

	.card-title {
		font-weight: bold;
	}

	.line-separator {
		background-color: #ccccccff;
		flex-grow: 5;
		height: 1px;
	}

	#reload {
		width: 50px;
		height: 50px;
		margin-left: 20px;
		margin-top: -30px;
		cursor: pointer;
	}

	#no-richieste {
		background: linear-gradient(to top, #0f971c, #07f49e);
		color: white;
		font-size: 30px;
		margin-bottom: -5px;
		padding: 30px;
	}
</style>
