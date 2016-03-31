package com.unbank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.unbank.sender.MailSender;
import com.unbank.sender.MailSenderInfo;
import com.unbank.tools.Values;

public class Bulu {
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext(
				new String[] { "applicationContext.xml" });
		String startTime = "2016-01-15";
		String endTime = "2016-01-15";
		String fileName = "bhi_2016-01-15_2015-01-15.xls";
		// String startTime = SimpleTools.getyyyyMMddTimeString(new Date(), -1);
		// String endTime = SimpleTools.getyyyyMMddTimeString(new Date(), -1);
		new Bulu().makeExcel(startTime, endTime,
				"E:\\crawlWorkspace2015\\ubk_bhi_spider\\bin\\temp\\"
						+ fileName);

	}

	private void makeExcel(String startTime, String endTime, String fileName) {
		// TODO Auto-generated method stub
		MailSenderInfo mailInfo = fillMailInfo(fileName, startTime, endTime);
		MailSender sms = new MailSender();
		sms.sendHtmlMail(mailInfo);
	}

	private MailSenderInfo fillMailInfo(String fileName, String startTime,
			String endTime) {
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
		String subject = startTime + Values.v.SUBJECT;
		String content = startTime + Values.v.CONTENT;
		mailInfo.setSubject(subject);
		mailInfo.setContent(content);
		String[] files = { fileName };
		mailInfo.setAttachFileNames(files);
		return mailInfo;
	}

}
