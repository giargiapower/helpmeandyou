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
									Approvazione utenti
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="fetchUtentiDaApprovare">
								</h1>
								<div class="table-responsive">
									<table class="table table-hover">
										<thead>
											<tr v-if="listaUtentiDaApprovare.length === 0">
												<td class="text-center">Nessun utente da approvare o in aggiornamento</td>
											</tr>
											<tr v-else>
												<th scope="col">#</th>
												<th scope="col">Utente</th>
												<th scope="col">Stato account</th>
												<th scope="col"></th>
											</tr>
										</thead>
										<tbody class="table-group-divider">
											<tr v-for="(utente, index) in listaUtentiDaApprovare" :key="utente.id">
												<th scope="row">{{ index + 1 }}</th>
												<td>{{ utente.nome + ' ' + utente.cognome }}</td>
												<td v-if="utente.stato === 'da_approvare'">Approvare</td>
												<td v-else>Aggiornamento</td>
												<td>
													<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" :data-bs-target="'#btn-verifica-' + utente.id">Verifica</button>
												</td>
											</tr>
										</tbody>
										<verifica-utente-item v-for="utente in listaUtentiDaApprovare" :key="'modal-' + utente.id" :utente="utente" :fromParent=1 @rimuovi-figlio="rimuoviFiglio" @blocca-utente="bloccaUtente"/>
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

	<SuccessShower ref="succShower" :message="successMessage"/>

</template>

<script>
	import axios from "axios";
	import AdminNavBar from "@/components/AdminNavBar";
	import SuccessShower from "@/components/SuccessShower";
	import VerificaUtenteItem from "@/components/VerificaUtenteItem";

	export default {
		name: "AdminHomeView",
		components: {
			VerificaUtenteItem,
			AdminNavBar,
			SuccessShower
		},
		data() {
			return {
				listaUtentiDaApprovare: [],
				successMessage: ''
			}
		},
		computed() {
			this.fetchUtentiDaApprovare();
		},
		mounted() {
			this.fetchUtentiDaApprovare();
		},
		methods: {
			// Funzione che calcola la lista di utenti da approvare
			async fetchUtentiDaApprovare() {
				await axios.get('/api/amministratore/da_approvare/list')
					.then(response => {
						this.listaUtentiDaApprovare = response.data.map(utente => {
							if (utente.categoria === null) {
								utente.categoria = "Nessuna categoria";
							}
							else
								utente.categoria = utente.categoria.tipo;
							return utente;
						});
						console.log('Lista utenti caricata con successo.')
					})
					.catch(errors => {
						console.error(errors)
					})
			},
			// Funzione che rimuove un utente dalla lista di utenti da approvare
			async rimuoviFiglio (userID) {
				console.log('Rimuovo utente con id ' + userID + ' dalla lista di utenti da approvare.')
				this.successMessage ='Utente approvato!\nPuoi consultare la lista di tutti gli utenti nella sezione "Gestione utenti".';
				this.$refs.succShower.toggle();
				setTimeout(function() {
					window.location.href = '/admin/admin-home/';
				}, 3000);
			},
			// Funzione che blocca un utente
			async bloccaUtente (userID) {
				console.log('Blocco utente con id ' + userID + '.')
				this.successMessage ='Utente bloccato con successo!\nPuoi consultare la lista di tutti gli utenti nella sezione "Gestione utenti".';
				this.$refs.succShower.toggle();
				setTimeout(function() {
					window.location.href = '/admin/admin-home/';
				}, 3000);
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

	#reload {
		width: 25px;
		height: 25px;
		margin-left: 20px;
		margin-top: -5px;
		cursor: pointer;
	}
</style>
