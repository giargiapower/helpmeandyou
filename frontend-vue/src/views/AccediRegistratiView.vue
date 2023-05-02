<template>
  <div class="accedi-registrati">
    <home-nav-bar />
    <login-form></login-form>
  </div>
</template>

<script>
  import LoginForm from "@/components/LoginForm";
  import HomeNavBar from "@/components/HomeNavBar";

  export default {
    name: "AccediRegistratiView",
    components: {
      LoginForm,
      HomeNavBar
    }
  }
</script>
