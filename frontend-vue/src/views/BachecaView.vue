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
							<button class="btn btn-primary btn-block">Cerca</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>



<!--		TODO> questo v-for sotto ha qualche problema. Se crei più richieste te ne accorgi. Inoltre bisognerebbe filtrare le richieste affinché un utente loggato non possa vedere le sue.-->
		<div class = "card-container">
			<div class="row row-cols-1 row-cols-md-3 g-4">
				<div class="col" v-for="item in richieste" :key="item.id">
					<richiesta-item v-for="nominativo in nominativi" :key="nominativo.id" :id="item.id" :nome="nominativo.nome" :cognome="nominativo.cognome" :descrizione="item.descrizione" :giorno="item.giorno" :regione="nominativo.regione" :provincia="nominativo.provincia" :citta="nominativo.citta" :indirizzo="nominativo.indirizzo" :categoria="item.categoria.tipo" :id-materiale="item.idMateriale">
						<!-- TODO: chiarire data richiesta effettuata / data richiesta accettata / data richiesta completata -->
					</richiesta-item>
				</div>
			</div>
		</div>
	</div>





<!--	<div class="bacheca">-->
<!--		<bacheca-nav-bar></bacheca-nav-bar>-->
<!--		<h1>Bacheca</h1>-->
<!--		<div class="lista-richieste">-->
<!--			<div class="row row-cols-1 row-cols-md-3 g-4">-->
<!--				<div class="col" v-for="item in richieste" :key="item.id">-->
<!--					<richiesta-item v-for="nominativo in nominativi" :key="nominativo.id"-->
<!--									:id="item.id"-->
<!--									:nome="nominativo.nome"-->
<!--									:cognome="nominativo.cognome"-->
<!--									:descrizione="item.descrizione"-->
<!--									:data="item.giorno"-->
<!--									:id-materiale="item.idMateriale"> &lt;!&ndash; TODO: chiarire data richiesta effettuata /-->
<!--                                                       data richiesta accettata /-->
<!--                                                       data richiesta completata &ndash;&gt;-->
<!--					</richiesta-item>-->
<!--				</div>-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->
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
		methods: {
			async fetchRichieste() {
				await axios.get('/api/richiesteaiuto/richieste')
					.then(response => {
						this.richieste = response.data;

						// Assegnazione dei nomi, cognomi e i componenti dell'indirizzo alla proprietà "nominativi"
						this.nominativi = this.richieste.map(richiesta => ({
							id: richiesta.id,
							nome: richiesta.pubAccount.nome,
							cognome: richiesta.pubAccount.cognome,
							regione: richiesta.indirizzo.regione,
							provincia: richiesta.indirizzo.provincia,
							citta: richiesta.indirizzo.citta,
							indirizzo: richiesta.indirizzo.indirizzo
						}));

						console.log('Richieste aggiungete con successo');
						console.log(this.richieste)
					})
					.catch(error => {
						console.log(error)
					})
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
		data() {
			return {
				richieste: [],
				nominativi: [],
				typedTitle: null,
				minDate: this.calculateMinDate(),
				maxDate: null,
				startDate: this.calculateMinDate()
			}
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
		mounted() {
			this.fetchRichieste();
		},
		unmounted() {
			this.stopTypedEffects(); // Interrompi gli effetti Typed quando il componente viene distrutto
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
		background: linear-gradient(to bottom, #a7fada, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15));
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
		margin-top: 50px;
		margin-bottom: 30px;
		padding: 1em 3em;
		padding-top: 2em;
		display: inline-block;
		background-color: rgba(118, 236, 183, 0.6); /* colore #76ecb7 con opacità al 50% */
		border-radius: 45px;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5); /* ombra al testo */
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
		/*clip-path: polygon(0% 0%, 100% 0%, 100% 75%, 75% 75%, 75% 100%, 50% 75%, 0% 75%);*/
	}

	.hero-title {
		font-family: 'Great Vibes', cursive;
		font-size: 36px;
		margin-bottom: 20px;
		/*clip-path: polygon(0 0, 100% 0, 100% 50%, 75% 50%, 75% 70%, 50% 50%, 0 50%);*/
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
