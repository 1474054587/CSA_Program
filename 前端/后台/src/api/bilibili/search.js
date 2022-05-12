import request from '@/utils/request'
export default {
  searchAll(keyword) {
    return request({
      url: `/eduservice/bili/searchAll/${keyword}`,
      method: 'get'
    })
  },
  searchByType(type, keyword) {
    return request({
      url: `/eduservice/bili/searchByType/${type}/${keyword}`,
      method: 'get'
    })
  },
}
