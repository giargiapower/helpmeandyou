<template>
	<div class="home-container">
		<magazzino-nav-bar/>
		<section class="text-center">
			<div class="p-5 bg-image"/>
			<div class="col-lg-10 mx-auto">
				<div class="card mx-4 shadow-5-strong">
					<div class="card-body py-5">
						<div class="row d-flex justify-content-center">
							<div class="col-lg-10">
								<h1 class="fw-bold mb-5">
									Gestione magazzino: Torino
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="chiamaFunzioni">
								</h1>
								<p>
									Denaro disponibile: {{ saldoMagazzino }}
									<img src="@/assets/reload.png" alt="reload" id="reload" @click="calcSaldoMagazzino">
								</p>

								<p v-if="listaMateriali.length === 0">Magazzino vuoto!</p>
								<button v-if="listaMateriali.length === 0" class="btn btn-success" type="button" data-bs-toggle="modal" data-bs-target="#aggiungiMaterialeModal">Aggiungi nuovo materiale</button>

								<div class="table-responsive">
									<table class="table table-hover" v-if="listaMateriali.length !== 0">
										<thead>
											<tr>
												<th scope="col">#</th>
												<th scope="col">Nome materiale</th>
												<th scope="col">Quantità</th>
												<th scope="col">
													<button class="btn btn-outline-success btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#aggiungiMaterialeModal">Aggiungi nuovo materiale</button>
												</th>
											</tr>
										</thead>
										<tbody class="table-group-divider">
											<tr v-for="(item, index) in this.uniqueMateriali()" :key="item.id">
												<th scope="row">{{ index + 1 }}</th>
												<td>{{ item.nome }}</td>

												<td v-if="editingItems[item.nome]">
													<input type="number" v-if="editingItems[item.nome]" v-model="item.editedQuantity" class="form-control" min="0">
												</td>
												<td v-else>{{ quantita[item.nome] }}</td>

												<td>
													<button v-if="!editingItems[item.nome]" class="btn btn-primary btn-block btn-sm" type="button" @click="toggleEditing(item)">Modifica quantità</button>
													<div v-else class="d-flex justify-content-center">
														<button class="btn btn-primary btn-sm flex-grow-1" type="button" @click="cancelEditing(item)" id="chiaro-button">Annulla</button>
														<button class="btn btn-primary btn-sm mx-1 flex-grow-1" type="button" @click="toggleEditing(item)">Salva</button>
													</div>
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

	<!-- aggiungiMaterialeModal -->
	<div class="modal fade" id="aggiungiMaterialeModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="aggiungiMaterialeModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form @submit.prevent="aggiungiNuovoMateriale" ref="form">
					<fieldset>
						<div class="modal-body">
							<legend class="custom-legend">
								<span class="legend-text">Aggiungi un nuovo materiale</span>
								<button type="reset" class="btn-close btn-lg" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
							<p class="text-muted">Nota: Il nome del materiale non deve essere già presente tra quelli attivi.</p>
							<div class="divider"/>
							<div class="input-group mb-3">
								<span class="input-group-text">Nome materiale</span>
								<input type="text" v-model="nome" class="form-control" aria-label="NuovoMateriale" aria-describedby="NuovoMateriale" required>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-primary mx-4 flex-grow-1" data-bs-dismiss="modal" type="reset" id="chiaro-button">Annulla</button>
							<button class="btn btn-primary mx-4 flex-grow-1" type="submit">Conferma</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

	<SuccessShower ref="succShower" :message="successMessage"/>

	<ErrorShower ref="errShower" :message="errorMessage"/>

</template>

<script>
	import MagazzinoNavBar from "@/components/MagazzinoNavBar";
	import SuccessShower from "@/components/SuccessShower";
	import ErrorShower from "@/components/ErrorShower";
	import axios from "axios";

	export default {
		name: "MagazzinoHomeView",
		components: {
			MagazzinoNavBar,
			SuccessShower,
			ErrorShower
		},
		data() {
			return {
				nome: '',
				quantita: {},
				tot: 0,
				saldoMagazzino: 0,
				listaMateriali: [],
				editingItems: {}, // Oggetto per tenere traccia della modalità di modifica per ciascun elemento
				successMessage: '',
				errorMessage: ''
			}
		},
		mounted() {
			this.fetchMaterialiMagazzino();
			this.uniqueMateriali();
			this.calcSaldoMagazzino();
		},
		computed() {
			this.fetchMaterialiMagazzino();
			this.uniqueMateriali();
		},
		methods: {
			// Funzione che chiama le altre funzioni di setup
			chiamaFunzioni() {
				this.fetchMaterialiMagazzino();
				this.uniqueMateriali();
			},
			// Funzione che calcola la quantità di un materiale per nome
			fetchQuantitaMateriale() {
				this.quantita = this.listaMateriali.reduce((acc, materiale) => {
					const chiave = materiale.nome;
					if (acc[chiave]) {
						acc[chiave]++;
					} else {
						acc[chiave] = 1;
					}
					return acc;
				}, {});
			},
			// Funzione che calcola la lista dei materiali senza duplicati
			uniqueMateriali() {
				const uniqueMateriali = [];
				const countMateriali = {};

				for (const item of this.listaMateriali) {
					if (!countMateriali[item.nome]) {
						countMateriali[item.nome] = 1;
						uniqueMateriali.push(item);
					}
					else
						countMateriali[item.nome]++;
				}
				for (const item of uniqueMateriali) {
					item.count = countMateriali[item.nome];
				}
				return uniqueMateriali;
			},
			// Funzione che calcola il saldo del magazzino
			async calcSaldoMagazzino() {
				await axios.get('http://localhost:32000/api/v1/conto/1')
					.then(response => {
						console.log("Aggiornamento saldo: " + response.data.saldo);
						this.saldoMagazzino = response.data.saldo;
					})
					.catch(error => {
						console.log(error);
					})
			},
			// Funzione che carica dal server i materiali presenti nel magazzino
			async fetchMaterialiMagazzino() {
				await axios.get('/api/magazzini/magazzini')
					.then(response => {
						console.log(response.data[0].materiali);
						this.listaMateriali = response.data[0].materiali;
						this.fetchQuantitaMateriale();
						// Inizializza editingItems a false per ogni elemento nella lista (non null)
						this.listaMateriali.forEach(item => {
							if (this && this.$set) {
								this.$set(this.editingItems, item.nome, false);
							}
						});
						// console.log('Materiali caricati con successo.');
					})
					.catch(error => {
						console.log(error);
					})
			},
			// Funzione che aggiunge un nuovo materiale al magazzino
			async aggiungiNuovoMateriale() {
				// controlla se in listaMateriali c'è già un materiale con lo stesso nome
				if (this.listaMateriali.some(item => item.nome === this.nome)) {
					this.errorMessage = 'Materiale già presente!';
					this.$refs.errShower.toggle();
					this.$refs.form.reset();
					return;
				}
				const url = `/api/magazzini/create/1/${this.nome}`;
				await axios.put(url, {nome: this.nome})
					.then(response => {
						console.log(response.data);
						this.listaMateriali.push(response.data);
						this.fetchQuantitaMateriale();
						// Inizializza editingItems a false per ogni elemento nella lista (non null)
						this.listaMateriali.forEach(item => {
							if (this && this.$set) {
								this.$set(this.editingItems, item.nome, false);
							}
						});
						this.successMessage ='Nuovo materiale aggiunto!';
						this.$refs.succShower.toggle();
						this.$refs.form.reset();
					})
					.catch(errors => {
						console.error(errors);
					})
			},
			// Funzione che gestisce la modifica della quantità di un materiale
			toggleEditing(item) {
				if (this.editingItems[item.nome]) {
					// Si procede solo nel caso in cui la quantità venga cambiata
					if (this.quantita[item.nome] !== item.editedQuantity) {
						// Salvataggio delle nuove quantità. Se la quantità è 0 elimina il materiale, altrimenti ne aggiorna la quantità
						if (item.editedQuantity === 0)
							this.eliminaMateriale(item);
						else
							this.aggiornaQuantitaMateriale(item);
						this.quantita[item.nome] = item.editedQuantity;
					}
				}
				else
					item.editedQuantity = this.quantita[item.nome];
				this.editingItems[item.nome] = !this.editingItems[item.nome];
			},
			// Funzione che annulla la modifica della quantità di un materiale
			cancelEditing(item) {
				this.editingItems[item.nome] = false;
				item.editedQuantity = this.quantita[item.nome];
				this.$refs.form.reset();
			},
			// Funzione che elimina un materiale dal magazzino
			async eliminaMateriale(item) {
				const url = `/api/magazzini/elimina/nome/1/${item.nome}`;
				await axios.delete(url, {materiale_nome: item.nome})
					.then(response => {
						this.listaMateriali = response.data.materiali;
						this.fetchQuantitaMateriale();
					})
					.catch(errors => {
						console.error(errors);
					})
			},
			// Funzione che aggiorna la quantità di un materiale nel magazzino
			async aggiornaQuantitaMateriale(item) {
				const url = `/api/magazzini/aggiorna/1/${item.nome}/${item.editedQuantity}`;
				await axios.put(url, { materiale_nome: item.nome, quantita: item.editedQuantity })
					.then(response => {
						console.log(response.data);
					})
					.catch(errors => {
						console.error(errors);
					})
			}
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background: linear-gradient(to top, rgba(230, 11, 9, 0.8), rgba(237, 227, 66, 0.8), rgba(237, 227, 66, 0.8));
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

	#chiaro-button {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#chiaro-button:hover {
		background-color: #0d17c419;
	}

	.btn-outline-success {
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	.btn-success {
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	p {
		font-size: 1.2em;
		margin-top: -20px;
		margin-bottom: 30px;
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

	.text-muted {
		font-size: 16px;
	}

	#reload {
		width: 25px;
		height: 25px;
		margin-left: 20px;
		margin-top: -5px;
		cursor: pointer;
	}
</style>
