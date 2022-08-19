<template>
  <div id="app">
    <div id="nav">
      <router-link v-bind:to="{ name: 'home' }">Home</router-link>&nbsp;|&nbsp;
      <router-link v-bind:to="{ name: 'logout' }" v-if="$store.state.token != ''">Logout</router-link>
    </div>
    <router-view />
  </div>
</template>


<style>
#nav{
  left:10%;
  padding-left:10%
}

@font-face {
  font-family: "Mouse Memoirs";
  src: local("Mouse Memoirs"),
   url(./fonts/MouseMemoirs-Regular.ttf) format("truetype");
}

@font-face {
  font-family: "Pangolin";
  src: local("Pangolin"),
  url(./fonts/Pangolin-Regular.ttf) format("truetype");
}

</style>