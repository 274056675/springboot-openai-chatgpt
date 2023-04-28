const researchGetters = {
  provinces: state => state.research.provinces,
  remoteValues: state => state.research.remoteValues,
  allOptinsAcc: state => state.research.allOptinsAcc,
  allDicListData: state => state.research.allDicListData,
  wxBindCode: state => state.user.wxBindCode,
  tenantId: state => state.user.tenantId,
  tenantName: state => state.user.tenantName,
}
export default researchGetters