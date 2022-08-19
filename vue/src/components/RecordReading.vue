<template>
<div>

    <div class="book-container">
        <book-card v-bind:book="book" v-for="book in books" v-bind:key="book.isbn" />
    </div>
</div>
  
</template>

<script>
import BookCard from '@/components/BookCard.vue';

export default {
    name: 'reading-list',
    props: {
        books: Array,
    },
    components: {
        BookCard
    }
}
</script>

<style scoped>

.book-container {
    background-color: #22162B;
    display: grid;
    gap: 1%;
    grid-auto-flow: column;
    margin-bottom: 0;
    padding-bottom: 0;


    overflow-x: auto;
    overscroll-behavior-inline: contain;
    /* border: 10px solid #57386e;
    border-radius: 1px; */
    /* height: 100%; */

}
</style>