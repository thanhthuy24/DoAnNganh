// store/index.js
import { createStore } from 'vuex';

export default createStore({
  state: {
    isLoggedIn: false,
    user: null, // Lưu thông tin người dùng
  },
  mutations: {
    login(state, user) {
      state.isLoggedIn = true;
      state.user = user;
    },
    logout(state) {
      state.isLoggedIn = false;
      state.user = null;
    },
  },
  actions: {
    login({ commit }, user) {
      commit('login', user); // Gọi mutation để cập nhật state
    },
    logout({ commit }) {
      commit('logout'); // Gọi mutation để cập nhật state
    },
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    user: (state) => state.user,
  },
});
