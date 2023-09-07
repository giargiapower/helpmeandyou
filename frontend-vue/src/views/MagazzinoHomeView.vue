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
<!--								TODO: Il luogo qui sotto dipenderà dalle credenziali dell'utente.-->
								<h1 class="fw-bold mb-5">Gestione magazzino: Torino</h1>
								<p>Denaro disponibile: {{ saldoMagazzino }}</p>
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
				<form @submit.prevent="aggiungiNuovo" ref="form">
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
								<input type="text" class="form-control" aria-label="NuovoMateriale" aria-describedby="NuovoMateriale" required>
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



<!--	<div class="magazzino-home">-->
<!--		<nav>-->
<!--			<router-link to="/magazzino">Logout</router-link>-->
<!--		</nav>-->
<!--		<h2>Gestione magazzino</h2>-->
<!--		<p>Denaro disponibile: {{ saldoMagazzino }}</p>-->
<!--		<table class="table" v-if="listaMateriali.length !== 0">-->
<!--			<thead class="table-dark">-->
<!--			<tr>-->
<!--				<th scope="col">Nome materiale</th>-->
<!--				<th scope="col">Quantità</th>-->
<!--				<th scope="col">Aggiungi/Elimina materiale</th>-->
<!--			</tr>-->
<!--			</thead>-->
<!--			<tbody>-->
<!--			<template v-for="item in this.uniqueMateriali()" :key="item.id">-->
<!--				<tr>-->
<!--					<td>{{ item.nome }}</td>-->
<!--					<td>{{ quantita[item.nome] }}</td>-->
<!--					<td>-->
<!--						<div class="btn-group">-->
<!--							<button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" :data-bs-target="`#aggiungiMateriale-${item.id}`" @click="onClick(item)">+-->
<!--							</button>-->
<!--							<button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" :data-bs-target="`#eliminaMateriale-${item.id}`" @click="onClick(item)">- -->
<!--							</button>-->
<!--						</div>-->
<!--					</td>-->
<!--				</tr>-->

<!--				<div v-for="item in this.uniqueMateriali()" :key="`modal-${item.nome}`">-->
<!--					<div class="modal fade" :id="`aggiungiMateriale-${item.id}`" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="`aggiungiMaterialeLabel-${item.id}`" aria-hidden="true">-->
<!--						<div class="modal-dialog">-->
<!--							<div class="modal-content">-->
<!--								<div class="modal-header">-->
<!--									<h1 class="modal-title fs-5" id="aggiungiMaterialeLabel">Quanti {{ item.nome }} vuoi aggiungere:</h1>-->
<!--									<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>-->
<!--								</div>-->
<!--								<div class="modal-body">-->
<!--									<label>Quantità materiale:</label>-->
<!--									<input type="number" v-model="this.tot" required>-->
<!--								</div>-->
<!--								<div class="modal-footer">-->
<!--									<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla-->
<!--									</button>-->
<!--									<button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="addMateriale(item.nome, this.tot)">Conferma-->
<!--									</button>-->
<!--								</div>-->
<!--							</div>-->
<!--						</div>-->
<!--					</div>-->
<!--					<div class="modal fade" :id="`eliminaMateriale-${item.id}`" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="`eliminaMaterialeLabel-${item.id}`" aria-hidden="true">-->
<!--						<div class="modal-dialog">-->
<!--							<div class="modal-content">-->
<!--								<div class="modal-header">-->
<!--									<h1 class="modal-title fs-5" id="eliminaMaterialeLabel">Quanti {{ item.nome }} vuoi-->
<!--										eliminare:</h1>-->
<!--									<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>-->
<!--								</div>-->
<!--								<div class="modal-body">-->
<!--									<label>Quantità materiale:</label>-->
<!--									<input type="number" v-model="quantita" required>-->
<!--								</div>-->
<!--								<div class="modal-footer">-->
<!--									<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla-->
<!--									</button>-->
<!--									<button type="button" class="btn btn-primary" data-bs-dismiss="modal">Conferma-->
<!--									</button>-->
<!--								</div>-->
<!--							</div>-->
<!--						</div>-->
<!--					</div>-->
<!--				</div>-->
<!--			</template>-->
<!--			</tbody>-->
<!--		</table>-->

<!--		<div v-if="listaMateriali.length === 0">-->
<!--			<p>Magazzino vuoto!</p>-->
<!--			<button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal"-->
<!--					data-bs-target="#aggiungiMateriale">+-->
<!--			</button>-->
<!--			<br>-->
<!--			<label>Inserisci un materiale!</label>-->
<!--			<div class="modal fade" id="aggiungiMateriale" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="aggiungiMaterialeLabel" aria-hidden="true">-->
<!--				<div class="modal-dialog">-->
<!--					<div class="modal-content">-->
<!--						<div class="modal-header">-->
<!--							<h1 class="modal-title fs-5" id="aggiungiMaterialeLabel">Aggiungi materiale</h1>-->
<!--							<button type="button" class="btn-close" data-bs-dismiss="modal"-->
<!--									aria-label="Chiudi"></button>-->
<!--						</div>-->
<!--						<div class="modal-body">-->
<!--							<label>Nome materiale:</label>-->
<!--							<input type="text" v-model="nome" required><br>-->
<!--							<label>Quantità materiale:</label>-->
<!--							<input type="number" v-model="quantita" required>-->
<!--						</div>-->
<!--						<div class="modal-footer">-->
<!--							<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>-->
<!--							<button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="addMateriale">-->
<!--								Conferma-->
<!--							</button>-->
<!--						</div>-->
<!--					</div>-->
<!--				</div>-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->
</template>

<script>
	import MagazzinoNavBar from "@/components/MagazzinoNavBar";
	import SuccessShower from "@/components/SuccessShower";
	import axios from "axios";

	export default {
		name: "MagazzinoHomeView",
		components: {
			MagazzinoNavBar,
			SuccessShower
		},
		methods: {
			onClick(nome) {
				this.nome = nome;
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
					} else {
						countMateriali[item.nome]++;
					}
				}

				for (const item of uniqueMateriali) {
					item.count = countMateriali[item.nome];
				}

				return uniqueMateriali;
			},
			// Funzione che carica dal server i materiali presenti nel magazzino
			async fetchMaterialiMagazzino() {
				await axios.get('/api/magazzini/1/materiali')
					.then(response => {
						this.listaMateriali = response.data;
						this.fetchQuantitaMateriale();
						// Inizializza editingItems per ogni elemento nella lista
						this.listaMateriali.forEach(item => {
							this.$set(this.editingItems, item.nome, false);
						});
						console.log('Materiali caricati con successo.');
					})
					.catch(error => {
						console.log(error);
					})
			},
			// Funzione che aggiunge un materiale al magazzino e lo salva sul server
			// TODO: NON FUNZIONA! MA NON SEMBRA SBAGLIATA !!!
			async addMateriale(nome, num) {
				const idMagazzino = 6;
				const url = `/api/magazzini/create/${idMagazzino}/${nome}`;
				await axios.put(url, {idMagazzino: 6, nome: nome})
					.then(response => {
						this.listaMateriali.push(response.data);
						console.log('Materiale aggiunto al magazzino');
						this.quantita[nome] += num;
						console.log('Quantità materiale aggiornata');
					})
					.catch(errors => {
						console.error(errors);
					})
			},
			async aggiungiNuovo() {
				this.successMessage ='Nuovo materiale aggiunto!';
				this.$refs.succShower.toggle();
				this.$refs.form.reset();
			},
			toggleEditing(item) {
				if (this.editingItems[item.nome]) {
					this.quantita[item.nome] = item.editedQuantity;
				} else {
					item.editedQuantity = this.quantita[item.nome];
				}
				this.editingItems[item.nome] = !this.editingItems[item.nome];
			},
			cancelEditing(item) {
				this.editingItems[item.nome] = false;
				item.editedQuantity = this.quantita[item.nome];
			}
		},
		data() {
			return {
				nome: '',
				quantita: {},
				tot: 0,
				saldoMagazzino: 0,
				listaMateriali: [],
				editingItems: {}, // Oggetto per tenere traccia della modalità di modifica per ciascun elemento
				successMessage: ''
			}
		},
		mounted() {
			this.fetchMaterialiMagazzino();
			this.uniqueMateriali();
		},
		computed() {
			this.fetchMaterialiMagazzino();
			this.uniqueMateriali();
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background: linear-gradient(to top, #e60b09, #ede342, #ede342);
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
</style>
