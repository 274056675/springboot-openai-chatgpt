import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import other from './modules/other'
import getters from './getters'

Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    user,
    other,
  },
  getters,
})
