<template>
  <div class="card" :class="{ selected: isSelected }">
    <img v-on:click="selectBook()" v-if="book.isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
    <div v-on:click="selectBook()" id="nocover" v-else >{{ book.book_name }}<p>{{ book.author }}</p></div>
    <div v-on:click="selectBook()" v-bind:class="{ 'color-overlay': isSelected }"></div>
    <p class="centered">Log Reading:</p>
    <form v-show="isSelected" class="centered">
        <!-- <label for="time-read">Minutes Read:</label><br> -->
        <input placeholder="Minutes" min=1 id="time-read" name="timeRead" type="number" v-model="readingActivity.minutes_read"/><br>
        <input id="submit" v-on:click.prevent="submitReadingInfo()" type="submit"/>
    </form>
  </div>
</template>

<script>
import BookService from '../services/BookService';
import FamilyService from '../services/FamilyService';

export default {
    name: 'book-card',
    props: {
        book: Object,
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            user: this.$store.state.user,
            readingActivity: {
              username: this.$store.state.user.username, 
              minutes_read: "",
              isbn: this.book.isbn
            }

        }
    },
    methods: {
        selectBook(){
            if(this.$store.state.selectedBook != this.book.isbn){
                this.$store.commit('SET_SELECTED_BOOK', this.book.isbn)
            }
            else{
                this.$store.commit('SET_SELECTED_BOOK', '')
            }
            this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', '0')
            
        },
        submitReadingInfo() {
            BookService.submitReading(this.readingActivity).then( response => {
                if (response.status === 201) {
                    // this.$router.push('/actioncompleted')
                }
                BookService.listCurrent(this.user).then(response => {
                    this.$store.commit('SET_CURRENTLY_READING', response.data)
                })
                BookService.listCompleted(this.user).then(response => {
                    this.$store.commit('SET_FINISHED_READING', response.data)
                })
            });
            this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', '0');
            FamilyService.getReadingActivityChild(this.user).then(response => {
                this.$store.commit('SET_TOTAL_MINUTES_READ', response.data.minutes_read); 
            }),
            BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                // this.minutes_read = response.data;
                // this.totalMinutes = response.data;
                this.$store.commit('SET_CUR_BOOK_MINUTES_READ', response.data)
            });
        }
        
    },
    computed: {
        isSelected(){
            return this.$store.state.selectedBook == this.book.isbn;
        }
    }
}
</script>

<style scoped>
p {
    color: white;
    font-size: 1.1rem;
    margin: 1px;
}

#nocover {
    height: 98%;
    font-size: 1.5rem;
    color: white;
}

#time-read {
    width: 5rem;
}

#submit {
    width: 5.5rem;
}

label {
    color: white;
    padding-bottom: 2rem;

}

#time_read {
    width: 6rem
}

.color-overlay {
    /* width: 100%; */
    height: 100%;
    background: #000;
    opacity: .7;
    position: relative;
    top: -101%;
    /* z-index: 1; */
}

.centered {
    position: relative;
    top: -155%;
    left: 0px;
}

.card {
    height: 14rem;
    background-color: #3f2950;
}

.card.selected {
    background-color: rgba(211,211,211,0.1);
    border: 2px solid white;
    border-radius: 1px;
}

.card .book-title {
    font-size: 1.5rem;
}

.card .book-author {
    font-size: 1rem;
}

img {
    height: 98%;
    /* height: 14rem; */
}
</style>