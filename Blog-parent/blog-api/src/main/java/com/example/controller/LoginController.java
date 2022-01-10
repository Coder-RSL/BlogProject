package com.example.controller;

import com.example.service.LoginService;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;//业务功能

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam ){
        //登录 验证用户 访问用户表 ，诞生

        return loginService.login(loginParam);
    }
}
