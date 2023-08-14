<template>
  <div class="crea-richiesta">
    <bacheca-nav-bar></bacheca-nav-bar>
    <form @submit="onSubmit" ref="form">
      <h2>Crea Richiesta d'Aiuto</h2>
      <table>
        <tbody>
        <tr>
          <td>
            <label>Data:</label>
            <input type="date" required>
          </td>
          <td>
            <input type="checkbox">
            <label>Mattina</label>
          </td>
          <td>
            <input type="checkbox">
            <label>Pomeriggio</label>
          </td>
        </tr>
        <tr>
          <td>
            <label>Regione:</label>
            <input type="text" required>
          </td>
          <td>
            <label>Provincia:</label>
            <input type="text" required>
          </td>
          <td>
            <label>Città:</label>
            <input type="text" required>
          </td>
          <td>
            <label>Indirizzo:</label>
            <input type="text" required>
          </td>
        </tr>
        <tr>
          <td>
            <label>Categoria Richiesta:</label>
            <select v-model="selectedCategoria">
              <option disabled value="">Seleziona un'opzione</option>
              <option v-for="cat in categorie" :key="cat.id">{{ cat.nome }}</option>
            </select>
          </td>
        </tr>
        <tr>
          <label for="text-input">Descrizione Richiesta:</label>
          <input type="text" id="text-input" v-model="textValue" required>
        </tr>
        <tr>
          <td>
            <label>Seleziona il materiale che ti serve:</label>
            <select v-model="selectedMateriale">
              <option disabled value="">-</option>
              <option v-for="materiale in this.uniqueMateriali()" :key="materiale.id">{{ materiale.nome }}</option>
            </select>
          </td>
        </tr>
        </tbody>
      </table>
      <input type="reset" value="Annulla" @click="onReset"> |
      <input type="submit" value="Pubblica">
    </form>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";
import axios from  'axios';
import _ from 'lodash';

export default {
  name: "CreaRichiestaView",
  components: { BachecaNavBar },
  methods: {
    onReset() {
      this.$refs.form.reset();
      this.$router.push('./');
    },
    onSubmit() {
      alert(`Richiesta pubblicata!\nPuoi consultare le prenotazioni attive nella sezione "Le mie attività".`);
      this.$refs.form.reset();
      this.$router.push('./');
    },
    // Funzione che calcola la lista dei materiali non duplicati
    uniqueMateriali() {
      const uniqueMateriali = [];
      const countMateriali = {};

      for (const item of this.listaMateriali) {
        if (!countMateriali[item.nome]) {
          countMateriali[item.nome] = 1;
          uniqueMateriali.push(item);
        } else {
          countMateriali[item.nome]++;
        }
      }

      for (const item of uniqueMateriali) {
        item.count = countMateriali[item.nome];
      }

      return uniqueMateriali;
    },
    // Funzione che calcola lista dei materiali
    async fetchMateriali() {
      await axios.get('/api/magazzini/6/materiali')
          .then(response => {
            this.listaMateriali = response.data;
            console.log('Materiali caricati!');
          })
          .catch(errore => {
            console.log(errore);
          })
    },
    // Funzione che calcola le categorie da assegnare a una richiesta in modo univoco
    async fetchCategorieRichieste() {
      await axios.get('/api/richiesteaiuto/richieste')
          .then(response => {
            this.listaRichieste = response.data;
            this.listaRichieste.forEach(ric => {
              this.categorie.push({id: this.uniqueId, nome: ric.categoria.tipo});
            });

            console.log('Categorie caricate on successo!');
            console.log(this.categorie);
          })
          .catch(errore => { console.log(errore); })
    }
  },
  data() {
    return {
      selectedCategoria: '',
      selectedMateriale: '',
      textValue: '',
      listaRichieste: [],
      listaMateriali: [],
      categorie: [],
      uniqueId: _.uniqueId()
    }
  },
  mounted() {
    this.fetchMateriali();
    this.uniqueMateriali();
    this.fetchCategorieRichieste();
  },
  computed() {
    this.fetchMateriali();
    this.uniqueMateriali();
    this.fetchCategorieRichieste();
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
