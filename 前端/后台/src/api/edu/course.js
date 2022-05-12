import request from '@/utils/request'
export default {
  addCourse(course) {
    return request({
      url: '/eduservice/course/addCourse',
      method: 'post',
      data: course
    })
  },
  //1 添加课程信息
  addCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/course/addCourseInfo',
      method: 'post',
      data: courseInfo
    })
  },
  //2 查询所有讲师
  getListTeacher() {
    return request({
      url: '/eduservice/teacher/findAll',
      method: 'get'
    })
  },
  //根据课程id查询课程基本信息
  getCourseInfoId(id) {
    return request({
      url: '/eduservice/course/getCourseInfo/' + id,
      method: 'get'
    })
  },
  updateCourse(courseInfo) {
    return request({
      url: '/eduservice/course/updateCourse',
      method: 'post',
      data: courseInfo
    })
  },
  //修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: '/eduservice/course/updateCourseInfo',
      method: 'post',
      data: courseInfo
    })
  },
  //课程确认信息显示
  getPublihCourseInfo(id) {
    return request({
      url: '/eduservice/course/getPublishCourseInfo/' + id,
      method: 'get'
    })
  },
  //课程最终发布
  publishCourse(id, status) {
    return request({
      url: '/eduservice/course/publishCourse/' + id + '/' + status,
      method: 'post'
    })
  },
  //TODO 课程列表
  //课程最终发布
  getListCourse() {
    return request({
      url: '/eduservice/course',
      method: 'get'
    })
  },
  deleteCourse(id) {
    return request({
      url: `/eduservice/course/${id}`,
      method: 'delete'
    })
  }

}
