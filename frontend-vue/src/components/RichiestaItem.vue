<template>
	<div class="col">
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">{{ richiesta.giorno }}</h5>
				<hr class="line-separator">
				<p class="card-text">{{ richiesta.descrizione }}</p>
				<button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Aiuta</button>
			</div>
		</div>
	</div>

	<!-- visualizzaModal -->
	<div class="modal fade" id="visualizzaModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="visualizzaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form">
					<fieldset>
						<div class="modal-header">
							<legend class="custom-legend">
								<span class="legend-text">Dai il tuo aiuto!</span>
								<button type="button" class="btn btn-outline-danger" @click.prevent="onSegnala">Segnala</button>
							</legend>
						</div>
						<div class="modal-body">
							<div class="container text-center">
								<div class="row row-cols-1 row-cols-md-2">
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Nome:</span>
											<span>{{ richiesta.pubAccount.nome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Cognome:</span>
											<span>{{ richiesta.pubAccount.cognome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Regione:</span>
											<span>{{ richiesta.indirizzo.regione }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Provincia:</span>
											<span>{{ richiesta.indirizzo.provincia }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Città:</span>
											<span>{{ richiesta.indirizzo.citta }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Indirizzo:</span>
											<span>{{ richiesta.indirizzo.indirizzo }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Categoria:</span>
											<span>{{ richiesta.categoria.tipo }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Materiale:</span>
											<span>{{ richiesta.idMateriale }}</span>
										</div>
									</div>
									<div class="col col-lg col-md-12 col-sm-12">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Descrizione:</span>
											<span>{{ richiesta.descrizione }}</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" id="chiaro-button">Annulla</button>
							<button class="btn btn-primary mx-4 flex-grow-1" type="button" @click="onAccetta">Accetta</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<SuccessShower ref="succShower" :message="successMessage"/>
</template>

<script>
	import axios from "axios";
	import SuccessShower from "@/components/SuccessShower";

	export default {
		name: "RichiestaItem",
		components: {
			SuccessShower
		},
		props: {
			richiesta: {required: true, type: Object},
			// id: {required: true, type: Number},
			// idUtenteRichiesta: {required: true, type: Number},
			// descrizione: {required: true, type: String},
			// nome: {required: true, type: String},
			// cognome: {required: true, type: String},
			// giorno: {required: true, type: String},
			// regione: {required: true, type: String},
			// provincia: {required: true, type: String},
			// citta: {required: true, type: String},
			// categoria: {required: true, type: String},
			// indirizzo: {required: true, type: String},
			// idMateriale: {required: true, type: Number},
			// idUtenteLoggato: {required: true, type: Number}
		},
		data() {
			return {
				materialeRichiesta: {},
				successMessage: ''
			}
		},
		methods: {
			// Funzione che carica dal server i materiali presenti nel magazzino per mostrarli nel componente
			async fetchMateriali() {
				await axios.get('/api/magazzini/1/materiali')
					.then(response => {
						response.data.forEach(element => {
							if (element.id === this.idMateriale)
								this.materialeRichiesta = element;
						});
						console.log('Materiali caricati con successo!');
					})
					.catch(error => {
						console.log(error);
					})
			},
			async onSegnala() {
				await axios.post('/api/segnalazioni/segnalazione/create',
					{
						titolo: '',
						descrizione: this.descrizione,
						tipologia: 'segnalazione richiesta',
						creatore: this.idUtenteLoggato,
						segnalato: this.idUtenteRichiesta
					})
					.then(response => {
						console.log(response.data);
						this.successMessage ='Grazie per la tua segnalazione!\nLa richiesta verrà presa in visione il prima possibile.';
						this.$refs.succShower.toggle();
						setTimeout(function() {
							window.location.href = `/accedi-registrati/bacheca/`;
						}, 3000);
					})
					.catch(errors => {
						console.log(errors);
					})
			},
			// Funzione che permette di accettare una richiesta
			async onAccetta() {
				await axios.put(`/api/richiesteaiuto/richiesta/accetta/${this.id}/${this.idUtenteLoggato}`)
					.then(response => {
						console.log(response.data);
						this.successMessage = 'Grazie per la tua disponibilità!\nPuoi consultare le tue prenotazioni attive nella sezione "Le mie attività."';
						this.$refs.succShower.toggle();
						setTimeout(function() {
							window.location.href = `/accedi-registrati/bacheca/`;
						}, 3000);
					})
					.catch(error => {
						// alert('Impossibile accettare la richiesta.\nRiprova più tardi.');
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
			}
		},
		mounted() {
			this.fetchMateriali()
		},
		computed() {
			this.fetchMateriali()
		}
	}
</script>

<style scoped>
	.card {
		margin-bottom: 50px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
		border-width: 1px;
		border-radius: 20px;
	}

	.card-title {
		font-weight: bold;
	}


	.btn-primary {
		margin: 5px 0; /* Aggiunge spazio verticale prima e dopo il bottone */
		padding: 5px 20px;
		font-size: 17px;
		background-color: #5c6df8;
		border: none;
		color: white;
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	.btn-primary:hover {
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

	.btn-outline-danger {
		padding: 5px 20px;
		align-self: flex-start;
		font-size: 17px;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	.line-separator {
		background-color: #ccccccff;
		flex-grow: 5;
		height: 1px;
	}

	/*Parte Modal*/
	.modal-content{
		background-color: #ffffff;
		border-radius: 45px;
		padding: 1em 2em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	form{
		background-color: #ffffff;
	}

	.custom-legend{
		display: flex;
		align-items: center;
	}

	.legend-text {
		flex-grow: 1;
		text-align: center;
		font-size: 32px;
		font-weight: bold;
		color: black;
	}

	p {
		font-size: 16px;
	}

	/* Parte tabella informazioni aggiuntive */
	.list-group-item {
		border: none;
	}

	.col {
		padding: 10px;
		color: black;
		font-size: 16px;
	}
</style>
