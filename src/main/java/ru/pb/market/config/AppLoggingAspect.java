package ru.pb.market.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppLoggingAspect {
    public static Long serviceTime;
    public static String serviceName;

    public static String userName;

//    @Before("execution(public * ru.pb.market.controllers.ProductController.*(..))")
//    public void beforeProductControllerClass(JoinPoint joinPoint) {
//        System.out.println("поймали метод " + joinPoint.getSignature().getDeclaringType());
//    }
//

    @Around("execution(public * ru.pb.market.controllers.*.*(..))")
    public Object controllersProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return checkTime(proceedingJoinPoint);
    }

    @Around("execution(public * ru.pb.market.repositories.*.*(..))")
    public Object repoProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return checkTime(proceedingJoinPoint);
    }


    @Around("execution(public * ru.pb.market.services.*.*(..))")
    public Object serviceProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long begin = System.currentTimeMillis();

        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        System.out.println("Метод " + proceedingJoinPoint.getSignature() + " выполнился за " + duration + "ms");
        serviceName = proceedingJoinPoint.getSignature().toShortString();
        serviceTime = duration;
        userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return out;
    }

    private Object checkTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //  System.out.println("Запуск метода "+proceedingJoinPoint.getSignature());
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long duration = System.currentTimeMillis() - begin;
        System.out.println("Метод " + proceedingJoinPoint.getSignature() + " выполнился за " + duration + "ms");
        return out;
    }
}
