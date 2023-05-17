<template>
  <div class="bacheca">
    <bacheca-nav-bar></bacheca-nav-bar>
    <h1>Bacheca</h1>
    <div class="lista-richieste">
      <ul>
        <li v-for="item in richieste" :key="item.id">
          <p>{{item}}</p>
          <!-- <richiesta-item
              :id="item.id"
              :nome="item.nome"
              :cognome="item.cognome"
              :descrizione="item.descrizione">
          </richiesta-item> -->
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";
// import RichiestaItem from "@/components/RichiestaItem";
import axios from 'axios';

export default {
  name: "BachecaView",
  components: {
    // RichiestaItem,
    BachecaNavBar
  },
  methods: {
    async fetchRichieste() {
      axios.get('/api/richiesteaiuto/richieste')
          .then(response => {
            this.richieste = response.data;
            console.log('Richieste aggiungete con successo');
          })
          .catch(error => {
            console.log(error)
          })
    }
  },
  data() {
    return {
      richieste: [
        {
          id: 1,
          nome: "Marco",
          cognome: "Digiani",
          descrizione: "prima richiesta",
        },
        {
          id: 2,
          nome: "Luca",
          cognome: "Bicchiere",
          descrizione: "seconda richiesta",
        }
      ]
    }
  },
  mounted() {
    this.fetchRichieste()
  }
}
</script>

<style scoped>
ul {
  list-style: none;
}
</style>
