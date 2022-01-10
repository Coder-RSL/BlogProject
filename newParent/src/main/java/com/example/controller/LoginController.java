package com.example.controller;


import com.example.service.LoginService;
import com.example.vo.LoginUserVo;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {
//
//    @Autowired
//    private SysUserService sysUserService; 不好

    @Autowired
    private LoginService loginService;

    @PostMapping
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }


}
