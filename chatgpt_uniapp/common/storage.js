import unisite from './unisite'
const keyName = unisite.key + '-';

export function setStorage(params = {}) {
  let {
    name,
    content,
  } = params;
  name = keyName + name
  let obj = {
    dataType: typeof (content),
    content: content,
    datetime: new Date().getTime()
  }
  uni.setStorageSync(name, JSON.stringify(obj))
}

export function getStorage(params = {}) {
  let {
    name,
    debug
  } = params;
  name = keyName + name
  let obj = uni.getStorageSync(name)
  if (obj) {
    obj = JSON.parse(obj)
    return debug ? obj : obj.content
  }
  return ''
}

export function removeStorage(params = {}) {
  let { name } = params
  return uni.removeStorageSync(name)
}