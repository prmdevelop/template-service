package com.ffi.templateservice.service;

import java.util.List;

import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.vo.TemplateLabelVO;
import com.ffi.templateservice.vo.TemplateMasterVO;

public interface TemplateService {

	public List<TemplateMasterVO> getTemplate(String search) throws ApplicationBusinessException;

	public List<TemplateLabelVO> loadTemplateDetails(String templateName) throws ApplicationBusinessException;
	
	public String getTemplatePath(String templateName,String user) throws ApplicationBusinessException;
}
