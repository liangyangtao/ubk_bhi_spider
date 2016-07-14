package com.unbank.paser.detail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.unbank.dao.BhiProDataStore;
import com.unbank.dao.BhiProStore;
import com.unbank.dao.WebBeanStore;
import com.unbank.mybatis.vo.BhiProDataWithBLOBs;
import com.unbank.mybatis.vo.BhiProWithBLOBs;
import com.unbank.paser.entity.WebBean;
import com.unbank.tools.SimpleTools;

public class BHIDetailPaser {
	// "#mylogproject > h1:nth-child(1)").first();
	public void analyzerPaper(String html, String url) {
		Map<String, Object> columns = analyzerPapercs(html, url,
				".vipShow > h1:nth-child(1)", 0);
	}

	// "#myvip > h1:nth-child(1)"
	public void analyzerVipPaper(String html, String url) {
		analyzerPapercs(html, url, ".vipShow > h1:nth-child(1)", 1);
	}

	public static void main(String[] args) {
		Document document = null;
		try {
			document = Jsoup.parse(new File(
					"C:\\Users\\Administrator\\Desktop\\vip2.html"), "gbk");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, Object> columns = new BHIDetailPaser().analyzerPapercs(
				document.toString(), "", ".vipShow > h1:nth-child(1)", 1);
		Set<String> keyset = columns.keySet();
		for (String string : keyset) {
			System.out.println(string + "================"
					+ columns.get(string));
		}

	}

	public Map<String, Object> analyzerPapercs(String html, String url,
			String proNameCss, int type) {
		Map<String, Object> columns = new HashMap<String, Object>();
		Document document = Jsoup.parse(html, url);
		// 项目概况
		int bhiId = fillinfoTable(document, proNameCss, type, url);
		// 业主及其联系方式
		Elements parentElement = document.select(".evod").first()
				.select("[colspan=4]");
		List<Integer> divingLine = new ArrayList<Integer>();
		for (Element element : parentElement) {
			Element elementprent = element.parent();
			divingLine.add(elementprent.elementSiblingIndex());
		}
		// 改版后由第三个开始
		for (int i = 3; i < divingLine.size(); i++) {
			int preIndex = divingLine.get(i);
			System.out.println(preIndex + "=============");
			int j = i + 1;
			int index = 0;
			if (j < divingLine.size()) {
				index = divingLine.get(i + 1);
			} else {
				// 如果只有一个
				index = divingLine.get(i);
			}
			fillPesonTables(document, preIndex, index, bhiId);
		}
		return columns;

	}

	private void fillPesonTables(Document document, int preIndex, int index,
			int bhiId) {
		Elements elements = document.select(".evod > tbody:nth-child(1) > tr");
		String peopleType = elements.get(preIndex).text().trim();
		StringBuffer tableName = new StringBuffer();
		Map<String, Object> columns = new HashMap<String, Object>();
		tableName.append("bhi_");
		String type = "";
		if (peopleType.equals("业主方及其联系方式")) {
			type = "owner";
			tableName.append("owner");
		} else if (peopleType.equals("设计单位及其联系方式")) {
			type = "design";
			tableName.append("design");

		} else if (peopleType.equals("可研单位及其联系方式")) {
			type = "feasibility_study";
			tableName.append("feasibility_study");
		} else if (peopleType.equals("初步设计单位及其联系方式")) {
			type = "primary_design";
			tableName.append("primary_design");
		} else if (peopleType.equals("勘察设计单位及其联系方式")) {
			type = "survey";
			tableName.append("survey");
		} else if (peopleType.equals("规划设计单位及其联系方式")) {
			type = "plant";
			tableName.append("plant");
		} else if (peopleType.equals("施工方及其联系方式")) {
			type = "construction";
			tableName.append("construction");
		} else if (peopleType.equals("环境评价单位及其联系方式")) {
			type = "environmental_assessment";
			tableName.append("environmental_assessment");
		} else {
			type = "other";
			tableName.append("other");
		}
		// 确定表名称，和单位
		Element departmentElement = document.select(
				".evod > tbody:nth-child(1) > tr:nth-child(" + (preIndex + 2)
						+ ") > td:nth-child(2)").first();

		if (departmentElement != null) {
			String department_text = departmentElement.text().trim();
			department_text = department_text.replace("近期参与项目", "");
			columns.put(type + "_department", department_text);
		}
		// 详细地址和 邮编
		Element desin_postcode_element = null;
		Element desin_detail_address_element = null;
		// 第二行
		Element element = elements.get(preIndex + 2);
		Elements bElement = element.select("b");
		if (bElement.text().trim().contains("邮编")) {
			desin_postcode_element = element.select("td").get(3);
		}
		if (bElement.text().trim().contains("详细地址")) {
			desin_detail_address_element = element.select("td").get(1);
		}
		// 邮编
		if (desin_postcode_element != null) {
			String postcode_text = desin_postcode_element.text();
			columns.put(type + "_postcode", postcode_text);
		}
		// 详细地址
		if (desin_detail_address_element != null) {
			String detail_address_text = desin_detail_address_element.text();
			columns.put(type + "_detail_address", detail_address_text);
		}
		// 网址
		// .evod > tbody:nth-child(1) > tr:nth-child(36)
		Element desin_webUrl = null;
		Element desin_webUrl_element = elements.get(preIndex + 3);
		Elements desinWebUrlBElement = desin_webUrl_element.select("b");
		// <b>网<em></em>址</b>
		if (desinWebUrlBElement.text().trim().contains("网址")) {
			desin_webUrl = desin_webUrl_element.select("td").get(1);
			if (desin_webUrl != null) {
				String desin_webUrl_text = desin_webUrl.text().trim();
				columns.put(type + "_weburl", desin_webUrl_text);
			}

		}
		// 联系人信息 、如果有网址，则从第4个开始，如果没有则第三个开始
		int i = 0;
		int end = 0;
		if (desin_webUrl == null) {
			i = preIndex + 4;
		} else {
			i = preIndex + 5;
		}
		if (index > preIndex) {
			end = index;
		} else {
			end = elements.size();
		}
		for (; i < end;) {
			Elements pro_people_elements = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(" + i
							+ ") > td:nth-child(2)");
			String pro_people_text = "";
			if (pro_people_elements.size() > 0) {
				Element pro_people_element = pro_people_elements.first();
				pro_people_text = pro_people_element.text();
				columns.put(
						type + "_people",
						(columns.get(type + "_people") == null ? "" : columns
								.get(type + "_people") + "|")
								+ pro_people_text);
			}

			Elements pro_people_job_elements = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(" + i
							+ ") > td:nth-child(4)");

			if (pro_people_job_elements.size() > 0) {
				Element pro_people_job_element = pro_people_job_elements
						.first();
				String pro_people_job_text = pro_people_job_element.text();
				columns.put(type + "_people_job",
						(columns.get(type + "_people_job") == null ? ""
								: columns.get(type + "_people_job") + "|")
								+ pro_people_text + "—" + pro_people_job_text);
			}
			// 电话
			Elements pro_tel_elements = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child("
							+ (i + 1) + ") > td:nth-child(2)");
			if (pro_tel_elements.size() > 0) {
				Element pro_tel_element = pro_tel_elements.first();
				String pro_tel_text = pro_tel_element.text();
				if (!pro_tel_text.trim().isEmpty()) {
					columns.put(
							type + "_tel",
							(columns.get(type + "_tel") == null ? "" : columns
									.get(type + "_tel") + "|")
									+ pro_people_text + "—" + pro_tel_text);
				}

			}

			// 传真
			Elements fax_elements = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child("
							+ (i + 1) + ") > td:nth-child(4)");
			if (fax_elements.size() > 0) {
				Element fax_element = fax_elements.first();
				String fax_text = fax_element.text();
				if (!fax_text.trim().isEmpty()) {
					columns.put(
							type + "_fax",
							(columns.get(type + "_fax") == null ? "" : columns
									.get(type + "_fax") + "|")
									+ pro_people_text + "—" + fax_text);
				}

			}

			i += 2;
		}

		WebBean webBean = new WebBean();
		webBean.setTableName(tableName.toString());
		columns.put("id", bhiId);
		new WebBeanStore().saveWebBean(columns, webBean);

	}

	// 项目概况
	private int fillinfoTable(Document document, String proNameCss, int type,
			String url) {
		BhiProWithBLOBs bhiPro = new BhiProWithBLOBs();
		BhiProDataWithBLOBs bhiProData = new BhiProDataWithBLOBs();
		Map<String, Object> columns = new HashMap<String, Object>();
		// 项目名称
		Element pro_name_element = document.select(proNameCss).first();
		String pro_name_text = pro_name_element.text();
		columns.put("pro_name", pro_name_text.trim());
		bhiPro.setProName(pro_name_text.trim());
		bhiProData.setProName(pro_name_text.trim());
		// 地区
		Element pro_area_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(2)")
				.first();
		String pro_area_text = pro_area_element.text();
		columns.put("pro_area", pro_area_text.trim());
		bhiPro.setProArea(pro_area_text.trim());
		bhiProData.setProArea(pro_area_text.trim());
		// 发布时间
		Element pro_time_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(4)")
				.first();
		String pro_time_text = pro_time_element.text();

		columns.put("pro_time", SimpleTools.stringToDate(pro_time_text));
		bhiPro.setProTime(SimpleTools.stringToDate(pro_time_text));
		bhiProData.setProTime(SimpleTools.stringToDate(pro_time_text));
		// 项目性质
		Element pro_nature_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(4)")
				.first();
		String pro_nature_text = pro_nature_element.text();
		columns.put("pro_nature", pro_nature_text.trim());
		bhiPro.setProNature(pro_nature_text.trim());
		bhiProData.setProNature(pro_nature_text.trim());
		// 企业性质
		Element firm_nature_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(7) > td:nth-child(4)")
				.first();
		String firm_nature_text = firm_nature_element.text();
		columns.put("firm_nature", firm_nature_text.trim());
		bhiPro.setFirmNature(firm_nature_text.trim());
		bhiProData.setFirmNature(firm_nature_text.trim());
		// 行业
		Element pro_trade_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(2)")
				.first();
		String pro_trade_text = pro_trade_element.text();
		columns.put("pro_trade", pro_trade_text.trim());
		bhiPro.setProTrade(pro_trade_text.trim());
		bhiProData.setProTrade(pro_trade_text.trim());
		// 投资总额
		Element pro_assets_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(2)")
				.first();
		String pro_assets_text = pro_assets_element.text();
		columns.put("pro_assets", pro_assets_text.trim());
		bhiPro.setProAssets(pro_assets_text.trim());
		bhiProData.setProAssets(pro_assets_text.trim());
		String proassets = pro_assets_text.trim();

		String proDatavalue = getNumber(proassets);
		String proDataunit = proassets.replace(proDatavalue, "");
		bhiProData.setProDatavalue(proDatavalue);

		bhiProData.setProDataunit(proDataunit);
		// 进展阶段

		Element pro_stage_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(5) > td:nth-child(2)")
				.first();
		String pro_stage_text = pro_stage_element.text();
		columns.put("pro_stage", pro_stage_text.trim());
		bhiPro.setProStage(pro_stage_text.trim());
		bhiProData.setProStage(pro_stage_text.trim());
		// 申报方式
		Element pro_way_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(6) > td:nth-child(2)")
				.first();
		String pro_way_text = pro_way_element.text();
		columns.put("pro_way", pro_way_text.trim());
		bhiPro.setProWay(pro_way_text.trim());
		bhiProData.setProWay(pro_way_text.trim());
		// 审批机关
		Element pro_office_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(6) > td:nth-child(4)")
				.first();
		String pro_office_text = pro_office_element.text();
		columns.put("pro_office", pro_office_text.trim());
		bhiPro.setProOffice(pro_office_text.trim());
		bhiProData.setProOffice(pro_office_text.trim());
		// 建设周期
		Element pro_cycle_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(3) > td:nth-child(4)")
				.first();
		String pro_cycle_text = pro_cycle_element.text();
		columns.put("pro_cycle", pro_cycle_text.trim());
		bhiPro.setProCycle(pro_cycle_text.trim());
		bhiProData.setProCycle(pro_cycle_text.trim());
		// 资金到位
		Element pro_fund_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(4) > td:nth-child(4)")
				.first();
		String pro_fund_text = pro_fund_element.text();
		columns.put("pro_fund", pro_fund_text.trim());
		bhiPro.setProFund(pro_fund_text.trim());
		bhiProData.setProFund(pro_fund_text.trim());
		// 设备来源
		Element equipment_source_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(7) > td:nth-child(2)")
				.first();
		String equipment_source_text = equipment_source_element.text();
		columns.put("equipment_source", equipment_source_text.trim());
		bhiPro.setEquipmentSource(equipment_source_text.trim());
		bhiProData.setEquipmentSource(equipment_source_text.trim());
		// 主管单位
		Element governing_unit_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(8) > td:nth-child(2)")
				.first();
		String governing_unit_text = governing_unit_element.text();
		columns.put("governing_unit", governing_unit_text.trim());
		bhiPro.setGoverningUnit(governing_unit_text.trim());
		bhiProData.setGoverningUnit(governing_unit_text.trim());
		// 所在地
		// .evod > tbody:nth-child(1) > tr:nth-child(9) > td:nth-child(2)
		Element address_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(9) > td:nth-child(2)")
				.first();
		String address_text = address_element.text();
		columns.put("address", address_text.trim());
		bhiPro.setAddress(address_text.trim());
		bhiProData.setAddress(address_text.trim());
		// 主要设备
		Element pro_facility_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(10) > td:nth-child(2)")
				.first();
		String pro_facility_text = pro_facility_element.text();
		columns.put("pro_facility", pro_facility_text.trim());
		bhiPro.setProFacility(pro_facility_text.trim());
		bhiProData.setProFacility(pro_facility_text.trim());

		// 建设内容
		Element pro_content_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(11) > td:nth-child(2)")
				.first();
		String pro_content_text = pro_content_element.text();
		columns.put("pro_content", pro_content_text.trim());
		bhiPro.setProContent(pro_content_text.trim());
		bhiProData.setProContent(pro_content_text.trim());

		Elements elements = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(13)");
		Element pro_intro_element = null;
		pro_intro_element = elements.last().select("td").first();
		String pro_intro_text = pro_intro_element.text().trim();
		bhiPro.setProIntro(pro_intro_text.trim());
		columns.put("pro_intro", pro_intro_text.trim());

		// emall
		bhiPro.setWeburl(document.baseUri());
		bhiProData.setWeburl(document.baseUri());
		bhiPro.setType((byte) type);
		bhiProData.setType((byte) type);
		// .evod > tbody:nth-child(1) > tr:nth-child(15) > td:nth-child(2) >
		// a:nth-child(1)
		Element pro_department_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(15) > td:nth-child(2)")
				.first();
		String pro_department = pro_department_element.text();
		pro_department = pro_department.replace("近期参与项目", "");
		columns.put("pro_department", pro_department.trim());

		Element detail_address_Element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(16) > td:nth-child(2)")
				.first();
		String detail_address = detail_address_Element.text();
		columns.put("detail_address", detail_address.trim());
		Element postcode_element = document
				.select(".evod > tbody:nth-child(1) > tr:nth-child(16) > td:nth-child(4)")
				.first();
		String postcode = postcode_element.text();
		columns.put("postcode", postcode.trim());
		Element pro_people_element = null;
		Element pro_tel_element = null;

		Element temp_element = document.select(
				".evod > tbody:nth-child(1) > tr:nth-child(17)").first();
		if (temp_element.text().trim().contains("网址")) {
			pro_people_element = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(18) > td:nth-child(2)")
					.first();
			pro_tel_element = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(19) > td:nth-child(2)")
					.first();
		} else {
			pro_people_element = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(17) > td:nth-child(2)")
					.first();
			pro_tel_element = document
					.select(".evod > tbody:nth-child(1) > tr:nth-child(18) > td:nth-child(2)")
					.first();
		}
		String pro_people = pro_people_element.text();
		columns.put("pro_people", pro_people.trim());

		String pro_tel = pro_tel_element.text();
		columns.put("pro_tel", pro_tel.trim());

		columns.put("weburl", document.baseUri());
		columns.put("type", type);
		WebBean webBean = new WebBean();
		webBean.setTableName("grab_bhi");
		new WebBeanStore().saveWebBean(columns, webBean);

		new BhiProDataStore().saveBhiPro(bhiProData);
		return new BhiProStore().saveBhiPro(bhiPro);

	}

	public static String getNumber(String a) {
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(a);
		return m.replaceAll("").trim();
	}
}
