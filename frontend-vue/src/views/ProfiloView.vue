<template>
  <div class="profilo">
    <bacheca-nav-bar></bacheca-nav-bar>
    <h2>Profilo</h2>
    <form @reset="gestisciReset" @submit="onSubmit">
      <table>
        <tbody>
        <tr>
          <td><label for="nome">Nome:</label></td>
          <td><input type="text" id="nome" name="nome" required></td>
        </tr>
        <tr>
          <td><label for="cognome">Cognome:</label></td>
          <td><input type="text" id="cognome" name="cognome" required></td>
          <td>
            <p>Documento d'identità:</p>
            <img :src="imageUrl" alt="Immagine caricata">
          </td>
        </tr>
        <tr>
          <td><label for="data-nascita">Data di nascita:</label></td>
          <td><input type="date" id="data-nascita" name="data-nascita" required></td>
        </tr>
        <tr>
          <td><label for="telefono">Telefono:</label></td>
          <td><input type="text" id="telefono" name="telefono" required></td>
        </tr>
        <tr>
          <td><label for="domicilio">Domicilio:</label></td>
          <td><input type="text" id="domicilio" name="domicilio" required></td>
        </tr>
        <tr>
          <td><label for="categorie">Categoria/e:</label></td>
          <td><input type="text" id="categorie" name="categorie" required></td>
        </tr>
        </tbody>
      </table><br>
      <input type="reset" value="Annulla"> |
      <input type="submit" value="Conferma i tuoi dati">
    </form>
  </div>
</template>

<script>
import BachecaNavBar from "@/components/BachecaNavBar";
export default {
  name: "ProfiloView",
  components: { BachecaNavBar },
  data() {
    return {
      imageUrl: 'C:/Users/serra/Desktop/ProgettoTAASS/helpmeandyou/frontend-vue/src/assets/logo.png'
    }
  },
  methods: {
    onSubmit() {
      alert("Salvataggio dati profilo effettuato con successo!");
      this.$router.push('/accedi-registrati/bacheca');
    },
    gestisciReset() {
      this.$router.push('/accedi-registrati/bacheca');
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
