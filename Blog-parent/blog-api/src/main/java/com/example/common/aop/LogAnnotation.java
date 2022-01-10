package com.example.common.aop;

import java.lang.annotation.*;
//Type代表可以放在Method上
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operater() default "";
}
