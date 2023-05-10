<template>
  <div class="bacheca">
    <bacheca-nav-bar></bacheca-nav-bar>
    <h1>Bacheca</h1>
    <div class="lista-richieste">
      <ul>
        <li v-for="item in richieste" :key="item.id">
          <richiesta-item
              :id="item.id"
              :autore="item.autore"
              :descrizione="item.descrizione">
          </richiesta-item>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";
import RichiestaItem from "@/components/RichiestaItem";

export default {
  name: "BachecaView",
  components: {
    RichiestaItem,
    BachecaNavBar
  },
  data() {
    return {
      richieste: [
        {id: 1, autore: "Marco", descrizione: "prima richiesta"},
        {id: 2, autore: "Mario", descrizione: "seconda richiesta"}
      ]
    }
  }
}
</script>

<style scoped>
ul {
  list-style: none;
}
</style>