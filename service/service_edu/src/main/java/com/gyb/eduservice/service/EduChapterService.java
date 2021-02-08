package com.gyb.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyb.eduservice.entity.EduChapter;
import com.gyb.eduservice.entity.vo.ChapterVo;

import java.util.List;

public interface EduChapterService extends IService<EduChapter> {
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean deleteChapter(String chapterId);
}
