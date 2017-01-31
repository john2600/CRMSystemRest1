package com.virtualpairprogrammers.avalon.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PerformanceTimingAdvice {
	// I need to look inside of the folder by all the classes in each 
	//service package then poincut should be changed with another expresion.
	@Pointcut("execution (* com.virtualpairprogrammers.services..*.*(..) ) || execution (* com.virtualpairprogrammers.dataaccess..*.*(..) ) ")
	public void allServiceMethods() {
	}

	@Around("allServiceMethods()")
	public Object performTimingMeasurement(ProceedingJoinPoint method) throws Throwable {
		long startTime = System.nanoTime();
		Object returnMethod;
		try {
			returnMethod = method.proceed();
			return returnMethod;
		} finally {
			long endTime = System.nanoTime();

			long timeTaken = endTime - startTime;

			System.out.println("The method " + method.getSignature().getName() + " took " + timeTaken + " nanoseconds");
		}
		

	}

	@Before("allServiceMethods()")
	public void beforeAdviceTesting(JoinPoint jp) {
		System.out.println("Now entering a method...." + jp.getSignature().getName());
	}

}
