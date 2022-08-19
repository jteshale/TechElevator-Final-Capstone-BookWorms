<template>
  <div class="card" v-bind:class="{ selected: isSelected }">
    <img v-on:click="selectBook()" v-if="book.isbn" v-bind:src="'http://covers.openlibrary.org/b/isbn/' + book.isbn + '-M.jpg'" />
    <div v-on:click="selectBook()" v-bind:class="{ 'color-overlay': isSelected }"></div>
    
    <form v-show="isSelected" class="centered">
        <p class="centered">Hours: {{ this.hours }}</p> 
        <p>Minutes: {{ this.minutes }}</p>
        <!-- <label for="time-read">Minutes Read:</label><br> -->
        
        <button id="submit" v-on:click.prevent="markComplete()" type="submit">Mark Finished</button>
    </form>
  </div>
</template>

<script>
import BookService from '../services/BookService';

export default {
    name: 'book-card',
    props: {
        book: Object,
    },
    data() {
        return {
            url: "/book/" + this.book.isbn,
            minutes_read: "",
            // hours: "",
            // minutes: "",
            // totalMinutes: "",
            readingActivity: {
                user_id: "",
                book_id: "",
                minutes_read: "",
                username: this.$store.state.user.username, 
                isbn: this.book.isbn
            },
            bookStatus: [this.book.isbn, 'READ']
        }
    },
    methods: {
        selectBook(){
            if(this.$store.state.currentlyReadingSelectedBook == this.book.isbn){
                this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', "")
            } else{
                this.$store.commit('SET_CURRENTLY_READING_SELECTED_BOOK', this.book.isbn)
            }
            BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                // this.minutes_read = response.data;
                // this.totalMinutes = response.data;
                this.$store.commit('SET_CUR_BOOK_MINUTES_READ', response.data)
            });
            // this.minuteHour();
            this.$store.commit('SET_SELECTED_BOOK', '0')
            
        },
        markComplete() {
            BookService.updateBookStatus(this.bookStatus).then( response => {
                if (response.status === 201) {
                    this.$router.push('/actioncompleted')
                }
                BookService.listCurrent(this.user).then(response => {
                this.$store.state.currentlyReading = response.data;
                }),
                BookService.listCompleted(this.user).then(response => {
                this.$store.state.finishedReading = response.data;
                })
            });
        },
        minuteHour() {
            let minute = this.totalMinutes % 60;
            this.totalMinutes -= minute;
            this.hours = this.totalMinutes/60;
            // this.totalMinutes %= 60;
            this.minutes = minute;
        }
        
    },
    created() {
        BookService.minutesRead(this.readingActivity.username, this.readingActivity.isbn).then(response => {
                this.minutes_read = response.data;
                this.totalMinutes = response.data;
            });
    },
    computed: {
        isSelected(){
            return this.$store.state.currentlyReadingSelectedBook == this.book.isbn;
        },
        totalMinutes(){
            return this.$store.state.curBookMinutesRead;
        },
        minutes(){
            return this.totalMinutes % 60;
        },
        hours(){
            let minute = this.totalMinutes % 60;
            return (this.totalMinutes-minute)/60;
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