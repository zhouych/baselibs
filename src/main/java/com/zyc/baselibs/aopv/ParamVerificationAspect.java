package com.zyc.baselibs.aopv;

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

/**
 * 针对方法的参数校验的Spring AOP切面
 * @author zhouyancheng
 *
 */
@Aspect
@Component
public class ParamVerificationAspect {

	private static final Logger logger = Logger.getLogger(ParamVerificationAspect.class); 
	
	private static final Class<?>[] exConstructorParamTypes = new Class<?>[] { String.class, Throwable.class };
	
	private static final String FORMAT_EX_FOR_CONSTRUCTOR = "[%s]异常类找不到支持当前参数类型%s的构造函数。";
	
	private static final String FORMAT_EX_FOR_RULEERROR = "[%s]校验规则不存在或者未注册。";
	
	private static final String FORMAT_EX_FOR_PARAMNULL = "[%s]参数类型对应的实例对象为null值。";
	
    @Pointcut("@annotation(com.zyc.baselibs.aopv.ParamVerification)")
	public void execPointCut() {
	}
    
    @SuppressWarnings("deprecation")
	@Before("execPointCut()")
    public void beforeExecute(JoinPoint joinPoint) throws Exception {
    	logger.debug("Execute 'beforeExecute(...)' ...");
    	
    	Object[] args = joinPoint.getArgs(); //获取函数（切入连接点）的参数
    	if(CollectionUtils.hasElement(args)) {
        	MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        	Method method = ms.getMethod();
        	ParamVerification pv = method.getAnnotation(ParamVerification.class);
        	//优先支持rules，其次支持ruleExpression：如果配置了rules，则ruleExpression失效；否则，ruleExpression配置生效。
        	if(CollectionUtils.hasElement(pv.rules())) {
            	Class<?>[] pts = ms.getParameterTypes();
        		VerificationRuler ruler;
        		Object obj;
        		Class<?> paramType;
        		for (int i = 0; i < args.length; i++) {
        			obj = args[i];
        			paramType = pts[i];
    				//必须确保待校验的对象实例对应的类是被Verifiable注解的
        			if(paramType.isAnnotationPresent(Verifiable.class)) {
        				if(obj == null) {
        					if(!paramType.getAnnotation(Verifiable.class).ignoreNull()) {
		    					this.handleVerifyException(pv, new NullPointerException(String.format(FORMAT_EX_FOR_PARAMNULL, paramType.getName())));
        					}
        				} else {
            				for (Class<?> clazz : pv.rules()) {
            					ruler = VerificationRulerContainer.getRuler(clazz.getName());
            					if(ruler == null) {
            						throw new RuntimeException(String.format(FORMAT_EX_FOR_RULEERROR, clazz.getName()));
            					}
            					
            					try {
            						ruler.execute(obj);
    							} catch (Exception e) {
    		    					this.handleVerifyException(pv, e);
    							}
    						}
        				}
        			}
				}
        	} else if(CollectionUtils.hasElement(pv.ruleExpression())) {
        		ParamVerificationRule[] rules = new ParamVerificationRule[pv.ruleExpression().length];
        		
        		//首先，确保所有的校验规则表达式格式正确，正常被解析
        		for (int i = 0; i < pv.ruleExpression().length; i++) {
        			rules[i] = ParamVerificationRule.resolve(pv.ruleExpression()[i]);
    			}
        		
        		//然后，逐个匹配规则进行校验
        		for (ParamVerificationRule rule : rules) {
    	    		try {
    	    			this.verify(rule, args);
    				} catch (Exception e) {
    					this.handleVerifyException(pv, e);
    				}
    			}
        	}
    	}
    	
		logger.debug("Execute 'beforeExecute(...)' end.");
    }

	private void handleVerifyException(ParamVerification pv, Exception e) throws BussinessException, Exception {
		if(pv.failed() == null) {
			throw new BussinessException(e.getMessage(), e);
		} else {
			if(pv.failed().isAssignableFrom(e.getClass())) {
				throw e;
			} else {
				Exception ex = null;
				
				try {
					ex = pv.failed().getConstructor(exConstructorParamTypes).newInstance(new Object[] { e.getMessage(), e });
				} catch (Exception cex) {
					throw new RuntimeException(String.format(FORMAT_EX_FOR_CONSTRUCTOR, pv.failed().getClass().getName(), Arrays.toString(exConstructorParamTypes)), cex);
				}
				
				if(ex != null) {
					throw ex;
				}
			}
		}
	}
    
	@Deprecated
	private void verify(ParamVerificationRule pvr, Object[] args) {
		for (Object obj : args) {
			if(obj.getClass().isAssignableFrom(pvr.getTarget())) {
				for (Class<?> rule : pvr.getRules()) {
					throw new RuntimeException("Rule not implemented. (" + rule.getName() + ")");
				}
			}
		}
	}
}

@Deprecated
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