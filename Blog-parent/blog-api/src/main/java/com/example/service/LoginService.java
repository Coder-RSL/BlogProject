package com.example.service;

import com.example.dao.pojo.SysUser;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result register(LoginParam loginParam);

    Result logout(String token);

}
