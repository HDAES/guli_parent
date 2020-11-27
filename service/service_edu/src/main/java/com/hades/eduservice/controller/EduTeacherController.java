package com.hades.eduservice.controller;


import com.hades.eduservice.entity.EduTeacher;
import com.hades.eduservice.service.EduTeacherService;
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
    public List<EduTeacher> getTeaches() {
        //调用service的方法实现查询
        List<EduTeacher> list =  teacherService.list(null);
        return list;
    }

    //2.讲师得到逻辑删除
    @ApiOperation(value = "根据ID删除讲师")
    @DeleteMapping("{id}")
    public boolean removeTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id) {
        boolean flag =  teacherService.removeById(id);
        return  flag;
    }

}

