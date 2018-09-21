package com.zyc.baselibs.aop;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.zyc.baselibs.commons.CollectionUtils;
import com.zyc.baselibs.ex.BussinessException;

@Aspect
@Component
public class ParamVerificationAspect {

	private static final Logger logger = Logger.getLogger(ParamVerificationAspect.class); 
	
	private static final Class<?>[] exConstructorParamTypes = new Class<?>[] { String.class, Throwable.class };
	
	private static final String FORMAT_EX_FOR_CONSTRUCTOR = "[%s]异常类找不到支持当前参数类型%s的构造函数。";
	
    @Pointcut("@annotation(com.zyc.baselibs.aop.ParamVerification)")
	public void execPointCut() {
		logger.debug("Execute 'execPointCut()' ...");
		//do something ...
		logger.debug("Execute 'execPointCut()' end.");
	}
    
    @Before("execPointCut()")
    public void beforeExecute(JoinPoint joinPoint) throws Exception {
    	logger.debug("Execute 'beforeExecute(...)' ...");
    	
    	MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    	Method method = ms.getMethod();
    	ParamVerification pv = method.getAnnotation(ParamVerification.class);
    	
    	if(CollectionUtils.hasElement(pv.ruleExpression())) {
    		ParamVerificationRule[] rules = new ParamVerificationRule[pv.ruleExpression().length];
    		
    		//首先，确保所有的校验规则表达式格式正确，正常被解析
    		for (int i = 0; i < pv.ruleExpression().length; i++) {
    			rules[i] = ParamVerificationRule.resolve(pv.ruleExpression()[i]);
			}
    		
    		//然后，逐个匹配规则进行校验
    		for (ParamVerificationRule rule : rules) {
	    		try {
	    			this.verify(rule, joinPoint.getArgs());
				} catch (Exception e) {
					if(pv.failed() == null) {
						throw new BussinessException(e.getMessage(), e);
					} else {
						if(e.getClass().isAssignableFrom(pv.failed())) {
							throw e;
						} else {
							try {
								Constructor<? extends Exception> constructor = pv.failed().getConstructor(exConstructorParamTypes);
								throw constructor.newInstance(new Object[] { e.getMessage(), e });
							} catch (Exception cex) {
								throw new RuntimeException(String.format(FORMAT_EX_FOR_CONSTRUCTOR, pv.failed().getClass().getName(), Arrays.toString(exConstructorParamTypes)), cex);
							}
						}
					}
				}
			}
    	}
    	
		logger.debug("Execute 'beforeExecute(...)' end.");
    }

	private void verify(ParamVerificationRule rule, Object[] args) {
		
	}
}

class ParamVerificationRule {
	
	public static final String EMPTY = "";
	
	public static final String COLON = ":";
	
	public static final String COMMA = ",";
	
	private Class<?> target;
	private Class<?>[] rules;
	
	private ParamVerificationRule() {}
	
	public Class<?> getTarget() {
		return target;
	}

	public Class<?>[] getRules() {
		return rules;
	}

	public static ParamVerificationRule resolve(String expression) {
		ParamVerificationRule rule = new ParamVerificationRule();
		
		try {
			//规则格式举例：expression = targetPackage.targetClass:[rulePackage1.ruleClass1,rulePackage2.ruleClass2]
			int index = expression.indexOf(COLON);
			
			String left = expression.substring(0, index);
			rule.target = Class.forName(left);

			String right = expression.substring(index + 1);
			List<Class<?>> rules = new ArrayList<Class<?>>(); 
			for (String mpe : middleParenthesesExpressions(right)) {
				//规则格式举例：mpe = [rulePackage1.ruleClass1,rulePackage2.ruleClass2]
				for (String className : mpe.substring(1, mpe.length() - 1).split(COMMA)) {
					rules.add(Class.forName(className));
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("当前参数的校验规则表达式[expression=" + expression + "]错误导致无法解析：" + e.getMessage(), e);
		}
		
		return rule;
	}
	
	private static String[] middleParenthesesExpressions(String expression) {
		//目前仅支持一个
		return new String[] { expression };
	}
	
}