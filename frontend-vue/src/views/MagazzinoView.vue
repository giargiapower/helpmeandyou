<template>
	<div class="home-container">
		<div class="hero-section">
			<div class="hero-content">
				<form @submit="onSubmit" ref="form">
					<fieldset>
						<legend>Login Magazzino</legend>
						<div class="mb-3">
							<input class="form-control" ref="email" type="email" id="email" name="email" v-model="email" @value="this.email" placeholder="Email" required>
						</div>
						<div class="mb-3">
							<input class="form-control" ref="password" type="password" id="password" name="password" v-model="password" @value="this.password" placeholder="Password" required>
						</div>
						<button class="btn btn-block btn-primary" type="submit">Accedi</button>
					</fieldset>
				</form>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		name: "MagazzinoView",
		methods: {
			onSubmit() {
				this.$router.push('/magazzino/magazzino-home');
			}
		}
	}
</script>

<style scoped>
	.home-container {
		animation: fadeInUp 1s ease both; /* Applica l'animazione a tutta la pagina */
	}

	.hero-section {
		background: linear-gradient(to bottom, #ea5459, #ede342);
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
</style>
