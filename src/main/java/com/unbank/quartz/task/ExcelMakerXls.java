package com.unbank.quartz.task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unbank.dao.BhiConstructionReader;
import com.unbank.dao.BhiDesignReader;
import com.unbank.dao.BhiEnvironmentalAssessmentReader;
import com.unbank.dao.BhiFeasibilityStudyReader;
import com.unbank.dao.BhiOwnerReader;
import com.unbank.dao.BhiPlantReader;
import com.unbank.dao.BhiPrimaryDesignReader;
import com.unbank.dao.BhiProReader;
import com.unbank.dao.BhiSurveyReader;
import com.unbank.mybatis.vo.BhiConstruction;
import com.unbank.mybatis.vo.BhiDesign;
import com.unbank.mybatis.vo.BhiEnvironmentalAssessment;
import com.unbank.mybatis.vo.BhiFeasibilityStudy;
import com.unbank.mybatis.vo.BhiOwner;
import com.unbank.mybatis.vo.BhiPlant;
import com.unbank.mybatis.vo.BhiPrimaryDesign;
import com.unbank.mybatis.vo.BhiProWithBLOBs;
import com.unbank.mybatis.vo.BhiSurvey;
import com.unbank.sender.MailSender;
import com.unbank.sender.MailSenderInfo;
import com.unbank.tools.SimpleTools;
import com.unbank.tools.Values;

public class ExcelMakerXls {

	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		String startTime = SimpleTools.getyyyyMMddTimeString(new Date(), 0);
		String endTime = SimpleTools.getyyyyMMddTimeString(new Date(), 0);
//		String startTime ="2016-05-24";
//		String endTime ="2016-05-24";
		new ExcelMakerXls().makeExcel(startTime, endTime);

	}

	public void makeExcel(String startTime, String endTime) {
		HSSFWorkbook xwb = new HSSFWorkbook();
		boolean isExit = bhiProMaker(startTime, endTime, xwb);
		if (isExit) {
			String filePath = makeExcelFile(startTime, endTime, xwb);
			System.out.println(filePath);
//			MailSenderInfo mailInfo = fillMailInfo(filePath);
//			MailSender sms = new MailSender();
//			sms.sendHtmlMail(mailInfo);
		}

	}

	private MailSenderInfo fillMailInfo(String filePath) {
		MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost(Values.v.SERVERHOST);
		mailInfo.setMailServerPort(Values.v.SERVERPORT);
		mailInfo.setValidate(true);
		mailInfo.setUserName(Values.v.USERNAME);
		mailInfo.setPassword(Values.v.PASSWORD);// 您的邮箱密码
		mailInfo.setFromAddress(Values.v.FROMADDRESS);
		List<String> address = new ArrayList<String>();
		// address.add("unbankspider@163.com");
		// List<String> address2 = new ArrayList<String>();
		String temp[] = Values.v.RECEIVER.split(",");
		for (String string : temp) {
			address.add(string);
		}
		// 添加邮件接收人
		mailInfo.setReceivers(address);
		// 添加邮件抄送人
		// mailInfo.setCcReceivers(address);
		// mailInfo.setToAddress("unbankspider@163.com");//邮箱密码unbank
		String subject = SimpleTools.getyyyyMMddTimeString(new Date(), -1)
				+ Values.v.SUBJECT;
		String content = SimpleTools.getyyyyMMddTimeString(new Date(), -1)
				+ Values.v.CONTENT;
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		String[] files = { filePath };
		mailInfo.setAttachFileNames(files);
		return mailInfo;
	}

	private String makeExcelFile(String startTime, String endTime,
			HSSFWorkbook xwb) {
		StringBuffer fileName = new StringBuffer();
		fileName.append("bhi_");
		fileName.append(startTime);
		fileName.append("_");
		fileName.append(endTime);
		fileName.append(".xls");
		String path = null;
		try {
			path = ExcelMakerXls.class.getClassLoader().getResource("").toURI()
					.getPath();
			path = path.substring(1, path.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		String filePath = path + "temp/";

		System.out.println(filePath);
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println("执行");
			file.mkdirs();
		}
		filePath = filePath + fileName.toString();
		try {
			FileOutputStream fileOut = new FileOutputStream(filePath);
			xwb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filePath;

	}

	private boolean bhiProMaker(String startTime, String endTime,
			HSSFWorkbook xwb) {
		List<BhiProWithBLOBs> bhiPros = new BhiProReader().readBhiProReader(
				startTime, endTime);
		if (bhiPros.size() == 0) {
			return false;
		}
		makeBhiproExcel(xwb, bhiPros);
		makeOwnerExcel(bhiPros, xwb);
		makeDesignExcel(bhiPros, xwb);
		makeConstructionExcel(bhiPros, xwb);
		makeEnvironmentalAssessmentExcel(bhiPros, xwb);
		makeFeasibilityStudyExcel(bhiPros, xwb);
		makePlantExcel(bhiPros, xwb);
		makePrimaryDesignExcel(bhiPros, xwb);
		makeSurveyExcel(bhiPros, xwb);
		// makeOtherExcel(bhiPros, xwb);
		return true;
	}

	private void makeSurveyExcel(List<BhiProWithBLOBs> bhiPros, HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_survey");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "survey_department",
				"survey_people", "survey_people_job", "survey_tel",
				"survey_fax", "survey_postcode", "survey_detail_address",
				"survey_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiSurvey> bhiSurveys = new BhiSurveyReader()
				.readBhiSurvey(values);
		for (int i = 0; i < bhiSurveys.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiSurveys.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiSurveys.get(i).getSurveyDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiSurveys.get(i).getSurveyPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiSurveys.get(i).getSurveyPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiSurveys.get(i).getSurveyTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiSurveys.get(i).getSurveyFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiSurveys.get(i).getSurveyPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiSurveys.get(i).getSurveyDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiSurveys.get(i).getSurveyWeburl());
		}
	}

	private void makePrimaryDesignExcel(List<BhiProWithBLOBs> bhiPros,
			HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_primary_design");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "primary_design_department",
				"primary_design_people", "primary_design_people_job",
				"primary_design_tel", "primary_design_fax",
				"primary_design_postcode", "primary_design_detail_address",
				"primary_design_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiPrimaryDesign> bhiPrimaryDesigns = new BhiPrimaryDesignReader()
				.readBhiPrimaryDesign(values);
		for (int i = 0; i < bhiPrimaryDesigns.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiPrimaryDesigns.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiPrimaryDesigns.get(i)
					.getPrimaryDesignDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiPrimaryDesigns.get(i).getPrimaryDesignPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiPrimaryDesigns.get(i)
					.getPrimaryDesignPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiPrimaryDesigns.get(i).getPrimaryDesignTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiPrimaryDesigns.get(i).getPrimaryDesignFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiPrimaryDesigns.get(i)
					.getPrimaryDesignPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiPrimaryDesigns.get(i)
					.getPrimaryDesignDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiPrimaryDesigns.get(i).getPrimaryDesignWeburl());
		}

	}

	private void makePlantExcel(List<BhiProWithBLOBs> bhiPros, HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_plant");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "plant_department",
				"plant_people", "plant_people_job", "plant_tel", "plant_fax",
				"plant_postcode", "plant_detail_address", "plant_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiPlant> bhiPlants = new BhiPlantReader().readBhiPlant(values);
		for (int i = 0; i < bhiPlants.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiPlants.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiPlants.get(i).getPlantDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiPlants.get(i).getPlantPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiPlants.get(i).getPlantPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiPlants.get(i).getPlantTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiPlants.get(i).getPlantFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiPlants.get(i).getPlantPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiPlants.get(i).getPlantDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiPlants.get(i).getPlantWeburl());
		}

	}

	private void makeFeasibilityStudyExcel(List<BhiProWithBLOBs> bhiPros,
			HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_feasibility_study");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "feasibility_study_department",
				"feasibility_study_people", "feasibility_study_people_job",
				"feasibility_study_tel", "feasibility_study_fax",
				"feasibility_study_postcode",
				"feasibility_study_detail_address", "feasibility_study_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiFeasibilityStudy> bhiFeasibilityStudys = new BhiFeasibilityStudyReader()
				.readBhiFeasibilityStudy(values);
		for (int i = 0; i < bhiFeasibilityStudys.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiFeasibilityStudys.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiFeasibilityStudys.get(i)
					.getFeasibilityStudyWeburl());
		}

	}

	private void makeEnvironmentalAssessmentExcel(
			List<BhiProWithBLOBs> bhiPros, HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_environmental_assessment");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id",
				"environmental_assessment_department",
				"environmental_assessment_people",
				"environmental_assessment_people_job",
				"environmental_assessment_tel", "environmental_assessment_fax",
				"environmental_assessment_postcode",
				"environmental_assessment_detail_address",
				"environmental_assessment_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiEnvironmentalAssessment> bhiEnvironmentalAssessments = new BhiEnvironmentalAssessmentReader()
				.readBhiEnvironmentalAssessment(values);
		for (int i = 0; i < bhiEnvironmentalAssessments.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiEnvironmentalAssessments.get(i)
					.getEnvironmentalAssessmentWeburl());
		}

	}

	private void makeConstructionExcel(List<BhiProWithBLOBs> bhiPros,
			HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_construction");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "construction_department",
				"construction_people", "construction_people_job",
				"construction_tel", "construction_fax",
				"construction_postcode", "construction_detail_address",
				"construction_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiConstruction> bhiConstructions = new BhiConstructionReader()
				.readBhiConstruction(values);
		for (int i = 0; i < bhiConstructions.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiConstructions.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiConstructions.get(i)
					.getConstructionDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiConstructions.get(i).getConstructionPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiConstructions.get(i)
					.getConstructionPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiConstructions.get(i).getConstructionTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiConstructions.get(i).getConstructionFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiConstructions.get(i).getConstructionPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiConstructions.get(i)
					.getConstructionDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiConstructions.get(i).getConstructionWeburl());
		}

	}

	private void makeDesignExcel(List<BhiProWithBLOBs> bhiPros, HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_design");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "design_department",
				"design_people", "design_people_job", "design_tel",
				"design_fax", "design_postcode", "design_detail_address",
				"design_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiDesign> bhiDesigns = new BhiDesignReader()
				.readBhiDesign(values);
		for (int i = 0; i < bhiDesigns.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiDesigns.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiDesigns.get(i).getDesignDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiDesigns.get(i).getDesignPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiDesigns.get(i).getDesignPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiDesigns.get(i).getDesignTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiDesigns.get(i).getDesignFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiDesigns.get(i).getDesignPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiDesigns.get(i).getDesignDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiDesigns.get(i).getDesignWeburl());
		}
	}

	private void makeOwnerExcel(List<BhiProWithBLOBs> bhiPros, HSSFWorkbook xwb) {
		HSSFSheet sheet = xwb.createSheet("bhi_owner");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "owner_department",
				"owner_people", "owner_people_job", "owner_tel", "owner_fax",
				"owner_postcode", "owner_detail_address", "owner_weburl" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		List<Integer> values = new ArrayList<Integer>();
		for (BhiProWithBLOBs bhiProWithBLOBs : bhiPros) {
			values.add(bhiProWithBLOBs.getId());
		}
		List<BhiOwner> bhiOwners = new BhiOwnerReader().readBhiOwner(values);
		for (int i = 0; i < bhiOwners.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiOwners.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiOwners.get(i).getOwnerDepartment());
			cell = row.createCell(2);
			cell.setCellValue(bhiOwners.get(i).getOwnerPeople());
			cell = row.createCell(3);
			cell.setCellValue(bhiOwners.get(i).getOwnerPeopleJob());
			cell = row.createCell(4);
			cell.setCellValue(bhiOwners.get(i).getOwnerTel());
			cell = row.createCell(5);
			cell.setCellValue(bhiOwners.get(i).getOwnerFax());
			cell = row.createCell(6);
			cell.setCellValue(bhiOwners.get(i).getOwnerPostcode());
			cell = row.createCell(7);
			cell.setCellValue(bhiOwners.get(i).getOwnerDetailAddress());
			cell = row.createCell(8);
			cell.setCellValue(bhiOwners.get(i).getOwnerWeburl());
		}
	}

	private void makeBhiproExcel(HSSFWorkbook xwb, List<BhiProWithBLOBs> bhiPros) {
		// 读取第一章表格内容
		HSSFSheet sheet = xwb.createSheet("bhi_pro");
		// 定义 row、cell
		HSSFRow row;
		row = sheet.createRow(0);
		String[] rows = new String[] { "id", "pro_name", "pro_area",
				"pro_time", "pro_nature", "firm_nature", "pro_trade",
				"pro_assets", "pro_stage", "pro_way", "pro_office",
				"pro_cycle", "pro_fund", "equipment_source", "governing_unit",
				"address", "pro_facility", "pro_content", "pro_intro",
				"weburl", "type" };
		for (int i = 0; i < rows.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(rows[i]);
		}
		for (int i = 0; i < bhiPros.size(); i++) {
			row = sheet.createRow(i + 1);
			Cell cell = row.createCell(0);
			cell.setCellValue(bhiPros.get(i).getId());
			cell = row.createCell(1);
			cell.setCellValue(bhiPros.get(i).getProName());
			cell = row.createCell(2);
			cell.setCellValue(bhiPros.get(i).getProArea());
			cell = row.createCell(3);
			cell.setCellValue(SimpleTools.dateToString(bhiPros.get(i)
					.getProTime(), "yyyy-MM-dd"));
			cell = row.createCell(4);
			cell.setCellValue(bhiPros.get(i).getProNature());
			cell = row.createCell(5);
			cell.setCellValue(bhiPros.get(i).getFirmNature());
			cell = row.createCell(6);
			cell.setCellValue(bhiPros.get(i).getProTrade());
			cell = row.createCell(7);
			cell.setCellValue(bhiPros.get(i).getProAssets());
			cell = row.createCell(8);
			cell.setCellValue(bhiPros.get(i).getProStage());
			cell = row.createCell(9);
			cell.setCellValue(bhiPros.get(i).getProWay());
			cell = row.createCell(10);
			cell.setCellValue(bhiPros.get(i).getProOffice());
			cell = row.createCell(11);
			cell.setCellValue(bhiPros.get(i).getProCycle());
			cell = row.createCell(12);
			cell.setCellValue(bhiPros.get(i).getProFund());
			cell = row.createCell(13);
			cell.setCellValue(bhiPros.get(i).getEquipmentSource());
			cell = row.createCell(14);
			cell.setCellValue(bhiPros.get(i).getGoverningUnit());
			cell = row.createCell(15);
			cell.setCellValue(bhiPros.get(i).getAddress());
			cell = row.createCell(16);
			cell.setCellValue(bhiPros.get(i).getProFacility());
			cell = row.createCell(17);
			cell.setCellValue(bhiPros.get(i).getProContent());
			cell = row.createCell(18);
			cell.setCellValue(bhiPros.get(i).getProIntro());
			cell = row.createCell(19);
			cell.setCellValue(bhiPros.get(i).getWeburl());
			cell = row.createCell(20);
			cell.setCellValue(bhiPros.get(i).getType());
		}
	}

}
