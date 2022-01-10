package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.dao.mapper.SysUserMapper;
import com.example.dao.pojo.SysUser;
import com.example.service.LoginService;
import com.example.service.SysUserService;
import com.example.utils.JWTUtils;
import com.example.vo.ErrorCode;
import com.example.vo.LoginUserVo;
import com.example.vo.Result;
import com.example.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
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
        LambdaQueryWrapper<SysUser> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /*
        * 1. token合法性校验
        * 2. 是否为空，解析是否成功，
        * 3.如果检验失败，则返回错误
        * 4.如果成功则返回结果， LoginUserVO
        *
        */
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);

        if (stringObjectMap == null){
            System.out.println("token错误");
            System.out.println("token错误");
            System.out.println("token错误");
            System.out.println("token错误");
            System.out.println("token错误");
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg()) ;
        };

        SysUser sysUser= loginService.checkToken(token);



        if(sysUser == null){
            System.out.println("token找不到人");
            System.out.println("token找不到人");
            System.out.println("token找不到人");
            System.out.println("token找不到人");
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(), ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo= new LoginUserVo();

        loginUserVo.setId(sysUser.getId().toString());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());

        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        SysUser sysUser = this.sysUserMapper.selectOne(queryWrapper);
        System.out.println(" ");
        return sysUser;
    }

    @Override
    public void save(SysUser sysUser) {
        //保存用户这id会自动生成
        //采用的是默认分布式 id 雪花算法
        //mybatisPlus
        this.sysUserMapper.insert(sysUser);
    }
    @Override
    public UserVo findUserVoById(Long id) {

        SysUser sysUser = sysUserMapper.selectById(id);
        if(sysUser == null){
            sysUser =new SysUser();
            sysUser.setId(1L);
//            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("佚名");
        }

        UserVo userVO =new UserVo();
        BeanUtils.copyProperties(sysUser,userVO);
        return  userVO;

    }

}
