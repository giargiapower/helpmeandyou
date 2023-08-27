<template>
	<div class="admin-home">
		<admin-nav-bar></admin-nav-bar>
		<h2>Approvazione utenti</h2>
		<table>
			<thead>
			<tr>
				<th>Utenti</th>
				<th>Stato account</th>
			</tr>
			</thead>
			<tbody>
			<tr v-for="utente in listaUtentiDaApprovare" :key="utente.id">
				<td>{{ utente.nome + ' ' + utente.cognome }}</td>
				<td v-if="utente.stato === 'da_approvare'">Approvare</td>
				<td v-else>Aggiornamento</td>
				<td>
					<button type="button" class="btn btn-primary" data-bs-toggle="modal" :data-bs-target="'#btn-verifica-' + utente.id">
						Verifica
					</button>
				</td>
			</tr>
			</tbody>
			<verifica-utente-item v-for="utente in listaUtentiDaApprovare" :key="'modal-' + utente.id" :utente="utente">
			</verifica-utente-item>
		</table>
	</div>
</template>

<script>
import AdminNavBar from "@/components/AdminNavBar";
import axios from "axios";
import VerificaUtenteItem from "@/components/VerificaUtenteItem";

export default {
	name: "AdminHomeView",
	components: {VerificaUtenteItem, AdminNavBar},
	methods: {
		// Funzione che calcola la lista di utenti da approvare
		async fetchUtentiDaApprovare() {
			await axios.get('/api/amministratore/da_approvare/list')
				.then(response => {
					this.listaUtentiDaApprovare = response.data;
					console.log('Lista utenti caricata con successo.')
				})
				.catch(errors => {
					console.error(errors)
				})
		}
	},
	data() {
		return {
			listaUtentiDaApprovare: []
		}
	},
	computed() {
		this.fetchUtentiDaApprovare();
	},
	mounted() {
		this.fetchUtentiDaApprovare();
	}
}
</script>

<style scoped>
table {
	margin: auto;
}
</style>
