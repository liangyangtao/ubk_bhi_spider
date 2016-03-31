package com.unbank.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.quartz.task.BhiSpider;
import com.unbank.tools.SimpleTools;

public class StartBhiSpiderQuartzJobBean {
	private static Log logger = LogFactory
			.getLog(StartBhiSpiderQuartzJobBean.class);

//	public static void main(String[] args) {
//		new StartBhiSpiderQuartzJobBean().executeInternal();
//	}

	public void executeInternal() {
		try {
			logger.info("重新启动定时任务");
			String startTime = SimpleTools.getyyyyMMddTimeString(new Date(), 0);
			String endTime = SimpleTools.getyyyyMMddTimeString(new Date(), 0);
			new BhiSpider().BHIConsole(startTime, endTime);
		} catch (Exception e) {
			logger.info("检测内容节点出错", e);
		}
	}

}
