<template>
	<div class="home-container">
		<home-nav-bar/>
		<div class="hero-section">
			<div class="container">
				<div class="row d-flex justify-content-center align-items-center">
					<div class="col-xl-10">
						<div class="card rounded-5">
							<div class="row g-0">
								<div class="col-lg-6" id="cardParteSx">
									<div class="card-body px-3 py-4 p-md-5">
										<div class="text-center">
											<img src="@/assets/logo_app-removebg.png" style="width: 200px;" alt="logo">
											<h4 class="mt-1 mb-3 pb-1">Effettua una donazione</h4>
										</div>

										<form ref="form" @submit.prevent="onSubmit">
											<fieldset>
												<legend>Inserisci i dati della tua carta di credito</legend>
												<div class="input-group mb-3">
													<span class="input-group-text">Nome</span>
													<input type="text" class="form-control" aria-label="Nome intestatario carta" aria-describedby="Nome intestatario carta" required>
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text">Cognome</span>
													<input type="text" class="form-control" aria-label="Cognome intestatario carta" aria-describedby="Cognome intestatario carta" required>
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text">Numero carta</span>
													<input type="text" minlength="16" maxlength="16" class="form-control" aria-label="Numero carta" aria-describedby="Numero carta" required>
													<span class="input-group-text text-muted">
														<i class="bi bi-credit-card"></i>
													</span>
												</div>
												<div class="input-group mb-3">
													<span class="input-group-text">Data scadenza</span>
													<input type="text" placeholder="MM/YY" minlength="5" maxlength="5" class="form-control" aria-label="Data scadenza" aria-describedby="Data scadenza" id="datascadenza" required>

													<span class="input-group-text ms-4" id="cvv">CVV</span>
													<input type="text" minlength="3" maxlength="3" class="form-control" aria-label="CVV" aria-describedby="CVV" required>
												</div>
												<p v-if="expiryDateError" class="text-danger" id="danger">{{ expiryDateError }}</p>
												<div class="input-group mb-3">
													<span class="input-group-text">Importo</span>
													<input type="number" class="form-control" v-model="importo" aria-label="Importo" aria-describedby="Importo"  min="0" step="1" required>
													<span class="input-group-text">€</span>
												</div>

												<div class="d-flex justify-content-center">
													<button class="btn btn-primary mx-2 flex-grow-1" type="button" id="annulla" @click = "gestisciReset">Annulla</button>
													<button class="btn btn-primary mx-2 flex-grow-1" type="submit">Invia denaro</button>
												</div>
											</fieldset>
										</form>
									</div>
								</div>

								<div class="col-lg-6 d-flex align-items-center gradient-custom-2" id="cardParteDx">
									<div class="text-white px-3 py-4 p-md-5 mx-md-4">
										<h4 class="mb-4">Siamo più di una semplice idea!</h4>
										<p>
											Insieme possiamo fare la differenza. Grazie al tuo sostegno possiamo rafforzare i nostri magazzini, espandere
											la nostra rete di aiuto e offrire nuove opportunità a chi ne ha bisogno. Unisciti a noi nella missione di costruire
											un mondo migliore attraverso l'aiuto reciproco e la condivisione.
										</p>
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
	import HomeNavBar from "@/components/HomeNavBar";
	import SuccessShower from "@/components/SuccessShower";
	import axios from "axios";

	export default {
		name: "DonazioniView",
		components: {
			HomeNavBar,
			SuccessShower
		},
		data() {
			return {
				importo: '',
				successMessage: '',
				expiryDateError: ''
			}
		},
		methods: {
			gestisciReset() {
				this.$refs.form.reset();
				this.$router.push('/');
			},
			verificaDataScadenza() {
				// Si ottiene il mese e l'anno correnti
				const oggi = new Date();
				const annoCorrente = oggi.getFullYear().toString();
				const meseCorrente = oggi.getMonth() + 1; // Nota: getMonth restituisce un valore da 0 a 11

				// Si estrae il mese e l'anno inseriti dall'utente
				const inputData = document.getElementById('datascadenza').value;

				const regex = /^(0[1-9]|1[0-2])\/\d{2}$/; // Regex per il formato MM/YY

				if (!regex.test(inputData)) {
					this.expiryDateError = 'Il formato della data deve essere MM/YY';
					return false;
				}

				const [inputMese, inputAnno] = inputData.split('/');
				const meseInserito = parseInt(inputMese, 10);
				const annoInserito = "20" + parseInt(inputAnno, 10);

				if (meseInserito < 1 || meseInserito > 12) {
					this.expiryDateError = 'Il formato della data deve essere MM/YY';
					return false;
				}

				// Confronta il mese e l'anno
				if (annoInserito < annoCorrente || (annoInserito === annoCorrente && meseInserito <= meseCorrente)) {
					this.expiryDateError = 'La data di scadenza della carta è scaduta';
					return false;
				} else {
					// La data è valida
					// console.log('La data è valida.');
					return true;
				}
			},
			async onSubmit() {
				if (this.verificaDataScadenza()){
					// console.log('Importo: ' + this.importo)
					await axios.put('http://localhost:32000/api/v1/conto/add/1',
						{
							saldo: this.importo
						},
						{
							headers: {
								'Content-Type': "application/json",
								'responseType': 'json'
							}
						})
						.then(response => {
							console.log(response.data);
							this.successMessage ='Grazie per la tua donazione!\n I tuoi fondi contribuiranno all\'acquisto di materiali per il progetto HelpMe&You.';
							this.$refs.succShower.toggle();
							this.$refs.form.reset();
							this.$router.push('/');
						})
						.catch(error => {
							if(error.response) {
								console.error('Response Data:', error.response.data);
								console.error('Response Status:', error.response.status);
								console.error('Response Headers:', error.response.headers);
							} else if(error.request) {
								console.error('No response received:', error.request);
							} else {
								console.error('Error:', error.message);
							}
						})
				}
			}
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background-color: #e9faf5;	  /* Aggiunge stile di sfondo per tutta la pagina */
		animation: fadeInUp 1s ease both;	/* Applica l'animazione a tutta la pagina */
	}

	.hero-section {
		background: url("@/assets/home-background.jpg") center/cover no-repeat;
	}

	.rounded-5{
		padding: 3.5em 3em;
		background-color: transparent;
		border: none;
	}

	h4 {
		font-weight: bold;
		font-size: 2em;
		margin-bottom: 1em;
		text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2);
	}

	.gradient-custom-2 {
		/*background: linear-gradient(to right, #ee7724, #d8363a, #dd3675, #b44593);*/
		background: linear-gradient(to right, #5c6df8, #0d93c4, #2c967b, #0e9351, #1ab209);
	}

	#cardParteSx{
		padding: 10px;
		background-color: #ffffff;
		border-top-left-radius: 32px;
		border-top-right-radius: 32px;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	#cardParteDx{
		padding: 10px;
		background-color: #ffffff;
		border-bottom-right-radius: 32px; /* Smussa l'angolo inferiore sinistro */
		border-bottom-left-radius: 32px; /* Smussa l'angolo inferiore destro */
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	@media (min-width: 62em) {
		/*Stili per schermi più grandi */
		#cardParteSx{
			border-top-right-radius: 0;
			border-bottom-left-radius: 32px;
		}
		#cardParteDx {
			border-bottom-left-radius: 0;
			border-top-right-radius: 32px; /* Rimuovi smussatura angolo superiore destro */
			border-bottom-right-radius: 32px; /* Mantieni smussatura angolo inferiore destro */
		}
	}

	#datascadenza{
		border-top-right-radius: 0.4em;
		border-bottom-right-radius: 0.4em;
	}

	#cvv{
		border-top-left-radius: 0.4em;
		border-bottom-left-radius: 0.4em;
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

	#annulla {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#annulla:hover {
		background-color: #0d17c419;
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

	/* Nasconde le frecce nell'input di tipo "number" */
	input[type="number"]::-webkit-inner-spin-button,
	input[type="number"]::-webkit-outer-spin-button {
		-webkit-appearance: none;
		margin: 0;
	}
	input[type="number"] {
		-moz-appearance: textfield; /* Nascondi frecce su Firefox */
	}

</style>
