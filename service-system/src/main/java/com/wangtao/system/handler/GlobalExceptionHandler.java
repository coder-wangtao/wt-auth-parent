package com.wangtao.system.handler;

import com.wangtao.common.result.Result;
import com.wangtao.common.result.ResultCodeEnum;
import com.wangtao.system.exception.WtException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public Result exceptionHandler(Exception e){
        System.out.println(e.getMessage());
        return Result.fail().message("服务器在维护！");
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定的异常");
    }

    @ExceptionHandler(WtException.class)
    @ResponseBody
    public Result error(WtException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定的异常");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException{
        return Result.build(null, ResultCodeEnum.PERMISSION);
    }
}
