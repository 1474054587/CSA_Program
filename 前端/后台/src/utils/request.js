import axios from 'axios'
import {
  Message,
  MessageBox
} from 'element-ui'
import store from '../store'
import {
  getToken
} from '@/utils/auth'
import Cookies from 'js-cookie'

// 创建axios实例
const service = axios.create({
  baseURL: process.env.BASE_API, // api 的 base_url
  timeout: 5000 // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (config.requestBase == 'Bili_1') {
      config.baseURL = process.env.Bili_1
      config.headers['Cookies'] = "buvid3=E3B97A08-B3B8-A3E8-9FF3-47772DE966C151069infoc; rpdid=|(J|)lJ|~uuu0J'uYJJRJ)Yl~; video_page_version=v_old_home; i-wanna-go-back=-1; LIVE_BUVID=AUTO3916419815112264; buvid_fp=E3B97A08-B3B8-A3E8-9FF3-47772DE966C151069infoc; buvid_fp_plain=undefined; CURRENT_QUALITY=80; nostalgia_conf=-1"
      // config.headers['Content-Type'] = "application/json;charset=utf-8"
    } else if (config.requestBase == 'Bili_2') {
      config.baseURL = process.env.Bili_2
    }
    if (store.getters.token) {
      config.headers['X-Token'] = getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改
     */
    const res = response.data
    if (res.code !== 20000) {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })

      // 50008:非法的token; 50012:其他客户端登录了;  50014:Token 过期了;
      if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
        MessageBox.confirm(
          '你已被登出，可以取消继续留在该页面，或者重新登录',
          '确定登出', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
          }
        ).then(() => {
          store.dispatch('FedLogOut').then(() => {
            location.reload() // 为了重新实例化vue-router对象 避免bug
          })
        })
      }
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
