package com.example.service.impl;


import com.alibaba.fastjson.JSON;
import com.example.dao.pojo.SysUser;
import com.example.service.LoginService;
import com.example.service.SysUserService;
import com.example.utils.JWTUtils;
import com.example.vo.ErrorCode;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    private static final String salt ="mszlu!@#";

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public Result login(LoginParam loginParam) {

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        password= DigestUtils.md5Hex(password+salt);//加盐
        SysUser sysUser = sysUserService.findUser(account,password);
        if(sysUser ==null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.name());
        }


        String token = JWTUtils.createToken(sysUser.getId());

        redisTemplate.opsForValue().set("Token_"+token, JSON.toJSONString(sysUser),1, TimeUnit.DAYS);
        return Result.success(token);

    }

    @Override
    public SysUser checkToken(String token) {
        if(StringUtils.isBlank(token)){

            return null;
        }
        JWTUtils.vertify(token);
        return null;
    }
}
