package com.ffi.templateservice.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ffi.templateservice.handler.TemplateProperities;
import com.ffi.templateservice.service.TemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Template Service End Point")
@RestController
@RequestMapping("/rest/Template/")
public class TemplateServiceEndpoint {

	private static final Logger logger = LogManager.getLogger(TemplateServiceEndpoint.class);

	@Autowired
	TemplateService templateService;

	@Autowired
	TemplateProperities templateProperities;

	@ApiOperation(value = "Upload Template")
	@PostMapping(value = "/uploadTemplate", consumes = "application/json", produces = "application/json")
	@ResponseBody
	public TemplateResponseJson<TemplateResponseObject> uploadTemplate(
			@RequestBody TemplateRequestJson templateRequestJson) {
		logger.info("Start of TemplateServiceEndpoint.uploadTemplate()");
		TemplateResponseJson<TemplateResponseObject> responseJson = new TemplateResponseJson<>();
		try {
			TemplateResponseObject responseObject = new TemplateResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("url", templateService.uploadTemplate(templateRequestJson.getTemplateName(),
					templateRequestJson.getData()));
			data.put("token", "");
			responseObject.setTemplateResponse(data);
			responseJson.setStatusMessage(templateProperities.getPropertyValue("success.upload.msg"));
			responseJson.setStatusCode(templateProperities.getPropertyValue("success.code"));
			responseJson.setData(responseObject);
		} catch (Exception e) {
			logger.error("Exception in TemplateServiceEndpoint.uploadTemplate()");
			responseJson.setErrorMessage(templateProperities.getPropertyValue("error.msg"));
			responseJson.setErrorCode(templateProperities.getPropertyValue("error.code"));
		}
		logger.info("End of TemplateServiceEndpoint.uploadTemplate()");
		return responseJson;
	}

	@ApiOperation(value = "Get Template")
	@GetMapping(value = "/getTemplate/{templateName}", produces = "application/json")
	@ResponseBody
	public TemplateResponseJson<TemplateResponseObject> getTemplate(@PathVariable final String templateName) {
		logger.info("Start of TemplateServiceEndpoint.getTemplate()");
		TemplateResponseJson<TemplateResponseObject> responseJson = new TemplateResponseJson<>();
		try {
			TemplateResponseObject responseObject = new TemplateResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("template", templateService.getTemplate(templateName));
			responseObject.setTemplateResponse(data);
			responseJson.setStatusMessage(templateProperities.getPropertyValue("success.search.msg"));
			responseJson.setStatusCode(templateProperities.getPropertyValue("success.code"));
			responseJson.setData(responseObject);
		} catch (Exception e) {
			logger.error("Exception in TemplateServiceEndpoint.getTemplate()");
			responseJson.setErrorMessage(templateProperities.getPropertyValue("error.msg"));
			responseJson.setErrorCode(templateProperities.getPropertyValue("error.code"));
		}
		logger.info("End of TemplateServiceEndpoint.getTemplate()");
		return responseJson;
	}

}
