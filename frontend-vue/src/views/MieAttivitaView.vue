<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<section class="text-center">
			<div class="p-5 bg-image"/>
			<div class="col-lg-10 mx-auto">
				<div class="card mx-4 shadow-5-strong">
					<div class="card-body py-5">
						<div class="row d-flex justify-content-center">
							<div class="col-lg-10">
								<h1 class="fw-bold mb-5">
									Le mie attività
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="fetchRichiesteAccettatePubblicateConcluse">
								</h1>
								<div class="accordion mb-3" id="accordionExample">
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
												Richieste accettate
											</button>
										</h2>
										<div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<div class="table-responsive">
													<table class="table table-hover">
														<thead>
															<tr>
																<th scope="col">#</th>
																<th scope="col">Data</th>
																<th scope="col">Richiedente</th>
																<th scope="col">Categoria</th>
																<th scope="col">Materiale</th>
																<th scope="col"></th>
															</tr>
														</thead>
														<tbody class="table-group-divider">
															<tr v-for="(ric, index) in this.listaRichiesteAccettate" :key="ric.id">
																<th scope="row">{{ index + 1}}</th>
																<td>{{ ric.giorno }}</td>
																<td>{{ ric.pubAccount.nome + ' ' + ric.pubAccount.cognome }}</td>
																<td>{{ ric.categoria.tipo }}</td>
																<td>{{ ric.nomeMateriale }}</td>
																<td>
																	<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal" @click="settaRichiesta(ric.id)">Visualizza</button>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>

									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
												Richieste pubblicate
											</button>
										</h2>
										<div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<table class="table table-hover">
													<thead>
													<tr>
														<th scope="col">#</th>
														<th scope="col">Data</th>
														<th scope="col">Aiutante</th>
														<th scope="col">Categoria</th>
														<th scope="col">Materiale</th>
														<th scope="col"></th>
													</tr>
													</thead>
													<tbody class="table-group-divider">
													<tr v-for="(ric, index) in this.listaRichiestePubblicate" :key="ric.id">
														<th scope="row">{{ index + 1}}</th>
														<td>{{ ric.giorno }}</td>
														<td v-if = "ric.accAccount !== null">{{ ric.accAccount.nome + ' ' + ric.accAccount.cognome }}</td>
														<td v-else>Non ancora accettata</td>
														<td>{{ ric.categoria.tipo }}</td>
														<td>{{ ric.nomeMateriale }}</td>
														<td>
															<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal" @click="settaRichiesta(ric.id)">Visualizza</button>
														</td>
													</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>

									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
												Richieste concluse
											</button>
										</h2>
										<div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<table class="table table-hover">
													<thead>
														<tr>
															<th scope="col">#</th>
															<th scope="col">Data</th>
															<th scope="col">Richiedente</th>
															<th scope="col">Aiutante</th>
															<th scope="col">Categoria</th>
															<th scope="col">Materiale</th>
															<th scope="col"></th>
															<th scope="col"></th>
														</tr>
													</thead>
													<tbody class="table-group-divider">
														<tr  v-for="(ric, index) in this.listaRichiesteConcluse" :key="ric.id">
															<th scope="row">{{ index + 1}}</th>
															<td>{{ ric.giorno }}</td>
															<td>{{ ric.pubAccount.nome + ' ' + ric.pubAccount.cognome }}</td>
															<td v-if = "ric.accAccount !== null">{{ ric.accAccount.nome + ' ' + ric.accAccount.cognome }}</td>
															<td v-else> - </td>
															<td>{{ ric.categoria.tipo }}</td>
															<td>{{ ric.nomeMateriale }}</td>
															<td>
																<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal" @click="settaRichiesta(ric.id)">Visualizza</button>
															</td>
															<td>
																<button v-if="ric.accAccount !== null" class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#segnalaModal" @click="settaRichiesta(ric.id)">Segnala</button>
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
					</div>
				</div>
			</div>
			<div class="bg-color"></div>
		</section>
	</div>

	<!-- visualizzaModal -->
	<div class="modal fade" id="visualizzaModal" data-bs-show="false" data-backdrop="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="visualizzaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form">
					<fieldset>
						<div class="modal-header">
							<legend class="custom-legend">
								<span class="legend-text">Informazioni aggiuntive</span>
								<button type="reset" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
						</div>

						<div class="modal-body">
							<div class="container text-center">
								<div class="row row-cols-1 row-cols-sm-2 row-cols-md-2">
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Nome:</span>
											<span>{{ richiestaSelezionata.pubAccount.nome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Cognome:</span>
											<span>{{ richiestaSelezionata.pubAccount.cognome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Regione:</span>
											<span>{{ richiestaSelezionata.indirizzo.regione }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Provincia:</span>
											<span>{{ richiestaSelezionata.indirizzo.provincia }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Città:</span>
											<span>{{ richiestaSelezionata.indirizzo.citta }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Indirizzo:</span>
											<span>{{ richiestaSelezionata.indirizzo.indirizzo }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Categoria:</span>
											<span>{{ richiestaSelezionata.categoria.tipo }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Materiale:</span>
											<span>{{ richiestaSelezionata.nomeMateriale }}</span>
										</div>
									</div>
									<div class="col col-lg col-md-12 col-sm-12">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Descrizione:</span>
											<span>{{ richiestaSelezionata.descrizione }}</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>


	<!-- segnalaModal -->
	<div class="modal fade" id="segnalaModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="segnalaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form">
					<fieldset>
						<div class="modal-body">
							<legend class="custom-legend">
								<span class="legend-text">Invia una segnalazione</span>
								<button type="reset" class="btn-close btn-lg" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
							<p class="text-muted">Nota: Inserisci il maggior numero di dettagli possibile</p>
							<div class="divider"/>
							<div class="input-group mb-3">
								<span class="input-group-text">Descrizione del problema</span>
								<textarea rows="5" class="form-control" v-model="descrizione" minlength="20" aria-label="Descrizione richiesta" aria-describedby="Descrizione richiesta" title="La descrizione deve avere almeno 20 caratteri" required></textarea>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary mx-4 flex-grow-1" data-bs-dismiss="modal" type="reset" id="chiaro-button">Annulla</button>
							<button class="btn btn-primary mx-4 flex-grow-1" type="button" @click="inviaSegnalazione">Conferma</button>
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
	import axios from "axios";

	export default {
		name: "MieAttivitaView",
		components: {
			BachecaNavBar,
			SuccessShower
		},
		mounted() {
			this.fetchRichiesteAccettatePubblicateConcluse();
		},
		computed() {
			this.fetchRichiesteAccettatePubblicateConcluse();
		},
		data() {
			return {
				successMessage: '',
				listaRichieste: [],
				listaRichiesteAccettate: [],
				listaRichiestePubblicate: [],
				listaRichiesteConcluse: [],
				richiestaSelezionata: {
					descrizione: '',
					giorno: '',
					indirizzo: {
						regione: '',
						provincia: '',
						citta: '',
						indirizzo: ''
					},
					accAccount: {
						nome: '',
						cognome: ''
					},
					pubAccount: {
						nome: '',
						cognome: ''
					},
					categoria: {
						tipo: ''
					},
					nomeMateriale: ''
				},
				descrizione: ''
			}
		},
		methods: {
			// Funzione che seleziona una richiesta precisa
			settaRichiesta(ricId) {
				for (let i = 0; i < this.listaRichieste.length; i++) {
					if (this.listaRichieste[i].id === ricId) {
						this.richiestaSelezionata = this.listaRichieste[i];
						console.log(this.richiestaSelezionata);
						return;
					}
				}
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
			// Funzione che restituisce tutte le richieste "accettate"/"pubblicate"/"concluse" dall'utente loggato
			async fetchRichiesteAccettatePubblicateConcluse() {
				await axios.get('/api/richiesteaiuto/richieste')
					.then(async response => {
						this.listaRichieste = [];
						this.listaRichiesteAccettate = [];
						this.listaRichiestePubblicate = [];
						this.listaRichiesteConcluse = [];
						let listaRichiesteTotali = response.data;
						console.log(listaRichiesteTotali);
						for (let i = 0; i < listaRichiesteTotali.length; i++) {
							listaRichiesteTotali[i].nomeMateriale = await this.findName(listaRichiesteTotali[i].idMateriale);

							if ((listaRichiesteTotali[i].stato !== 'terminata') && (new Date(listaRichiesteTotali[i].giorno) < new Date())) {
								listaRichiesteTotali[i].stato = 'terminata';
								this.terminaRichiesta(listaRichiesteTotali[i].id);
							}
							if (listaRichiesteTotali[i].stato === 'accettata' && listaRichiesteTotali[i].accAccount.id.toString() === this.$store.state.userId) {
								this.listaRichieste.push(listaRichiesteTotali[i]);
								this.listaRichiesteAccettate.push(listaRichiesteTotali[i]);
							} else if ((listaRichiesteTotali[i].stato === 'pubblicata' || listaRichiesteTotali[i].stato === 'accettata') && listaRichiesteTotali[i].pubAccount.id.toString() === this.$store.state.userId) {
								this.listaRichieste.push(listaRichiesteTotali[i]);
								this.listaRichiestePubblicate.push(listaRichiesteTotali[i]);
							} else if (listaRichiesteTotali[i].stato === 'terminata' && (listaRichiesteTotali[i].pubAccount.id.toString() === this.$store.state.userId || (listaRichiesteTotali[i].accAccount !== null && listaRichiesteTotali[i].accAccount.id.toString() === this.$store.state.userId))) {
								this.listaRichieste.push(listaRichiesteTotali[i]);
								this.listaRichiesteConcluse.push(listaRichiesteTotali[i]);
							}
						}
						console.log('accettate: ', this.listaRichiesteAccettate);
						console.log('pubblicate: ', this.listaRichiestePubblicate);
						console.log('concluse: ', this.listaRichiesteConcluse);
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
			// Funzione che termina una richiesta (causa data scaduta) (questa non ha async-await perché non restituisce nulla, basta che venga eseguita)
			terminaRichiesta(ricId) {
				axios.put(`/api/richiesteaiuto/richiesta/termina/${ricId}`)
					.then(response => {
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
			},
			// Funzione che invia una segnalazione
			async inviaSegnalazione() {
				this.settaRichiesta();
				await axios.post('/api/segnalazioni/segnalazione/create',
					{
						titolo: '',
						descrizione: this.descrizione,
						tipologia: 'segnalazione utente',
						creatore : this.$store.state.userId,
						segnalato  : this.richiestaSelezionata.pubAccount.id
					})
					.then(response => {
						console.log(response.data);
						this.successMessage ='Segnalazione inviata!\nFaremo del nostro meglio per prendere opportune precauzioni.';
						this.$refs.succShower.toggle();
						this.$refs.form.reset();
						this.descrizione = '';
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
		background: linear-gradient(to top, #0f971c, #07f49e, #60efff, #60efff);
		/*background: linear-gradient(to top, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15), rgba(92, 109, 248, 0.49), #5c6df8);*/
		animation: fadeInUp 1s ease both;
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
		backdrop-filter: blur(60px);
		border-radius: 45px;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
	}

	.accordion {
		border-radius: 8px;
		--bs-accordion-active-bg: rgba(7, 244, 158, 0.33);
		--bs-accordion-active-color: black;
	}

	.accordion-button {
		padding: 10px 20px;
		font-size: 18px;
		border: none;
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

	#chiaro-button {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#chiaro-button:hover {
		background-color: #0d17c419;
	}

	/*Parte Modal*/
	.modal-content{
		background-color: #ffffff;
		border-radius: 45px;
		padding: 1.5em 3em;
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

	.btn-close {
		align-self: flex-start;
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

	.divider {
		border: none;
		border-top: 1px solid rgba(204, 204, 204, 0.6);
		margin-bottom: 33px;
		margin-top: -10px;
	}

	#reload {
		width: 25px;
		height: 25px;
		margin-left: 20px;
		margin-top: -5px;
		cursor: pointer;
	}
</style>
