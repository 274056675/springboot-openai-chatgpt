import website from '../config/website'

const keyName = website.key + '-';

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
  localStorage.setItem(name, JSON.stringify(obj))
}

export function getStorage(params = {}) {
  let {
    name,
    debug
  } = params;
  name = keyName + name
  let obj = localStorage.getItem(name)
  if (obj) {
    obj = JSON.parse(obj)
    return debug ? obj : obj.content
  }
  return ''
}

export function removeStorage(params = {}) {
  let { name } = params
  name = keyName + name
  return localStorage.removeItem(name)
}