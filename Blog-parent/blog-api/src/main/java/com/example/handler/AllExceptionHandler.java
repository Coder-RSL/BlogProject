package com.example.handler;

import com.example.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class AllExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doEception(Exception e){
        e.printStackTrace();
        return Result.fail(-999,"异常处理");
    }
}
