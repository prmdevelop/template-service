package com.ffi.templateservice.service;

import java.util.List;
import java.util.Map;

import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.vo.TemplateLabelVO;

public interface TemplateService {
	
	public String uploadTemplate(String templateName,Map<String,Map<String,Double>> data) throws ApplicationBusinessException;
	public List<TemplateMaster> getTemplate(String search) throws ApplicationBusinessException; 
	public List<TemplateLabelVO> fromTemplateMaster(String templateName) throws ApplicationBusinessException;
}
