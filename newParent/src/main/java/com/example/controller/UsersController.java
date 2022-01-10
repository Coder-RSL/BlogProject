package com.example.controller;


import com.example.dao.pojo.SysUser;
import com.example.service.SysUserService;
import com.example.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UsersController {
    @Autowired
    private SysUserService sysUserService;
    @PostMapping("currentUser")
    public Result currentUser(@RequestHeader ("Authorization") String token){
        return sysUserService.findUserByToken(token);

    }
}
