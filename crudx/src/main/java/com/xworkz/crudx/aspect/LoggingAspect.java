package com.xworkz.crudx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    public LoggingAspect()
    {
        log.info("Logging aspect constructor");
    }

    @Pointcut("execution(* com.xworkz.crudx.restcontroller.*.*(..))")
    public void allRestControllerMethods(){}

    @Pointcut("execution(* com.xworkz.crudx.service.*.*(..))")
    public void allServiceMethods(){}

    @Before("allRestControllerMethods() || allServiceMethods()")
    public void logBefore(JoinPoint joinPoint)
    {
         log.info("Before execution: "+joinPoint.getSignature().getName()+" method in "+joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("allRestControllerMethods() || allServiceMethods()")
    public void logAfter(JoinPoint joinPoint)
    {
        log.info("After execution: "+joinPoint.getSignature().getName()+" method in "+joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterThrowing(value = "allServiceMethods()",throwing = "exception")
    public void logAfterThrow(JoinPoint joinPoint,Exception exception)
    {
        log.error("Exception in {}.{}: {}",joinPoint.getTarget().getClass().getSimpleName(),joinPoint.getSignature().getName(),exception.getMessage());
    }
}
