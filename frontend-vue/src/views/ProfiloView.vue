<template>
	<div class="home-container">
		<bacheca-nav-bar :id-utente="idUtente"/>
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
											<h3 class="f-w-600">Nome Cognome</h3>
											<h5>Categoria se c'è (?)</h5>
										</div>
									</div>
								</div>
								<div class="col-sm-8">
									<div class="card-block">
										<h3 class="m-b-25 p-b-5 b-b-default f-w-600" id="title">Il mio profilo</h3>
										<div class="row">
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Data di nascita</p>
												<h6 class="text-muted f-w-400">....data....</h6>
											</div>
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Numero di telefono</p>
												<h6 class="text-muted f-w-400">{{ editing ? '' : '+39 ' + phoneNumber }}</h6>
												<input v-if="editing" v-model="editedPhoneNumber" class="form-control" @input="validatePhoneNumber">
												<p v-if="phoneNumberError" class="text-danger" id="danger">{{ phoneNumberError }}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Email</p>
												<h6 class="text-muted f-w-400">{{ editing ? '' : email }}</h6>
												<input v-if="editing" v-model="editedEmail" class="form-control" @input="validateEmail">
												<p v-if="emailError" class="text-danger" id="danger">{{ emailError }}</p>
											</div>
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Domicilio(?)</p>
												<h6 class="text-muted f-w-400">{{ editing ? '' : domicile }}</h6>
												<input v-if="editing" v-model="editedDomicile" class="form-control">
											</div>
										</div>
										<div class="row">
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Documento d'identità</p>
<!--												TODO: volendo mettere il link per il download qui sotto come fatto altrove-->
												<h6 class="text-muted f-w-400">{{ editing ? '' : identityDocument }}</h6>
<!--												<input v-if="editing" v-model="editedIdentityDocument" type="file" class="form-control">-->
												<input v-if="editing" type="file" class="form-control">
											</div>
											<div class="col-sm-6">
												<p class="m-b-10 f-w-600">Curriculum</p>
												<!--												TODO: volendo mettere il link per il download qui sotto come fatto altrove-->
												<h6 class="text-muted f-w-400">{{ editing ? '' : curriculum }}</h6>
<!--												<input v-if="editing" v-model="editedCurriculum" type="file" class="form-control">-->
												<input v-if="editing" type="file" class="form-control">
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



<!--	<div class="profilo">-->
<!--		<bacheca-nav-bar></bacheca-nav-bar>-->
<!--		<h2>Profilo</h2>-->
<!--		<form @reset="gestisciReset" @submit="onSubmit">-->
<!--			<table>-->
<!--				<tbody>-->
<!--				<tr>-->
<!--					<td><label for="nome">Nome:</label></td>-->
<!--					<td><input type="text" id="nome" name="nome" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="cognome">Cognome:</label></td>-->
<!--					<td><input type="text" id="cognome" name="cognome" required></td>-->
<!--					<td>-->
<!--						<p>Documento d'identità:</p>-->
<!--						<img :src="imageUrl" alt="Immagine caricata">-->
<!--					</td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="data-nascita">Data di nascita:</label></td>-->
<!--					<td><input type="date" id="data-nascita" name="data-nascita" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="telefono">Telefono:</label></td>-->
<!--					<td><input type="text" id="telefono" name="telefono" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="domicilio">Domicilio:</label></td>-->
<!--					<td><input type="text" id="domicilio" name="domicilio" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="categorie">Categoria/e:</label></td>-->
<!--					<td><input type="text" id="categorie" name="categorie" required></td>-->
<!--				</tr>-->
<!--				</tbody>-->
<!--			</table>-->
<!--			<br>-->
<!--			<input type="reset" value="Annulla"> |-->
<!--			<input type="submit" value="Conferma i tuoi dati">-->
<!--		</form>-->
<!--	</div>-->
</template>

<script>
	import BachecaNavBar from "@/components/BachecaNavBar";
	import SuccessShower from "@/components/SuccessShower";

	export default {
		name: "ProfiloView",
		components: {
			BachecaNavBar,
			SuccessShower
		},
		data() {
			return {
				// 	imageUrl: 'C:/Users/serra/Desktop/ProgettoTAASS/helpmeandyou/frontend-vue/src/assets/logo.png'
				// TODO: questi dati sotto vanno presi con le get dal backend
				phoneNumber: '1234567890',
				phoneNumberError: '',
				email: 'ciccio@pasticcio.com',
				emailError: '',
				domicile: 'Via delle vie, 1',
				identityDocument: 'Carta d\'identità',
				curriculum: 'Curriculum',
				editedPhoneNumber: '1234567890',
				editedEmail: 'ciccio@pasticcio.com',
				editedDomicile: 'Via delle vie, 1',
				editedIdentityDocument: 'Carta d\'identità',	// dato di tipo file
				editedCurriculum: 'Curriculum',		// dato di tipo file
				editing: false,
				idUtente: this.$store.state.userId,
				successMessage: ''
			};
		},
		methods: {
			// onSubmit() {
			// 	alert("Salvataggio dati profilo effettuato con successo!");
			// 	this.$router.push('/accedi-registrati/bacheca');
			// },
			// gestisciReset() {
			// 	this.$router.push('/accedi-registrati/bacheca');
			// },
			toggleEditing() {
				if (this.editing && this.phoneNumberError === '' && this.emailError === '') {
					// TODO: eseguire qui azioni di salvataggio: invio dati al backend :) (solo in caso di eventuali modifiche)
					this.phoneNumber = this.editedPhoneNumber;
					this.email = this.editedEmail;
					this.domicile = this.editedDomicile;
					this.identityDocument = this.editedIdentityDocument;
					this.curriculum = this.editedCurriculum;

					// se il salvataggio è tutto ok, allora:
					this.successMessage ='Salvataggio dati profilo effettuato con successo!';
					this.$refs.succShower.toggle();
				}
				else {
					this.phoneNumberError = '';
					this.editedPhoneNumber = this.phoneNumber;
					this.editedEmail = this.email;
					this.emailError = '';
					this.editedDomicile = this.domicile;
					this.editedIdentityDocument = this.identityDocument;
					this.editedCurriculum = this.curriculum;
				}
				this.editing = !this.editing;
			},
			cancelEditing() {
				this.editing = false;
				this.phoneNumberError = '';
				this.emailError = '';
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
			margin-bottom: 20px;
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
		/*centra il contenuto*/
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
		color: #919aa3 !important;
	}

	.f-w-600 {
		font-weight: 600;
	}
</style>
