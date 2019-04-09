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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffi.templateservice.dao.TemplateDao;
import com.ffi.templateservice.domain.TemplateDetails;
import com.ffi.templateservice.domain.TemplateMaster;
import com.ffi.templateservice.domain.TemplateSection;
import com.ffi.templateservice.exception.ApplicationBusinessException;
import com.ffi.templateservice.handler.TemplateProperities;
import com.ffi.templateservice.vo.TemplateLabelVO;

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
			/*Map<String, Double> data = new HashMap<>();
			data.put("DS_CASH_EQUIV_YY1", 18345.00);
			data.put("DS_CASH_EQUIV_YY2", 17345.00);
			data.put("DS_CASH_EQUIV_YY3", 16345.00);
			data.put("DS_CASH_EQUIV_YY4", 15345.00);
			
			data.put("DS_FIN_INV_MARKET_SEC_YY1", 18123.00);
			data.put("DS_FIN_INV_MARKET_SEC_YY2", 17123.00);
			data.put("DS_FIN_INV_MARKET_SEC_YY3", 16123.00);
			data.put("DS_FIN_INV_MARKET_SEC_YY4", 15123.00);
			
			data.put("DS_TOT_RECEIVABLES_YY1", 18659.00);
			data.put("DS_TOT_RECEIVABLES_YY2", 17659.00);
			data.put("DS_TOT_RECEIVABLES_YY3", 16659.00);
			data.put("DS_TOT_RECEIVABLES_YY4", 15659.00);
			
			data.put("DS_ALLOW_DOUBT_DEBT_YY1", 18548.00);
			data.put("DS_ALLOW_DOUBT_DEBT_YY2", 17548.00);
			data.put("DS_ALLOW_DOUBT_DEBT_YY3", 16548.00);  
			data.put("DS_ALLOW_DOUBT_DEBT_YY4", 15548.00);      
			
			data.put("DS_INVENTORY_YY1", 18922.00);
			data.put("DS_INVENTORY_YY2", 17922.00);
			data.put("DS_INVENTORY_YY3", 16922.00);
			data.put("DS_INVENTORY_YY4", 15922.00);
			
			data.put("DS_PREPAID_EXP_YY1", 18788.00);
			data.put("DS_PREPAID_EXP_YY2", 17788.00);
			data.put("DS_PREPAID_EXP_YY3", 16788.00);
			data.put("DS_PREPAID_EXP_YY4", 15788.00);
			
			data.put("DS_CURR_DEF_TAX_YY1", 18777.00);
			data.put("DS_CURR_DEF_TAX_YY2", 17777.00);
			data.put("DS_CURR_DEF_TAX_YY3", 16777.00);
			data.put("DS_CURR_DEF_TAX_YY4", 15777.00);
			
			data.put("DS_OTH_OP_CURR_ASSET_YY1", 18911.00);
			data.put("DS_OTH_OP_CURR_ASSET_YY2", 17911.00);
			data.put("DS_OTH_OP_CURR_ASSET_YY3", 16911.00);
			data.put("DS_OTH_OP_CURR_ASSET_YY4", 15911.00);
			
			data.put("DS_INTERCOM_CURR_ASSET_YY1", 18667.00);
			data.put("DS_INTERCOM_CURR_ASSET_YY2", 17667.00);
			data.put("DS_INTERCOM_CURR_ASSET_YY3", 16667.00);
			data.put("DS_INTERCOM_CURR_ASSET_YY4", 15667.00);
			
			data.put("DS_OTH_NON_OP_CA_YY1", 18112.00);
			data.put("DS_OTH_NON_OP_CA_YY2", 17112.00);
			data.put("DS_OTH_NON_OP_CA_YY3", 16112.00);
			data.put("DS_OTH_NON_OP_CA_YY4", 15112.00);*/
			
			Map<String, Map<String,Double>> data = new HashMap<>();
			Map<String,Double> yearValue = new HashMap<>();
			yearValue.put("_YY1", 18234.00);
			yearValue.put("_YY2", 17234.00);
			yearValue.put("_YY3", 16234.00);
			yearValue.put("_YY4", 15234.00);
			data.put("CASH_EQUIV", yearValue);
			yearValue = new HashMap<>();
			yearValue.put("_YY1", 18989.00);
			yearValue.put("_YY2", 17989.00);
			yearValue.put("_YY3", 16989.00);
			yearValue.put("_YY4", 15989.00);
			data.put("FIN_INV_MARKET_SEC", yearValue);
			
			// -----
			populateValueToTemplate1(templatePath, data);
			
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
			logger.info("Error in TemplateServiceImpl.copySelectedTemplate()"+e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
		logger.info("End of TemplateServiceImpl.copySelectedTemplate()");
		return filePathObj.toString();
	}

	public void populateValueToTemplate(String templatePath, Map<String, Double> data)
			throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.populateValueToTemplate()");
		try(InputStream uploadedFile = new FileInputStream(templatePath);
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
			logger.info("Error in TemplateServiceImpl.populateValueToTemplate()"+e.getStackTrace());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.msg"));
		}
	}
	
	public void populateValueToTemplate1(String templatePath, Map<String, Map<String,Double>> data)
			throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.populateValueToTemplate()");
		try(InputStream uploadedFile = new FileInputStream(templatePath);
			Workbook workbook = new XSSFWorkbook(uploadedFile)) {
			for (Map.Entry<String, Map<String, Double>> dataEntry : data.entrySet()) {
				Name aNamedDataSheetCell = workbook.getNameAt(workbook.getNameIndex(dataEntry.getKey()));
				AreaReference arefDataSheet = new AreaReference(aNamedDataSheetCell.getRefersToFormula(), null);
				CellReference crefsDataSheet = arefDataSheet.getAllReferencedCells()[0];
				for (Map.Entry<String, Double> yearEntry : dataEntry.getValue().entrySet()) {
					Name aNamedYearDataSheet = workbook.getNameAt(workbook.getNameIndex(yearEntry.getKey()));
					AreaReference arefYearDataSheet = new AreaReference(aNamedYearDataSheet.getRefersToFormula(), null);
					CellReference[] crefsYearDataSheet = arefYearDataSheet.getAllReferencedCells();
					for (int i = 0; i < crefsYearDataSheet.length; i++) {
						Sheet dataSheet = workbook.getSheet(crefsYearDataSheet[i].getSheetName());
						Row rowDataSheet = dataSheet.getRow(crefsDataSheet.getRow());
						Cell cellDataSheet = rowDataSheet.getCell(crefsYearDataSheet[i].getCol());
						cellDataSheet.setCellValue(yearEntry.getValue());
					}
					
					Name aNamedBalanceSheet = workbook.getNameAt(workbook.getNameIndex("BS_"+dataEntry.getKey()+yearEntry.getKey()));
					AreaReference arefBalanceSheet = new AreaReference(aNamedBalanceSheet.getRefersToFormula(), null);
					CellReference crefsBalanceSheet = arefBalanceSheet.getAllReferencedCells()[0];
					Sheet balanceSheet = workbook.getSheet(crefsBalanceSheet.getSheetName());
					Row rowBalanceSheet = balanceSheet.getRow(crefsBalanceSheet.getRow());
					Cell cellBalanceSheet = rowBalanceSheet.getCell(crefsBalanceSheet.getCol());
					cellBalanceSheet.setCellValue(yearEntry.getValue());
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
			e.printStackTrace();
			logger.info("Error in TemplateServiceImpl.populateValueToTemplate()"+e.getStackTrace());
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
			logger.info("Error in TemplateServiceImpl.onedriveApiForUpload()"+e.getStackTrace());
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
			logger.error("Error in TemplateServiceImpl.getTemplate()"+ e.getCause());
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.retrieved.msg"));
		}
		logger.info("End of TemplateServiceImpl.getTemplate()");
		return templates;
	}
	
	@Override
	public List<TemplateLabelVO> fromTemplateMaster(String templateName) throws ApplicationBusinessException {
		logger.info("Start of TemplateServiceImpl.fromTemplateMaster()");
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
			logger.error("Error in TemplateServiceImpl.fromTemplateMaster()");
			throw new ApplicationBusinessException(templateProperities.getPropertyValue("error.retrieved.msg"));
		}
		logger.info("End of TemplateServiceImpl.fromTemplateMaster()");
		return templateLabel;
	}
	
	public static void main(String[] args) {
		try (FileInputStream fileInputStream = new FileInputStream("D:\\Phillips 66_SME TemplateV0.1.xlsx");
				XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {
			XSSFSheet worksheet = workbook.getSheet("Data Sheet");
			Iterator<Row> it = worksheet.rowIterator();
			while (it.hasNext()) {
				XSSFRow r = (XSSFRow) it.next();
				Iterator<Cell> it1 = r.cellIterator();
				while (it1.hasNext()) {
					XSSFCell cell = (XSSFCell) it1.next();
					System.out.println("Row: " + cell.getRowIndex() + " ,Column: " + cell.getColumnIndex()+"  Value *="+cell+"*");
				}
				System.out.println();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

