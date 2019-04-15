package com.ffi.templateservice.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.handler.TemplateServiceConfiguration;
import com.ffi.templateservice.repository.TemplateRepository;

@Component
public class TemplateDaoImpl implements TemplateDao {
	private static final Logger logger = LogManager.getLogger(TemplateDaoImpl.class);

	@Autowired
	TemplateRepository templateRepository;

	@Autowired
	TemplateServiceConfiguration configuration;

	@Override
	public List<TemplateMaster> getTemplate(String search) throws ApplicationBusinessException {
		logger.info("Start of TemplateDaoImpl.getTemplate()");
		List<TemplateMaster> template;
		try {
			template = templateRepository.getTemplate(search);
		} catch (Exception e) {
			logger.error("Error in TemplateDaoImpl.getTemplate()");
			throw new ApplicationBusinessException(configuration.getError().getRetrieve());
		}
		logger.info("End of TemplateDaoImpl.getTemplateSearchDataDao()");
		return template;
	}

}