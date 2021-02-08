package com.gyb.eduservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyb.eduservice.entity.EduCourse;
import com.gyb.eduservice.entity.vo.CoursePublishVo;

public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getPublishCourseInfo(String id);
}
