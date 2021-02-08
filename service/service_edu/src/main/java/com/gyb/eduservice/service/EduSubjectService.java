package com.gyb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyb.eduservice.entity.EduSubject;
import com.gyb.eduservice.entity.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EduSubjectService extends IService<EduSubject> {

    void saveSubject(MultipartFile file, EduSubjectService subjectService);

    List<OneSubject> getAllOneTwoSubject();
}
