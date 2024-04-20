/**
 * 全局配置文件
 */
import { env } from './env';
export default {


    key: 'uni-chatgpt-web',//配置主键,目前用于存储
    tokenHeader: 'Blade-Auth',
    //接口访问服务头部
    Authorization: 'c2FiZXI6c2FiZXJfc2VjcmV0',
    apiRequestHead: env.NODE_ENV == 'development' ? 'mjkj-chat' : 'mjkj-chat',

}