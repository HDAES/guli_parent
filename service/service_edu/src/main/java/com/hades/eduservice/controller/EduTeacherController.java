package com.hades.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hades.commonutils.R;
import com.hades.eduservice.entity.EduTeacher;
import com.hades.eduservice.entity.vo.TeacherQuery;
import com.hades.eduservice.service.EduTeacherService;
import com.hades.serverbase.exceptionHandler.MyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-11-26
 */
@Api(tags="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {


    //service注入
    @Autowired
    private EduTeacherService teacherService;

    //1.查询讲师表所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("find")
    public R getTeaches() {
        //调用service的方法实现查询
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("item", list);
    }

    //2.讲师得到逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }


    //3.分页查询方法
    @ApiOperation(value = "分页查询")
    @GetMapping("pageTeacher/{current}/{size}")
    public R pageListTeacher(
            @ApiParam(name = "current", value = "页数", required = true)
            @PathVariable Long current,
            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable Long size
    ) {
        //创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        teacherService.page(pageTeacher, null);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //4.条件查询带分页
    @PostMapping("pageTeacherCondition/{current}/{size}")
    public R pageTeacherCondition(
            @ApiParam(name = "current", value = "页数", required = true)
            @PathVariable Long current,
            @ApiParam(name = "size", value = "每页条数", required = true)
            @PathVariable Long size,
            @RequestBody(required = false) TeacherQuery teacherQuery
    ) {
        //创建配置对象
        Page<EduTeacher> pageTeacher = new Page<>(current, size);
        //构建条件
        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (level != null) {
            queryWrapper.eq("level", level);
        }
        if (StringUtils.isNotEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }
        if (StringUtils.isNotEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }
        teacherService.page(pageTeacher, queryWrapper);
        long total = pageTeacher.getTotal();
        List<EduTeacher> records = pageTeacher.getRecords();
        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.save(eduTeacher);
        return flag ? R.ok() : R.error();
    }

    //根据教师ID查询
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@ApiParam(name = "id", value = "教师id", required = true) @PathVariable String id) {
        EduTeacher teacher = teacherService.getById(id);
        return R.ok().data("teacher", teacher);
    }

    //根据ID修改
    @PostMapping("updateTeacher")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher) {
        boolean flag = teacherService.updateById(eduTeacher);
        return flag? R.ok(): R.error();

    }
}

