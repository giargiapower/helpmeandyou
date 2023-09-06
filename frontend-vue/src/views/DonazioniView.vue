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

										<form ref="form" @reset="gestisciReset" @submit="onSubmit">
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
												<div class="input-group mb-3">
													<span class="input-group-text">Importo</span>
													<input type="number" class="form-control" aria-label="Importo" aria-describedby="Importo"  min="0" step="1" required>
													<span class="input-group-text">€</span>
												</div>

												<div class="d-flex justify-content-center">
													<button class="btn btn-primary mx-2 flex-grow-1" type="reset" id="annulla">Annulla</button>
													<!-- Button trigger modal -->
<!--													<button class="btn btn-primary mx-2 flex-grow-1" type="submit" data-bs-toggle="modal" data-bs-target="#inviaDenaro">Invia denaro</button>-->
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

<!--	TODO: da sistemare: vogliamo che compaia solo se lo status della chiamata al backend "Invia Denaro" va a buon fine :)-->
	<!-- Modal -->
<!--	<div class="modal fade" id="inviaDenaro" tabindex="-1" aria-labelledby="inviaDenaroLabel" aria-hidden="true">-->
<!--		<div class="modal-dialog">-->
<!--			<div class="modal-content">-->
<!--				<div class="modal-header">-->
<!--					<h1 class="modal-title fs-5" id="inviaDenaroLabel">Grazie per la tua donazione!</h1>-->
<!--					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>-->
<!--				</div>-->
<!--				<div class="modal-body">-->
<!--					I tuoi fondi contribuiranno all'acquisto di materiali per il progetto HelpMe&You.-->
<!--				</div>-->
<!--				<div class="modal-footer">-->
<!--					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>-->
<!--					<button type="button" class="btn btn-primary">Save changes</button>-->
<!--				</div>-->
<!--			</div>-->
<!--		</div>-->
<!--	</div>-->



<!--	<div class="donazioni">-->
<!--		<home-nav-bar />-->
<!--		<h1>Donazioni</h1>-->
<!--		<form @reset="gestisciReset" @submit="onSubmit">-->
<!--			<table>-->
<!--				<h2>Inserisci i dati della tua carta di credito</h2>-->
<!--				<tbody>-->
<!--				<tr>-->
<!--					<td><label for="nome">Nome:</label></td>-->
<!--					<td><input type="text" id="nome" name="nome" required></td>-->
<!--					<td><label for="cognome">Cognome:</label></td>-->
<!--					<td><input type="text" id="cognome" name="cognome" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="numcarta">Numero carta:</label></td>-->
<!--					<td><input type="text" id="numcarta" name="numcarta" required></td>-->
<!--					<td><label for="cvv">CVV:</label></td>-->
<!--					<td><input type="text" id="cvv" name="cvv" required></td>-->
<!--				</tr>-->
<!--				<tr>-->
<!--					<td><label for="datascadenza">Data di scandenza:</label></td>-->
<!--					<td><input type="date" id="datascadenza" name="datascadenza" required></td>-->
<!--					<td><label for="importo">Importo donazione:</label></td>-->
<!--					<td><input type="text" id="importo" name="importo" required></td>-->
<!--				</tr>-->
<!--				</tbody>-->
<!--			</table>-->
<!--			<input type="reset" value="Annulla"> |-->
<!--			<input type="submit" value="Invia denaro">-->
<!--		</form>-->
<!--	</div>-->
</template>

<script>
	import HomeNavBar from "@/components/HomeNavBar";
	import SuccessShower from "@/components/SuccessShower";

	export default {
		name: "DonazioniView",
		components: {
			HomeNavBar,
			SuccessShower
		},
		data() {
			return {
				successMessage: ''
			}
		},
		methods: {
			gestisciReset() {
				this.$refs.form.reset();
				this.$router.push('/');
			},
			onSubmit() {
				this.successMessage ='Grazie per la tua donazione!\n I tuoi fondi contribuiranno all\'acquisto di materiali per il progetto HelpMe&You.';
				this.$refs.succShower.toggle();
				this.$refs.form.reset();
				this.$router.push('/');
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
		/*height: 100vh;*/
		/*display: flex;*/
		/*align-items: center;*/
		/*justify-content: center;*/
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

	/* Nascondi le frecce nell'input di tipo "number" */
	input[type="number"]::-webkit-inner-spin-button,
	input[type="number"]::-webkit-outer-spin-button {
		-webkit-appearance: none;
		margin: 0;
	}
	input[type="number"] {
		-moz-appearance: textfield; /* Nascondi frecce su Firefox */
	}

</style>
