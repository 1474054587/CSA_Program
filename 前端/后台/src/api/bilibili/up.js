import request from '@/utils/request'
export default {
  //根据id查询UP
  getTeacherInfoByID(mid) {
    return request({
      url: `/eduservice/bili/getUserInfoByID/${mid}`,
      method: 'get'
    })
  },
}
