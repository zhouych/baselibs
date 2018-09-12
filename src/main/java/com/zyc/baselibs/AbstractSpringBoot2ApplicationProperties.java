package com.zyc.baselibs;

import org.springframework.beans.factory.annotation.Value;

/**
 * 用于读取SpringBoot（version >= 2.x）的应用配置文件（application.yml）的类
 * @author zhouyancheng
 *
 */
public abstract class AbstractSpringBoot2ApplicationProperties {
	
	@Value("${spring.application.name}")
	private String springApplicationName;
	
	@Value("${spring.application.admin.enabled}")
	private String springApplicationAdminEnabled;

	@Value("${server.port}")
	private int serverPort;
	
	public String getSpringApplicationName() {
		return springApplicationName;
	}

	public String getSpringApplicationAdminEnabled() {
		return springApplicationAdminEnabled;
	}

	public int getServerPort() {
		return serverPort;
	}

	public String getServerServletContextPath() {
		return "";
	}
}
