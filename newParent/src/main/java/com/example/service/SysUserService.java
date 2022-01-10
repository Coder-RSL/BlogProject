package com.example.service;

import com.example.dao.pojo.SysUser;
import com.example.vo.Result;

public interface SysUserService {
    SysUser findUserById(Long Id);
    SysUser findUser(String account, String password);

    Result findUserByToken(String token);
}
