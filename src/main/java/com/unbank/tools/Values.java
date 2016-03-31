package com.unbank.tools;

public class Values {
	// 图片服务器地址
	public static Values v;

	public String SERVERHOST;
	public String SERVERPORT;
	public String USERNAME;
	public String PASSWORD;
	public String FROMADDRESS;
	public String RECEIVER;
	public String SUBJECT;
	public String CONTENT;
	public String PROXYIP;
	public String PROXYPORT;

	public void init() {
		v = this;
		v.SERVERHOST = this.SERVERHOST;
		v.SERVERPORT = this.SERVERPORT;
		v.USERNAME = this.USERNAME;
		v.PASSWORD = this.PASSWORD;
		v.FROMADDRESS = this.FROMADDRESS;
		v.RECEIVER = this.RECEIVER;
		v.SUBJECT = this.SUBJECT;
		v.CONTENT = this.CONTENT;
		v.PROXYPORT = this.PROXYPORT;
		v.PROXYIP = this.PROXYIP;

	}

	public static Values getV() {
		return v;
	}

	public static void setV(Values v) {
		Values.v = v;
	}

	public String getSERVERHOST() {
		return SERVERHOST;
	}

	public void setSERVERHOST(String sERVERHOST) {
		SERVERHOST = sERVERHOST;
	}

	public String getSERVERPORT() {
		return SERVERPORT;
	}

	public void setSERVERPORT(String sERVERPORT) {
		SERVERPORT = sERVERPORT;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getFROMADDRESS() {
		return FROMADDRESS;
	}

	public void setFROMADDRESS(String fROMADDRESS) {
		FROMADDRESS = fROMADDRESS;
	}

	public String getRECEIVER() {
		return RECEIVER;
	}

	public void setRECEIVER(String rECEIVER) {
		RECEIVER = rECEIVER;
	}

	public String getSUBJECT() {
		return SUBJECT;
	}

	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}

	public String getCONTENT() {
		return CONTENT;
	}

	public void setCONTENT(String cONTENT) {
		CONTENT = cONTENT;
	}

	public String getPROXYIP() {
		return PROXYIP;
	}

	public void setPROXYIP(String pROXYIP) {
		PROXYIP = pROXYIP;
	}

	public String getPROXYPORT() {
		return PROXYPORT;
	}

	public void setPROXYPORT(String pROXYPORT) {
		PROXYPORT = pROXYPORT;
	}

}
