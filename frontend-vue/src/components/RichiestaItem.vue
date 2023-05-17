<template>
  <div class="richiesta-aiuto">
    <h3>{{ nome + ' ' + cognome }}</h3>
    <p>{{ descrizione }}</p>
    <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#aiuto">
      Aiuta
    </button>
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
                  <p>Nome richiedente: {{ nome }}</p>
                </td>
                <td>
                  <button class="segnala" @click="onSegnala">Segnala</button>
                </td>
              </tr>
              <tr>
                <td>
                  <p>Cognome richiedente: {{ cognome }}</p>
                </td>
              </tr>
              </tbody>
            </table>
            <p>Seleziona i materiali che ti servono per il giorno {{ formattedDate }}</p>
            <ul>
              <li>
                <!-- TODO aggiungere la lista di tutti i materiali disponibili nel magazzino -->
                Lista dei materiali nel magazzino !!!
              </li>
            </ul>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
            <button type="button" class="btn btn-primary">Accetta</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import moment from "moment/moment";

export default {
  name: "RichiestaItem",
  props:{
    id: { required: true, type: Number },
    descrizione: { required: true, type: String },
    nome: { required: true, type: String },
    cognome: { required: true, type: String },
    data: { required: true, type: Date}
  },
  methods: {
    onSegnala() {
      alert("Grazie per la tua segnalazione!\nLa richiesta verrà presa in visione il prima possibile.");
    },
    onAccetta() {
      alert(`Grazie per la tua disponibilità!\nPuoi consultare le tue prenotazioni attive nella sezione "Le mie attività."`)
    }
  },
  data() {
    return {
      // TODO Recuperare la lista dei materiali dal magazzino
      listaMateriali: []
    }
  },
  computed: {
    formattedDate() {
      return moment(this.data).format('DD/MM/YYYY');
    }
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
</style>
