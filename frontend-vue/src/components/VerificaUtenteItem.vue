<template>
	<div class="modal fade" :id="'btn-verifica-' + utente.id" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-verificaLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form">
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

<!--										TODO: da sistemare. Se non si riesce a schermo piuttosto si toglie il toggle a Modifica categoria e lo si fa scomparire una volta cliccato-->
											<select v-if="editing" class="form-select" aria-label="Categoria" v-model="editedCategory">
												<option>Nessuna categoria</option>
												<option>cat1</option>
												<option>cat2</option>
<!--												<option v-for="cat in categorie" :key="cat.id">{{ cat.nome }}</option>-->
											</select>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Documento:</span>
											<span>...</span>
<!--											<p v-if="utente.path_documento === utente.email + '.pdf'">Documento d'identità non caricato</p>-->
<!--											                  <a v-else href="#" @click="idDocPath(utente.email + '.pdf')">Download Doc.Identità</a>-->
<!--											<a v-else href="#">Download Doc.Identità</a>-->
										</div>
									</div>
									<div class="col col-lg col-md-12 col-sm-12">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Curriculum:</span>
											<span>...</span>
<!--											<p v-if="utente.path_curriculum === utente.email + '.pdf'">CV non caricato</p>-->
<!--											                  <a v-else href="#" @click="cvPath(utente.email + '.pdf')">Download CV</a>-->
<!--											<a v-else href="#">Download CV</a>-->
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="modal-footer">
							<button class="btn btn-outline-success mx-4 flex-grow-1" type="button" @click="editCategory"> {{ editing ? "Salva" : "Modifica categoria" }}</button>
							<button class="btn btn-primary mx-4 flex-grow-1" type="button" @click="cancelEditing" data-bs-dismiss="modal" id="chiaro-button">Annulla</button>
							<button class="btn btn-primary mx-4 flex-grow-1">Approva</button>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>




<!--	<div :id="'btn-verifica-' + utente.id" class="modal fade" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-verificaLabel" aria-hidden="true">-->
<!--		<div class="modal-dialog modal-lg model-dialog-centered">-->
<!--			<div class="modal-content">-->
<!--				<div class="modal-header">-->
<!--					<h1 class="modal-title fs-5" id="btn-verificaLabel">-->
<!--						Utente: {{ utente.nome + ' ' + utente.cognome }}-->
<!--					</h1>-->
<!--					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>-->
<!--				</div>-->
<!--				<div class="modal-body">-->
<!--					<form>-->
<!--						<table>-->
<!--							<tbody>-->
<!--							<tr>-->
<!--								<td>-->
<!--									<p>Nome: {{ utente.nome }}</p>-->
<!--									<p>Cognome: {{ utente.cognome }}</p>-->
<!--									<p>N° Telefono: {{ utente.telefono }}</p>-->
<!--									<p>Indirizzo: {{ utente.indirizzo }}</p>-->
<!--									<p v-if="utente.category === null">Categoria: Nessuna categoria</p>-->
<!--									<p v-else>Categoria: {{ utente.category }}</p>-->
<!--									<button type="button" class="btn btn-secondary" data-bs-target="#aggiornaCat" data-bs-toggle="modal">Aggiorna categoria-->
<!--									</button>-->
<!--								</td>-->
<!--								<td>-->
<!--									<p>Documento d'identità</p>-->
<!--									<p v-if="utente.path_documento === utente.email + '.pdf'">Documento d'identità non caricato</p>-->
<!--									&lt;!&ndash;                  <a v-else href="#" @click="idDocPath(utente.email + '.pdf')">Download Doc.Identità</a>&ndash;&gt;-->
<!--									<a v-else href="#">Download Doc.Identità</a>-->
<!--								</td>-->
<!--								<td>-->
<!--									<p>CV</p><br>-->
<!--									<p v-if="utente.path_curriculum === utente.email + '.pdf'">CV non caricato</p>-->
<!--									&lt;!&ndash;                  <a v-else href="#" @click="cvPath(utente.email + '.pdf')">Download CV</a>&ndash;&gt;-->
<!--									<a v-else href="#">Download CV</a>-->
<!--								</td>-->
<!--							</tr>-->
<!--							</tbody>-->
<!--						</table>-->
<!--					</form>-->
<!--				</div>-->
<!--				<div class="modal-footer">-->
<!--					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>-->
<!--					<button type="button" class="btn btn-primary">Approva</button>-->
<!--				</div>-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->
<!--	<div class="modal fade" id="aggiornaCat" aria-hidden="true" aria-labelledby="aggiornaCatLabel" tabindex="-1">-->
<!--		<div class="modal-dialog modal-dialog-centered">-->
<!--			<div class="modal-content">-->
<!--				<div class="modal-header">-->
<!--					<h1 class="modal-title fs-5" id="aggiornaCatLabel">Categorie Utenti</h1>-->
<!--					&lt;!&ndash;         Questo bottone ha qualcosa che non va ancora, vedo di sistemarlo io tranqui&ndash;&gt;-->
<!--					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<!--				</div>-->
<!--				<div class="modal-body">-->
<!--					<p>Scegli una o più categorie da assegnare ad un utente:</p>-->
<!--				</div>-->
<!--				&lt;!&ndash;		  Al momento questi due pulsanti non vanno. Vedo di sistemarli io tranqui, fai pure le chiamate&ndash;&gt;-->
<!--				&lt;!&ndash;        <div class="modal-footer">&ndash;&gt;-->
<!--				&lt;!&ndash;          <button class="btn btn-secondary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Annulla</button>&ndash;&gt;-->
<!--				&lt;!&ndash;          <button class="btn btn-primary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Conferma</button>&ndash;&gt;-->
<!--				&lt;!&ndash;        </div>&ndash;&gt;-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->

</template>

<script>
// import axios from "axios";

export default {
	name: "VerificaUtenteItem",
	props: {
		utente: {required: true, type: Object}
	},
	methods: {
		// Funzione che ritorna il CV di un utente
		/*
		PER FARLO FUNZIONARE è STATA CAMBIATA L'ANNOTAZIONE DA @RequestPart a @RequestParam,
		perchè si chiama una GET e non una POST
		 */
		// async cvPath(file) {
		//   await axios.get(`/api/amministratore/cv?fileName=${file}`,
		//       {
		//         responseType: "blob",
		//         headers: {
		//           Accept: 'application/pdf'
		//         }
		//       })
		//       .then(response => {
		//         // Create a URL for the blob response and open it in a new window for download
		//         const pdfURL = URL.createObjectURL(response.data);
		//         console.log(pdfURL);
		//         const downloadLink = document.createElement('a');
		//         console.log(downloadLink);
		//         downloadLink.href = pdfURL;
		//         downloadLink.download = file;
		//         downloadLink.click();
		//         // Clean up the URL object after the download
		//         URL.revokeObjectURL(pdfURL);
		//       })
		//       .catch(errors => {
		//         console.log(errors);
		//       })
		// },
		// Funzione che ritorna il documento d'identità di un utente
		// async idDocPath(file){
		//   await axios.get(`/api/amministratore/id?fileName=${file}`,
		//       {
		//         responseType: "blob",
		//         headers: {
		//           Accept: 'application/pdf'
		//         }
		//       })
		//       .then(response => {
		//         // Create a URL for the blob response and open it in a new window for download
		//         const pdfURL = URL.createObjectURL(response.data);
		//         console.log(pdfURL);
		//         const downloadLink = document.createElement('a');
		//         console.log(downloadLink);
		//         downloadLink.href = pdfURL;
		//         downloadLink.download = file;
		//         downloadLink.click();
		//         // Clean up the URL object after the download
		//         URL.revokeObjectURL(pdfURL);
		//       })
		//       .catch(errors => {
		//         console.error(errors);
		//       })
		// }
		editCategory() {
			if (this.editing) {
				this.categoryCopy = this.editedCategory;
			} else {
				this.editedCategory = this.categoryCopy;
			}
			this.editing = !this.editing;
		},
		cancelEditing() {
			this.editing = false;
			this.editedCategory= '';
			this.categoryCopy = this.utente.category;
		}
	},
	data() {
		return {
			file: '',
			editing: false,
			editedCategory: '',
			categoryCopy: this.utente.category
		}
	},
	computed() {
		// this.cvPath(this.file);
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
