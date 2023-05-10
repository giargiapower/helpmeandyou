<template>
  <div class="magazzino-login">
    <h2>Login magazzino</h2>
    <form @submit="onSubmit">
      <fieldset>
        <label>Username:</label>
        <input type="text" required><br>
        <label>Password:</label>
        <input type="password" required>
      </fieldset>
      <input type="submit" value="Accedi">
    </form>
  </div>
</template>

<script>
export default {
  name: "MagazzinoView",
  methods: {
    onSubmit() {
      this.$router.push('/magazzino/magazzino-home');
    }
  }
}
</script>

<style scoped>

</style>
