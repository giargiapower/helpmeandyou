<template>
	<div class="home-container">
		<admin-nav-bar></admin-nav-bar>
		<section class="text-center">
			<div class="p-5 bg-image"/>
			<div class="col-lg-10 mx-auto">
				<div class="card mx-4 shadow-5-strong">
					<div class="card-body py-5">
						<div class="row d-flex justify-content-center">
							<div class="col-lg-10">
								<h1 class="fw-bold mb-5">
									Segnalazioni utenti
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="fetchSegnalazioni">
								</h1>
								<div class="table-responsive">
									<table class="table table-hover">
										<thead>
											<tr v-if="listaSegnalazioni.length === 0">
												<td class="text-center">Nessuna segnalazione</td>
											</tr>
											<tr v-else>
												<th scope="col">#</th>
												<th scope="col">Utente segnalato</th>
												<th scope="col">Tipologia</th>
												<th scope="col"></th>
											</tr>
										</thead>
										<tbody v-if="!loading" class="table-group-divider">
											<tr v-for="(segnalazione, index) in listaSegnalazioni" :key="segnalazione.id">
												<th scope="row">{{ index + 1 }}</th>
												<td>{{ segnalazione.nomeSegnalato }}</td>
												<td>{{ segnalazione.tipologia }}</td>
												<td>
													<button class="btn btn-block btn-primary btn-sm" type="button" @click="openModal(segnalazione)">Apri</button>
												</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div v-if="loading" class="d-flex justify-content-center">
									<div class="spinner-border text-primary" role="status">
										<span class="visually-hidden">Loading...</span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="bg-color"></div>
		</section>
	</div>

<!--	Modal-->
	<div class="modal fade" id="verificaModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="verificaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<div class="modal-header">
					<legend class="custom-legend">
						<span class="legend-text">Segnalazione: {{ currentSegnalazione.tipologia }}</span>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</legend>
				</div>
				<div class="modal-body">
					<div class="row row-cols-1 row-cols-md-2">
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Dati creatore:</span>
								<span>{{ currentSegnalazione.nomeCreatore }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Dati segnalato:</span>
								<span>{{ currentSegnalazione.nomeSegnalato }}</span>
							</div>
						</div>
						<div v-if="currentSegnalazione.tipologia !== 'segnalazione richiesta'" class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Titolo:</span>
								<span>{{ currentSegnalazione.titolo }}</span>
							</div>
						</div>
						<div class="col col-lg col-md-12 col-sm-12">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Descrizione:</span>
								<span>{{ currentSegnalazione.descrizione }}</span>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" @click="eliminaSegnalazione(currentSegnalazione.id)">Elimina segnalazione</button>
					<button class="btn btn-primary mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" id="chiaro-button">Annulla</button>
					<button class="btn btn-danger mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" @click="bloccaUtente(currentSegnalazione.segnalato)">Blocca utente</button>
				</div>
			</div>
		</div>
	</div>

</template>

<script>
	import AdminNavBar from "@/components/AdminNavBar";
	import axios from 'axios';
	import { Modal } from 'bootstrap';

	export default {
		name: "AdminSegnalazioniView",
		components: {AdminNavBar},
		props: {
			segnalazione: {
				id: {required: true, type: Number},
				titolo: {required: true, type: String},
				descrizione: {required: true, type: String},
				tipologia: {required: true, type: String},
				creatore: {required: true, type: Number},
				segnalato: {required: true, type: Number}
			}
		},
		data() {
			return {
				listaSegnalazioni: [],
				currentSegnalazione: {},
				loading: true
			}
		},
		mounted() {
			this.fetchSegnalazioni();
		},
		methods: {
			// Funzione che carica la lista delle segnalazioni
			async fetchSegnalazioni() {
				this.loading = true;
				await axios.get('/api/segnalazioni/segnalazione/list')
					.then(async response => {
						this.listaSegnalazioni = response.data;
						for (let i = 0; i < this.listaSegnalazioni.length; i++) {
							this.listaSegnalazioni[i].nomeCreatore = await this.infoUtente(this.listaSegnalazioni[i].creatore);
							this.listaSegnalazioni[i].nomeSegnalato = await this.infoUtente(this.listaSegnalazioni[i].segnalato);
						}
						console.log('Segnalazioni caricate con successo.');
						this.loading = false;
					})
					.catch(error => {
						if (error.response) {
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if (error.request) {
							console.error('No response received:', error.request);
						} else {
							console.error('Error:', error.message);
						}
					})
			},
			// Restituisce il nome dell'utente segnalato
			async infoUtente(idUtente) {
				// console.log("Vorrei le info dell'utente con id: " + idUtente);
				let utenteDaRestituire = "";
				await axios.get(`/api/amministratore/utente/${idUtente}`)
					.then(response => {
						utenteDaRestituire = response.data.nome + " " + response.data.cognome;
					})
					.catch(error => {
						if (error.response) {
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if (error.request) {
							console.error('No response received:', error.request);
						} else {
							console.error('Error:', error.message);
						}
					})
				return utenteDaRestituire;
			},
			// Funzione che blocca un utente
			async bloccaUtente(segnalatoId) {
				await axios.put(`/api/amministratore/blocca/${segnalatoId}`)
					.then(response => {
						console.log(response.data);

						// Elimina tutte le segnalazioni dell'utente bloccato
						for (let i = 0; i < this.listaSegnalazioni.length; i++) {
							if (this.listaSegnalazioni[i].segnalato === segnalatoId) {
								this.eliminaSegnalazione(this.listaSegnalazioni[i].id);
								this.listaSegnalazioni = this.listaSegnalazioni.filter(segnalato => segnalato !== segnalatoId);
							}
						}
					})
					.catch(error => {
						if (error.response) {
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if (error.request) {
							console.error('No response received:', error.request);
						} else {
							console.error('Error:', error.message);
						}
					})
			},
			openModal(segnalazione) {
				this.currentSegnalazione = segnalazione;
				// Apri il modal
				const modal = new Modal(document.getElementById("verificaModal"));
				modal.show();
			},
			// Funzione che elimina le segnalazione arrivate
			async eliminaSegnalazione(segnalazioneId) {
				await axios.delete(`/api/segnalazioni/segnalazione/delete/${segnalazioneId}`)
					.then(response => {
						console.log(response.data);
						this.listaSegnalazioni = this.listaSegnalazioni.filter(segnalazione => segnalazione.id !== segnalazioneId);
					})
					.catch(error => {
						if (error.response) {
							console.error('Response Data:', error.response.data);
							console.error('Response Status:', error.response.status);
							console.error('Response Headers:', error.response.headers);
						} else if (error.request) {
							console.error('No response received:', error.request);
						} else {
							console.error('Error:', error.message);
						}
					})
			}
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background: linear-gradient(to top, rgba(89, 92, 255, 0.8), rgba(155, 248, 244, 0.8), rgba(155, 248, 244, 0.8));
		animation: fadeInUp 1s ease;
	}

	h1 {
		font-weight: bold;
		font-size: 2.5em;
		margin-bottom: 20px;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
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

	.bg-image {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
		height: 45vh;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.bg-color {
		background: linear-gradient(to bottom, rgba(13, 23, 196, 0), rgba(13, 23, 196, 0));
		height: 50px;
	}

	.shadow-5-strong {
		margin-top: -100px;
		border-radius: 45px;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
	}

	.btn-primary {
		padding: 5px 20px;
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

	.btn-danger {
		margin: 5px 0;
		padding: 5px 20px;
		font-size: 17px;
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	.btn-danger:hover {
		background-color: #a90000;
	}

	.btn-close {
		align-self: flex-start;
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

	tbody {
		text-align: center;

	}

	#reload {
		width: 25px;
		height: 25px;
		margin-left: 20px;
		margin-top: -5px;
		cursor: pointer;
	}
</style>
