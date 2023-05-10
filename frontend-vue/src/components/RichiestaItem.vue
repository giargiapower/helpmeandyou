<template>
  <div class="richiesta-aiuto">
    <h3>{{ autore }}</h3>
    <p>{{ descrizione }}</p>
    <!-- todo implementare pagina modale con bootstrap -->
    <input type="button" value="Aiuta" @click="onClick">
  </div>
</template>

<script>
export default {
  name: "RichiestaItem",
  props:{
    id: { required: true, type: Number },
    autore: { required: true, type: String },
    descrizione: { required: true, type: String }
  },
  methods: {
    onClick() {
      this.$router.push('/accedi-registrati/bacheca/accetta-richiesta')
    }
  }
}
</script>

<style scoped>

</style>
