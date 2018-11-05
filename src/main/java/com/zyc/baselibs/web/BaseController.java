package com.zyc.baselibs.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zyc.baselibs.commons.Log4jUtlis;
import com.zyc.baselibs.commons.StringUtils;
import com.zyc.baselibs.entities.BaseEntity;
import com.zyc.baselibs.entities.DataStatus;
import com.zyc.baselibs.ex.ExceptionUtils;
public abstract class BaseController {
	
    protected static final String restPath = "/api";
    
    protected static final String STRING_EMPTY = "";
    
    protected String getCommonPath() {
    	return STRING_EMPTY;
    }

	protected void handleException(ResponseResult result, Exception e, Logger logger) {
		result.setStatus("1");
		String message = ExceptionUtils.uimessage(e);
		result.setMessage(message);
		Log4jUtlis.error(logger, message, e);
	}

    protected <T extends BaseEntity> String requestDetail(Model model, ClientAction action, String entityId, boolean readonly, T entity) {
    	model.addAttribute("action", action.getValue());
    	for (ClientAction value : ClientAction.values()) {
        	model.addAttribute("whether_" + value.getValue(), action.equals(value));
		}
    	model.addAttribute("actionText", action.getText());
    	model.addAttribute("allDataStatus", DataStatus.toList());
    	model.addAttribute("readonly", readonly || !DataStatus.ENABLED.getValue().equals(entity.getDatastatus()));
    	
    	HttpServletRequest request = this.getRequest();
    	if(request != null) {
    		String returnUrl = this.getParamReturnUrl();
    		model.addAttribute("returnUrl", StringUtils.isBlank(returnUrl) ? this.getDetailReturnUrlDefaults() : returnUrl); //记录上一次url，以便返回
    	}
    	
    	return this.getCommonPath() + "/detail";
    }
    
    protected String getParamReturnUrl() {
    	return this.getRequest().getParameter("returnUrl");
    }

    /**
     * 获取索引（列表）页ViewResolver的url（格式："commonPath/index"）
     * @return
     */
    protected String getIndexUrl() {
    	return this.getCommonPath() + "/index";
    }
    
    /**
     * 获取详情ViewResolver的url（格式："commonPath/detail"）
     * @return
     */
    protected String getDetailUrl() {
    	return this.getCommonPath() + "/detail";
    }
    
    /**
     * 获取重定向到编辑页的url（格式："redirect:commonPath/editpage/entityId?xxxxxx"）
     * @param entityId 实体id
     * @return
     */
    protected String getRedirectEditpageUrl(String entityId) {
    	return "redirect:" + this.getCommonPath() + "/editpage/" + entityId + this.getRequestParamUrl();
    }
    
    protected String getRequestParamUrl() {
    	List<String> params = new ArrayList<String>();
    	
    	String returnUrl = this.getParamReturnUrl();
    	if(StringUtils.isNotBlank(returnUrl)) {
        	params.add("returnUrl=" + returnUrl);
    	}
    	
    	return params.isEmpty() ? STRING_EMPTY : ("?" + String.join("&", params));
    }
    
    /**
     * 获取从详情页返回的url默认值，可通过重写来改写默认值。
     * @return
     */
    protected String getDetailReturnUrlDefaults() {
    	return this.getCommonPath();
    }
    
    protected ServletRequestAttributes getAttributes() {
    	return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }
    
    protected HttpServletRequest getRequest() {
    	return this.getAttributes().getRequest();
    }

    protected HttpServletResponse getResponse() {
    	return this.getAttributes().getResponse();
    }
}
