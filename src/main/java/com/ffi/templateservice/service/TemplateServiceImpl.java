package com.ffi.templateservice.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffi.templateservice.dao.TemplateDao;
import com.ffi.templateservice.domain.TemplateDetails;
import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.domain.TemplateSection;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.handler.TemplateServiceConfiguration;
import com.ffi.templateservice.mapper.TemplateMapper;
import com.ffi.templateservice.vo.TemplateLabelVO;
import com.ffi.templateservice.vo.TemplateMasterVO;

@Service
public class TemplateServiceImpl implements TemplateService {

	private static final Logger logger = LogManager.getLogger(TemplateServiceImpl.class);
	
	TemplateMapper mapper=Mappers.getMapper(TemplateMapper.class);

	@Autowired
	TemplateDao templateDao;

	@Autowired
	TemplateServiceConfiguration configuration;

	@Override
	public List<TemplateMasterVO> getTemplate(String search) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.getTemplate()");
		List<TemplateMasterVO> templateMasterVO=null;
		try {
			List<TemplateMaster> templates = templateDao.getTemplate(search);
			templateMasterVO= templates.stream().map(c -> mapper.fromTemplateMaster(c)).collect(Collectors.toList());
		} catch (Exception e) {
			logger.error("Error in TemplateServiceImpl.getTemplate()"+ e.getCause());
			throw new ApplicationBusinessException(configuration.getSuccess().getRetrieve());
		}
		logger.info("End of TemplateServiceImpl.getTemplate()");
		return templateMasterVO;
	}

	@Override
	public List<TemplateLabelVO> loadTemplateDetails(String templateName) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.loadTemplate()");
		List<TemplateLabelVO> templateLabel = new ArrayList<>();
		try {
			List<TemplateMaster> templateMasters = templateDao.getTemplate(templateName);
			for(TemplateMaster templateMaster : templateMasters){
				for(TemplateSection templateSection : templateMaster.getTemplateSection()){
					for(TemplateDetails templateDetail : templateSection.getTemplateDetails()){
						TemplateLabelVO templateLabelVO = new TemplateLabelVO();
						templateLabelVO.setTemplateLabelId(templateDetail.getLabelMaster().getId().toString());
						templateLabelVO.setTemplateLineItem(templateDetail.getLabelMaster().getLabelId());
						templateLabel.add(templateLabelVO);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in TemplateServiceImpl.loadTemplate()");
			throw new ApplicationBusinessException(configuration.getError().getRetrieve());
		}
		logger.info("End of TemplateServiceImpl.loadTemplate()");
		return templateLabel;
	}

	@Override
	public String getTemplatePath(String templateName, String user) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.getTemplatePath()");
		Path dirPathObj = Paths.get(configuration.getTarget());
		String delimiter = configuration.getDelimiter();
		Path subDirPathObj = Paths.get(dirPathObj + delimiter + user);
		Path filePathObj = Paths.get(subDirPathObj + delimiter + templateName);
		boolean dirExists = Files.exists(dirPathObj);
		File src = new File(configuration.getSource() + delimiter + templateName);
		File target = new File(configuration.getTarget() + delimiter + user + delimiter + templateName);
		try {
			if (dirExists) {
				boolean subDirExists = Files.exists(subDirPathObj);
				if (!subDirExists) {
					Files.createDirectory(subDirPathObj);
				}
				boolean filesExists = Files.exists(filePathObj);
				if (!filesExists) {
					Files.copy(src.toPath(), target.toPath());
				}
			} else {
				Files.createDirectory(dirPathObj);
				Files.createDirectory(subDirPathObj);
				Files.copy(src.toPath(), target.toPath());
			}
		} catch (Exception e) {
			logger.info("Error in TemplateServiceImpl.getTemplatePath()" + e.getStackTrace());
			throw new ApplicationBusinessException(configuration.getError().getRetrieve());
		}
		logger.info("End of TemplateServiceImpl.getTemplatePath()");
		return filePathObj.toString();
	}
}
