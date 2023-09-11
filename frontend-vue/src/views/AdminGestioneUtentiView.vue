<template>
	<div class="home-container">
		<admin-nav-bar></admin-nav-bar>
		<section class="text-center">
			<div class="p-5 bg-image"/>
			<div class="col-lg-6 mx-auto">
				<div class="card mx-4 shadow-5-strong">
					<div class="card-body py-5">
						<div class="row d-flex justify-content-center">
							<div class="col-lg-10">
								<h1 class="fw-bold mb-5">Gestione utenti</h1>
								<div class="table-responsive">
									<table class="table table-hover">
										<thead>
										<tr>
											<th scope="col">#</th>
											<th scope="col">Utente</th>
											<th scope="col"></th>
										</tr>
										</thead>
										<tbody class="table-group-divider" v-for="(user, index) in listaUtentiApprovati" :key="user.id">
										<tr>
											<th scope="row">{{index + 1}}</th>
											<td>{{ user.nome + ' ' + user.cognome }}</td>
											<td class="text-end">
												<button class="btn btn-block btn-primary btn-sm" type="button" @click="openModal(user)">Visualizza</button>
											</td>
										</tr>
										</tbody>
									</table>
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
						<span class="legend-text">Utente: {{ currentUser.nome + ' ' + currentUser.cognome }}</span>
						<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
					</legend>
				</div>
				<div class="modal-body">
					<div class="row row-cols-1 row-cols-md-2">
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Nome:</span>
								<span>{{currentUser.nome}}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Cognome:</span>
								<span>{{ currentUser.cognome }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
<!--								TODO: togliere perché non esiste-->
								<span class="fw-bold me-3">Data di nascita:</span>
								<span>...</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Numero di telefono:</span>
								<span>{{ currentUser.telefono }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Email:</span>
								<span>{{ currentUser.email }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Domicilio(?):</span>
								<span>{{ currentUser.indirizzo }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Documento d'identità:</span>
								<span>{{ currentUser.path_documento }}</span>
							</div>
						</div>
						<div class="col">
							<div class="list-group-item d-flex">
								<span class="fw-bold me-3">Curriculum:</span>
								<span>{{ currentUser.path_curriculum }}</span>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" id="chiaro-button">Chiudi</button>
					<button class="btn btn-danger mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" @click="bloccaUtente(currentUser.id)">Blocca utente</button>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	import AdminNavBar from "@/components/AdminNavBar";
	import { Modal } from "bootstrap";
	import axios from "axios";

	export default {
		name: "AdminGestioneUtentiView",
		components: {AdminNavBar},
		methods: {
			openModal(user) {
				this.currentUser = user;
				// Apri il modal
				const modal = new Modal(document.getElementById("verificaModal"));
				modal.show();
			},
			// Funzione che ritorna tutti gli utenti approvati
			async fetchUtenti() {
				await axios.get('/api/amministratore/allUtenti')
					.then(response => {
						this.listaUtentiApprovati = response.data;
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
			// Funzione che blocca un utente
			// TODO: funziona ma bisogna sistemare il fatto che il currentUser risulti null
			// errore presente anche in un altro file
			async bloccaUtente(segnalatoId) {
				await axios.put(`/api/amministratore/blocca/${segnalatoId}`)
					.then(response => {
						// TODO: un messaggio che mostri che l'utente x sia bloccato
						console.log(response.data);
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
		},
		data() {
			return {
				listaNomiUtenti: [],
				currentUser: null,
				listaUtentiApprovati: []
			}
		},
		mounted() {
			this.fetchUtenti();
		},
		computed() {
			this.bloccaUtente();
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background: linear-gradient(to top, #595cff, #9bf8f4, #9bf8f4);
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
</style>
