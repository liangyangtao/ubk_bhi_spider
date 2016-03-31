package com.unbank.quartz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unbank.quartz.task.ExcelMakerXls;
import com.unbank.tools.SimpleTools;

public class StartBhiMkXlsQuartzJobBean {
	private static Log logger = LogFactory
			.getLog(StartBhiMkXlsQuartzJobBean.class);

	public void executeInternal() {
		try {
			logger.info("重新启动定时任务");
			String startTime = SimpleTools
					.getyyyyMMddTimeString(new Date(), -1);
			String endTime = SimpleTools.getyyyyMMddTimeString(new Date(), -1);
			new ExcelMakerXls().makeExcel(startTime, endTime);
		} catch (Exception e) {
			logger.info("检测内容节点出错", e);
		}
	}

}
