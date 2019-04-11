package com.ffi.templateservice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffi.templateservice.dao.TemplateDao;
import com.ffi.templateservice.domain.TemplateDetails;
import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.domain.TemplateSection;
import com.ffi.templateservice.dto.TemplateFinancialDTO;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.handler.TemplateProperities;

@Service
public class TemplateServiceImpl implements TemplateService {

	private static final Logger logger = LogManager.getLogger(TemplateServiceImpl.class);

	@Autowired
	TemplateDao templateDao;

	@Autowired
	TemplateProperities templateProperities;

	@Override
	public String uploadTemplate(String templateName, List<TemplateFinancialDTO> data)
			throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.uploadTemplate()");
		String webUrl = "";
		try {
			
			//Change the lineItem id from financial to the template lineItem
			Map<String, String> templateLineItemMap = fromTemplateMaster(templateName);
			for (int i = 0; i < data.size(); i++) {
				String lineItemId = data.get(i).getLineItem();
				if (templateLineItemMap.containsKey(lineItemId)) {
					data.get(i).setLineItem(templateLineItemMap.get(lineItemId));
				}
			}
			
			//Making the lineItem as the key & year and lineItem value as value
			Map<String, Map<String, Double>> financialData = new HashMap<>();
			for (int i = 0; i < data.size(); i++) {
				Map<String, Double> lineItemValue;
				if (financialData.containsKey(data.get(i).getLineItem())) {
					lineItemValue = financialData.get(data.get(i).getLineItem());
					lineItemValue.put(data.get(i).getYear(), data.get(i).getLineItemValue());
				} else {
					lineItemValue = new HashMap<>();
					lineItemValue.put(data.get(i).getYear(), data.get(i).getLineItemValue());
				}
				financialData.put(data.get(i).getLineItem(), lineItemValue);
			}

			//Sort the nested map based on the key i.e year in reverse order
			Map<String, TreeMap<String, Double>> sortedData = new HashMap<>();
			for (Map.Entry<String, Map<String, Double>> dataEntry : financialData.entrySet()) {
				Map<String, Double> periodEntry = dataEntry.getValue();
				TreeMap<String, Double> sortedPeriodMap = new TreeMap<>(Collections.reverseOrder());
				sortedPeriodMap.putAll(periodEntry);
				sortedData.put(dataEntry.getKey(), sortedPeriodMap);
			}

			String templatePath = copySelectedTemplate(templateName, "UserName");
			populateValueToTemplate(templatePath, sortedData);
			webUrl = onedriveApiForUpload(templatePath);
		} catch (ApplicationBusinessException e) {
			logger.info("Error in TemplateServiceImpl.uploadTemplate()");
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
		logger.info("End of TemplateServiceImpl.uploadTemplate()");
		return webUrl;
	}

	public String copySelectedTemplate(String templateName, String user) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.copySelectedTemplate()");
		Path dirPathObj = Paths.get(templateProperities.getPropertyValue("template.directory.targetDirPath"));
		String delimiter = templateProperities.getPropertyValue("template.delimiter");
		Path subDirPathObj = Paths.get(dirPathObj + delimiter + user);
		Path filePathObj = Paths.get(subDirPathObj + delimiter + templateName);
		boolean dirExists = Files.exists(dirPathObj);
		File src = new File(
				templateProperities.getPropertyValue("template.directory.sourceDirPath") + delimiter + templateName);
		File target = new File(templateProperities.getPropertyValue("template.directory.targetDirPath") + delimiter
				+ user + delimiter + templateName);
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
			logger.info("Error in TemplateServiceImpl.copySelectedTemplate()" + e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
		logger.info("End of TemplateServiceImpl.copySelectedTemplate()");
		return filePathObj.toString();
	}

	private void populateValueToTemplate(String templatePath, Map<String, TreeMap<String, Double>> data)
			throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.populateValueToTemplate()");
		try (InputStream uploadedFile = new FileInputStream(templatePath);
				Workbook workbook = new XSSFWorkbook(uploadedFile)) {
			for (Map.Entry<String, TreeMap<String, Double>> dataEntry : data.entrySet()) {
				int lineItemIndex = workbook.getNameIndex(dataEntry.getKey());
				int count = 1;
				if (lineItemIndex != -1) {
					Name namedLineItem = workbook.getNameAt(lineItemIndex);
					AreaReference arefLineItem = new AreaReference(namedLineItem.getRefersToFormula(), null);
					CellReference crefsLineItem = arefLineItem.getAllReferencedCells()[0];
					for (Map.Entry<String, Double> periodEntry : dataEntry.getValue().entrySet()) {
						Name namedPeriod = workbook.getNameAt(workbook.getNameIndex("DS_YY" + count));
						AreaReference arefPeriod = new AreaReference(namedPeriod.getRefersToFormula(), null);
						CellReference[] crefsPeriod = arefPeriod.getAllReferencedCells();
						for (int i = 0; i < crefsPeriod.length; i++) {
							Sheet dataSheet = workbook.getSheet(crefsPeriod[i].getSheetName());
							Row rowDataSheet = dataSheet.getRow(crefsLineItem.getRow());
							Cell cellDataSheet = rowDataSheet.getCell(crefsPeriod[i].getCol());
							cellDataSheet.setCellValue(periodEntry.getValue());
						}

						Name aNamedBalanceSheet = workbook
								.getNameAt(workbook.getNameIndex("BS_" + dataEntry.getKey() + "_YY" + count));
						AreaReference arefBalanceSheet = new AreaReference(aNamedBalanceSheet.getRefersToFormula(),
								null);
						CellReference crefsBalanceSheet = arefBalanceSheet.getAllReferencedCells()[0];
						Sheet balanceSheet = workbook.getSheet(crefsBalanceSheet.getSheetName());
						Row rowBalanceSheet = balanceSheet.getRow(crefsBalanceSheet.getRow());
						Cell cellBalanceSheet = rowBalanceSheet.getCell(crefsBalanceSheet.getCol());
						cellBalanceSheet.setCellValue(periodEntry.getValue());
						count++;
					}
				}
			}

			FileOutputStream outputFile = new FileOutputStream(new File(templatePath));
			FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
			formulaEvaluator.evaluateAll();
			workbook.setSheetHidden(workbook.getSheetIndex("Data Sheet"), true);
			workbook.write(outputFile);
			outputFile.close();
			logger.info("End of TemplateServiceImpl.populateValueToTemplate()");
		} catch (Exception e) {
			logger.info("Error in TemplateServiceImpl.populateValueToTemplate()" + e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
	}

	private String onedriveApiForUpload(String templatePath) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.onedriveApiForUpload()");
		String webUrl = "";
		try {
			URL url = new URL(templateProperities.getPropertyValue("onedrive.rest.url")
					+ URLEncoder.encode(templatePath, "UTF-8"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			if (conn.getResponseCode() != 200) {
				throw new ApplicationBusinessException("Failed : HTTP error code : " + conn.getResponseCode());
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			StringBuilder sb = new StringBuilder();
			String inputLine = "";
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine);
			}
			inputLine = sb.toString();
			conn.disconnect();
			JSONObject jsonObject = new JSONObject(inputLine);
			String responseDataObject = jsonObject.getString("data");
			JSONObject jsonObject2 = new JSONObject(responseDataObject);
			String responseObject = jsonObject2.getString("oneDriveResponse");
			webUrl = new JSONObject(responseObject).getString("URL");
		} catch (Exception e) {
			logger.info("Error in TemplateServiceImpl.onedriveApiForUpload()" + e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
		logger.info("End of TemplateServiceImpl.copySelectedTemplate()");
		return webUrl;
	}

	@Override
	public List<TemplateMaster> getTemplate(String search) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.getTemplate()");
		List<TemplateMaster> templates;
		try {
			templates = templateDao.getTemplate(search);
		} catch (Exception e) {
			logger.error("Error in TemplateServiceImpl.getTemplate()" + e.getCause());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.retrieved.msg"));
		}
		logger.info("End of TemplateServiceImpl.getTemplate()");
		return templates;
	}

	@Override
	public Map<String, String> fromTemplateMaster(String templateName) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.fromTemplateMaster()");
		Map<String, String> templateLabel1 = new HashMap<>();
		try {
			List<TemplateMaster> templateMasters = templateDao.getTemplate(templateName);
			for (TemplateMaster templateMaster : templateMasters) {
				for (TemplateSection templateSection : templateMaster.getTemplateSection()) {
					for (TemplateDetails templateDetail : templateSection.getTemplateDetails()) {
						templateLabel1.put(templateDetail.getLabelMaster().getId().toString(),
								templateDetail.getLabelMaster().getLabelId());
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in TemplateServiceImpl.fromTemplateMaster()");
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.retrieved.msg"));
		}
		logger.info("End of TemplateServiceImpl.fromTemplateMaster()");
		return templateLabel1;
	}
}
