// store/index.js
// import axios from 'axios';
import APIs, { endpoints } from '@/configs/APIs.js';
import { createStore } from 'vuex';
import { useCookies } from "vue3-cookies";

import createPersistedState from "vuex-persistedstate";
// import {  } from 'configs/APIs';
// import auth from './modules/auth';

const { cookies } = useCookies();

export default createStore({
  state: {
    isLoggedIn: false,
    user: null, // Lưu thông tin người dùng
    token: null,
    currentUser: null
  },
  mutations: {
    login(state, user) {
      state.isLoggedIn = true;
      state.user = user;
    },
    logout(state) {
      state.token = null;
      state.isLoggedIn = false;
      state.user = null;
    },
    set_token(state, token) {
      state.token = token;
    }
  },
  actions: {
    async login({ commit }, credentials) {
      try {
        const res = await APIs.post(endpoints.login, credentials);
        const token = res.data;
      
        commit('set_token', token);
        
        const response = await APIs.get(endpoints.currentUser, {
          headers: {
            Authorization: `Bearer ${token}`, // Include token in the headers
          },
        });

        const user = response.data;
        commit('login', user);

      } catch(err){
        console.error('Login failed: ', err);
      }


       // Gọi mutation để cập nhật state
    },
    logout({ commit }) {
      cookies.set('token', '');
      commit('logout'); // Gọi mutation để cập nhật state
      
    },
    initializeStore({ commit }) {
      const user = localStorage.getItem('user');
      if (user) {
        commit('setUser', JSON.parse(user));
      }
    },
  },
  getters: {
    isLoggedIn: (state) => state.isLoggedIn,
    user: (state) => state.user,
  },

  // modules: {
  //   auth
  // },
  plugins: [createPersistedState()]
});
