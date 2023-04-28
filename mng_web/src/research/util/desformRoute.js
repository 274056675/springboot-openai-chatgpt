import router from '@/router/router';
import { getFormListDetailApi } from '@/api/research/formlist'
export const getDesformDataByRouteDataId = () => {
  return new Promise((resolve, reject) => {
    let dataId = router.history.current.query.dataId
    getFormListDetailApi(dataId).then(res => {
      if (res.data.success) {
        let dataAnalyJosn = {}
        try {
          dataAnalyJosn = eval('(' + res.data.data.formDataJson + ')')
        } catch (e) {
          console.error('数据解析异常')
          dataAnalyJosn = {}
        }
        resolve(dataAnalyJosn)
      } else {
        resolve({ error: '获取数据异常' })
      }
    }).catch(error => {
      reject(error)
    })
  })
}