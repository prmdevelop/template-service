package com.ffi.templateservice.controller;

import java.io.Serializable;
import java.util.List;

import com.ffi.templateservice.dto.TemplateFinancialDTO;

public class TemplateRequestJson implements Serializable{

	private static final long serialVersionUID = 1L;

	private String templateName;
	
	private List<TemplateFinancialDTO> data;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public List<TemplateFinancialDTO> getData() {
		return data;
	}

	public void setData(List<TemplateFinancialDTO> data) {
		this.data = data;
	}
	
	@Override
	public String toString() {
		return "TemplateRequestJson [templateName=" + templateName + ", data=" + data + "]";
	}

	

	
}
