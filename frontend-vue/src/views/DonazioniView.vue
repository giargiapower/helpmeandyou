<template>
  <div class="donazioni">
    <home-nav-bar />
    <h1>Donazioni</h1>
    <form @reset="gestisciReset" @submit="onSubmit">
      <table>
        <h2>Inserisci i dati della tua carta di credito</h2>
        <tbody>
        <tr>
          <td><label for="nome">Nome:</label></td>
          <td><input type="text" id="nome" name="nome" required></td>
          <td><label for="cognome">Cognome:</label></td>
          <td><input type="text" id="cognome" name="cognome" required></td>
        </tr>
        <tr>
          <td><label for="numcarta">Numero carta:</label></td>
          <td><input type="text" id="numcarta" name="numcarta" required></td>
          <td><label for="cvv">CVV:</label></td>
          <td><input type="text" id="cvv" name="cvv" required></td>
        </tr>
        <tr>
          <td><label for="datascadenza">Data di scandenza:</label></td>
          <td><input type="date" id="datascadenza" name="datascadenza" required></td>
          <td><label for="importo">Importo donazione:</label></td>
          <td><input type="text" id="importo" name="importo" required></td>
        </tr>
        </tbody>
      </table>
      <input type="reset" value="Annulla"> |
      <input type="submit" value="Invia denaro">
    </form>
  </div>
</template>

<script>
import HomeNavBar from "@/components/HomeNavBar";

export default {
  name: "DonazioniView",
  components: {
    HomeNavBar
  },
  methods: {
    gestisciReset() {
      this.$router.push('/');
    },
    onSubmit() {
      alert("Grazie per la tua donazione!\n" +
          "I tuoi fondi contribuiranno all'acquisto di materiali per il progetto HelpMe&You.");
      this.$router.push('/');
    }
  }
}
</script>

<style scoped>
table {
margin: auto;
}
</style>
