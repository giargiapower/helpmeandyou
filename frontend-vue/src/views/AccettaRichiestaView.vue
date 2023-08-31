<!--TODO: DA ELIMINARE!!!-->
<template>
  <div class="accetta-richiesta">
    <table>
      <h2>Dai il tuo aiuto!</h2>
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
    <input type="button" value="Annulla" class="annulla" @click="onAnnulla"> |
    <input type="button" value="Accetta" class="conferma" @click="onAccetta">
  </div>
</template>

<script>
import moment from 'moment'

export default {
  name: "AccettaRichiestaView",
  props: {
    nome: { required: true, type: String },
    cognome: { required: true, type: String },
    data: { required: true, type: Date}
  },
  methods: {
    onSegnala() {
      alert("Grazie per la tua segnalazione!\nLa richiesta verrà presa in visione il prima possibile.");
      this.$router.push('./');
    },
    onAnnulla() {
      this.$router.push('./');
    },
    onAccetta() {
      alert(`Grazie per la tua disponibilità!\nPuoi consultare le tue prenotazioni attive nella sezione "Le mie attività."`)
      this.$router.push('./');
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
