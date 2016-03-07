package com.b5m.sms.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class LoggerAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAspect.class);
	static String name = "";
	static String type = "";

	@Around("execution(* com.b5m..*Controller.*(..)) || execution(* com.b5m..*Impl.*(..)) || execution(* com.b5m..*DAO.*(..))")
	public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable {
		type = joinPoint.getSignature().getDeclaringTypeName();
		
		if (type.indexOf("Controller") > -1) {
			name = "Controller \t: ";
		} else if (type.indexOf("Service") > -1) {
			name = "ServiceImpl \t: ";
		} else if (type.indexOf("DAO") > -1) {
			name = "DAO \t\t: ";
		}
		LOGGER.info(name + type + "." + joinPoint.getSignature().getName() + "()");
		return joinPoint.proceed();
	}
}
