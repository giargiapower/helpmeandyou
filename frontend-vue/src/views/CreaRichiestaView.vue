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
            <label>Luogo:</label>
            <input type="text" required>
          </td>
        </tr>
        <tr>
          <td>
            <label>Categoria Richiesta:</label>
            <select v-model="selectedOption">
              <option disabled value="">Seleziona un'opzione</option>
              <option v-for="option in options" :key="option.id" :value="option.value">{{ option.label }}</option>
            </select>
          </td>
        </tr>
        <tr>
          <label for="text-input">Descrizione Richiesta:</label>
          <input type="text" id="text-input" v-model="textValue" required>
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
    }
  },
  data() {
    return {
      selectedOption: '',
      options: [
        { id: 1, label: 'Opzione 1', value: 'opzione1' },
        { id: 2, label: 'Opzione 2', value: 'opzione2' },
        { id: 3, label: 'Opzione 3', value: 'opzione3' }
      ],
      textValue: ''
    }
  }
}
</script>

<style scoped>
table {
  margin: auto;
}
</style>
