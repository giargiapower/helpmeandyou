<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<div class="hero-section">
			<div class="hero-content">
				<form @submit.prevent="onSubmit" ref="form">
					<fieldset>
						<legend>Crea richiesta d'aiuto</legend>
						<p class="text-muted">Nota: Cerca di essere il più possibile dettagliato nella richiesta</p>
						<div class="input-group mb-3">
							<span class="input-group-text">Data</span>
							<input type="date" class="form-control" v-model="selectedData" :min="minDate" aria-label="Data" aria-describedby="Data" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Regione</span>
							<input type="text" class="form-control" v-model="indirizzo.regione" aria-label="Regione" aria-describedby="Regione" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Provincia</span>
							<input type="text" class="form-control" v-model="indirizzo.provincia" minlength="3" aria-label="Provincia" aria-describedby="Provincia" title="Inserisci il nome per esteso" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Città</span>
							<input type="text" class="form-control" v-model="indirizzo.citta" aria-label="Città" aria-describedby="Città" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Indirizzo</span>
							<input type="text" class="form-control" v-model="indirizzo.indirizzo" aria-label="Indirizzo" aria-describedby="Indirizzo" id="indirizzo" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Categoria</span>
							<select class="form-select" aria-label="Categoria" required v-model="selectedCategoria">
								<option disabled value="">Scegli una categoria...</option>
								<option v-for="cat in categorie" :key="cat.id">{{ cat }}</option>
							</select>
							<span class="input-group-text text-muted custom-tooltip" id="infoCategoria">
								<i class="bi bi-question-circle"></i>
							</span>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Materiale</span>
							<select class="form-select" aria-label="Categoria" required v-model="selectedMateriale">
								<option disabled value="">Scegli un materiale...</option>
								<option value="nessunaMateriale">Nessun materiale</option>
								<option v-for="materiale in this.uniqueMateriali()" :key="materiale.id">{{
										materiale.nome
									}}
								</option>
							</select>
							<span class="input-group-text text-muted custom-tooltip" id="infoMateriale">
								<i class="bi bi-question-circle"></i>
							</span>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Descrizione</span>
							<textarea rows="5" class="form-control" v-model="descrizione" minlength="20" aria-label="Descrizione richiesta" aria-describedby="Descrizione richiesta" title="La descrizione deve avere almeno 20 caratteri" required></textarea>
						</div>

						<div class="d-flex justify-content-center">
							<button class="btn btn-primary mx-2 flex-grow-1" type="reset" id="chiaro-button" @click="onReset">Annulla</button>
							<button class="btn btn-primary mx-2 flex-grow-1" type="submit">Pubblica</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<SuccessShower ref="succShower" :message="successMessage"/>

</template>

<script>
	import BachecaNavBar from "@/components/BachecaNavBar";
	import SuccessShower from "@/components/SuccessShower";
	import axios from 'axios';

	export default {
		name: "CreaRichiestaView",
		components: {
			BachecaNavBar,
			SuccessShower
		},
		data() {
			return {
				descrizione: '',
				indirizzo: {regione: '', provincia: '', citta: '', indirizzo: ''},
				idMateriale : null,
				pubAccount : { id : null },
				categoria : { tipo : '' },
				selectedCategoria: '',
				selectedMateriale: '',
				selectedData: '',
				textValue: '',
				listaRichieste: [],
				listaMateriali: [],
				categorie: [],
				minDate: this.calculateMinDate(),
				idUtente: this.$store.state.userId,
				successMessage: ''
			}
		},
		methods: {
			// Funzione che annulla la procedura di creazione della richiesta
			onReset() {
				this.$refs.form.reset();
				this.$router.push('./');
			},
			// Funzione che crea la richiesta di aiuto
			// TODO: la chiamata funziona, la richiesta viene accettata, compare nella bacheca,
			// per sistemare bisogna: togliere un materiale dal magazzino (=> stato non disponibile)
			// se ci sono più occorrenze di quel materiale nel magazzino
			// l'utente non deve vedere in bacheca la propria richiesta, solo nella vista "Mie Attività"
			// ultimo: aggiungere più categorie !!!
			async onSubmit() {
				let idSelectedMateriale;
				this.listaMateriali.forEach(materiale => {
					if(this.selectedMateriale === materiale.nome) {
						idSelectedMateriale = materiale.id;
					}
				});
				await axios.post('/api/richiesteaiuto/richiesta/crea',
					{
						descrizione: this.descrizione,
						giorno: this.selectedData,
						indirizzo: {
							regione: this.indirizzo.regione,
							provincia: this.indirizzo.provincia,
							citta: this.indirizzo.citta,
							indirizzo: this.indirizzo.indirizzo
						},
						idMateriale : idSelectedMateriale,
						pubAccount : { id : this.idUtente },
						categoria : { tipo : this.selectedCategoria }
					})
					.then(response => {
						console.log(response.data);
						this.successMessage ='Richiesta pubblicata!\nPuoi consultare le prenotazioni attive nella sezione "Le mie attività".';
						this.$refs.succShower.toggle();
						this.$refs.form.reset();
						this.$router.push('./');
					})
					.catch(error => {
						// TODO: servirebbe un alert anche qui
						if(error.response) {
							// The request was made and the server responded with a status code
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if(error.request) {
							// The request was made but no response was received
							console.error('No response received:', error.request);
						} else {
							// Something happened in setting up the request that triggered an error
							console.error('Error:', error.message);
						}
					})
			},
			// Funzione che calcola la lista dei materiali non duplicati
			uniqueMateriali() {
				const uniqueMateriali = [];
				const countMateriali = {};

				for (const item of this.listaMateriali) {
					if (!countMateriali[item.nome]) {
						countMateriali[item.nome] = 1;
						uniqueMateriali.push(item);
					} else {
						countMateriali[item.nome]++;
					}
				}

				for (const item of uniqueMateriali) {
					item.count = countMateriali[item.nome];
				}

				return uniqueMateriali;
			},
			// Funzione che calcola lista dei materiali
			async fetchMateriali() {
				await axios.get('/api/magazzini/1/materiali')
					.then(response => {
						this.listaMateriali = response.data;
						console.log('Materiali caricati!');
					})
					.catch(errore => {
						console.log(errore);
					})
			},
			// Funzione per restituire tutte le categorie
			async fetchCategorie() {
				await axios.get('/api/amministratore/categorie')
					.then(response => {
						const listaElem = response.data;
						listaElem.forEach(elem => {
							this.categorie.push(elem.tipo);
						})
						console.log('Categorie caricate con successo.')
					})
					.catch(error => {
						// TODO: servirebbe un alert anche qui
						if(error.response) {
							// The request was made and the server responded with a status code
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if(error.request) {
							// The request was made but no response was received
							console.error('No response received:', error.request);
						} else {
							// Something happened in setting up the request that triggered an error
							console.error('Error:', error.message);
						}
					})
			},
			// Funzione che calcola le categorie da assegnare a una richiesta in modo univoco
			// async fetchCategorieRichieste() {
			// 	await axios.get('/api/richiesteaiuto/richieste')
			// 		.then(response => {
			// 			this.listaRichieste = response.data;
			// 			this.listaRichieste.forEach(ric => {
			// 				this.categorie.push({id: this.uniqueId, nome: ric.categoria.tipo});
			// 			});
			//
			// 			console.log('Categorie caricate con successo!');
			// 			console.log(this.categorie);
			// 		})
			// 		.catch(errore => {
			// 			console.log(errore);
			// 		})
			// },
			calculateMinDate() {
				const currentDate = new Date();
				const minYear = currentDate.getFullYear();
				const minMonth = currentDate.getMonth() + 1;
				const minDay = currentDate.getDate();

				return `${minYear}-${minMonth.toString().padStart(2, '0')}-${minDay.toString().padStart(2, '0')}`;
			}
		},
		mounted() {
			this.fetchMateriali();
			this.uniqueMateriali();
			this.fetchCategorie();
			// this.fetchCategorieRichieste();
		},
		computed() {
			this.fetchMateriali();
			this.uniqueMateriali();
			// this.fetchCategorieRichieste();
		}
	}
</script>

<style scoped>
	.home-container {
		animation: fadeInUp 1s ease both;	/* Applica l'animazione a tutta la pagina */
	}

	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
		/*height: 100vh;*/
		/*display: flex;*/
		/*align-items: center;*/
		/*justify-content: center;*/
	}

	.hero-content {
		background-color: #ffffff;
		margin: 3.5em;
		border-radius: 45px;
		padding: 1.5em 3em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	legend {
		font-weight: bold;
		font-size: 2em;
		color: black;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
	}

	p {
		margin-bottom: 2em;
	}

	.mb-3 {
		text-align: start;
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

	#chiaro-button {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#chiaro-button:hover {
		background-color: #0d17c419;
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

	/*Parte Registrazione*/
	form{
		background-color: #ffffff;	  /* Aggiunge stile di sfondo per tutta la pagina */
	}

	#indirizzo{
		border-top-right-radius: 0.4em;
		border-bottom-right-radius: 0.4em;
	}

	#numeroCivico{
		border-top-left-radius: 0.4em;
		border-bottom-left-radius: 0.4em;
	}

	.custom-tooltip {
		position: relative;
	}

	.custom-tooltip:hover::before {
		position: absolute;
		/* Posiziona il tooltip sopra l'elemento genitore */
		bottom: 100%;
		left: auto;
		right: 0;
		transform: translateX(50%);
		background-color: #333;
		color: #fff;
		padding: 5px;
		border-radius: 5px;
		opacity: 0; /* Rendi il tooltip invisibile di default */
		visibility: hidden;
		transition: opacity 0.2s, visibility 0.2s;
		white-space: normal;
		min-width: 300px;
		z-index: 9999;
	}

	.custom-tooltip:hover::before {
		opacity: 1; /* Rendi il tooltip visibile al passaggio del mouse */
		visibility: visible;
	}

	#infoCategoria:hover::before {
		content: "Seleziona la categoria alla quale la tua richiesta apparterrà: solo le persone con questa qualifica possono visualizzare e accettare questo tipo di richiesta";
	}

	#infoMateriale:hover::before {
		content: "Seleziona un materiale che potrebbe servire per svolgere questa richiesta";
	}

	/* Media query per spostare il tooltip sulla sinistra in caso di schermo stretto */
	@media screen and (max-width: 970px) {
		.custom-tooltip:hover::before {
			transform: translateX(0%);
		}
	}

</style>
