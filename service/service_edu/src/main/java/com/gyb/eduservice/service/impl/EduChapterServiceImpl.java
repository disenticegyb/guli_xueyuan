package com.gyb.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyb.eduservice.entity.EduChapter;
import com.gyb.eduservice.entity.EduVideo;
import com.gyb.eduservice.entity.vo.ChapterVo;
import com.gyb.eduservice.entity.vo.VideoVo;
import com.gyb.eduservice.mapper.EduChapterMapper;
import com.gyb.eduservice.service.EduChapterService;
import com.gyb.eduservice.service.EduVideoService;
import com.gyb.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService videoService;

    //课程大纲列表，根据课程id进行查询
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
       //查询所有章节
        QueryWrapper<EduChapter> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(queryWrapper);
        //查询课程所有的小节
        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id",courseId);
        List<EduVideo> videos = videoService.list(wrapperVideo);

        //创建list集合，用于最终封装数据
        List<ChapterVo> charterVos = new ArrayList<>();

        //遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for(int i = 0; i < eduChapters.size();i++){
            //得到每个章节
            EduChapter eduChapter = eduChapters.get(i);
            //eduChapter对象值复制到chapterVo
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //把chapterVo放到最终list集合
            charterVos.add(chapterVo);

            //封装章节小节
            List<VideoVo> videoList = new ArrayList<>();

            for(int m = 0 ; m < videos.size() ; m++){
                //得到每个小节
                EduVideo eduVideo = videos.get(m);
                //判断，小节
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合
                    videoList.add(videoVo);
                }

            }
            //把封装之后小节list集合，放到章节对象里面
            chapterVo.setChildren(videoList);
        }
        return charterVos;

    }

    //删除章节的方法
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据chapterid章节id 查询小节表，如果查询数据，不进行删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = videoService.count(wrapper);
        //判断
        if(count >0) {//查询出小节，不进行删除
            throw new GuliException(20001,"不能删除");
        } else { //不能查询数据，进行删除
            //删除章节
            int result = baseMapper.deleteById(chapterId);
            //成功  1>0   0>0
            return result>0;
        }
    }
}
