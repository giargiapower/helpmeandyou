<template>
  <div class="mie-attivita">
    <bacheca-nav-bar></bacheca-nav-bar>
    <table>
      <tr>
        <td>
          <!-- TODO aggiungere le tabelle al posto delle liste
                con le informazioni delle varie richieste -->
          <button @click="PrimoshowDropdown = !PrimoshowDropdown">Mostra tendina</button>
          <ul v-show="PrimoshowDropdown">
            <li>Opzione 1</li>
            <li>Opzione 2</li>
            <li>Opzione 3</li>
          </ul><br>
          <button @click="SecondoshowDropdown = !SecondoshowDropdown">Mostra tendina</button>
          <ul v-show="SecondoshowDropdown">
            <li>Opzione 1</li>
            <li>Opzione 2</li>
            <li>Opzione 3</li>
          </ul><br>
          <button @click="TerzoshowDropdown = !TerzoshowDropdown">Mostra tendina</button>
          <ul v-show="TerzoshowDropdown">
            <li>Opzione 1</li>
            <li>Opzione 2</li>
            <li>Opzione 3</li>
          </ul><br>
        </td>
      </tr>
    </table>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";

export default {
  name: "MieAttivitaView",
  components: { BachecaNavBar },
  data() {
    return {
      PrimoshowDropdown: false,
      SecondoshowDropdown: false,
      TerzoshowDropdown: false
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
