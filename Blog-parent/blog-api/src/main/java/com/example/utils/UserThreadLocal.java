package com.example.utils;

import com.example.dao.pojo.SysUser;

import java.util.ArrayList;
import java.util.List;
public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final List<SysUser> LOCAL = new ArrayList<>();



    public static void put(SysUser sysUser){
        System.out.println("setSysUser"+sysUser);
        System.out.println("setSysUser"+sysUser);
        System.out.println("setSysUser"+sysUser);
        System.out.println("setSysUser"+sysUser);
        LOCAL.add(sysUser);
    }
    public static SysUser get(){

        if(LOCAL.get(0)==null){

        }

        SysUser sysUser = LOCAL.get(0);
        System.out.println("getSysUser"+sysUser);
        System.out.println("getSysUser"+sysUser);
        System.out.println("getSysUser"+sysUser);
        return LOCAL.get(0);
    }
    public static void remove(){
        System.out.println("removeSysUser");
        System.out.println("remove");
        System.out.println("remove");
        System.out.println("remove");
        System.out.println("remove");
        LOCAL.remove(0);
    }
}
