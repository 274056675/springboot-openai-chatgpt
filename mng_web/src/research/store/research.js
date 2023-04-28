const research = {
  state: {
    // 基础
    provinces: {}, //省市区数据
    // 表单
    remoteValues: [], //远程取值是否完成
    allOptinsAcc: [],//所有的配置是否处理完成
    allDicListData: [],//所有字典列表

  },
  mutations: {
    SET_PROVINCES: (state, provinces) => {
      state.provinces = provinces;
    },
    SET_REMOTE_VAKUES: (state, data) => {
      if (data.type == 'del') {
        state.remoteValues = []
      } else {
        state.remoteValues.push(data.bool);
      }
    },
    SET_ALL_OPTINS_ACC: (state, data) => {
      if (data.type == 'del') {
        state.allOptinsAcc = []
      } else {
        state.allOptinsAcc.push(data.bool);
      }
    },
    SET_ALL_DIC_LIST_DATA: (state, data) => {
      state.allDicListData = data
    },
  },
  actions: {},
}
export default research