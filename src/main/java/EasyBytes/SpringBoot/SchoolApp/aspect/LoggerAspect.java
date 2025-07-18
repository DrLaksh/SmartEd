package EasyBytes.SpringBoot.SchoolApp.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component

//@Slf4j is an Lombok annotation which will allow me to log the logger statements without creating the Log object manually.

public class LoggerAspect {

    @Around("execution(* EasyBytes.SpringBoot.SchoolApp..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info(joinPoint.getSignature().toString() + "method execution start");
        Instant start = Instant.now();
        Object returnObj = joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start,finish).toMillis();
        log.info("Time took to execute " +joinPoint.getSignature().toString() + "method is : " + timeElapsed);
        log.info(joinPoint.getSignature().toString() + "method execution end");
        return returnObj;
    }

    @AfterThrowing(value = "execution(* EasyBytes.SpringBoot.SchoolApp..*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint , Exception ex){
        log.error(joinPoint.getSignature() + "An exception happened due to : " +ex.getMessage() );
    }

}
