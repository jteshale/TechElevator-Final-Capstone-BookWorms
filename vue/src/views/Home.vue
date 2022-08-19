<template>
  <div>
    <img class="homebackground" src="living-room.jpg">
  <div class="home">
    <h1>Home</h1>
    <p>Welcome, {{username}}!</p>
  </div>
  <div class="nav">
    <p><router-link class="profilelink" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
    <router-link class="familylink" :to="{ name: 'family'}">Family</router-link>
    <p><router-link class="readinglistlink" :to="{ name: 'readingList' }">Reading List</router-link></p>
    <p><router-link class="prizeslink" :to="{ name: 'prizes' }">Prizes</router-link></p>
  </div>
  </div>
</template>

<script>
import theFamily from '../views/Family.vue';

export default {
  name: "home",
  components: theFamily,
  data() {
    return {
      username: this.$store.state.user.username,
    }
  }
};
</script>

<style>
   .homebackground {
      width: 90vw;
      height: 100vh;
      padding-left: 4%;
    } 

  h1 {
  margin: 10px;
  margin-left: 0px;
  
  }

.home {
  position:absolute;
  top:20px;
  left: 10%;
  color: #724E91;
  text-decoration: none;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 150%;
}

.nav {
  margin-top: 0px;
  position: absolute;
  top: 150px;
  left: 10%;
  color: #F8C630;
  font-family: "Mouse Memoirs", sans-serif;
  font-size: 25px;

}

.familyview {
  border-width: 20px;
  border-color: black;
  text-align: center;
  top: 10px;
}

.profilelink,
.readinglistlink,
.prizeslink {
      color: #F8C630;

}

</style>