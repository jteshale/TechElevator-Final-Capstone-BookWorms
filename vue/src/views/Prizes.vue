<template>
<div>
    <img class="homebackground" src="living-room.jpg">
        <div class="home">
            <p>Welcome, {{username}}!</p>
        </div>
        <div class="nav">
            <p><router-link class="profilelink" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
            <router-link class="familylink" :to="{ name: 'family'}">Family</router-link>
            <p><router-link class="readinglistlink" :to="{ name: 'readingList' }">Reading List</router-link></p>
            <p><router-link class="prizeslink" :to="{ name: 'prizes' }">Prizes</router-link></p>
        </div>
         <div class="prizewindow">
             <h1 class="pageheading">Prizes coming soon!</h1>
        </div>
         <div class="prizebody">
             <p v-for="prize in this.$store.state.prizes" v-bind:key="prize.description">Prizes will be displayed here!</p>
            <!-- <p> {{ this.prize }} </p> -->
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">
             <img id="trophy" src="trophy1.png">

        </div>
</div>
</template>   

<script>   
import PrizeService from '../services/PrizeService.js'

    export default {   
        data() {
            return {
            username: this.$store.state.user.username,
             }
        },

        created() {
            PrizeService.list().then( (response) => {
               this.$store.state.prizes = response.data; 
            })
        }
    };

</script>

<style scoped>
    .homebackground {
        width: 90vw;
        height: 100vh;
        padding-left: 4%;
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

.prizebody {
    position: absolute;
    height: 50%;
    width: 50%;
    top: 17%;
    left: 28%;
    background-color: #22162b;
    margin: 0 auto;
    color: white;
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 1.8em;
    text-align: center;
}

.prizewindow {
  text-align: center;
  position: absolute;
  top: 50px;
  left: 18%;
  background: rgba(114, 78, 145, 0.7);
  height: 70vh;
  width: 70vw;
}

.pageheading {
    font-family: "Mouse Memoirs", sans-serif;
    font-size: 4em;
    padding-top: 1.5%;
    color: #F8C630;
}

#trophy {
    height: 50%;
}

</style>