package com.example.controller;

import com.example.service.LoginService;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("register")
public class RegisterController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result register(@RequestBody LoginParam loginParam){
        //sso单点登录，后期如果吧登陆注册功能拎出去（单独服务，可以独立提供接口服务）
        return loginService.register(loginParam);
    }
}
