package com.jty.eduservice.controller;


import com.jty.commonutils.R;
import com.jty.eduservice.entity.EduSubject;
import com.jty.eduservice.entity.EduTeacher;
import com.jty.eduservice.entity.subject.OneSubject;
import com.jty.eduservice.service.EduSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * 课程科目 前端控制器
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    @Autowired
    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来文件，把文件内容读取出来
//    @PostMapping("addSubject")
//    public R addSubject(MultipartFile file) {
//        //上传过来excel文件
//        subjectService.saveSubject(file,subjectService);
//        return R.ok();
//    }

    //添加课程分类
    @PostMapping(value = "addSubject",consumes = "application/json;charset=UTF-8")
    public R addSubject(@RequestBody EduSubject eduSubject) {
        boolean save = subjectService.save(eduSubject);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}

