package com.ffi.templateservice.handler;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@ConfigurationProperties("template-service")
public class TemplateServiceConfiguration {
	
	private Success success;
	private Error error;
	private String swagger;
	private String target;
	private String source;
	private String delimiter;
	
	public static class Success {

		private String code;
		private String retrieve;
		private String delete;
		private String modify;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getRetrieve() {
			return retrieve;
		}

		public void setRetrieve(String retrieve) {
			this.retrieve = retrieve;
		}

		public String getDelete() {
			return delete;
		}

		public void setDelete(String delete) {
			this.delete = delete;
		}

		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}
	}

	public static class Error {

		private String record;
		private String code;
		private String retrieve;
		private String delete;
		private String modify;

		public String getRecord() {
			return record;
		}

		public void setRecord(String record) {
			this.record = record;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getRetrieve() {
			return retrieve;
		}

		public void setRetrieve(String retrieve) {
			this.retrieve = retrieve;
		}

		public String getDelete() {
			return delete;
		}

		public void setDelete(String delete) {
			this.delete = delete;
		}

		public String getModify() {
			return modify;
		}

		public void setModify(String modify) {
			this.modify = modify;
		}
	}
	
	public Success getSuccess() {
		return success;
	}

	public void setSuccess(Success success) {
		this.success = success;
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}
	
	public String getSwagger() {
		return swagger;
	}

	public void setSwagger(String swagger) {
		this.swagger = swagger;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDelimiter() {
		return delimiter;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
}
