package com.example.handler;

import com.alibaba.fastjson.JSON;
import com.example.dao.pojo.SysUser;
import com.example.service.LoginService;
import com.example.utils.UserThreadLocal;
import com.example.vo.ErrorCode;
import com.example.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //在执行controller方法之前进行执行
        /*
        1.需要判断请求 的接口的路径 是否为HandlerMethod（controller方法）
        2.token是否为空，未登录
        3.如果token不为空，进行登录验证chceckToken
        4.如果登录成功放行即可
        * */
        System.out.println("进行登录拦截");
        if(!(handler instanceof HandlerMethod)){
        //handler可能是RequestResourceHandler springboot程序访问静态资源 默认去classpath下的static的目录去查询年
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("====================request start==============================");
        String requestURI =request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}", request.getMethod());
        log.info("token:{}" ,token);
        log.info("====================request end==============================");

        if(StringUtils.isBlank(token)) {
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));

            return false;
        }
        SysUser sysUser = loginService.checkToken(token);  //对token进行认证
        if(sysUser==null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), "未登录");
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));

            return false;
        }


        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //如果不删除 ThreadLocal中用完的信息，会有内存泄露的风险
        UserThreadLocal.remove();
    }
}
