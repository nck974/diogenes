package dev.nichoko.diogenes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.aspectj.lang.annotation.Before;

@Aspect
@Component
public class ControllerLogger {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogger.class);

    @Pointcut("execution(public * dev.nichoko.diogenes.controller.*.*(..))")
    public void controllerDeclaration() {
    }

    /**
     * Log each request to any controller showing which method is called and with which arguments
     * 
     * @param joinPoint
     */
    @Before("controllerDeclaration()")
    public void logControllerRequests(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("Received a request to {}", joinPoint.getSignature().toShortString());

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                logger.info("Argument ({}): {}", arg.getClass().getSimpleName(), arg);
            }
        }
    }
}
