package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dao.mapper.SysUserMapper;
import com.example.dao.pojo.SysUser;
import com.example.service.LoginService;
import com.example.service.SysUserService;
import com.example.vo.ErrorCode;
import com.example.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService{
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Autowired
    private LoginService loginService;


    @Override
    public SysUser findUserById(Long Id) {

        SysUser sysUser = sysUserMapper.selectById(Id);
        if(sysUser == null){
            sysUser =new SysUser();
            sysUser.setNickname("佚名");
        }

        return  sysUser;

    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser>  queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");//加速
        return sysUserMapper.selectOne(queryWrapper);

    }

    @Override
    public Result findUserByToken(String token) {

//       1、合法性校验
//        是否为空
//       2、失败，返回错误
//       3、成功返回LoginUserVo


        SysUser sysUser = loginService.checkToken(token);

        return null;
    }

}
