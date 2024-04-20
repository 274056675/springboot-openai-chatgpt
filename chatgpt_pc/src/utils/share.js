import Vue from 'vue'
import { getInviteurlApi } from '@/api/user'

export const share = () => {
  let p = new Promise((resolve, reject) => {
    getInviteurlApi().then(res => {
      let title = res.msg
      const textArea = document.createElement('textarea')
      textArea.value = title
      // 使text area不在viewport，同时设置不可见
      document.body.appendChild(textArea)
      textArea.focus()
      textArea.select()
      return new Promise((resolve, reject) => {
        // 执行复制命令并移除文本框
        document.execCommand('copy') ? resolve() : reject(new Error('出错了'))
        textArea.remove()
      }).then(() => {
        Vue.prototype.$message.success("复制成功");
        resolve()
      }).catch(() => {
        Vue.prototype.$message.error('复制失败')
        reject()
      })
      // navigator.clipboard
      //   .writeText(title)
      //   .then(() => {
      //     Vue.prototype.$message.success('复制成功')
      //   })
      //   .catch(() => {
      //     Vue.prototype.$message.error('复制失败')
      //   })
    })
  })
  return p
}