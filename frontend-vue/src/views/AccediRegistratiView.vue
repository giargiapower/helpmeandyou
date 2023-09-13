<template>
	<div class="home-container">
		<home-nav-bar/>
		<div class="hero-section">
			<div class="hero-content">
				<form @submit.prevent="onSubmitLogin" ref="form">
					<fieldset>
						<legend>Login</legend>
						<div class="mb-3">
							<input class="form-control" ref="email" type="email" id="email" name="email" v-model="email" @value="this.email" placeholder="Email" required>
						</div>
						<div class="mb-3">
							<input class="form-control" ref="password" type="password" id="password" name="password" v-model="password" @value="this.password" placeholder="Password" required>
						</div>
						<!-- Collega AccediRegistratiView con BachecaView -->
						<button class="btn btn-block btn-primary" type="submit">Accedi</button>
					</fieldset>
				</form>

				<div class="alternative-login">
					<div class="or-container">
						<div class="line-separator"></div>
						<div class="or-label">oppure</div>
						<div class="line-separator"></div>
					</div>
					<button class="btn btn-block btn-primary" type="submit" id="google-button" @click="googleLogin">
						<i class="bi bi-google"></i>
						Continua con Google
					</button>
				</div>

				<div class="divider"/>

				<div class="register-option">
					<div class="or-label">Non hai ancora un account? Registrati ora!</div>
					<!-- Button trigger modal -->
					<button type="button" class="btn btn-block btn-primary" id="chiaro-button" data-bs-toggle="modal" data-bs-target="#registrazioneModal">Registrati</button>
				</div>
			</div>
		</div>
	</div>


	<!-- Modal -->
	<div class="modal fade" id="registrazioneModal" data-bs-backdrop="static" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="registrazioneModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-xl">
			<div class="modal-content">
				<form ref="form" @reset="gestisciReset" @submit.prevent="onSubmitRegistrazione">
					<fieldset>
						<div class="modal-body">
							<legend class="custom-legend">
								<span class="legend-text">Registrazione: inserisci i tuoi dati</span>
								<button type="reset" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
							<p class="text-muted">Nota: Per effettuare la registrazione occorre essere maggiorenni</p>

							<div class="divider2"/>

							<div class="input-group mb-3">
								<span class="input-group-text">Nome</span>
								<input type="text" class="form-control" v-model="nome" aria-label="Nome" aria-describedby="Nome" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Cognome</span>
								<input type="text" class="form-control" v-model="cognome" aria-label="Cognome" aria-describedby="Cognome" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Data di nascita</span>
								<input type="date" class="form-control" :max="maxDate" aria-label="Data nascita" aria-describedby="Data nascita" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Numero di telefono</span>
								<span class="input-group-text">+39</span>
								<input type="text" class="form-control" v-model="telefono" minlength="10" maxlength="10" aria-label="Numero telefono" aria-describedby="Numero telefono" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Email</span>
								<input type="email" class="form-control" v-model="email" aria-label="Email" aria-describedby="Email" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Password</span>
								<input type="password" class="form-control" v-model="password" aria-label="Password" aria-describedby="Password" pattern=".{8,}" title="La password deve avere almeno 8 caratteri" required>
							</div>
<!--								TODO: io toglierei il domicilio all'utente e lo lascerei solo alla richiesta. Se vuole l'utente filtra la regione della richiesta e stop-->
							<div class="input-group mb-3">
								<span class="input-group-text">Domicilio(?)</span>
								<input type="text" class="form-control" v-model="indirizzo" aria-label="Domicilio" aria-describedby="Domicilio" required>
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Documento d'identità</span>
								<input type="file" class="form-control" ref="documento_identita" @change="handleFileChangeDoc" aria-label="Documento d'identità" aria-describedby="Documento d'identità" required>
<!--								Volendo qui sopra aggiungere "accept="application/pdf" se vogliamo solo pdf-->
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Curriculum</span>
								<input type="file" class="form-control" ref="cv" @change="handleFileChangeCv" aria-label="Curriculum" aria-describedby="Curriculum" required>
<!--								Volendo qui sopra aggiungere "accept="application/pdf" se vogliamo solo pdf-->
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
	<ErrorShower ref="errShower" :message="errorMessage"/>
	<SuccessShower ref="succShower" :message="successMessage"/>



<!--	<div class="accedi-registrati">-->
<!--		<form @submit="onSubmit" ref="form">-->
<!--			<fieldset>-->
<!--				<legend>Login</legend>-->
<!--				<label for="email">Email:</label>-->
<!--				<input type="email" @value="this.email" required>-->

<!--				<label for="password">Password:</label>-->
<!--				<input type="password" @value="this.password" required>-->

<!--				&lt;!&ndash; Collega AccediRegistratiView con BachecaView &ndash;&gt;-->
<!--				<input type="submit" value="Accedi">-->
<!--				&lt;!&ndash; Collega AccediRegistratiView con RegistrazioneView &ndash;&gt;-->
<!--				<input type="button" value="Registrati" @click="onClick">-->
<!--			</fieldset>-->
<!--		</form>-->
<!--	</div>-->
</template>

<script>
	import HomeNavBar from "@/components/HomeNavBar";
	import ErrorShower from "@/components/ErrorShower.vue";
	import SuccessShower from "@/components/SuccessShower";
	import axios from "axios";
	import { googleTokenLogin } from "vue3-google-login";

	export default {
		name: "AccediRegistratiView",
		components: {
			HomeNavBar,
			ErrorShower,
			SuccessShower
		},
		data() {
			return {
				email: '',
				password: '',
				nome: '',
				cognome: '',
				telefono: null,
				indirizzo: '',
				cv: null,
				documento_identita: null,
				maxDate: this.calculateMaxDate(),
				errorMessage: '',
				successMessage: ''
			}
		},
		computed() {
			this.onSubmitLogin();
			this.onSubmitRegistrazione();
		},
		methods: {
			handleFileChangeDoc(event) {
				this.documento_identita = event.target.files[0];
			},
			handleFileChangeCv(event) {
				this.cv = event.target.files[0];
			},
			// Funione che verifica le credenziali del login. Per funzionare, da GET è diventata una POST
			async onSubmitLogin() {
				try {
					await axios.post('api/utenti/login', {email: this.email, password: this.password})
						.then(response => {
							console.log(response.data);
							this.$refs.form.reset();
							this.$store.commit('setUserId', response.data.id);
							this.$router.push('../accedi-registrati/bacheca');
						})
						.catch(error => {
							// Handle the error
							this.errorMessage = 'Credenziali errate! Riprova o registrati.';
							this.$refs.errShower.toggle();
							if (error.response) {
								// The request was made and the server responded with a status code
								console.error('Response Data:', error.response.data);
								console.error('Response Status:', error.response.status);
								console.error('Response Headers:', error.response.headers);
							} else if (error.request) {
								// The request was made but no response was received
								console.error('No response received:', error.request);
							} else {
								// Something happened in setting up the request that triggered an error
								console.error('Error:', error.message);
							}
						})
				} catch (e) {
					this.errorMessage = e.toString() || 'Errore inatteso';
					this.$refs.errShower.toggle();
					console.log(e);
				}
			},

			googleLogin() {
				googleTokenLogin()
					.then(async (response) => {

						const res = await fetch(
							"https://www.googleapis.com/oauth2/v3/userinfo?access_token=" + response.access_token, {
								method: "GET"
							}
						);

						const user = await res.json();
						// console.log(user);
						this.email = user.email.toString();
						this.nome = user.given_name.toString();
						this.cognome = user.family_name.toString();

						await axios.post('api/utenti/google-login', {email: this.email})
							.then(response => {
								console.log(response.data);
								this.$refs.form.reset();
								this.$store.commit('setUserId', response.data.id);
								this.$router.push('../accedi-registrati/bacheca');							})
							.catch(error => {
								this.errorMessage = 'Credenziali errate! Riprova o registrati.';
								this.$refs.errShower.toggle();
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
					})
					.catch(error => {
						if (error.toString() !== 'Error: Popup window closed'){
							this.errorMessage = error.toString() || 'Errore inatteso';
							this.$refs.errShower.toggle();
							console.log(error)
						}
					});
			},

			// Funziona che permette a un utente di registrarsi. Per funzionare si è dovuto aggiungere il dominio del
			// frontend "http://localhost:8080" alle "origins" cel controller in AccountController
			// TODO: manca il set dei documenti, per ora si lasciano vuoti
			async onSubmitRegistrazione() {
				await axios.post('/api/utenti/registrazione/create/account',
					{
						email: this.email,
						password: this.password,
						nome: this.nome,
						cognome: this.cognome,
						telefono: this.telefono,
						indirizzo: this.indirizzo,
						stato: 'da_approvare',
						cv: this.cv,
						documento_identita: this.documento_identita
					},
					{
						headers: {
							'Content-Type': "application/json",
							'responseType': 'json'
						}
					})
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
					});

				// Chiamata dei set dei documenti
				const formData = new FormData();
				formData.append('email', this.email);
				formData.append('doc', this.documento_identita);
				formData.append('cv', this.cv);

				await axios.post('/api/utenti/registrazione/create/Documents', formData,
					{
						headers: {
							'Content-Type': 'multipart/form-data'
						}
					})
					.then(response => {
						console.log(response.data);
						this.successMessage ='Registrazione completata!\nGrazie per esserti registrato. Entro qualche giorno un operatore approverà il tuo account così da poter iniziare ad usare il servizio HelpMe&You!';
						this.$refs.succShower.toggle();
						// Ritardo di 5 secondi (5000 millisecondi)
						setTimeout(function() {
							window.location.href = '/';
						}, 5000);
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
			gestisciReset() {
				this.$refs.form.reset();
			},
			calculateMaxDate() {
				const currentDate = new Date();
				const maxYear = currentDate.getFullYear() - 18; // Calcola la data massima per avere almeno 18 anni
				const maxMonth = currentDate.getMonth() + 1; // Aggiunge 1 perché i mesi in JavaScript sono indicizzati da 0
				const maxDay = currentDate.getDate();

				return `${maxYear}-${maxMonth.toString().padStart(2, '0')}-${maxDay.toString().padStart(2, '0')}`;
			}
		}
	}
</script>

<style scoped>
	.home-container {
		animation: fadeInUp 1s ease both; /* Applica l'animazione a tutta la pagina */
	}

	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
		height: 100vh;
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.hero-content {
		background-color: #ffffff;
		border-radius: 45px;
		padding: 1.5em 3em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	legend {
		font-weight: bold;
		font-size: 2em;
		color: black;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
	}

	p {
		margin-bottom: 2em;
	}

	.mb-3 {
		text-align: start;
	}

	.btn-primary {
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

	.btn-primary:hover {
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

	.divider2 {
		border: none;
		border-top: 1px solid rgba(204, 204, 204, 0.6);
		margin-bottom: 33px;
		margin-top: -10px;
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
	.modal-content {
		background-color: #ffffff;
		border-radius: 45px;
		padding: 1.5em 3em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	form {
		background-color: #ffffff; /* Aggiunge stile di sfondo per tutta la pagina */
	}

	.custom-legend {
		display: flex;
		align-items: center;
	}

	.legend-text {
		flex-grow: 1;
		text-align: center;
		color: black;
	}

	.btn-close {
		align-self: flex-start;
	}
</style>
