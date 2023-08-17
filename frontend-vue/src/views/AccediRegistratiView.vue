<template>
	<div class="home-container">
		<home-nav-bar/>
		<div class="hero-section">
			<div class="hero-content">
				<form @submit="onSubmit" ref="form">
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

				<div class="divider"></div>

				<div class="register-option">
					<div class="or-label">Non hai ancora un account? Registrati ora!</div>
					<button class="btn btn-block btn-primary" @click="onClick" id="registrati-button">Registrati</button>
				</div>
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
// import axios from "axios";

export default {
	name: "AccediRegistratiView",
	components: {
		HomeNavBar
	},
	methods: {
		fetchLogin() {
			fetch('api/utenti/login', {
				method: 'POST',
				headers: {'Content-Type': 'application/json'},
				body: JSON.stringify({email: this.email, password: this.password})
			}).then(response => response.json())
				.then(data => console.log(data))
		},
		/*fetchLogin() {
		  axios.get('api/utenti/login', {params: {email: this.email, password: this.password}})
			  .then(response => {
				console.log(response);
				// this.$refs.form.reset();
				// this.$router.push('../accedi-registrati/bacheca');
			  })
			  .catch(error => {
				console.log(error)
			  })
		},*/
		onSubmit() {
			this.$refs.form.reset();
			this.$router.push('../accedi-registrati/bacheca');
		},
		onClick() {
			this.$refs.form.reset();
			this.$router.push('../accedi-registrati/registrazione');
		}
	},
	data() {
		return {
			email: '',
			password: ''
		}
	},
	computed() {
		this.fetchLogin()
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
		background-color: rgb(255, 255, 255);
		border-radius: 45px;
		padding: 1.5em 3em;
		display: inline-block;
		height: fit-content;
		box-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
	}

	legend {
		font-weight: bold;
		font-size: 2em;
		margin-bottom: 1em;
		/*text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.2); !* ombra al testo *!*/
	}

	.mb-3 {
		text-align: start;
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

	#google-button {
		background-color: #dd4b39;
	}

	#google-button:hover {
		background-color: #b70000ff;
	}

	#registrati-button {
		background-color: #ffffff;
		border: 0.1px solid rgba(13, 23, 196, 0.5);
		color: #0d17c4ff;
	}

	#registrati-button:hover {
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
