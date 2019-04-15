package com.ffi.templateservice.controller;

import java.io.Serializable;

public class TemplateRequestJson implements Serializable{

	private static final long serialVersionUID = 1L;

	private String templateName;
	
	public String getTemplateName() {
		return templateName;
	}

	@Override
	public String toString() {
		return "TemplateRequestJson [templateName=" + templateName + "]";
	}
	
}
