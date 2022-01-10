package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.dao.pojo.SysUser;
import com.example.service.LoginService;
import com.example.service.SysUserService;
import com.example.utils.JWTUtils;
import com.example.utils.UserThreadLocal;
import com.example.vo.ErrorCode;
import com.example.vo.Result;
import com.example.vo.params.LoginParam;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserService sysUserService;

    private static final String salt = "*S&nsd"; //加密盐

    @Override
    public Result login(LoginParam loginParam) {
        System.out.println("ThisLo Threa:"+Thread.currentThread());

        /*
        * 1. 检查参数是否合法
        * 2. 根据用户的用户名和密码到user表中查询，是否存在
        * 3. 如果不存在 登陆失败
        * 4. 如果存在，使用jwt 生成token 返回前端
        *
        * */
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        System.out.println(account+password);
        if(StringUtils.isBlank(account) || StringUtils.isBlank(password)){

            return  Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        password = DigestUtils.md5Hex(password +salt);
       SysUser sysUser= sysUserService.findUser(account,password);
        if(sysUser == null){
            return  Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }
        String token = JWTUtils.createToken(sysUser.getId());

        UserThreadLocal.put(sysUser);

        return Result.success(token);

    }

    @Override
    public SysUser checkToken(String token) {
        System.out.println("checkToken");

        if(StringUtils.isBlank(token)){
            return null;
        }
        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token); //检验token

        if(stringObjectMap==null){
            return null;
        }

        Object userId = stringObjectMap.get("userId");//获得userID

        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        System.out.println("userId:"+userId);
        Long aLong = Long.valueOf(userId.toString());
        SysUser sysUser = sysUserService.findUserById(aLong);




        return sysUser;
    }

    @Override
    public Result register(LoginParam loginParam) {
        /*
          1. 判断参数 是否合法
          2.判断账户是否存在，存在，账户已经被注册
          3. 如果账户不存在，注册用户
          4.生成token
          5.注意加上事物，一旦中间任何过程出现问题，注册的用户 需要滚回
        */

        String account =loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)||StringUtils.isBlank(nickname)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(),ErrorCode.PARAMS_ERROR.getMsg());
        }
        SysUser sysUser= sysUserService.findUserByAccount(account);
        if(sysUser!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(),ErrorCode.ACCOUNT_EXIST.getMsg());
        }
        sysUser =new SysUser();
        sysUser.setNickname(nickname);
        sysUser.setAccount(account);
        sysUser.setPassword(DigestUtils.md5Hex(password+salt));
        sysUser.setLastLogin(System.currentTimeMillis());
        sysUser.setCreateDate(System.currentTimeMillis());
        sysUser.setAvatar("C:\\Users\\86130\\Desktop\\QQ图片20211028175653.jpg");
        sysUser.setSalt("");
        sysUser.setSalt("");
        sysUser.setEmail("");
        sysUser.setDeleted(0);
        this.sysUserService.save(sysUser);

        String token=JWTUtils.createToken(sysUser.getId());
        return Result.success(token);
    }
    @Override
    public Result logout(String token) {
        return Result.success(null);
    }
}
