package com.gyb.eduservice.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gyb.commonutils.R;
import com.gyb.eduservice.entity.EduTeacher;
import com.gyb.eduservice.entity.vo.TeacherQuery;
import com.gyb.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@ApiModel("讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    //访问地址： http://localhost:8001/eduservice/teacher/findAll
    //把service注入
    @Autowired
    private EduTeacherService teacherService;

    /**
     * 查询讲师所有数据
     * @return
     */
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher(){
         List<EduTeacher> list = teacherService.list(null);
         return R.ok().data("items",list);
    }

    /**
     * 2 逻辑删除讲师
     * @param id
     * @return
     */
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id" , value = "讲师ID" ,required = true)
                           @PathVariable String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        } else {
            return R.error();
        }
    }

    /**
     * 调用方法实现分页
     * 调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListteacher(@PathVariable long current,@PathVariable long limit){
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        int i = 10/0;

        //将查询数据封装至pageTeacher
        teacherService.page(pageTeacher,null);

        //总记录数
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total",total).data("rows",records);
    }

    public R pageTeacherCondition(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);

        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        //多条件组合查询
        //mybatis
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        //模糊查询
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }

        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }

        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }

        if(!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }

        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    //添加讲师接口的方法
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean save = teacherService.save(eduTeacher);
        if(save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //根据讲师id进行查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id) {
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改功能
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        if(flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
