import axios from 'axios';


export default {

    list(user) {
        return axios.get(`/books`, user);
    },
    listCurrent(user){
        return axios.get('/currently_reading', user)
    },
    listCompleted(user){
        return axios.get('/finished_reading', user)
    },
    listReadingDetails() {
        return axios.get(`/reading_details`)
    },
    minutesRead(username, isbn) {
        return axios.get('/minutes_read/'+username+'/'+isbn)
    },
    updateBookStatus(data){
        return axios.put('/update_book_status', data)
    },
    submitReading(readingActivity) {
        return axios.post('/log_reading', readingActivity);

    },

    submitBook(addedBook) {
        return axios.post(`/add_book`, addedBook);
    }
}