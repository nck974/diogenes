package dev.nichoko.diogenes.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.aspectj.lang.annotation.Before;

@Aspect
@Component
public class FileSystemLogger {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemLogger.class);

    @Pointcut("execution(public * dev.nichoko.diogenes.service.FileStorageServiceImpl.*(..))")
    public void fileSystemImplDeclaration() {
    }

    /**
     * Log each change in the files
     * 
     * @param joinPoint
     */
    @Before("fileSystemImplDeclaration()")
    public void logControllerRequests(JoinPoint joinPoint) {
        if (logger.isInfoEnabled()) {
            logger.info("File change {}", joinPoint.getSignature().toShortString());

            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {

                if (arg == null) {
                    logger.info("Argument: null");
                    continue;
                }

                logger.info("Argument ({}): {}", arg.getClass().getSimpleName(), arg);

                if (arg instanceof MultipartFile) {
                    MultipartFile multipartFile = (MultipartFile) arg;
                    String filename = multipartFile.getOriginalFilename();
                    logger.info("MultipartFile filename: {}", filename);
                }
            }

        }
    }
}
