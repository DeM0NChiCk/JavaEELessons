package ru.itis.lessonservlet.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class MyAspect {

    @Pointcut("execution(public * ru.itis.lessonservlet.aspect.service.MyService.*(..))")
    public void callAtMyServicePublic() { }

    @Pointcut("@annotation(AspectAnnotation)")
    public void callAtMyServiceAnnotation() { }

    @Pointcut("execution(* ru.itis.lessonservlet.aspect.service.MyService.method1(..)) && args(list,..))")
    public void callAtMyServiceMethod1(List<String> list) { }

    @Pointcut("execution(* ru.itis.lessonservlet.aspect.service.MyService.check())")
    public void callAtMyServiceAfterReturning() { }

    @Before(value = "callAtMyServiceMethod1(list)", argNames = "jp,list")
    public void beforeCallAtMethod1(JoinPoint jp, List<String> list) {
        log.info("before {} list {}", jp.toString(), list);
    }

    @Before("callAtMyServicePublic()")
    public void beforeCallAtMethod1(JoinPoint jp) {
        String args = Arrays.stream(jp.getArgs())
                .map(Object::toString)
                .collect(Collectors.joining(","));
        log.info("before {}, args=[{}]", jp, args);
    }

    @Before("callAtMyServiceAnnotation()")
    public void beforeCallAt(JoinPoint jp) {
        log.info("before {}", jp.toString());
    }

    @After("callAtMyServicePublic()")
    public void afterCallAt(JoinPoint jp) {
        log.info("after {}", jp.toString());
    }

    @AfterReturning(pointcut="callAtMyServiceAfterReturning()", returning="retVal")
    public void afterReturningCallAt(JoinPoint jp, boolean retVal) {
        log.info("after {} return value {}", jp.toString(), retVal);
    }

}
