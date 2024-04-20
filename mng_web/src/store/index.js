import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import common from './modules/common'
import tags from './modules/tags'
import logs from './modules/logs'
import getters from './getters'
import research from '../research/store/research';
import researchGetters from '../research/store/getters';
Vue.use(Vuex)
const store = new Vuex.Store({
    modules: {
        user,
        common,
        logs,
        tags,
        research,
    },
    getters: {
        ...getters,
        ...researchGetters,
    },
})

export default store