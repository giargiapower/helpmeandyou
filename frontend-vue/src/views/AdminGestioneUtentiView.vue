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
									Gestione utenti
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="fetchUtenti">
								</h1>
								<div class="table-responsive">
									<table class="table table-hover">
										<thead>
											<tr>
												<th scope="col">#</th>
												<th scope="col">Utente</th>
												<th scope="col">Stato</th>
												<th scope="col"></th>
											</tr>
										</thead>
										<tbody class="table-group-divider" v-for="(user, index) in listaTuttiUtenti" :key="user.id">
											<tr>
												<th scope="row">{{index + 1}}</th>
												<td>{{ user.nome + ' ' + user.cognome }}</td>
												<td>{{ user.stato }}</td>
												<td class="text-end">
													<button class="btn btn-block btn-primary btn-sm max-width-button" type="button" data-bs-toggle="modal" :data-bs-target="'#btn-verifica-' + user.id">Visualizza</button>
												</td>
											</tr>
										</tbody>
										<verifica-utente-item v-for="utente in listaTuttiUtenti" :key="'modal-' + utente.id" :utente="utente" :fromParent=2 @blocca-utente="bloccaUtente"/>
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

</template>

<script>
	import AdminNavBar from "@/components/AdminNavBar";
	import VerificaUtenteItem from "@/components/VerificaUtenteItem";
	import axios from "axios";

	export default {
		name: "AdminGestioneUtentiView",
		components: {
			AdminNavBar,
			VerificaUtenteItem
		},
		data() {
			return {
				listaTuttiUtenti: []
			}
		},
		mounted() {
			this.fetchUtenti();
		},
		computed() {
			this.bloccaUtente();
		},
		methods: {
			// Funzione che ritorna tutti gli utenti approvati
			async fetchUtenti() {
				await axios.get('/api/amministratore/utenti')
					.then(response => {
						this.listaTuttiUtenti = response.data.map(utente => {
							if (utente.categoria === null) {
								utente.categoria = "Nessuna categoria";
							}
							else
								utente.categoria = utente.categoria.tipo;
							return utente;
						});
						console.log('Lista utenti caricata con successo.')
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
			// Funzione che modifica lo stato di un utente dalla lista di utenti in "bloccato"
			async bloccaUtente (userID) {
				console.log("Ho bloccato l'utente con id " + userID + " dalla lista di utenti.");
				for (let i = 0; i < this.listaTuttiUtenti.length; i++) {
					if (this.listaTuttiUtenti[i].id === userID) {
						this.listaTuttiUtenti[i].stato = "bloccato";
						return;
					}
				}
			}
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

	.max-width-button {
		max-width: 200px;
		margin-right: 100px;
	}

	#reload {
		width: 25px;
		height: 25px;
		margin-left: 20px;
		margin-top: -5px;
		cursor: pointer;
	}
</style>
