package com.zyc.baselibs;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.Assert;

/**
 * Spring上下文的持有者：提供获取Spring上下文中的bean的函数。</br>
 * 使用之前需要确保该对象被实例化，且传入该对象的ApplicationContext对象实例不为空。</br>
 * 例如代码实例化方式：通过Spring的Configuration、Bean注解来实例化该对象。
 * @author zhouyancheng
 *
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext;
	
	public SpringContextHolder(ApplicationContext applicationContext) {
		SpringContextHolder.applicationContext = applicationContext;
	}

    public static ApplicationContext getApplicationContext() {
        assertContext();
        return applicationContext;
    }
    
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		assertContext();
		SpringContextHolder.applicationContext = applicationContext;
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

	public void destroy() throws Exception {
		applicationContext = null;
	}

}
