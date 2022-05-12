import request from '@/utils/request'

export default {
  //条件分页课程查询的方法
  getCourseList(page, limit, searchObj) {
    return request({
      url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
      method: 'post',
      data: searchObj
    })
  },
  //查询所有分类的方法
  getAllSubject() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },
  getCourseInfo(id) {
    return request({
      url: '/eduservice/course/getCourse/' + id,
      method: 'get'
    })
  },
  getVideoInfo(bvid) {
    return request({
      url: `/eduservice/bili/getVideoInfo/${bvid}`,
      method: 'get'
    })
  },
  //课程详情的方法
  getCourseInfo_old(id) {
    return request({
      url: '/eduservice/coursefront/getFrontCourseInfo/' + id,
      method: 'get'
    })
  }
}
