package com.gyb.eduservice.controller;

import com.gyb.commonutils.R;
import com.gyb.eduservice.entity.EduChapter;
import com.gyb.eduservice.entity.vo.ChapterVo;
import com.gyb.eduservice.service.EduChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 章节 前端控制器
 */
@RestController
@RequestMapping("/eduservice/chapter")
@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService chapterService;

    //课程大纲列表，根据课程id进行查询
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> chapterVideoByCourseId = chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",chapterVideoByCourseId);
    }

    //添加章节
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        chapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId){
        EduChapter byId = chapterService.getById(chapterId);
        return R.ok().data("chapter",byId);
    }

    //修改章节
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        chapterService.updateById(eduChapter);
        return R.ok();
    }

    //删除的方法
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = chapterService.deleteChapter(chapterId);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }

    }

}
