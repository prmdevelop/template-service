package com.ffi.templateservice.controller;

import java.io.Serializable;
import java.util.Map;

public class TemplateResponseObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> templateResponse;

	public Map<String, Object> getTemplateResponse() {
		return templateResponse;
	}

	public void setTemplateResponse(Map<String, Object> templateResponse) {
		this.templateResponse = templateResponse;
	}

	
}
