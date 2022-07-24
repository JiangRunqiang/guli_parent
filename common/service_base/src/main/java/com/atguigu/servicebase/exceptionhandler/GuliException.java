package com.atguigu.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : Runqiang Jiang
 * @create 2022/7/24 21:41
 */
@Data
@AllArgsConstructor  //生成有参数的构造方法
@NoArgsConstructor  //生成无参数的构造方法
public class GuliException extends RuntimeException{

    private Integer code;//状态码

    private String msg; //异常的信息






}
