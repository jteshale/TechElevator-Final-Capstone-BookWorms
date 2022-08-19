<template>
<div id="backgroundimg">
    <img class="homebackground" src="living-room.jpg">
    <div class="home">
    <p>Welcome, {{ this.username }}!</p>
    </div>
    
    <div class="nav">
    <p><router-link class="profilelink" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
    <router-link class="familylink" :to="{ name: 'family'}">Family</router-link>
    <p><router-link class="readinglistlink" :to="{ name: 'readingList' }">Reading List</router-link></p>
    <p><router-link class="prizeslink" :to="{ name: 'prizes' }">Prizes</router-link></p>
    </div>

     <div class="profilewindow">
        <!-- <h3>Welcome to your profile!</h3> -->
        <!-- <button id="recordreading" v-on:click="setReadingActivitytoTrue()">Add Reading Activity</button> -->
        <form v-show="addReadingActivity" >
            <input name="userName" type="text" v-model="readingActivity.username"/>
            <input name="minutes_read" type="number" v-model="readingActivity.minutes_read" />
            <input name="isbn" type="text" v-model="readingActivity.isbn"/>
            <button type="submit" v-on:click.prevent="submitReadingActivity()">Submit Reading Activity</button>
        </form>
            <h2>Your Books:</h2>
            <div v-on:click="onClickBooks()">
            <record-reading class="allBooks" v-bind:books="$store.state.allBooks"/>
            </div>
            <!-- <h2>Currently Reading: <h3>Hours: {{this.hours}} Minutes: {{this.minutes}}</h3></h2> -->
            <h2>Currently Reading: <h3>Hours: {{hours}} Minutes: {{minutes}}</h3></h2>
            <currently-reading class="allBooks" v-bind:books="$store.state.currentlyReading"/>
            <h2>Finished Books:</h2>
            <finished-reading v-bind:books="$store.state.finishedReading"/>
    </div>


 
</div>
   
</template>

<script>
import BookService from '../services/BookService';
import RecordReading from '@/components/RecordReading.vue';
import CurrentlyReading from '@/components/CurrentlyReading.vue';
import FinishedReading from '../components/FinishedReading.vue';
import FamilyService from '../services/FamilyService';
export default {
  components: { 
    RecordReading,
    CurrentlyReading,
    FinishedReading
   },
    name: 'the-profile',
  

    data() {
        return {
            username: this.$store.state.user.username,
          addReadingActivity: false,
          totalMinutes: "",
          hours: "",
          minutes: "",
          readingActivity: {
              username: "", 
              minutes_read: "",
              isbn: ""
          }
    }
},
    methods: {
        setReadingActivitytoTrue() {
            this.addReadingActivity = true;
        },
        onClickBooks() {
            FamilyService.getReadingActivityChild().then(response => {
                this.totalMinutes = response.data.minutes_read;
                this.minuteHour();
            })
        },

        submitReadingActivity() {
            BookService
            .submitReading(this.readingActivity).then( response => {
                if (response.status === 201) {
                    this.$router.push('/actioncompleted')
                }
            })
        },

        minuteHour() {
            let minute = this.totalMinutes % 60;
            this.totalMinutes -= minute;
            this.hours = this.totalMinutes/60;
            this.hours = this.hours.toFixed(1);
            // this.totalMinutes %= 60;
            this.minutes = minute;
        }
    },
    computed: {
  
    },
    props: ["user"],
    
    created() {
            BookService.listCurrent(this.user).then(response => {
                this.$store.state.currentlyReading = response.data;
            }),
            BookService.listCompleted(this.user).then(response => {
                this.$store.state.finishedReading = response.data;
            }),
            BookService.list(this.user).then(response => {
                this.$store.state.allBooks = response.data;
            }),
            FamilyService.getReadingActivityChild().then(response => {
                this.totalMinutes = response.data.minutes_read;
            }),
            FamilyService.getReadingActivityChild().then(response => {
                this.$store.commit('SET_TOTAL_MINUTES_READ', response.data.minutes_read); 
            }),
            this.onClickBooks()
        },
        
    }
</script>


<style scoped>
h3 {
    margin-bottom: 0;
    margin-top: 0;
    padding-left: 13vw;
}

h2 {
    display: flex;
    background-color: #3a2649;
    color:white;
    font-family: "Mouse Memoirs", sans-serif;
    text-align: left;
    font-size: 2.5rem;
    margin-bottom: 0;
    margin-top: 0;
    padding-top: 10px;
    padding-left: 2rem;
    padding-bottom: 10px;
}

.allBooks {
    height: 13rem;
    padding-bottom: 3.5vh;
}

.bookscompleted {
    height:50%;
    width: 70%;
    background-color: #22162B;
    position:relative;
    margin: 0 auto;
    color:white;
}

.profilewindow {
    text-align: center;
    position:absolute;
    top:50px;
    left:18%;
    background: rgba(114,78,145,.95);
    /* background: #22162B; */
    height:97.4vh;
    width: 70vw;
}
</style>