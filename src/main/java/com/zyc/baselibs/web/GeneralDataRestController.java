package com.zyc.baselibs.web;

import org.apache.log4j.Logger;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.zyc.baselibs.data.DataStatus;
import com.zyc.baselibs.data.EmptyNodeType;
import com.zyc.baselibs.data.IncludeOrExclude;
import com.zyc.baselibs.data.TreeNodeRelationship;
import com.zyc.baselibs.service.GeneralDataService;
import com.zyc.baselibs.vo.EntryBeanable;

@RestController
public class GeneralDataRestController extends BaseController {
	
	private static final Logger logger = Logger.getLogger(GeneralDataRestController.class);

	private static GeneralDataService generalDataService;
	
	public GeneralDataRestController(GeneralDataService generalDataService) {
    	Assert.notNull(generalDataService, "The context 'generalDataService' is null.");
		GeneralDataRestController.generalDataService = generalDataService;
	}
	
    @RequestMapping(value = restPath + "/tree/relationships", method = RequestMethod.GET)
    public String relationships() {
    	return this.enumToListJSON(TreeNodeRelationship.class);
    }

    @RequestMapping(value = restPath + "/includeorexclude", method = RequestMethod.GET)
    public String includeorexclude() {
    	return this.enumToListJSON(IncludeOrExclude.class);
    }

    @RequestMapping(value = restPath + "/datastatus", method = RequestMethod.GET)
    public String datastatus() {
    	return this.enumToListJSON(DataStatus.class);
    }
    
    @RequestMapping(value = restPath + "/emptynodetypes", method = RequestMethod.GET)
    public String emptynodetypes() {
    	return this.enumToListJSON(EmptyNodeType.class);
    }
    
    private <T extends Enum<?> & EntryBeanable> String enumToListJSON(Class<T> clazz) {
		return JSON.toJSONString(this.enumToList(clazz, generalDataService, logger));
    }
    
}
