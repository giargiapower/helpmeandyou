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
					<button class="btn btn-block btn-primary" type="submit" id="google-button">
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
								<input type="file" class="form-control" v-on="documento_identita" aria-label="Documento d'identità" aria-describedby="Documento d'identità">
<!--								Volendo qui sopra aggiungere "accept="application/pdf" se vogliamo solo pdf-->
							</div>
							<div class="input-group mb-3">
								<span class="input-group-text">Curriculum</span>
								<input type="file" class="form-control" v-on="cv" aria-label="Curriculum" aria-describedby="Curriculum">
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
	import axios from "axios";

	export default {
		name: "AccediRegistratiView",
		components: {
			HomeNavBar
		},
		methods: {
			// TODO: per il momento non va, probabile debba essere una POST, ma dà 403 error e problemi di CORS
			async onSubmitLogin() {
        // await axios.get('api/utenti/login',
		// 	{
		// 		params: {
		// 			account: {
		// 				email: this.email,
		// 				password: this.password
		// 			}
		// 		}
        //     })
        //     .then(response => {
        //       console.log(response.data);
        //       this.$refs.form.reset();
        //       this.$router.push('../accedi-registrati/bacheca');
            // })
            // .catch(error => {
            //   console.log(error);
            // })
      },
			// Funziona che permette a un utente di registrarsi
			// per funzionare si è dovuto aggiungere il dominio del frontend "http://localhost:8080" alle "origins" cel controller
			// in AccountController
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
					// TODO: fare l'alert più carino
					alert("Registrazione completata!\nGrazie per esserti registrato. Entro qualche giorno un operatore approverà il tuo account così da poter iniziare ad usare il servizio HelpMe&You!");
					console.log(response.data);
					// this.$refs.form.reset();
					// this.$router.push('/');
					// TODO: vedi se riesci a migliorare, altrimenti lasciamo questo sotto
					window.location.href = '/';
				})
					.catch(error => {
						// Handle the error
						if (error.response) {
							// The request was made and the server responded with a status code
							alert(error.response.data);
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
			},
			gestisciReset() {
				this.$refs.form.reset();
			},
			// onClick() {
			// 	this.$refs.form.reset();
			// 	// this.$router.push('../accedi-registrati/registrazione');
			// },
			calculateMaxDate() {
				const currentDate = new Date();
				const maxYear = currentDate.getFullYear() - 18; // Calcola la data massima per avere almeno 18 anni
				const maxMonth = currentDate.getMonth() + 1; // Aggiungi 1 perché i mesi in JavaScript sono indicizzati da 0
				const maxDay = currentDate.getDate();

				return `${maxYear}-${maxMonth.toString().padStart(2, '0')}-${maxDay.toString().padStart(2, '0')}`;
			}
		},
		data() {
			return {
				email: '',
				password: '',
				nome: '',
				cognome:'',
				telefono: null,
				indirizzo: '',
				cv: null,
				documento_identita: null,
				maxDate: this.calculateMaxDate()
			}
		},
		computed() {
			// this.onSubmitLogin();
			this.onSubmitRegistrazione();
		}
	}
</script>

<style scoped>
	.home-container {
		animation: fadeInUp 1s ease both;	/* Applica l'animazione a tutta la pagina */
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
		color: black;
	}

	.btn-close {
		align-self: flex-start;
	}
</style>
