package com.jty.eduservice.controller;


import com.jty.commonutils.R;
import com.jty.eduservice.entity.EduCourse;
import com.jty.eduservice.entity.vo.CourseInfoVo;
import com.jty.eduservice.entity.vo.CoursePublishVo;
import com.jty.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 前端控制器
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {

    @Autowired
    private EduCourseService courseService;

    //课程列表 基本实现
    //TODO  完善条件查询带分页
    @GetMapping
    public R getCourseList() {
        List<EduCourse> list = courseService.list(null);
        return R.ok().data("list",list);
    }

    @PostMapping("addCourse")
    public R addCourse(@RequestBody EduCourse eduCourse) {
        boolean save = courseService.save(eduCourse);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //添加课程基本信息的方法
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        //返回添加之后课程id，为了后面添加大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程基本信息
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    @GetMapping("getCourse/{courseId}")
    public R getCourse(@PathVariable String courseId) {
        EduCourse eduCourse = courseService.getById(courseId);
        System.out.println(eduCourse.toString());
        return R.ok().data("courseInfo",eduCourse);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody EduCourse eduCourse) {
        boolean flag = courseService.updateById(eduCourse);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",coursePublishVo);
    }

    //课程最终发布
    //修改课程状态
    @PostMapping("publishCourse/{id}/{status}")
    public R publishCourse(@PathVariable String id,@PathVariable String status) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus(status);//设置课程发布状态
        courseService.updateById(eduCourse);
        return R.ok();
    }

    //删除课程
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }

}

