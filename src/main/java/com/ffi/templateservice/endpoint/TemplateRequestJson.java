package com.ffi.templateservice.endpoint;

import java.util.Map;

public class TemplateRequestJson {

	private String templateName;

	private Map<String, Map<String, Double>> data;

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public Map<String, Map<String, Double>> getData() {
		return data;
	}

	public void setData(Map<String, Map<String, Double>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "TemplateRequestJson [templateName=" + templateName + ", data=" + data + "]";
	}
}
