
export const setTreeDataUtil = (
  arr,
  parentidKeyName,
  idName = 'id',
  topPidDefault = 0,
  delHasChild) => {
  // 处理成树结构数据
  //树结构数据处理方法 arr:数据  parentidKeyName:父id的key名  idName:id的key名  topPidDefault最顶级pid的值
  if (delHasChild) {
    arr = arr.map((item) => {
      delete item[delHasChild]
      return item
    })
  }
  let data = arr
  let menuArr = []
  let childrenObj = {}

  data.forEach((item) => {
    if (
      item[parentidKeyName] != topPidDefault &&
      item[parentidKeyName] != null &&
      item[parentidKeyName] != ''
    ) {
      if (childrenObj[item[parentidKeyName]] == undefined) {
        childrenObj[item[parentidKeyName]] = []
      }
      childrenObj[item[parentidKeyName]] = [
        ...childrenObj[item[parentidKeyName]],
        item,
      ]
    } else {
      menuArr.push(item)
    }
  })
  menuArr.map((item) => {
    childrenFun(item)
  })
  function childrenFun (obj) {
    if (childrenObj[obj[idName]] && !obj.isScope) {
      obj.children = childrenObj[obj[idName]]
      obj.children.map((item) => {
        return childrenFun(item)
      })
    } else {
      return obj
    }
  }
  return menuArr
}


export const findFlowStepDataFun = (json, resourceId, option) => {
  //查找当前流程的下一步
  return new Promise(resolve => {
    let columnKey = []
    let column = option.column;
    let group = option.group;
    let dataArr = [];
    if (column) {
      dataArr = [...dataArr, ...column];
    }
    if (group) {
      dataArr = [...dataArr, ...group];
    }
    columnKey = dataArr.map((item) => item.prop);
    columnKey = columnKey.filter(item => item);
    
    let step = json.childShapes
    //当前步骤
    let currStep = {}
    step.forEach((item) => {
      if (item.resourceId == resourceId) {
        currStep = item
      }
    })
    //下一步箭头
    let nextId = []
    currStep.outgoing.forEach(outItem => {
      nextId.push(outItem.resourceId)
    })
    let nextArrows = []
    step.forEach(item => {
      if (nextId.includes(item.resourceId)) {
        nextArrows.push(item)
      }
    })
    //下一步流程走向
    let succeedId = []
    let failId = []
    nextArrows.forEach(item => {
      let arrows = item.properties.conditionsequenceflow
      if (arrows && arrows.expression) {
        if (arrows.expression.staticValue == "${pass}") {
          succeedId.push(item.outgoing[0].resourceId)
        } else if (arrows.expression.staticValue == "${!pass}") {
          failId.push(item.outgoing[0].resourceId)
        } else {
          succeedId.push(item.outgoing[0].resourceId)
        }
      } else {
        succeedId.push(item.outgoing[0].resourceId)
      }
    })
    
    let flowArr = []
    //获取 审批通过/驳回指向的流程数据
    step.forEach((item) => {
      if (succeedId.length > 0 && item.resourceId == succeedId[0]) {
        flowArr.push({
          succeed: true,
          data: item
        })
      }
      if (failId.length > 0 && item.resourceId == failId[0]) {
        flowArr.push({
          succeed: false,
          data: item
        })
      }
    })
    //获取流程 指定候选组
    flowArr = flowArr.map(item => {
      let task = item.data.properties.usertaskassignment
      if (task && task.assignment) {
        //已绑定分配人 忽略候选
        if (task.assignment.candidateGroups && task.assignment.candidateGroups.length > 0) {
          //获取候选组绑定的角色 
          let groups = []
          task.assignment.candidateGroups.forEach(groupItem => {
            let key = groupItem.value.replace('${', '').replace('}', '')
            if (!(columnKey.includes(key))) {
              groups.push(key)
            }
          })
          item.groups = groups
        }
      }
      return item
    })
    resolve(flowArr)
  })

}

export const findFlowCandRoleDataFun = (json) => {
  //查找所有流程候选组配置的角色
  return new Promise(resolve => {
    let step = json.childShapes
    let roleArr = []
    step.forEach(item => {
      let task = item.properties.usertaskassignment
      if (task && task.assignment) {
        if (task.assignment.candidateGroups && task.assignment.candidateGroups.length > 0) {
          task.assignment.candidateGroups.forEach(groupItem => {
            let key = groupItem.value.replace('${', '').replace('}', '')
            if (key && key.indexOf('role_') == 0 && (!roleArr.includes(key))) {
              roleArr.push(key)
            }
          })
        }
      }
    })
    resolve(roleArr)
  })
}

export const findFlowCurrTowardsFun = (json, resourceId) => {
  //查找当前流程走向
  return new Promise(resolve => {
    let step = json.childShapes
    //当前步骤
    let currStep = {}
    step.forEach((item) => {
      if (item.resourceId == resourceId) {
        currStep = item
      }
    })
    //下一步箭头id
    let nextId = []
    currStep.outgoing.forEach(outItem => {
      nextId.push(outItem.resourceId)
    })
    let nextArrows = []
    let countersign = false //是否会签
    step.forEach(item => {
      if (nextId.includes(item.resourceId)) {
        nextArrows.push(item)
        if (item.properties.multiinstance_type != 'None') {
          countersign = true
        }
      }
    })
    //下一步流程走向
    let succeedId = []
    let failId = []
    let obj = {
      agreeBtn: false,
      disagreeBtn: false,
    }
    nextArrows.forEach(item => {
      let arrows = item.properties.conditionsequenceflow
      if (arrows && arrows.expression) {
        if (arrows.expression.staticValue == "${pass}") {
          succeedId.push(item.outgoing[0].resourceId)
          obj.agreeText = item.properties.name
        } else if (arrows.expression.staticValue == "${!pass}") {
          failId.push(item.outgoing[0].resourceId)
          obj.disagreeText = item.properties.name
        } else {
          succeedId.push(item.outgoing[0].resourceId)
          obj.agreeText = item.properties.name
        }
      } else if (arrows == '${pass}') {
        succeedId.push(item.outgoing[0].resourceId)
        obj.agreeText = item.properties.name
      } else if (arrows == "${!pass}") {
        failId.push(item.outgoing[0].resourceId)
        obj.disagreeText = item.properties.name
      } else {
        succeedId.push(item.outgoing[0].resourceId)
        obj.agreeText = item.properties.name
      }
    })
    if (succeedId.length > 0) {
      obj.agreeBtn = true
    }
    if (failId.length > 0) {
      obj.disagreeBtn = true
    }
    if (countersign && failId.length <= 0) {
      obj.disagreeBtn = true
      obj.disagreeText = '驳回'
    }
    resolve(obj)
  })
}

export const analysisFunction = (fun) => {
  if (fun) {
    // fun = fun.replace(/↵/g, '\n')
    fun = fun.replace(/\/\*{1,2}[\s\S]*?\*\//gis, '')
    // fun = fun.replace(/(?:^|\n|\r)\s*\/\*[\s\S]*?\*\/\s*(?:\r|\n|$)/g, '')
    fun = fun.replace(/(?:^|\n|\r)\s*\/\/.*(?:\r|\n|$)/g, '')
    // fun = fun.replace('&lt;', '<')
    try {
      if (eval(`(${fun})`)) {
        return eval(`(${fun})`)
      } else {
        return () => { }
      }
    } catch {
      return false
    }
  }
}
export const getStrDataFunction = (str) => {
  return analysisFunction(
    `function getData(){${str}}`
  )();
}

export const findParentNodeFun = (el, findName, topName) => {
  /* 
    el：dom元素
    findName：查找的class名
    topName：class顶级
  */
  let parentNode = el.parentNode
  let classArr = parentNode.className.split(' ')
  if (classArr.includes(findName)) {
    return parentNode
  } else if (classArr.includes(topName) || parentNode.tagName == 'BODY') {
    return ''
  }
  return findParentNodeFun(el.parentNode, findName, topName)
}

export const downloadFileFun = (data, name = "文件") => {
  //文件下载
  let blob = new Blob([data], {
    type: 'application/vnd.ms-excel',
    name: name,
  })
  let url = window.URL.createObjectURL(blob)
  var a = document.createElement('a')
  document.body.appendChild(a)
  a.style = 'display: none'
  a.href = url
  a.download = name
  a.click()
  window.URL.revokeObjectURL(url)
}

export const getCurrentDateFun = (type) => {
  let now = new Date();
  let year = now.getFullYear(); //获取完整的年份(4位,1970-????)
  let month = now.getMonth() + 1; //获取当前月份(0-11,0代表1月)
  let today = now.getDate(); //获取当前日(1-31)
  let hour = now.getHours(); //获取当前小时数(0-23)
  let minute = now.getMinutes(); //获取当前分钟数(0-59)
  let second = now.getSeconds(); //获取当前秒数(0-59)
  let nowTime = ''
  if (type == 'data') {
    nowTime = year + '-' + fillZero(month) + '-' + fillZero(today)
  } else if (type == 'time') {
    nowTime = fillZero(hour) + ':' + fillZero(minute) + ':' + fillZero(second)
  } else if (type == 'SplicingTime') {
    //用于根据时间生成编号
    nowTime = year + fillZero(month) + fillZero(today) + fillZero(hour) + fillZero(minute) + fillZero(second)
  } else {
    nowTime = year + '-' + fillZero(month) + '-' + fillZero(today) + ' ' + fillZero(hour) + ':' +
      fillZero(minute) + ':' + fillZero(second)
  }
  return nowTime
}
function fillZero (str) {
  var realNum;
  if (str < 10) {
    realNum = '0' + str;
  } else {
    realNum = str;
  }
  return realNum;
}