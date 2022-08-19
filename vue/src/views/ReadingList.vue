<template>
<div id="backgroundimg">
    <img class="homebackground" src="living-room.jpg">
    <div class="home">
    <p>Welcome, {{this.username}}!</p>
    </div>
    
    <div class="nav">
    <p><router-link class="profilelink" v-bind:to="{name: 'profile'}">Profile</router-link> </p>
    <p><router-link class="familylink" :to="{ name: 'family'}">Family</router-link></p>
    <p><router-link class="readinglistlink" :to="{ name: 'readingList' }">Reading List</router-link></p>
    <p><router-link class="prizelink" :to="{ name: 'prizes' }">Prizes</router-link></p>
    </div>

     <div class="profilewindow">
        <h3>Welcome to your Books!</h3>
        <button id="addbook" v-on:click="setReadingActivitytoTrue()">Add a Book!</button>
        <div id="addbookform">
        <form v-show="addReadingActivity" >
            <input name="isbn" type="text" placeholder="Enter ISBN" v-model="addedBook.isbn"/>
            <input name="title" type="text" placeholder="Enter title" v-model="addedBook.book_name"/>
            <input name="author" type="text" placeholder="Enter author" v-model="addedBook.author"/>
            <input name="pages" type="number" placeholder="Enter number of pages" v-model="addedBook.numberofpages"/>
            <input name="format" type="text" placeholder="PAPER or DIGITAL?" v-model="addedBook.format"/>
            <button v-on:click.prevent="submitAddedBook()">Add Book</button>
        </form>
        </div>
         <div class="bookAdded" v-for="book in this.$store.state.bookCompleted" v-bind:key="book.isbn">
            <p>{{ book.book_name + ", " + book.author + ", " + book.isbn }}</p>
        </div>
    </div>

 
</div>
   
</template>

<script>
import BookService from '../services/BookService'
export default {
    
    name: 'reading-list',
  

    data() {
        return {
            username: this.$store.state.user.username,
            addReadingActivity: false,
            addedBook: {
                book_name: "",
                isbn: "",
                author: "",
                numberofpages: "",
                format: ""
            }
        }
},
    methods: {
        setReadingActivitytoTrue() {
            this.addReadingActivity = true;
        },

        submitAddedBook() {
            BookService
            .submitBook(this.addedBook).then(response => {

                if (response.status === 201) {
                    this.$router.push('/actioncompleted')
                }
        })},
    
    },
    created() {
            BookService.list(this.user).then(response => {
                this.$store.state.bookCompleted = response.data;
            })
        }
}

</script>


<style scoped>

.bookAdded {
    height:40px;
    width: 50%;
    background: rgba(255, 253, 253, 0.7);
    position: relative;
    top: 175px;
    left: auto;
    margin: 0 auto;
    color:rgb(56, 53, 53);
    font-size: 20px;
    font-family: "Mouse Memoirs", sans-serif;
    border-radius: 15px;
    border: solid;
    border-color: white;
    margin-bottom: 4px;
}

.profilewindow {
    text-align: center;
    position:absolute;
    top:50px;
    left:18%;
    background: rgba(114,78,145,0.7);
    height:70vh;
    width: 70vw;
}

#addbookform {
    position: absolute;
    top: 125px;
    left: 20px
}

#addbook {
    
    left: 400px;
}

input[name="isbn"] {
    height: 50px;
    font-size: 20px;
}

input[name="title"] {
    height: 50px;
    font-size: 20px;
}

input[name="author"] {
    height: 50px;
    font-size: 20px;
}

input[name="pages"] {
    height: 50px;
    font-size: 20px;
}

input[name="format"] {
    height: 50px;
    font-size: 20px;
}
</style>