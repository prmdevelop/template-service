package com.ffi.templateservice.endpoint;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ffi.templateservice.handler.TemplateServiceConfiguration;
import com.ffi.templateservice.service.TemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Template Service End Point")
@RestController
@RequestMapping("/template/service")
public class TemplateServiceController {

	private static final Logger logger = LogManager.getLogger(TemplateServiceController.class);

	@Autowired
	TemplateService templateService;

	@Autowired
	TemplateServiceConfiguration configuration;

	@ApiOperation(value = "Get Template")
	@GetMapping(value = "/getTemplate/{templateName}", produces = "application/json")
	@ResponseBody
	public TemplateResponseJson<TemplateResponseObject> getTemplate(@PathVariable final String templateName) {
		logger.info("Start of TemplateServiceEndpoint.getTemplate()");
		TemplateResponseJson<TemplateResponseObject> responseJson = new TemplateResponseJson<>();
		try {
			TemplateResponseObject responseObject = new TemplateResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("value", templateService.getTemplate(templateName));
			responseObject.setTemplateResponse(data);
			responseJson.setStatusMessage(configuration.getSuccess().getRetrieve());
			responseJson.setStatusCode(configuration.getSuccess().getCode());
			responseJson.setData(responseObject);
		} catch (Exception e) {
			logger.error("Exception in TemplateServiceEndpoint.getTemplate()");
			responseJson.setErrorMessage(configuration.getError().getRetrieve());
			responseJson.setErrorCode(configuration.getError().getCode());
		}
		logger.info("End of TemplateServiceEndpoint.getTemplate()");
		return responseJson;
	}
	
	@ApiOperation(value = "Load Template Details")
	@GetMapping(value = "/loadTemplate/{templateName}/{user}",consumes = "application/json", produces = "application/json")
	public TemplateResponseJson<TemplateResponseObject> getTemplateDetails(@PathVariable("templateName") String templateName,@PathVariable("user") String user) {
		logger.info("Start of TemplateServiceEndpoint.getTemplateDetails()");
		TemplateResponseJson<TemplateResponseObject> responseJson = new TemplateResponseJson<>();
		try {
			TemplateResponseObject responseObject = new TemplateResponseObject();
			Map<String, Object> data = new HashMap<>();
			data.put("templatePath",templateService.getTemplatePath(templateName,user));
			data.put("templateDetail",templateService.loadTemplateDetails(templateName));
			responseObject.setTemplateResponse(data);
			responseJson.setStatusMessage(configuration.getSuccess().getRetrieve());
			responseJson.setStatusCode(configuration.getSuccess().getCode());
			responseJson.setData(responseObject);
		} catch (Exception e) {
			logger.error("Exception in TemplateServiceEndpoint.getTemplateDetails()");
			responseJson.setErrorMessage(configuration.getError().getRetrieve());
			responseJson.setErrorCode(configuration.getError().getCode());
		}
		logger.info("End of TemplateServiceEndpoint.getTemplateDetails()");
		return responseJson;
	}

}
