import Vue from 'vue'
import Vuex from 'vuex'
import app from './modules/app'
import user from './modules/user'
import getters from './getters'
import navTab from './modules/navTab'
import launchModule from './modules/launchModule'
import queryParams from './modules/queryParams'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    app,
    user,
    navTab,
    launchModule,
    queryParams
  },
  getters
})

export default store
