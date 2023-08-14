<template>
  <div class="segnalazioni">
    <admin-nav-bar></admin-nav-bar>
    <h2>Segnalazioni</h2>
    <table>
      <thead>
        <tr>
          <th>Utente Segnalato</th>
          <th>Tipologia segnalazione</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="segnalazione in listaSegnalazioni" :key="segnalazione.id">
          <td>{{ segnalazione.segnalato }}</td>
          <td>{{ segnalazione.tipologia }}</td>
          <td>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#btn-visualizza">
              Visualizza
            </button>
            <div class="modal fade" id="btn-visualizza" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="btn-visualizzaLabel" aria-hidden="true">
              <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="btn-visualizzaLabel">Segnalazione della richiesta {{ segnalazione.titolo }} da parte dell'utente {{segnalazione.creatore}} </h1>
                  </div>
                  <div class="modal-body">
                    {{ segnalazione.descrizione }}
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <button type="button" class="btn btn-primary">Segnala utente</button>
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
import axios from 'axios';

export default {
  name: "AdminSegnalazioniView",
  components: { AdminNavBar },
  props: {
    segnalazione: {
      id: {required: true, type: Number},
      titolo: {required: true, type: String},
      descrizione: {required: true, type: String},
      tipologia: {required: true, type: String},
      creatore: {required: true, type: Number},
      segnalato: {required: true, type: Number}
    }
  },
  methods: {
    async fetchSegnalazioni() {
      await axios.get('/api/segnalazioni/segnalazione/list')
          .then(response => {
            this.listaSegnalazioni = response.data;
            console.log('Segnalazioni caricate con successo.');
          })
          .catch(error => {
            console.log(error);
          })
    }
  },
  data() {
    return {
      listaSegnalazioni: []
    }
  },
  mounted() {
    this.fetchSegnalazioni()
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
