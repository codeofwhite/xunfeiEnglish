import {createStore} from 'vuex';
import createPersistedState from 'vuex-persistedstate'; // 持久化

export default createStore({
    state: {
        userEmail: '',
        isLoggedIn: false
    },
    mutations: {
        setUserEmail(state, email) {
            state.userEmail = email;
        },
        setLoggedIn(state, status) {
            state.isLoggedIn = status;
        }
    },
    plugins: [createPersistedState()]
});