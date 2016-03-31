package com.unbank.paser.entity;

import java.util.Map;

public class WebBean {
	private String webUrl;
	private String crawlType;
	private String bodyXPath;
	private String cloumXpath;
	private String detailurlXpath;
	private String nextPageXpath;
	private String tableName;
	private String compareAtrr;
	private Map<String, String> detailAtrr;

	public WebBean(String webUrl, String crawlType, String bodyXPath,
			String cloumXpath, String detailurlXpath, String nextPageXpath,
			Map<String, String> detailAtrr, String tableName, String compareAtrr) {
		this.webUrl = webUrl;
		this.crawlType = crawlType;
		this.bodyXPath = bodyXPath;
		this.cloumXpath = cloumXpath;
		this.detailurlXpath = detailurlXpath;
		this.nextPageXpath = nextPageXpath;
		this.detailAtrr = detailAtrr;
		this.tableName = tableName;
		this.compareAtrr = compareAtrr;
	}

	public WebBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getCrawlType() {
		return crawlType;
	}

	public void setCrawlType(String crawlType) {
		this.crawlType = crawlType;
	}

	public String getBodyXPath() {
		return bodyXPath;
	}

	public void setBodyXPath(String bodyXPath) {
		this.bodyXPath = bodyXPath;
	}

	public String getDetailurlXpath() {
		return detailurlXpath;
	}

	public void setDetailurlXpath(String detailurlXpath) {
		this.detailurlXpath = detailurlXpath;
	}

	public String getNextPageXpath() {
		return nextPageXpath;
	}

	public void setNextPageXpath(String nextPageXpath) {
		this.nextPageXpath = nextPageXpath;
	}

	public Map<String, String> getDetailAtrr() {
		return detailAtrr;
	}

	public void setDetailAtrr(Map<String, String> detailAtrr) {
		this.detailAtrr = detailAtrr;
	}

	public String getCloumXpath() {
		return cloumXpath;
	}

	public void setCloumXpath(String cloumXpath) {
		this.cloumXpath = cloumXpath;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCompareAtrr() {
		return compareAtrr;
	}

	public void setCompareAtrr(String compareAtrr) {
		this.compareAtrr = compareAtrr;
	}

}
