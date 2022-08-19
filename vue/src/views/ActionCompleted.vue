<template>
<div>
    <img class="couch" src="couch.jpg"> 
  <div class="conf">  
  <h1>Action completed! Good job!</h1>
  <router-link v-bind:to="{name: 'home'}">Return Home</router-link>
  </div>
</div>
</template>

<script>
export default {
    
}
</script>

<style>
.couch {
  border: solid; 
  border-color: black;
  border-width: 2px;
  height: 100vh;
  width: 100vw;
}

.conf {
  position: absolute;
  font-size: 30px;
  color: #f8c630;
  font-family: "Mouse Memoirs", sans-serif;
  top: 20px;
  left: 10%;
}


</style>