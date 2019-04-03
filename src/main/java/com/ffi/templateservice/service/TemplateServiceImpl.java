package com.ffi.templateservice.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffi.templateservice.dao.TemplateDao;
import com.ffi.templateservice.domain.TemplateMaster;
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
	public String uploadTemplate(String templateName) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.uploadTemplate()");
		String webUrl = "";
		try {
			String templatePath = copySelectedTemplate(templateName, "UserName");
			// ----Need to remove
			Map<String, Double> data = new HashMap<>();
			data.put("DS_CASH_EQUIV_YY1", 18234.00);
			data.put("DS_FIN_INV_MARKET_SEC_YY1", 18345.00);
			data.put("DS_CASH_EQUIV_YY2", 17234.00);
			data.put("DS_FIN_INV_MARKET_SEC_YY2", 17345.00);
			// -----
			populateValueToTemplate(templatePath, data);
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
		File src = new File(templateProperities.getPropertyValue("template.directory.sourceDirPath") + delimiter + templateName);
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

	public void populateValueToTemplate(String templatePath, Map<String, Double> data)
			throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.populateValueToTemplate()");
		try (InputStream uploadedFile = new FileInputStream(templatePath);
				Workbook workbook = new XSSFWorkbook(uploadedFile)) {
			Iterator<Map.Entry<String, Double>> itr = data.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, Double> pair = itr.next();
				Name aNamedCell = workbook.getNameAt(workbook.getNameIndex(pair.getKey()));
				AreaReference aref = new AreaReference(aNamedCell.getRefersToFormula(), null);
				CellReference[] crefs = aref.getAllReferencedCells();
				for (int i = 0; i < crefs.length; i++) {
					Sheet s = workbook.getSheet(crefs[i].getSheetName());
					Row r = s.getRow(crefs[i].getRow());
					Cell c = r.getCell(crefs[i].getCol());
					c.setCellValue(pair.getValue());
				}
			}
			FileOutputStream outputFile = new FileOutputStream(new File(templatePath));
			FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
			formulaEvaluator.evaluateAll();
			workbook.write(outputFile);
			outputFile.close();
			logger.info("End of TemplateServiceImpl.populateValueToTemplate()");
		} catch (Exception e) {
			logger.info("Error in TemplateServiceImpl.populateValueToTemplate()" + e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
	}

	public String onedriveApiForUpload(String templatePath) throws ApplicationBusinessException {
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
		List<TemplateMaster> searchlist;
		try {
			searchlist = templateDao.getTemplate(search);
		} catch (Exception e) {
			logger.error("Error in TemplateServiceImpl.getTemplate()" + e.getCause());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.retrieved.msg"));
		}
		logger.info("End of TemplateServiceImpl.getTemplate()");
		return searchlist;
	}
}
