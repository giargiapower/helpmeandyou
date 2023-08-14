<template>
  <div class="richiesta-aiuto">

    <div class="card" style="width: 18rem;">
      <div class="card-body">
        <h5 class="card-title">{{ nome + ' ' + cognome }}</h5>
        <p class="card-text">{{ descrizione }}</p>
        <a href="#" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#aiuto">
          Aiuta
        </a>
      </div>
    </div>

    <div class="modal fade" id="aiuto" data-bs-backdrop="static" tabindex="-1" aria-labelledby="aiutoLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h1 class="modal-title fs-5" id="aiutoLabel">Dai il tuo aiuto!</h1>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Annulla"></button>
          </div>
          <div class="modal-body">
            <table>
              <tbody>
              <tr>
                <td>
                  <p>Richiedente: {{ nome }} {{ cognome }}</p><hr>
                </td>
              </tr>
              <tr>
                <td>
                  <p>Descrizione: {{ descrizione }}</p><hr>
                </td>
              </tr>
              </tbody>
            </table>
            <p>Seleziona i materiali che ti servono per il giorno {{ data }}:</p>
            <ul>
              <li>
                <div>Materiale utilizzati per completare la richiesta: {{this.materialeRichiesta.nome}}</div>
              </li>
            </ul>
          </div>
          <div class="modal-footer d-flex mb-3">
            <button type="button" class="btn btn-primary me-auto" @click="onSegnala">Segnala</button>
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
            <button type="button" class="btn btn-primary" @click="onAccetta">Accetta</button>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

import axios from "axios";

export default {
  name: "RichiestaItem",
  props:{
    id: { required: true, type: Number },
    descrizione: { required: true, type: String },
    nome: { required: true, type: String },
    cognome: { required: true, type: String },
    data: { required: true, type: Date},
    idMateriale: {required: true, type: Number}
  },
  data() {
    return {
      materialeRichiesta: {}
    }
  },
  methods: {
    // Funzione che carica dal server i materiali presenti nel magazzino per mostrarli nel componente
    async fetchMateriali() {
      await axios.get('/api/magazzini/6/materiali')
          .then(response => {
            response.data.forEach(element => {
              if(element.id === this.idMateriale)
                this.materialeRichiesta = element;
            });
            console.log('Materiali caricati con successo!');
          })
          .catch(error => {
            console.log(error);
          })
    },
    onSegnala() {
      alert("Grazie per la tua segnalazione!\nLa richiesta verrà presa in visione il prima possibile.");
    },
    onAccetta() {
      alert(`Grazie per la tua disponibilità!\nPuoi consultare le tue prenotazioni attive nella sezione "Le mie attività."`)
    }
  },
  mounted() {
    this.fetchMateriali()
  },
  computed() {
    this.fetchMateriali()
  }
}
</script>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}
table {
  margin: auto;
}
.richiesta-aiuto {
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
