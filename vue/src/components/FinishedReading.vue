<template>
<div>

    <div class="book-container">
        <finished-reading-card v-bind:book="book" v-for="book in books" v-bind:key="book.isbn" />
    </div>
</div>
  
</template>

<script>
import FinishedReadingCard from '@/components/FinishedReadingCard.vue';

export default {
    name: 'reading-list',
    props: {
        books: Array,
    },
    components: {
        FinishedReadingCard
    }
}
</script>

<style scoped>

.book-container {
    background-color: #22162B;
    display: grid;
    gap: 1%;
    grid-auto-flow: column;


    overflow-x: auto;
    overscroll-behavior-inline: contain;
    /* border: 10px solid #57386e;
    border-radius: 1px; */
    /* height: 100%; */

}
</style>