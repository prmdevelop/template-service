package com.ffi.templateservice.dao;

import java.util.List;


import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.exception.ApplicationBusinessException;

public interface TemplateDao {

	public List<TemplateMaster> getTemplate(String search) throws ApplicationBusinessException;
}
