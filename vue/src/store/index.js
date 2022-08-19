import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

/*
 * The authorization header is set for axios when you login but what happens when you come back or
 * the page is refreshed. When that happens you need to check for the token in local storage and if it
 * exists you should set the header so that it will be attached to each request
 */
const currentToken = localStorage.getItem('token')
const currentUser = JSON.parse(localStorage.getItem('user'));

if(currentToken != null) {
  axios.defaults.headers.common['Authorization'] = `Bearer ${currentToken}`;
}

export default new Vuex.Store({
  state: {
    prizes: [],

    familyMembers: {

    },
    readingCompleted: {
      
    },
    bookAdded: {

    },
    currentlyReading: {

    },
    finishedReading: {

    },
    allBooks: {

    },
    totalMinutesRead: "",
    curBookMinutesRead: "",
    selectedBook: "",
    currentlyReadingSelectedBook: "",
    token: currentToken || '',
    user: currentUser || {}
  },
  mutations: {
    SET_CUR_BOOK_MINUTES_READ(state, payload){
      state.curBookMinutesRead = payload;
    },
    SET_TOTAL_MINUTES_READ(state, payload){
      state.totalMinutesRead = payload;
    },
    SET_CURRENTLY_READING(state, payload){
      state.currentlyReading = payload;
    },
    SET_FINISHED_READING(state, payload){
      state.finishedReading = payload;
    },
    SET_SELECTED_BOOK(state, payload){
      state.selectedBook = payload;
    },
    SET_CURRENTLY_READING_SELECTED_BOOK(state, payload){
      state.currentlyReadingSelectedBook = payload;
    },
    SET_AUTH_TOKEN(state, token) {
      state.token = token;
      localStorage.setItem('token', token);
      axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
    },
    SET_USER(state, user) {
      state.user = user;
      localStorage.setItem('user',JSON.stringify(user));
    },
    LOGOUT(state) {
      localStorage.removeItem('token');
      localStorage.removeItem('user');
      state.token = '';
      state.user = {};
      axios.defaults.headers.common = {};
    }
  }
})
