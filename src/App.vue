<template>
  <div>
    <Navbar />
    <div id="app">
      <router-view></router-view>
    </div>
    <FooterBar />
  </div>
</template>

<script>
import Navbar from '@/assets/NavBar.vue';
import FooterBar from '@/assets/FooterBar.vue';

export default {
  name: 'App',
  components: {
    Navbar,
    FooterBar, // Add FooterBar to the components section
  },
};
</script>

<style>
div#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
