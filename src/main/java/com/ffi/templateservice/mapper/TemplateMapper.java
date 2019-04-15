package com.ffi.templateservice.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.vo.TemplateMasterVO;

@Mapper
public interface TemplateMapper {
	
	TemplateMasterVO fromTemplateMaster(TemplateMaster templateMaster);
	
	@InheritInverseConfiguration
	@Mapping(ignore = true, target = "id")
	TemplateMaster toTemplateMaster(TemplateMasterVO templateMasterVO);
}
