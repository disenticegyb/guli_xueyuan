package com.gyb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyb.eduservice.entity.EduCourse;
import com.gyb.eduservice.entity.vo.CourseInfoVo;
import com.gyb.eduservice.entity.vo.CoursePublishVo;

public interface EduCourseService extends IService<EduCourse> {

    //add课程
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);
}
