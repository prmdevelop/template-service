package com.ffi.templateservice.service;

import com.ffi.templateservice.exception.ApplicationBusinessException;

public interface TemplateService {
	
	public String uploadTemplate(String templateName) throws ApplicationBusinessException;
}
