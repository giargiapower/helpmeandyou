<template>
  <div class="registrazione">
    <form @reset="gestisciReset" @submit="onSubmit" ref="form">
      <table>
        <caption>Inserisci le tue credenziali</caption>
        <tbody>
        <tr>
          <td><label for="nome">Nome:</label></td>
          <td><input type="text" id="nome" name="nome" required></td>
          <td><label for="cognome">Cognome:</label></td>
          <td><input type="text" id="cognome" name="cognome" required></td>
        </tr>
        <tr>
          <td><label for="data-nascita">Data di nascita:</label></td>
          <td><input type="date" id="data-nascita" name="data-nascita" required></td>
          <td><label for="numtel">N° telefono:</label></td>
          <td><input type="text" id="numtel" name="numtel" required></td>
        </tr>
        <tr>
          <td><label for="email">Email:</label></td>
          <td><input type="email" id="email" name="email" required></td>
          <td><label for="password">Password:</label></td>
          <td><input type="password" id="password" name="password" required></td>
        </tr>
        <tr>
          <td><label for="domicilio">Domicilio:</label></td>
          <td><input type="text" id="domicilio" name="domicilio" required></td>
          <td><label for="img-documento">Documento d'indentità:</label></td>
          <td><input type="file" id="img-documento" accept="application/pdf" required></td>
        </tr>
        </tbody>
      </table>
      <input type="reset" value="Annulla"> |
      <input type="submit" value="Conferma i tuoi dati">
    </form>
  </div>
</template>

<script>
export default {
  name: "RegistrazioneAccount",
  methods: {
    gestisciReset() {
      this.$refs.form.reset();
      this.$router.push('/');
    },
    onSubmit() {
      alert("Registrazione completata!\nGrazie per esserti registrato," +
          " ora puoi accedere liberamente al tuo account.");
      this.$refs.form.reset();
      this.$router.push('/accedi-registrati');
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>