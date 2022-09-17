package com.anything.aop;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class TransactionAspect {

	private final PlatformTransactionManager transactionManager;

	private static final int TX_METHOD_TIMEOUT = 3000;

	@Bean
	public TransactionInterceptor txAdvice() {

		DefaultTransactionAttribute readOnlyAttribute =
				new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
		readOnlyAttribute.setReadOnly(true);
		readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);

		List<RollbackRuleAttribute> rollbackRules = new ArrayList<RollbackRuleAttribute>();
		rollbackRules.add(new RollbackRuleAttribute(Exception.class));

		RuleBasedTransactionAttribute writeAttribute =
				new RuleBasedTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules);
		writeAttribute.setTimeout(TX_METHOD_TIMEOUT);

		Properties txAttributes = new Properties();
		txAttributes.setProperty("select*", readOnlyAttribute.toString());

		/*
		 * 기본 메서드 형식
		 */
		txAttributes.setProperty("save*", writeAttribute.toString());
		txAttributes.setProperty("update*", writeAttribute.toString());
		txAttributes.setProperty("delete*", writeAttribute.toString());

		/*
		 * 기타...
		 */
		txAttributes.setProperty("login", writeAttribute.toString());
		txAttributes.setProperty("refresh", writeAttribute.toString());

		TransactionInterceptor txAdvice = new TransactionInterceptor();
		txAdvice.setTransactionAttributes(txAttributes);
		txAdvice.setTransactionManager(transactionManager);

		return txAdvice;
	}

	@Bean
	public Advisor txAdviceAdvisor() {

		AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
		pointcut.setExpression("(execution(* *..*.service..*.*(..)))");

		return new DefaultPointcutAdvisor(pointcut, txAdvice());
	}
}
