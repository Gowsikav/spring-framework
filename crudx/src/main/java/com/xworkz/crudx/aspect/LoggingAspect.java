package com.xworkz.crudx.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingAspect {

    public LoggingAspect()
    {
        log.info("Logging aspect constructor");
    }

    @Before("execution(* com.xworkz.crudx.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint)
    {
         log.info("Before execution: "+joinPoint.getSignature().getName()+" method in "+joinPoint.getTarget().getClass().getSimpleName());
    }

    @After("execution(* com.xworkz.crudx.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint)
    {
        log.info("After execution: "+joinPoint.getSignature().getName()+" method in "+joinPoint.getTarget().getClass().getSimpleName());
    }

}
