<template>
	<bacheca-nav-bar/>
	<!-- Section: Design Block -->
	<div class="home-container">
		<section class="text-center">
			<!-- Background image -->
			<div class="p-5 bg-image"/>
			<!-- Background image -->
			<div class="col-lg-10 mx-auto">
				<div class="card mx-4 shadow-5-strong">
					<div class="card-body py-5">
						<div class="row d-flex justify-content-center">
							<div class="col-lg-10">
								<h1 class="fw-bold mb-5">Le mie attività</h1>
								<div class="accordion mb-3" id="accordionExample">
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
												Richieste accettate
											</button>
										</h2>
										<div id="collapseOne" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<div class="table-responsive">
													<table class="table table-hover">
														<thead>
															<tr>
																<th scope="col">#</th>
																<th scope="col">Data</th>
																<th scope="col">Richiedente</th>
																<th scope="col">Categoria</th>
																<th scope="col">Materiale</th>
																<th scope="col"></th>
															</tr>
														</thead>
														<tbody class="table-group-divider">
															<tr>
																<th scope="row">1</th>
																<td>data1</td>
																<td>ric1</td>
																<td>Nessuna categoria</td>
																<td>Nessun materiale</td>
																<td>
																	<!-- Button trigger modal -->
																	<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
																</td>
															</tr>
															<tr>
																<th scope="row">2</th>
																<td>data2</td>
																<td>ric2</td>
																<td>cat2</td>
																<td>mat2</td>
																<td>
																	<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
																</td>
															</tr>
														</tbody>
													</table>
												</div>
											</div>
										</div>
									</div>
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
												Richieste pubblicate
											</button>
										</h2>
										<div id="collapseTwo" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<table class="table table-hover">
													<thead>
													<tr>
														<th scope="col">#</th>
														<th scope="col">Data</th>
														<th scope="col">Richiedente</th>
														<th scope="col">Categoria</th>
														<th scope="col">Materiale</th>
														<th scope="col"></th>
													</tr>
													</thead>
													<tbody class="table-group-divider">
													<tr>
														<th scope="row">1</th>
														<td>data1</td>
														<td>ric1</td>
														<td>Nessuna categoria</td>
														<td>Nessun materiale</td>
														<td>
															<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
														</td>
													</tr>
													<tr>
														<th scope="row">2</th>
														<td>data2</td>
														<td>ric2</td>
														<td>cat2</td>
														<td>mat2</td>
														<td>
															<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
														</td>
													</tr>
													</tbody>
												</table>
											</div>
										</div>
									</div>
									<div class="accordion-item">
										<h2 class="accordion-header">
											<button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
												Richieste concluse
											</button>
										</h2>
										<div id="collapseThree" class="accordion-collapse collapse" data-bs-parent="#accordionExample">
											<div class="accordion-body">
												<table class="table table-hover">
													<thead>
														<tr>
															<th scope="col">#</th>
															<th scope="col">Data</th>
															<th scope="col">Richiedente</th>
															<th scope="col">Categoria</th>
															<th scope="col">Materiale</th>
															<th scope="col"></th>
															<th scope="col"></th>
														</tr>
													</thead>
													<tbody class="table-group-divider">
														<tr>
															<th scope="row">1</th>
															<td>data1</td>
															<td>ric1</td>
															<td>Nessuna categoria</td>
															<td>Nessun materiale</td>
															<td>
																<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
															</td>
															<td>
																<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#segnalaModal">Segnala</button>
															</td>
														</tr>
														<tr>
															<th scope="row">2</th>
															<td>data2</td>
															<td>ric2</td>
															<td>cat2</td>
															<td>mat2</td>
															<td>
																<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#visualizzaModal">Visualizza</button>
															</td>
															<td>
																<button class="btn btn-block btn-primary btn-sm" type="button" data-bs-toggle="modal" data-bs-target="#segnalaModal">Segnala</button>
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
					</div>
				</div>
			</div>
			<div class="bg-color"></div>
		</section>
	</div>


	<!-- visualizzaModal -->
	<div class="modal fade" id="visualizzaModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="visualizzaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form ref="form">
					<fieldset>
						<div class="modal-body">
							<legend class="custom-legend">
								<span class="legend-text">Informazioni aggiuntive</span>
								<button type="reset" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>

							<div class="container text-center mt-5">
								<div class="row row-cols-1 row-cols-sm-2 row-cols-md-2">
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Nome:</span>
											<span>Maria</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Cognome:</span>
											<span>Rossi</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Regione:</span>
											<span>Piemonte</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Provincia:</span>
											<span>Torino</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Città:</span>
											<span>Venaria Reale</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Indirizzo:</span>
											<span>Via Rossi 15/2</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Categoria:</span>
											<span>Nessuna categoria</span>
										</div>
									</div>
									<div class="col">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Materiale:</span>
											<span>Tagliaerba</span>
										</div>
									</div>
									<div class="col col-lg col-md-12 col-sm-12">
										<div class="list-group-item d-flex">
											<span class="fw-bold me-3">Descrizione:</span>
											<span>Ciao sono una descrizione blablabla blablabla blablabla blablabla blablabla blablabla blablabla blablabla blablabla</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
		</div>
	</div>


	<!-- segnalaModal -->
	<div class="modal fade" id="segnalaModal" data-bs-show="false" data-bs-keyboard="false" tabindex="-1" aria-labelledby="segnalaModalLabel" aria-hidden="true">
		<div class="modal-dialog modal-lg modal-dialog-centered">
			<div class="modal-content">
				<form @submit="onSubmit" ref="form">
					<fieldset>
						<div class="modal-body">
							<legend class="custom-legend">
								<span class="legend-text">Invia una segnalazione</span>
								<button type="reset" class="btn-close btn-lg" data-bs-dismiss="modal" aria-label="Close"></button>
							</legend>
							<p class="text-muted">Nota: Inserisci il maggior numero di dettagli possibile</p>

							<div class="input-group mb-3">
								<span class="input-group-text">Descrizione del problema</span>
								<textarea rows="5" class="form-control" minlength="20" aria-label="Descrizione richiesta" aria-describedby="Descrizione richiesta" title="La descrizione deve avere almeno 20 caratteri" required></textarea>
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









	<!--	<div class="mie-attivita">-->
<!--		<bacheca-nav-bar></bacheca-nav-bar>-->
<!--		<table>-->
<!--			<tr>-->
<!--				<td>-->
<!--					&lt;!&ndash; TODO aggiungere le tabelle al posto delle liste-->
<!--						  con le informazioni delle varie richieste &ndash;&gt;-->
<!--					<button @click="PrimoshowDropdown = !PrimoshowDropdown">Mostra tendina</button>-->
<!--					<ul v-show="PrimoshowDropdown">-->
<!--						<li>Opzione 1</li>-->
<!--						<li>Opzione 2</li>-->
<!--						<li>Opzione 3</li>-->
<!--					</ul>-->
<!--					<br>-->
<!--					<button @click="SecondoshowDropdown = !SecondoshowDropdown">Mostra tendina</button>-->
<!--					<ul v-show="SecondoshowDropdown">-->
<!--						<li>Opzione 1</li>-->
<!--						<li>Opzione 2</li>-->
<!--						<li>Opzione 3</li>-->
<!--					</ul>-->
<!--					<br>-->
<!--					<button @click="TerzoshowDropdown = !TerzoshowDropdown">Mostra tendina</button>-->
<!--					<ul v-show="TerzoshowDropdown">-->
<!--						<li>Opzione 1</li>-->
<!--						<li>Opzione 2</li>-->
<!--						<li>Opzione 3</li>-->
<!--					</ul>-->
<!--					<br>-->
<!--				</td>-->
<!--			</tr>-->
<!--		</table>-->
<!--	</div>-->
</template>

<script>
	import BachecaNavBar from "@/components/BachecaNavBar";

	export default {
		name: "MieAttivitaView",
		components: {BachecaNavBar},
		methods: {
			onSubmit() {
				if (this.$refs.form.checkValidity()) {
					alert(`Segnalazione inviata!\nFaremo del nostro meglio per prendere opportune precauzioni.`);
				} else {
					alert('Form non valido')
				}
			}
		},
		data() {
			return {
				PrimoshowDropdown: false,
				SecondoshowDropdown: false,
				TerzoshowDropdown: false
			}
		}
	}
</script>

<style scoped>
	.home-container {
		min-height: 100%;
		text-align: center;
		background: linear-gradient(to bottom, #a7fada, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15));
		/*background: linear-gradient(to top, #ccfaee, #e9faf5, rgba(13, 23, 196, 0.15), rgba(92, 109, 248, 0.49), #5c6df8);*/
		animation: fadeInUp 1s ease both;
	}

	h1 {
		font-weight: bold;
		font-size: 2.5em;
		margin-bottom: 20px;
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
		backdrop-filter: blur(60px);
		border-radius: 45px;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.5);
	}

	.accordion {
		border-radius: 8px;
		--bs-accordion-active-bg: rgba(13, 23, 196, 0.15);
		--bs-accordion-active-color: black;
	}

	.accordion-button {
		padding: 10px 20px;
		font-size: 18px;
		border: none;
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

</style>
