package com.atguigu.servicebase.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : Runqiang Jiang
 * @create 2022/7/24 18:12
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    // 指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody //为了返回数据
    public R error(Exception exception){
        exception.printStackTrace();
        return R.error().message("执行了全局异常处理");

    }

    //特定异常
    // 指定出现什么异常执行这个方法
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody //为了返回数据
    public R error(ArithmeticException exception){
        exception.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");

    }

    //自定义异常
    // 指定出现什么异常执行这个方法
    @ExceptionHandler(GuliException.class)
    @ResponseBody //为了返回数据
    public R error(GuliException exception){
        log.error(exception.getMsg());
        exception.printStackTrace();
        return R.error().code(exception.getCode()).message(exception.getMsg());

    }
}
