package com.zyc.baselibs;

import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

/**
 * Spring上下文的工具类：提供获取Spring上下文中的bean的函数。</br>
 * 使用之前需要确保setApplicationContext被调用，且传入该函数的参数ApplicationContext对象实例不为空。</br>
 * <p>
 * <code>SpringBoot Exapmle ('main' method): </code></br>
 * <code>SpringContextUtils.setApplicationContext(App.run(App.class, args));</code>
 * </p>
 * @author zhouyancheng
 *
 */
public final class SpringContextUtils {
	
	private static ApplicationContext applicationContext;

    public static ApplicationContext getApplicationContext() {
        assertContext();
        return applicationContext;
    }
    
    public static void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtils.applicationContext = applicationContext;
    }
    
    public static Object getBean(String name) {
        assertContext();
        return applicationContext.getBean(name);
    }
    
    public static Object getBean(Class<?> requiredType){
        assertContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertContext() {
    	Assert.notNull(applicationContext, "The context 'applicationContext' is null.");
    }
}
