import request from '@/utils/request'
export default {
  //1 课程分类列表
  getSubjectList() {
    return request({
      url: '/eduservice/subject/getAllSubject',
      method: 'get'
    })
  },
  //添加课程
  addSubject(subject) {
    return request({
      url: `/eduservice/subject/addSubject`,
      method: 'post',
      data: subject
    })
  }
}
