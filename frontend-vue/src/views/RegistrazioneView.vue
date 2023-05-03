<template>
  <div class="registrazione-view">
    <home-nav-bar />
    <registrazione-account></registrazione-account>
  </div>
</template>

<script>
import RegistrazioneAccount from "@/components/RegistrazioneAccount";
import HomeNavBar from "@/components/HomeNavBar";

export default {
  name: "RegistrazioneView",
  components: {
    RegistrazioneAccount,
    HomeNavBar
  }
}
</script>
