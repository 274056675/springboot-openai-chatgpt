import {
  setStorage,
  getStorage
} from "@/utils/storage.js"
import {
} from "@/api/user.js"

const mqtt = {
  state: {
    sysConfig: getStorage({ name: 'sysConfig' }) || {}, //系统的配置参数
  },
  actions: {

  },
  mutations: {

    SET_SYS_CONFIG(state, sysConfig) {
      state.sysConfig = sysConfig
      setStorage({
        name: 'sysConfig',
        content: state.sysConfig
      })
    },
  
  }
}

export default mqtt