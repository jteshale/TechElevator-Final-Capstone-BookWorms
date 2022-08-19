import axios from 'axios';


export default {

    getFamilyName(user) {
        return axios.get(`/family_name`, user);
    },

    list(username) {
        return axios.get(`/family_members`, username);
    },

    registerChild(id) {
        return axios.post(`/register_child`, id)
    },

    registerFamily(family) {
        return axios.post(`/register_family_account`, family);
    },

    addToFamilyAccount(id) {
        return axios.post(`/add_family_member`, id)
    },

    getReadingActivity(username) {
        return axios.get(`/check_reading_activity/`+username)
    },
    
    getReadingActivityChild(user) {
        return axios.get(`/check_reading_activity_child`, user)
    }
}