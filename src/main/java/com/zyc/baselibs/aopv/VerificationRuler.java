package com.zyc.baselibs.aopv;

/**
 * 校验规则
 * @author zhouyancheng
 *
 */
public interface VerificationRuler {
	/**
	 *  执行校验规则
	 * @param obj
	 * @throws Exception 校验不通过必须抛出异常
	 */
	void execute(Object obj) throws Exception;
}
