package dev.SpringBootAPI.ECommerce.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Intercepta todos os métodos dentro de ECommerce
    @Around("execution(* dev.SpringBootAPI.ECommerce.controllers.*.*(..)) || " +
            "execution(* dev.SpringBootAPI.ECommerce.services.*.*(..)) || " +
            "execution(* dev.SpringBootAPI.ECommerce.repositories.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        // Obtém informações sobre o metodo sendo executado
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();

        logger.info("Executing {}.{} with arguments {}", className, methodName, args);

        Object result;
        try {
            result = joinPoint.proceed(); // Executa o metodo
        } catch (Exception e) {
            // Loga a exceção com detalhes do metodo e da mensagem
            logger.error("Exception in {}.{}: {}, Args: {}", className, methodName, e.getMessage(), args, e);
            throw e; // Repassa a exceção para ser tratada em outro lugar
        }

        long executionTime = System.currentTimeMillis() - start;
        logger.info("Executed {}.{} in {} ms", className, methodName, executionTime);

        return result;
    }
}
