package com.gyb.eduservice.controller;

import com.gyb.commonutils.R;
import com.gyb.eduservice.entity.OneSubject;
import com.gyb.eduservice.service.EduSubjectService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {

    private EduSubjectService subjectService;

    //添加课程分类
    //获取上传过来的文件，把文件内筒读取出来
    public R addSubject(MultipartFile file){
        //上传过来excel文件
        subjectService.saveSubject(file,subjectService);
        return R.ok();
    }
    //课程分类列表（树形）
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        //list集合泛型是一级分类
        List<OneSubject> list = subjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }

}
