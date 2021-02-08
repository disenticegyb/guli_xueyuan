package com.gyb.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyb.eduservice.entity.EduCourse;
import com.gyb.eduservice.entity.EduCourseDescription;
import com.gyb.eduservice.entity.vo.CourseInfoVo;
import com.gyb.eduservice.entity.vo.CoursePublishVo;
import com.gyb.eduservice.mapper.EduCourseMapper;
import com.gyb.eduservice.service.EduCourseDescriptionService;
import com.gyb.eduservice.service.EduCourseService;
import com.gyb.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述注入
    @Autowired
    private EduCourseDescriptionService courseDescriptionService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1向课程表添加课程基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);

        int insert = baseMapper.insert(eduCourse);

        if(insert == 0){
            throw new GuliException(2001,"添加课程信息失败");
        }

        //获取课程id
        String cid = eduCourse.getId();

        //向课程简介表增加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        courseDescriptionService.save(eduCourseDescription);
        return cid;

    }

    //根据课程id查询课程基本信息
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //查询描述表
        EduCourseDescription byId = courseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(byId.getDescription());
        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if(i == 0){
            throw  new GuliException(20001,"修改课程信息失败");
        }

        //修改描述表
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(courseInfoVo.getId());
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        courseDescriptionService.updateById(eduCourseDescription);
    }

    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;
    }
}
