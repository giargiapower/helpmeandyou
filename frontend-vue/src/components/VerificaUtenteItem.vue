<template>
	<div class="modal fade" :id="'btn-verifica-' + utente.id" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-verificaLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form" @submit.prevent="approvaUtente">
					<fieldset>
						<div class="modal-header">
							<legend class="custom-legend">
								<span class="legend-text">Utente: {{ utente.nome + ' ' + utente.cognome }}</span>
								<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
						</div>
						<div class="modal-body">
							<div class="container text-center">
								<div class="row row-cols-1 row-cols-md-2">
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Nome:</span>
											<span>{{ utente.nome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Cognome:</span>
											<span>{{ utente.cognome }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Email:</span>
											<span>{{ utente.email }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Domicilio(?):</span>
											<span>{{ utente.indirizzo }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">N° telefono:</span>
											<span>{{ utente.telefono }}</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Categoria:</span>

											<span v-if="editing"></span>
											<span v-else>{{ categoryCopy }}</span>

											<select v-if="editing" class="form-select" aria-label="Categoria" v-model="editedCategory">
												<option v-for="categoria in listaCategorie" :value="categoria.tipo" :key="categoria.tipo">
													{{ categoria.tipo }}
												</option>
											</select>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Documento:</span>
<!--											<span>...</span>-->
<!--											è giusto che sia (utente.path_documento === utente.email) perché
												se non è presente il doc, viene salvato in automatico con la mail -->
											<p v-if="utente.path_documento === utente.email + '.pdf'">Documento d'identità non caricato</p>
											<a v-else href="#" @click="idDocPath(utente.email + '.pdf')">Download Doc.Identità</a>
										</div>
									</div>
									<div class="col col-lg col-md-12 col-sm-12">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Curriculum:</span>
<!--											<span>...</span>-->
<!--											vale lo stesso del documento d'identità -->
											<p v-if="utente.path_curriculum === utente.email + '.pdf'">CV non caricato</p>
											<a v-else href="#" @click="cvPath(utente.email + '.pdf')">Download CV</a>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div v-if="this.fromParent === 1" class="modal-footer">
							<button v-if="!editing" class="btn btn-outline-success mx-4 flex-grow-1" type="button" @click="editCategory">Modifica categoria</button>
							<div v-else class="d-flex justify-content-center">
								<button class="btn btn-primary mx-4 flex-grow-1" type="button" @click="cancelEditing" id="chiaro-button">Annulla modifiche</button>
								<button class="btn btn-outline-success mx-4 flex-grow-1" type="button" @click="editCategory">Salva</button>
							</div>
							<button class="btn btn-danger mx-4 flex-grow-1" type="button" @click="bloccaUtente" data-bs-dismiss="modal">Blocca utente</button>
							<button class="btn btn-primary mx-4 flex-grow-1" type="submit">Approva</button>
						</div>
						<div v-else-if="utente.stato !== 'bloccato'" class="modal-footer">
							<button class="btn btn-primary mx-4 flex-grow-1" type="button" data-bs-dismiss="modal" id="chiaro-button">Chiudi</button>
							<button class="btn btn-danger mx-4 flex-grow-1" type="button" @click="bloccaUtente">Blocca utente</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>

</template>

<script>
	import axios from "axios";

	export default {
		name: "VerificaUtenteItem",
		props: {
			utente: {required: true, type: Object},
			fromParent: {required: true, type: Number}
		},
		data() {
			return {
				file: '',
				editing: false,
				editedCategory: this.utente.categoria,
				categoryCopy: this.utente.categoria,
				listaCategorie: []
			}
		},
		computed() {
			this.cvPath(this.file);
			this.idDocPath(this.file);
			this.approvaUtente();
		},
		methods: {
			// Funzione che ritorna il CV di un utente
			/*
			PER FARLO FUNZIONARE è STATA CAMBIATA L'ANNOTAZIONE DA @RequestPart a @RequestParam,
			perchè si chiama una GET e non una POST
			 */
			async cvPath(file) {
				await axios.get(`/api/amministratore/cv?fileName=${file}`,
					{
						responseType: "blob",
						headers: {
							Accept: 'application/pdf'
						}
					})
					.then(response => {
						// Create a URL for the blob response and open it in a new window for download
						const pdfURL = URL.createObjectURL(response.data);
						console.log(pdfURL);
						const downloadLink = document.createElement('a');
						console.log(downloadLink);
						downloadLink.href = pdfURL;
						downloadLink.download = file;
						downloadLink.click();
						// Clean up the URL object after the download
						URL.revokeObjectURL(pdfURL);
					})
					.catch(errors => {
						console.log(errors);
					})
			},
			// Funzione che ritorna il documento d'identità di un utente
			async idDocPath(file) {
				await axios.get(`/api/amministratore/id?fileName=${file}`,
					{
						responseType: "blob",
						headers: {
							Accept: 'application/pdf'
						}
					})
					.then(response => {
						// Create a URL for the blob response and open it in a new window for download
						const pdfURL = URL.createObjectURL(response.data);
						console.log(pdfURL);
						const downloadLink = document.createElement('a');
						console.log(downloadLink);
						downloadLink.href = pdfURL;
						downloadLink.download = file;
						downloadLink.click();
						// Clean up the URL object after the download
						URL.revokeObjectURL(pdfURL);
					})
					.catch(errors => {
						console.error(errors);
					})
			},
			// Funzione che aggiorna lo stato dell'account di un utente da "da_approvare" a "approvato"
			async approvaUtente() {
				if (this.utente.categoria !== this.editedCategory)
					await this.aggiornaCategoria();

				await axios.put(`/api/amministratore/da_approvare/valuta/${this.utente.id}`,
					{
						decisione: 'approvato'
					})
					.then(response => {
						console.log(response.data);
						this.$emit('rimuovi-figlio', this.utente.id);
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
			// Funzione che recupera le categorie per l'utente
			async prendiCategorie() {
				await axios.get('/api/amministratore/categorie')
					.then(response => {
						this.listaCategorie = response.data;
						console.log(this.listaCategorie);
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
			// Funzione che aggiorna la categoria di un utente
			async aggiornaCategoria() {
				await axios.put(`/api/amministratore/aggiorna_categoria/${this.utente.id}`,
					{
						tipo: this.editedCategory
					})
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
			// Funzione che blocca un utente
			async bloccaUtente() {
				await axios.put(`/api/amministratore/blocca/${this.utente.id}`)
					.then(response => {
						// TODO: un messaggio che mostri che l'utente x sia bloccato
						console.log(response.data);
						if (this.fromParent === 1)
							this.$emit('rimuovi-figlio', this.utente.id);
						else
							this.$emit('blocca-utente', this.utente.id);
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
			editCategory() {
				this.prendiCategorie();
				if (this.editing) {
					this.categoryCopy = this.editedCategory;
				} else {
					this.editedCategory = this.categoryCopy;
				}
				this.editing = !this.editing;
			},
			cancelEditing() {
				this.editing = false;
				this.editedCategory = this.utente.categoria;
				this.categoryCopy = this.utente.categoria;
			}
		}
	}
</script>

<style scoped>
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

	.btn-outline-success {
		margin: 5px 0;
		padding: 5px 20px;
		font-size: 17px;
		cursor: pointer;
		transition: background-color 0.5s ease;
		border-radius: 20px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	.btn-close {
		align-self: flex-start;
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



	/*.home-container {*/
	/*	min-height: 100%;*/
	/*	text-align: center;*/
	/*	background: linear-gradient(to bottom, #a7fada, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15));*/
	/*	!*background: linear-gradient(to top, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15), rgba(92, 109, 248, 0.49), #5c6df8);*!*/
	/*	animation: fadeInUp 1s ease both;*/
	/*}*/

	/*h1 {*/
	/*	font-weight: bold;*/
	/*	font-size: 2.5em;*/
	/*	margin-bottom: 20px;*/
	/*	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);*/
	/*}*/

</style>
