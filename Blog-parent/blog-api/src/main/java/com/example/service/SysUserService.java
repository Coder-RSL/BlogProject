package com.example.service;

import com.example.dao.pojo.SysUser;
import com.example.vo.Result;
import com.example.vo.UserVo;

public interface SysUserService {
    SysUser findUserById(Long Id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    UserVo findUserVoById(Long id);
}
