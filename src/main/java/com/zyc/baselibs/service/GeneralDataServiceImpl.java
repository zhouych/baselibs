package com.zyc.baselibs.service;

import org.springframework.stereotype.Service;

@Service
public class GeneralDataServiceImpl extends AbstractBaseService implements GeneralDataService {

	public boolean deleteOnLogic(String entityId) throws Exception {
		throw new RuntimeException("The type GeneralDataService must implement the inherited abstract method EntityDeleteService.deleteOnLogic(String)");
	}

	public boolean deleteOnPhysical(String entityId) throws Exception {
		throw new RuntimeException("The type GeneralDataService must implement the inherited abstract method EntityDeleteService.deleteOnPhysical(String)");
	}

}
