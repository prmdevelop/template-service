package com.ffi.templateservice.service;

import java.util.List;
import java.util.Map;

import com.ffi.templateservice.dto.TemplateFinancialDTO;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.vo.TemplateMasterVO;

public interface TemplateService {

	public String uploadTemplate(String templateName, List<TemplateFinancialDTO> financialData)
			throws ApplicationBusinessException;

	public List<TemplateMasterVO> getTemplate(String search) throws ApplicationBusinessException;

	public Map<String, String> fromTemplateMaster(String templateName) throws ApplicationBusinessException;
}
