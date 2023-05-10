<template>
  <div class="magazzino-home">
    <nav>
      <router-link to="/magazzino">Logout</router-link>
    </nav>
    <h2>Gestione magazzino</h2>
    <p>Denaro disponibile: {{ saldoMagazzino }}</p>
    <table>
      <tbody>
        <tr>
          <td>
            <p>Nome materiale</p>
          </td>
          <td>
            <p>Quantità</p>
          </td>
          <td>
            <p>Aggiungi/Elimina materiale</p>
          </td>
        </tr>
        <tr>
          <td><!-- TODO Lista nomi materiali --></td>
          <td><!-- TODO Lista della quantità di ciascun materiale --></td>
          <td><!-- TODO Bottoni +/- per aggiungere o eliminare un materiale --></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: "MagazzinoHomeView",
  data() {
    return {
      saldoMagazzino: 0
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
