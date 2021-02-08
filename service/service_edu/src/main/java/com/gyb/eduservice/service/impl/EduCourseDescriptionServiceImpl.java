package com.gyb.eduservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyb.eduservice.entity.EduCourseDescription;
import com.gyb.eduservice.mapper.EduCourseDescriptionMapper;
import com.gyb.eduservice.service.EduCourseDescriptionService;
import org.springframework.stereotype.Service;

@Service
public class EduCourseDescriptionServiceImpl extends ServiceImpl<EduCourseDescriptionMapper, EduCourseDescription> implements EduCourseDescriptionService {
}
