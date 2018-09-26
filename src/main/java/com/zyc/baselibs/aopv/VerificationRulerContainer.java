package com.zyc.baselibs.aopv;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zyc.baselibs.annotation.EnumMapping;
import com.zyc.baselibs.annotation.EnumMappingUtils;
import com.zyc.baselibs.annotation.FieldRule;
import com.zyc.baselibs.annotation.FieldRuleUtils;
import com.zyc.baselibs.commons.StringUtils;

/**
 * 用于存放校验规则的容器
 * @author zhouyancheng
 *
 */
@Component
public final class VerificationRulerContainer {

	private static final Map<String, VerificationRuler> data = new HashMap<String, VerificationRuler>();
	
	static {
		//注册针对FieldRule注解的校验规则
		VerificationRulerContainer.register(FieldRule.class.getName(), new VerificationRuler() {
			public void execute(Object obj) throws Exception {
				FieldRuleUtils.verifyRequired(obj);
			}
		});
		
		//注册针对EnumMapping注解的校验规则
		VerificationRulerContainer.register(EnumMapping.class.getName(), new VerificationRuler() {
			public void execute(Object obj) throws Exception {
				EnumMappingUtils.verifyEnumField(obj);
			}
		});
		
		//注册OverallVerificationRuler校验规则：其内部包含针对FieldRule注解、EnumMapping注解的校验规则
		VerificationRulerContainer.register(OverallVerificationRuler.class.getName(), new OverallVerificationRuler());		
	}
	
	public static VerificationRuler getRuler(String key) {
		return StringUtils.isBlank(key) ? null : data.get(key);
	}
	
	public static void register(String key, VerificationRuler verificationRuler) {
		if(StringUtils.isBlank(key) || verificationRuler == null) {
			throw new IllegalArgumentException("Error parameter: key=" + key + "; verificationRuler=" + verificationRuler);
		}
		
		if(data.containsKey(key) || data.containsValue(verificationRuler)) {
			throw new RuntimeException("The data already exists: key=" + key + "; verificationRuler=" + verificationRuler);
		}
		
		data.put(key, verificationRuler);
	}
}
