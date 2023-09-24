<template>
	<div class="home-container">
		<bacheca-nav-bar/>
		<div class="hero-section">
			<div class="container">
				<div class="page-content page-container" id="page-content">
					<div class="padding">
						<div class="card user-card-full">
							<div class="row m-l-0 m-r-0">
								<div class="col-sm-4 bg-c-lite-green user-profile">
									<div class="card-block text-center text-white">
										<div class="m-b-25">
											<img src="@/assets/user1.png" class="img-fluid rounded-circle" alt="User-Profile-Default-Image">
										</div>
										<div id="ombra">
											<h3 class="f-w-600">{{ this.nome + ' ' + this.cognome}}</h3>
											<h5 v-if="this.categoria !== null">{{ this.categoria.tipo }}</h5>
										</div>
									</div>
								</div>
								<div class="col-sm-8">
									<div class="card-block">
										<h3 class="m-b-25 p-b-5 b-b-default f-w-600" id="title">Il mio profilo</h3>
										<div class="row">
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Email</p>
												<h6 class="text-muted f-w-400">{{ editing ? '' : email }}</h6>
												<input v-if="editing" v-model="editedEmail" class="form-control" @input="validateEmail" name="email" autocomplete="prova@email.it">
												<p v-if="emailError" class="text-danger" id="danger">{{ emailError }}</p>
											</div>
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Numero di telefono</p>
												<h6 class="text-muted f-w-400">{{ editing ? '' : '+39 ' + phoneNumber }}</h6>
												<input v-if="editing" v-model="editedPhoneNumber" class="form-control" @input="validatePhoneNumber" name="phoneNumber" autocomplete="0000000000">
												<p v-if="phoneNumberError" class="text-danger" id="danger">{{ phoneNumberError }}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Documento d'identità</p>
												<p v-if="path_documento === email + '.pdf'">Documento d'identità non caricato</p>
												<a v-else href="#" @click="idDocPath(this.email + '.pdf')">Download Doc.Identità</a>
												<input v-if="editing" type="file" ref="documento_identita" style="display: none" accept=".pdf" @change="handleFileChange('doc', $event)"/>
												<button @click="openFileInputDoc" v-if="editing" class="btn">
													<i class="bi bi-pencil" title="Modifica documento"></i>
												</button>
											</div>
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Curriculum</p>
												<p v-if="path_curriculum === email + '.pdf'">CV non caricato</p>
												<a v-else href="#" @click="cvPath(this.email + '.pdf')">Download CV</a>
												<input v-if="editing" type="file" ref="curriculum" style="display: none" accept=".pdf" @change="handleFileChange('cv', $event)"/>
												<button @click="openFileInputCv" v-if="editing" class="btn">
													<i class="bi bi-pencil" title="Modifica curriculum"></i>
												</button>
											</div>
										</div>
										<button v-if="!editing" class="btn btn-block btn-primary d-grid gap-2 col-6 mx-auto" type="button" @click="toggleEditing">Modifica</button>
										<div v-else class="d-flex justify-content-center">
											<button class="btn btn-primary mx-2 mx-md-3 mx-lg-5 flex-grow-1" type="button" @click="cancelEditing" id="annulla">Annulla</button>
											<button class="btn btn-primary mx-2 mx-md-3 mx-lg-5 flex-grow-1" type="button" @click="toggleEditing">Salva</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
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
		name: "ProfiloView",
		components: {
			BachecaNavBar,
			SuccessShower
		},
		data() {
			return {
				nome: '',
				cognome: '',
				categoria: '',
				curriculum: null,
				documento_identita: null,
				phoneNumber: '',
				phoneNumberError: '',
				email: '',
				emailError: '',
				path_documento: '',
				path_curriculum: '',
				editedPhoneNumber: '',
				editedEmail: '',
				editedAccount: false,
				editedIdentityDocument: false,
				temporaryIdentityDocument: null,
				editedCurriculum: false,
				temporaryCurriculum: null,
				editing: false,
				successMessage: ''
			};
		},
		mounted() {
			this.fetchInfoUtente();
		},
		methods: {
			// Funzione che restituisce le info di un utente per id
			async fetchInfoUtente() {
				await axios.get(`/api/utenti/utente/${this.$store.state.userId}`)
					.then(response => {
						console.log(response.data);
						this.nome = response.data.nome;
						this.cognome = response.data.cognome;
						this.categoria = response.data.categoria;
						this.phoneNumber = response.data.telefono;
						this.email = response.data.email;
						this.path_documento = response.data.path_documento;
						this.path_curriculum = response.data.path_curriculum;
						this.loadCvPath(this.email + '.pdf');
						this.loadIdDocPath(this.email + '.pdf');
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
			// Apre l'input file quando si fa clic sull'icona di modifica
			openFileInputDoc() {
				this.$refs.documento_identita.click();
			},
			openFileInputCv() {
				this.$refs.curriculum.click();
			},
			handleFileChange(identifier, event) {
				if (identifier === 'doc') {
					this.editedIdentityDocument = true;
					this.temporaryIdentityDocument = event.target.files[0];
				} else if (identifier === 'cv') {
					this.editedCurriculum = true;
					this.temporaryCurriculum = event.target.files[0];
				}
			},
			toggleEditing() {
				if (this.editing && this.phoneNumberError === '' && this.emailError === '') {
					if (this.phoneNumber !== this.editedPhoneNumber) {
						this.phoneNumber = this.editedPhoneNumber;
						this.editedAccount = true;
					}
					if (this.email !== this.editedEmail) {
						this.email = this.editedEmail;
						this.editedAccount = true;
					}
					if (this.editedIdentityDocument === true) {
						this.documento_identita = this.temporaryIdentityDocument;
					}
					if(this.editedCurriculum === true) {
						this.curriculum = this.temporaryCurriculum;
					}
					if (this.editedAccount === true) {
						this.setAccount();
					}
					if (this.editedIdentityDocument === true || this.editedCurriculum === true) {
						this.setDocuments();
					}
					if (this.editedAccount === true || this.editedIdentityDocument === true || this.editedCurriculum === true){
						this.successMessage ='Salvataggio dati profilo effettuato con successo!';
						this.$refs.succShower.toggle();
					}
				}
				else {
					this.emailError = '';
					this.phoneNumberError = '';
					this.editedEmail = this.email;
					this.editedPhoneNumber = this.phoneNumber;
					this.editedAccount = false;
					this.editedIdentityDocument = false;
					this.editedCurriculum = false;
				}
				this.editing = !this.editing;
			},
			// Funzione che effettua il download di un file
			downloadFile(file) {
				let pdfURL = null;
				// Create a URL for the blob response and open it in a new window for download
				pdfURL = URL.createObjectURL(file);
				// console.log(pdfURL);
				let downloadLink = document.createElement('a');
				// console.log(downloadLink);
				downloadLink.href = pdfURL;
				downloadLink.download = this.email + '.pdf';
				downloadLink.click();
				// Clean up the URL object after the download
				URL.revokeObjectURL(pdfURL);
			},
			// Funzione che salva il curriculum di un utente
			async loadCvPath(file) {
				await axios.get(`/api/amministratore/cv?fileName=${file}`,
					{
						responseType: "blob",
						headers: {
							Accept: 'application/pdf'
						}
					})
					.then(response => {
						this.curriculum = response.data;
					})
					.catch(errors => {
						console.log(errors);
					})
			},
			// Funzione che effettua il download del curriculum di un utente
			async cvPath(file) {
				if (this.editedCurriculum === true) {
					this.downloadFile(this.temporaryCurriculum);
				}
				else {
					await axios.get(`/api/amministratore/cv?fileName=${file}`,
						{
							responseType: "blob",
							headers: {
								Accept: 'application/pdf'
							}
						})
						.then(response => {
							this.curriculum = response.data;
							this.downloadFile(this.curriculum);
						})
						.catch(errors => {
							console.error(errors);
						})
				}
			},
			// Funzione che salva il documento d'identità di un utente
			async loadIdDocPath(file) {
				await axios.get(`/api/amministratore/id?fileName=${file}`,
					{
						responseType: "blob",
						headers: {
							Accept: 'application/pdf'
						}
					})
					.then(response => {
						this.documento_identita = response.data;
					})
					.catch(errors => {
						console.error(errors);
					})
			},
			// Funzione che effettua il download del documento d'identità di un utente
			async idDocPath(file) {
				if (this.editedIdentityDocument === true) {
					this.downloadFile(this.temporaryIdentityDocument);
				}
				else {
					await axios.get(`/api/amministratore/id?fileName=${file}`,
						{
							responseType: "blob",
							headers: {
								Accept: 'application/pdf'
							}
						})
						.then(response => {
							this.documento_identita = response.data;
							this.downloadFile(this.documento_identita);
						})
						.catch(errors => {
							console.error(errors);
						})
				}
			},
			// Funzione che fa il set dei documenti di un utente
			async setDocuments() {
				// Chiamata dei set dei documenti
				const formData = new FormData();
				formData.append('email', this.email);
				formData.append('doc', this.documento_identita);
				formData.append('cv', this.curriculum);

				await axios.post('/api/utenti/registrazione/update/Documents', formData,
					{
						headers: {
							'Content-Type': 'multipart/form-data'
						}
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
			// Funzione che fa il set delle modifiche di un utente
			async setAccount() {
				await axios.put(`/api/amministratore/modifica_dati/${this.$store.state.userId}`, {email: this.email, telefono: this.phoneNumber})
					.then(response => {
						console.log(response.data);
					})
					.catch(error => {
						if (error.response) {
							this.errorMessage = error.toString() || 'Errore inatteso';
							this.$refs.errShower.toggle();
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
			// Funzione che annulla le modifiche
			cancelEditing() {
				this.editing = false;
				this.phoneNumberError = '';
				this.emailError = '';
				this.editedAccount = false;
				this.editedCurriculum = false;
				this.editedIdentityDocument = false;
			},
			validatePhoneNumber() {
				if (!/^\d{10}$/.test(this.editedPhoneNumber)) {		/* Espressione regolare: ^\d{10}$ controlla che la stringa inizi (^) e finisca ($) con esattamente 10 cifre numeriche (\d). */
					this.phoneNumberError = 'Il numero di telefono deve avere esattamente 10 cifre.';
				} else {
					this.phoneNumberError = '';
				}
			},
			checkEmail(email) {
				const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
				return emailRegex.test(email);
			},
			validateEmail() {
				if (!this.editedEmail || !this.checkEmail(this.editedEmail)) {
					this.emailError = 'Inserisci un indirizzo email valido.';
				} else {
					this.emailError = '';
				}
			}
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background-color: #e9faf5;
		animation: fadeInUp 1s ease both;
	}

	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
	}

	.padding {
		padding: 3.5em 3em;
	}

	.img-fluid {
		max-width: 80%; /* Dimensione massima per schermi grandi */
	}

	.card {
		border-radius: 45px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
		border: none;
	}

	.card-block {
		align-items: center;
		justify-content: center;
	}

	#ombra {
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	#title {
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
		font-size: 2.5em;
	}

	@media (max-width: 575.5px) {
		.img-fluid {
			max-width: 40%; /* Dimensione massima per schermi piccoli */
		}
		#title {
			font-size: 2.3em;
		}
	}

	@media (min-width: 576px) and (max-width: 992px) {
		.card-block .row {
			margin-bottom: 20px;
			padding-bottom: 5px;
		}
		.card-block h5 {
			font-size: 18px;
		}
		#title {
			font-size: 2.3em;
		}
	}

	@media (min-width: 575.5px) and (max-width: 767.5px) {
		.img-fluid {
			min-width: 100%;
		}
		.card-block h3 {
			font-size: 20px;
		}
		.card-block h5 {
			font-size: 15px;
		}
		.card-block p {
			font-size: 15px;
		}
		#danger {
			font-size: 14px;
		}
		.card-block h6 {
			font-size: 14px;
		}
		.card-block .row {
			margin-bottom: 10px;
			padding-bottom: 2.5px;
		}
		#title {
			font-size: 2em;
		}
	}

	/* Stile per le scritte a schermo intero */
	@media (min-width: 992px) {
		.card-block h3 {
			font-size: 28px;
		}
		.card-block h5 {
			font-size: 20px;
		}
		.card-block p {
			font-size: 18px;
		}
		#danger {
			font-size: 16px;
		}
		.card-block h6 {
			font-size: 16px;
		}
		.card-block .row {
			margin-bottom: 40px;
			padding-bottom: 5px;
		}
	}

	.card-block {
		padding: 1.8em;
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

	.btn-primary {
		margin: 20px 0;
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

	.btn-primary:hover {
		background-color: #0d17c4ff;
	}

	#annulla {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#annulla:hover {
		background-color: #0d17c419;
	}

	.bg-c-lite-green {
		background: linear-gradient(to right, rgb(92, 109, 248), rgba(13, 147, 196, 0.8), rgba(44, 150, 123, 0.6), rgba(14, 147, 81, 0.8), #1ab209);
		min-height: 100%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
	}

	/* Parte preimpostata */
	.user-card-full {
		overflow: hidden;
	}

	.m-r-0 {
		margin-right: 0;
	}

	.m-l-0 {
		margin-left: 0;
	}

	.user-profile {
		padding: 20px 0;
	}

	.m-b-25 {
		margin-bottom: 25px;
	}

	@media only screen and (min-width: 1400px){
		p {
			font-size: 14px;
		}
	}

	.b-b-default {
		border-bottom: 1px solid #e0e0e0;
	}

	.p-b-5 {
		padding-bottom: 5px !important;
	}

	.m-b-10 {
		margin-bottom: 10px;
	}

	.text-muted {
		color: #646a70 !important;
	}

	.f-w-600 {
		font-weight: 600;
	}

	.bi-pencil {
		font-size: 1.2em;
	}
</style>
