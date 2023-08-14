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
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#btn-verifica">
              Verifica
            </button>
            <div class="modal fade" id="btn-verifica" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-verificaLabel" aria-hidden="true">
              <div class="modal-dialog model-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="btn-verificaLabel">Utente: {{ utente.nome + ' ' + utente.cognome }}</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
                  </div>
                  <div class="modal-body">
                    <form>
                      <table>
                        <tbody>
                          <tr>
                            <td>
                              <p>Nome: {{utente.nome}}</p>
                              <p>Cognome: {{utente.cognome}}</p>
                              <p>N° Telefono: {{utente.telefono}}</p>
                              <p>Indirizzo: {{utente.indirizzo}}</p>
                              <p v-if="utente.category === null">Categoria: Nessuna categoria</p>
                              <p v-else>Categoria: {{utente.category}}</p>
                            </td>
                            <td>
                              <p>Documento d'identità</p>
                              <img src=utente.path_documento class="img-fluid" alt="Immagine non disponibile!" aria-label="Documento d'identità"><br>
                              <button type="button" class="btn btn-secondary" data-bs-target="#aggiornaCat" data-bs-toggle="modal">Aggiorna categoria</button>
                            </td>
                          </tr>
                        </tbody>
                      </table>
                    </form>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                    <button type="button" class="btn btn-primary">Approva</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal fade" id="aggiornaCat" aria-hidden="true" aria-labelledby="aggiornaCatLabel" tabindex="-1">
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="aggiornaCatLabel">Categorie Utenti</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                  </div>
                  <div class="modal-body">Scegli una o più categorie da assegnare ad un utente:</div>
                  <div class="modal-footer">
                    <button class="btn btn-secondary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Annulla</button>
                    <button class="btn btn-primary" data-bs-target="#btn-verifica" data-bs-toggle="modal">Conferma</button>
                  </div>
                </div>
              </div>
            </div>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import AdminNavBar from "@/components/AdminNavBar";
import axios from "axios";

export default {
  name: "AdminHomeView",
  components: { AdminNavBar },
  methods: {
    onClick() {

    },
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
  mounted() {
    this.fetchUtentiDaApprovare();
  },
  computed() {
    this.fetchUtentiDaApprovare();
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
