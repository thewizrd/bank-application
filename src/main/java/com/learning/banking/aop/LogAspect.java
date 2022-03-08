package com.learning.banking.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * LogAspect
 *
 * @author bryan
 * @date Feb 17, 2022-10:28:45 AM
 */
@Aspect
@Component
public class LogAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	/*
	 * @Pointcut - execution
	 * https://stackoverflow.com/q/68654715
	 * https://stackoverflow.com/a/44249802
	 */

	/*
	 * 1) .* - Class name
	 * 2) .* - Method name
	 * 3) (..) - Arguments
	 */
	@Pointcut("execution(* com.learning.banking.controller.*.*(..)) ")
	public void logRestController() {}
	
	@Before("logRestController()")
	public void beforeLogRestController(JoinPoint joinPoint) {
		LOGGER.info("Method called: " + joinPoint.getSignature().toShortString());
	}
	
	@AfterThrowing(pointcut = "logRestController()", throwing = "ex")
	public void afterExceptionThrowController(Exception ex) {
		LOGGER.error("Exception: " + ex);
		ex.printStackTrace(); // DEBUG mode
	}
}
