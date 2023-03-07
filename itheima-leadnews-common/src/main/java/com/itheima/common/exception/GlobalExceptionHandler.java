package com.itheima.common.exception;

import com.itheima.common.pojo.Result;
import com.itheima.common.pojo.StatusCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Arthurocky
 */
//标识为全局异常处理器
@RestControllerAdvice
public class GlobalExceptionHandler {


    //写一个方法  方法 用于当controller发送异常的时候被调用 要捕获异常 代替 controller 去返回给前端

    /**
     * 系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public Result handlerException(Exception e)
    {
        e.printStackTrace();
        System.out.println("网络出现故障");
        return Result.errorMessage("网络有异常"+e.getMessage());
    }

    //LeadNewsException业务上的异常 才进行处理
    /**
     * 业务异常
     */
    @ExceptionHandler(value = LeadNewsException.class)
    public Result handlerLeadNewsException(LeadNewsException e)
    {
        e.printStackTrace();
        return Result.errorMessage(e.getMessage(), StatusCode.CUSTOM_FAILURE.code(), null);
    }

}