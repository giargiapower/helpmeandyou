<template>
  <div class="admin-home">
    <h1>Login Amministratore</h1>
    <form @submit="onSubmit" ref="form">
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
  name: "AmministratoreView",
  methods: {
    onSubmit() {
      this.$refs.form.reset();
      this.$router.push('/admin/admin-home');
    }
  }
}
</script>

<style scoped>

</style>
