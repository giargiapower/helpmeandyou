<template>
  <div class="magazzino-home">
    <nav>
      <router-link to="/magazzino">Logout</router-link>
    </nav>
    <h2>Gestione magazzino</h2>
    <p>Denaro disponibile: {{ saldoMagazzino }}</p>
    <table class="table" v-if="listaMateriali.length !== 0">
      <thead class="table-dark">
        <tr>
          <th scope="col">Nome materiale</th>
          <th scope="col">Quantità</th>
          <th scope="col">Aggiungi/Elimina materiale</th>
        </tr>
      </thead>
      <tbody>
        <template v-for="item in this.uniqueMateriali()" :key="item.id">
          <tr>
            <td>{{item.nome}}</td>
            <td>{{ quantita[item.nome] }}</td>
            <td>
              <div class="btn-group">
                <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" :data-bs-target="`#aggiungiMateriale-${item.id}`" @click="onClick(item)">+</button>
                <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" :data-bs-target="`#eliminaMateriale-${item.id}`" @click="onClick(item)">-</button>
              </div>
            </td>
          </tr>

          <div v-for="item in this.uniqueMateriali()" :key="`modal-${item.nome}`">
            <div class="modal fade" :id="`aggiungiMateriale-${item.id}`" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="`aggiungiMaterialeLabel-${item.id}`" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="aggiungiMaterialeLabel">Quanti {{item.nome}} vuoi aggiungere:</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
                  </div>
                  <div class="modal-body">
                    <label>Quantità materiale:</label>
                    <input type="number" v-model="this.tot" required>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="addMateriale(item.nome, this.tot)">Conferma</button>
                  </div>
                </div>
              </div>
            </div>
            <div class="modal fade" :id="`eliminaMateriale-${item.id}`" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" :aria-labelledby="`eliminaMaterialeLabel-${item.id}`" aria-hidden="true">
              <div class="modal-dialog">
                <div class="modal-content">
                  <div class="modal-header">
                    <h1 class="modal-title fs-5" id="eliminaMaterialeLabel">Quanti {{item.nome}} vuoi eliminare:</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
                  </div>
                  <div class="modal-body">
                    <label>Quantità materiale:</label>
                    <input type="number" v-model="quantita" required>
                  </div>
                  <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
                    <button type="button" class="btn btn-primary" data-bs-dismiss="modal" >Conferma</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </template>
      </tbody>
    </table>

    <div v-if="listaMateriali.length === 0">
      <p>Magazzino vuoto!</p>
      <button type="button" class="btn btn-primary btn-lg" data-bs-toggle="modal" data-bs-target="#aggiungiMateriale">+</button><br>
      <label>Inserisci un materiale!</label>
      <div class="modal fade" id="aggiungiMateriale" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="aggiungiMaterialeLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <h1 class="modal-title fs-5" id="aggiungiMaterialeLabel">Aggiungi materiale</h1>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Chiudi"></button>
            </div>
            <div class="modal-body">
              <label>Nome materiale:</label>
              <input type="text" v-model="nome" required><br>
              <label>Quantità materiale:</label>
              <input type="number" v-model="quantita" required>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Annulla</button>
              <button type="button" class="btn btn-primary" data-bs-dismiss="modal" @click="addMateriale">Conferma</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "MagazzinoHomeView",
  methods: {
    onClick(nome) { this.nome = nome; },
    // Funzione che calcola la quantità di un materiale per nome
    fetchQuantitaMateriale() {
      this.quantita = this.listaMateriali.reduce((acc, materiale) => {
        const chiave = materiale.nome;
        if (acc[chiave]) {
          acc[chiave]++;
        } else {
          acc[chiave] = 1;
        }
        return acc;
      }, {});
    },
    // Funzione che calcola la lista dei materiali senza duplicati
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
    // Funzione che carica dal server i materiali presenti nel magazzino
    async fetchMaterialiMagazzino() {
      await axios.get('/api/magazzini/6/materiali')
          .then(response => {
            this.listaMateriali = response.data;
            this.fetchQuantitaMateriale();
            console.log('Materiali caricati con successo.');
          })
          .catch(error => {
            console.log(error);
          })
    },
    // Funzione che aggiunge un materiale al magazzino e lo salva sul server
    // TODO: NON FUNZIONA! MA NON SEMBRA SBAGLIATA !!!
    async addMateriale (nome, num) {
      const idMagazzino = 6;
      const url = `/api/magazzini/create/${idMagazzino}/${nome}`;
      await axios.put(url, {idMagazzino: 6, nome: nome})
          .then(response => {
            this.listaMateriali.push(response.data);
            console.log('Materiale aggiunto al magazzino');
            this.quantita[nome] += num;
            console.log('Quantità materiale aggiornata');
          })
          .catch(errors => {
            console.error(errors);
          })
    }
  },
  data() {
    return {
      nome: '',
      quantita: {},
      tot: 0,
      saldoMagazzino: 0,
      listaMateriali: []
    }
  },
  mounted() {
    this.fetchMaterialiMagazzino();
    this.uniqueMateriali();
  },
  computed() {
    this.fetchMaterialiMagazzino();
    this.uniqueMateriali();
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
.btn-space {
  width: 10px;
}
</style>
