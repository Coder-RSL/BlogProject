package com.example.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    //    taken header sighture
    private  static final  String SING ="SKDK!DLS#3233";



//    public static String getToken (Map<String, String> map){
//
//
//        Calendar instance = Calendar.getInstance();
//        instance.add(Calendar.DATE,7);
//
//        JWTCreator.Builder builder = JWT.create();
//
//        map.forEach((k,v)->{
//            builder.withClaim(k, v);
//        });
//
//        String Token =builder.withExpiresAt(instance.getTime())
//                .sign(Algorithm.HMAC256(SING));
//
//        return Token;
//    }

    public static String createToken(Long userId){
        Map<String ,Object> claims = new HashMap<>();

        String token = JWT.create()
                .withHeader(claims)
                .withClaim("userId",userId)
                .withExpiresAt(new Date(System.currentTimeMillis()+600*1000*6)) //一天
                .sign(Algorithm.HMAC256(SING));
                ;

                return token;
    }

    public static void vertify(String token){
        JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }

    public static DecodedJWT getTokenInfo(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
        return verify;

    }

    public static Map<String,Object> checkToken(String token){



        return null;
    }
    public static void main(String[] args) {
        String token = JWTUtils.createToken(100L);
        System.out.println(token);
        DecodedJWT tokenInfo = JWTUtils.getTokenInfo(token);
        System.out.println(tokenInfo.getClaim("userId").asLong());
    }
}
