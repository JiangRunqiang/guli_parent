package com.atguigu.serviceedu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.serviceedu.entity.EduTeacher;
import com.atguigu.serviceedu.entity.vo.TeacherQuery;
import com.atguigu.serviceedu.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author atguigu
 * @since 2022-07-24
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
public class EduTeacherController {

    // 把Service注入
    @Autowired
    private EduTeacherService teacherService;

    // 查询讲师表中的所有数据
    //rest 风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllTeacher() {
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);

    }

    //逻辑删除讲师的方法
    @ApiOperation(value = "逻辑删除讲师")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师Id", required = true) @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    //3 分页查询讲师的方法
    //current；代表当前页 limit:每页记录数
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current, @PathVariable long limit) {
        //创建page
        Page<EduTeacher> pageTeacher = new Page<>(current, limit);
        //调用方法实现分页
        //调用方法的时候，底层封装，把分页所有数据凤凰组昂道pageTeacher对象里面
        teacherService.page(pageTeacher, null);

        try {
            int i=1/0;
        } catch (Exception e) {
            //执行自定含义异常
            throw  new GuliException(20001,"执行了自定义异常");
        }

        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合
//       Map map =new HashMap<>();
//       map.put("total",total);
//       map.put("rows",records);
//       return R.ok().data(map);

        return R.ok().data("total", total).data("rows", records);
    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "条件查询带分页")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R  pageTeacherCondition(@PathVariable long current, @PathVariable long limit, @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建Page对象
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //构造条件
        QueryWrapper<EduTeacher> queryWrapper=new QueryWrapper<>();
        // 多条件组合查询
        // mybatis 学过动态SQL
        // 判断条件值是否为空，如果不为空，则拼接上条件
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if(!StringUtils.isEmpty(name)){
            //构造条件
            queryWrapper.like("name",name);
        }
        if(!StringUtils.isEmpty(level)){
            queryWrapper.eq("level",level);
        }
        if(!StringUtils.isEmpty(begin)){
            queryWrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(end)){
            queryWrapper.le("gmt_create",end);
        }

        //调用方法实现条件查询带分页
        teacherService.page(pageTeacher,queryWrapper);
        long total = pageTeacher.getTotal();//总记录数
        List<EduTeacher> records = pageTeacher.getRecords();//数据list集合

        return R.ok().data("total", total).data("rows", records);
    }

    //添加讲师的方法
    @PostMapping("addTeacher")
    @ApiOperation(value = "添加讲师")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
        boolean save = teacherService.save(eduTeacher);
        return save?R.ok():R.error();
    }

    // 根据讲师ID进行查询
    @GetMapping("getTeacher/{id}")
    @ApiOperation(value = "根据讲师ID进行查询")
    public R getTeacherById(@PathVariable String id){
        EduTeacher eduTeacher= teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    // 讲师修改功能
    @ApiOperation(value = "讲师修改")
    @PostMapping("updateTeacher")
    public  R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = teacherService.updateById(eduTeacher);
        return flag?R.ok():R.error();
    }

}

