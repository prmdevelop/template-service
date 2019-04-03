package com.ffi.templateservice.service;

import java.util.List;

import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.exception.ApplicationBusinessException;

public interface TemplateService {
	
	public String uploadTemplate(String templateName) throws ApplicationBusinessException;
	public List<TemplateMaster> getTemplate(String search) throws ApplicationBusinessException; 
}
