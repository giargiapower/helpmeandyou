<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<div class="hero-section">
			<div class="hero-content">
				<form @submit="onSubmit" ref="form">
					<fieldset>
						<legend>Crea richiesta d'aiuto</legend>
						<p class="text-muted">Nota: Cerca di essere il più possibile dettagliato nella richiesta</p>
						<div class="input-group mb-3">
							<span class="input-group-text">Data</span>
							<input type="date" class="form-control" :min="minDate" aria-label="Data" aria-describedby="Data" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Regione</span>
							<input type="text" class="form-control" aria-label="Regione" aria-describedby="Regione" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Provincia</span>
							<input type="text" class="form-control" aria-label="Provincia" aria-describedby="Provincia" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Città</span>
							<input type="text" class="form-control" aria-label="Città" aria-describedby="Città" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Indirizzo</span>
							<input type="text" class="form-control" aria-label="Indirizzo" aria-describedby="Indirizzo" id="indirizzo" required>

							<span class="input-group-text ms-4" id="numeroCivico">N°</span>
							<input type="text" class="form-control" aria-label="NumeroCivico" aria-describedby="NumeroCivico" required>
						</div>
						<div class="input-group mb-3">
							<span class="input-group-text">Categoria</span>
							<select class="form-select" aria-label="Categoria" required v-model="selectedCategoria">
								<option disabled value="">Scegli una categoria...</option>
								<option value="nessunaCategoria">Nessuna categoria</option>
								<option v-for="cat in categorie" :key="cat.id">{{ cat.nome }}</option>
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
							<textarea rows="5" class="form-control" minlength="20" aria-label="Descrizione richiesta" aria-describedby="Descrizione richiesta" title="La descrizione deve avere almeno 20 caratteri" required></textarea>
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



<!--	<div class="crea-richiesta">-->
<!--		<bacheca-nav-bar></bacheca-nav-bar>-->
<!--		<form @submit="onSubmit" ref="form">-->
<!--			<h2>Crea Richiesta d'Aiuto</h2>-->
<!--			<table>-->
<!--				<tbody>-->
<!--				<tr>-->
<!--					<td>-->
<!--						<label>Data:</label>-->
<!--						<input type="date" required>-->
<!--					</td>-->
<!--					<td>-->
<!--						<input type="checkbox">-->
<!--						<label>Mattina</label>-->
<!--					</td>-->
<!--					<td>-->
<!--						<input type="checkbox">-->
<!--						<label>Pomeriggio</label>-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td>-->
<!--						<label>Regione:</label>-->
<!--						<input type="text" required>-->
<!--					</td>-->
<!--					<td>-->
<!--						<label>Provincia:</label>-->
<!--						<input type="text" required>-->
<!--					</td>-->
<!--					<td>-->
<!--						<label>Città:</label>-->
<!--						<input type="text" required>-->
<!--					</td>-->
<!--					<td>-->
<!--						<label>Indirizzo:</label>-->
<!--						<input type="text" required>-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td>-->
<!--						<label>Categoria Richiesta:</label>-->
<!--						<select v-model="selectedCategoria">-->
<!--							<option disabled value="">Seleziona un'opzione</option>-->
<!--							<option v-for="cat in categorie" :key="cat.id">{{ cat.nome }}</option>-->
<!--						</select>-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<label for="text-input">Descrizione Richiesta:</label>-->
<!--					<input type="text" id="text-input" v-model="textValue" required>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td>-->
<!--						<label>Seleziona il materiale che ti serve:</label>-->
<!--						<select v-model="selectedMateriale">-->
<!--							<option disabled value="">-</option>-->
<!--							<option v-for="materiale in this.uniqueMateriali()" :key="materiale.id">{{-->
<!--									materiale.nome-->
<!--								}}-->
<!--							</option>-->
<!--						</select>-->
<!--					</td>-->
<!--				</tr>-->
<!--				</tbody>-->
<!--			</table>-->
<!--			<input type="reset" value="Annulla" @click="onReset"> |-->
<!--			<input type="submit" value="Pubblica">-->
<!--		</form>-->
<!--	</div>-->
</template>

<script>
	import BachecaNavBar from "@/components/BachecaNavBar";
	import axios from 'axios';
	import _ from 'lodash';

	export default {
		name: "CreaRichiestaView",
		components: {BachecaNavBar},
		methods: {
			onReset() {
				this.$refs.form.reset();
				this.$router.push('./');
			},
			onSubmit() {
				alert(`Richiesta pubblicata!\nPuoi consultare le prenotazioni attive nella sezione "Le mie attività".`);
				this.$refs.form.reset();
				this.$router.push('./');
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
				await axios.get('/api/magazzini/6/materiali')
					.then(response => {
						this.listaMateriali = response.data;
						console.log('Materiali caricati!');
					})
					.catch(errore => {
						console.log(errore);
					})
			},
			// Funzione che calcola le categorie da assegnare a una richiesta in modo univoco
			async fetchCategorieRichieste() {
				await axios.get('/api/richiesteaiuto/richieste')
					.then(response => {
						this.listaRichieste = response.data;
						this.listaRichieste.forEach(ric => {
							this.categorie.push({id: this.uniqueId, nome: ric.categoria.tipo});
						});

						console.log('Categorie caricate on successo!');
						console.log(this.categorie);
					})
					.catch(errore => {
						console.log(errore);
					})
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
				selectedCategoria: '',
				selectedMateriale: '',
				textValue: '',
				listaRichieste: [],
				listaMateriali: [],
				categorie: [],
				uniqueId: _.uniqueId(),
				minDate: this.calculateMinDate()
			}
		},
		mounted() {
			this.fetchMateriali();
			this.uniqueMateriali();
			this.fetchCategorieRichieste();
		},
		computed() {
			this.fetchMateriali();
			this.uniqueMateriali();
			this.fetchCategorieRichieste();
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
		/*text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); !* ombra al testo *!*/
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

	#google-button {
		background-color: #dd4b39;
	}

	#google-button:hover {
		background-color: #b70000ff;
	}

	#chiaro-button {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#chiaro-button:hover {
		background-color: #0d17c419;
	}

	.or-container {
		align-items: center;
		display: flex;
		margin: 10px 0;
	}

	.line-separator {
		background-color: #ccccccff;
		flex-grow: 5;
		height: 1px;
	}

	.or-label {
		flex-grow: 1;
		margin: 0 15px;
		text-align: center;
	}

	.divider {
		border: none;
		border-top: 1px solid #ccccccff;
		margin: 15px 0;
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
	.modal-content{
		background-color: #ffffff;
		border-radius: 45px;
		padding: 1.5em 3em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	form{
		background-color: #ffffff;	  /* Aggiunge stile di sfondo per tutta la pagina */
	}

	.custom-legend{
		display: flex;
		align-items: center;
	}

	.legend-text {
		flex-grow: 1;
		text-align: center;
	}

	.btn-close {
		align-self: flex-start;
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
