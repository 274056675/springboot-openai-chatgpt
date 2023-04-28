import request from "@/router/axios";

/**
 * 文件流返回
 * @param url 接口地址
 */
export const exportBlob = (url) => {
  return request({
    url: url,
    method: 'get',
    responseType: 'blob'
  })
}
