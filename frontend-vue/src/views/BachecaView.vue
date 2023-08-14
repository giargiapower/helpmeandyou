<template>
  <div class="bacheca">
    <bacheca-nav-bar></bacheca-nav-bar>
    <h1>Bacheca</h1>
    <div class="lista-richieste">
      <div class="row row-cols-1 row-cols-md-3 g-4">
        <div class="col" v-for="item in richieste" :key="item.id">
          <richiesta-item v-for="nominativo in nominativi" :key="nominativo.id"
              :id="item.id"
              :nome="nominativo.nome"
              :cognome="nominativo.cognome"
              :descrizione="item.descrizione"
              :data="item.giorno"
              :id-materiale="item.idMateriale"> <!-- TODO: chiarire data richiesta effettuata /
                                                       data richiesta accettata /
                                                       data richiesta completata -->
          </richiesta-item>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";
import RichiestaItem from "@/components/RichiestaItem";
import axios from 'axios';

export default {
  name: "BachecaView",
  components: {
    RichiestaItem,
    BachecaNavBar
  },
  methods: {
    async fetchRichieste() {
      await axios.get('/api/richiesteaiuto/richieste')
          .then(response => {
            this.richieste = response.data;

            // Assegnazione dei nomi e dei cognomi alla proprietà "nominativi"
            this.nominativi = this.richieste.map(richiesta => ({
              id: richiesta.id,
              nome: richiesta.pubAccount.nome,
              cognome: richiesta.pubAccount.cognome
            }));

            console.log('Richieste aggiungete con successo');
            console.log(this.richieste)
          })
          .catch(error => {
            console.log(error)
          })
    }
  },
  data() {
    return {
      richieste: [],
      nominativi: []
    }
  },
  mounted() {
    this.fetchRichieste();
  },
  computed() {
    this.fetchRichieste();
  }
}
</script>
