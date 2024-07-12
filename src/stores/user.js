import {createStore} from 'vuex';
import createPersistedState from 'vuex-persistedstate'; // 持久化

export default createStore({
    state: {
        user_email: '',
        isLoggedIn: false
    },
    mutations: {
        setUserEmail(state, email) {
            state.uemail = email;
        },
        setLoggedIn(state, status) {
            state.isLoggedIn = status;
        }
    },
    plugins: [createPersistedState()]
});