/* 
 当前mixins使用于表单设计器
*/
// import request from '@/router/axios';
import { getRemoteValuesApi, executeRuleByApi, getSelectRemoteDataApi, getJsOrCssStrApi, getActionApi, postActionApi, putActionApi, deleteActionApi, requestActionApi } from "@/api/research/form";

export default {
  data() {
    return {

    }
  },
  mounted() {
  },
  methods: {
    //远程接口取值 column：所有的字段配置
    mixinGetApiData(column) {
      return new Promise(async (resolve) => {
        let formObj = {}
        let specialObj = {} //特殊的
        let formKey = []
        let promiseArr = []
        column.forEach(item => {
          if (item.getValueApiName) {
            let url = item.getValueApiName
            if (url.indexOf('/') == 0) {
              url = '/api' + url
            }
            formKey.push({
              prop: item.prop,
              type: item.type
            })
            promiseArr.push(
              getRemoteValuesApi(url)
            )
          }
        });
        let resArr = await Promise.all(promiseArr)
        resArr.forEach((item, index) => {
          if (!['title'].includes(formKey[index].type)) {
            formObj[formKey[index].prop] = item.data
          } else {
            specialObj[formKey[index].prop] = {
              data: item.data,
              type: formKey[index].type
            }
          }
        })
        resolve({ formObj, specialObj })
      })
    },
    //填值规则 column：所有的字段配置  commonFormData：表单的所有值
    mixinGetExecuteRule(column, commonFormData) {
      return new Promise(async (resolve) => {
        let rules = []
        column.forEach(item => {
          if (item.fillRuleCode) {
            rules.push({
              ruleCode: item.fillRuleCode,
            })
          }
        })
        if (rules.length == 0) {
          resolve(false)
          return false
        }
        let res = await executeRuleByApi({
          rules,
          commonFormData
        })
        
        let ruleResObj = {}
        res.data.data.forEach(item => {
          ruleResObj[item.ruleCode] = item.result
        })
        if (res.data.success) {
          resolve(ruleResObj)
        } else {
          resolve(false)
        }
      })
    },
    //选择字段远端数据
    mixinGetSelectRemoteData(url, format) {
      return new Promise(async (resolve) => {
        if (url.indexOf('/') == 0) {
          url = '/api' + url
        }
        let res = await getSelectRemoteDataApi(url)
        let data = res.data
        try {
          if (format) {
            format = format.split('.')
            format.forEach(item => {
              data = data[item]
            })
          }
        } catch (error) {
          console.warn('字段远端数据值格式配置异常,url:', url)
        }
        resolve(data)
      })
    },
    //js增强 接口请求api
    mixinRequestData(url, parameter, method, type, config) {
      return new Promise(async (resolve, reject) => {
        if (url.indexOf('/') == 0) {
          url = '/api' + url
        }
        if (type == 'request') {
          requestActionApi(url, parameter, method).then(res => {
            resolve(res.data)
          }).catch(err => {
            reject(err)
          })
        }
        if (method == 'get' && !type) {
          getActionApi(url, parameter, config).then(res => {
            resolve(res.data)
          }).catch(err => {
            reject(err)
          })
        }
        if (method == 'post' && !type) {
          postActionApi(url, parameter, config).then(res => {
            resolve(res.data)
          }).catch(err => {
            reject(err)
          })
        }
        if (method == 'put' && !type) {
          putActionApi(url, parameter, config).then(res => {
            resolve(res.data)
          }).catch(err => {
            reject(err)
          })
        }
        if (method == 'delete' && !type) {
          deleteActionApi(url, parameter, config).then(res => {
            resolve(res.data)
          }).catch(err => {
            reject(err)
          })
        }
      })

    },
    //js/css外部增强
    mixinExternalEnhance(url) {
      return new Promise(async (resolve) => {
        let res = {}
        if (url.indexOf('/') == 0) {
          url = '/api' + url
        }
        res = await getJsOrCssStrApi(url)
        resolve(res.data)
      })
    },
  },
}