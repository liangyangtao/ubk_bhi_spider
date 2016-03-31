package com.unbank.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.unbank.dao.BhiProReader;
import com.unbank.mybatis.vo.BhiProWithBLOBs;

public class WeekReport {
	private static Log logger = LogFactory.getLog(WeekReport.class);
	static {
		// 启动日志
		try {
			PropertyConfigurator.configure(WeekReport.class.getClassLoader()
					.getResource("").toURI().getPath()
					+ "log4j.properties");
			logger.info("---日志系统启动成功---");
		} catch (Exception e) {
			logger.error("日志系统启动失败:", e);
		}
	}

	public static void main(String[] args) {
		new WeekReport().makeWord();
	}

	public void makeWord() {

		// SELECT count(1) as num from bhi_pro where pro_time >='2015-08-18' and
		// pro_time <='2015-08-19';
		//
		// SELECT pro_area ,count(1) as num from bhi_pro where pro_time
		// >='2015-08-18' and pro_time <='22015-08-19' GROUP BY pro_area;
		//
		//
		// SELECT pro_trade,count(1) as num from bhi_pro where pro_time
		// >='2015-08-18' and pro_time <='22015-08-19' GROUP BY pro_trade;
		//
		// SELECT pro_area,pro_trade, count(1) as num from bhi_pro where
		// pro_time >='2015-08-18' and pro_time <='22015-08-19' GROUP BY
		// pro_area,pro_trade;

		String startTime = "2015-11-16";
		String endTime = "2015-11-20";
		List<BhiProWithBLOBs> bhiProWithBLOBs = new BhiProReader()
				.readBhiProReader(startTime, endTime);
		// 地域
		Map<String, Integer> area_nums = new HashMap<String, Integer>();
		Set<String> areaSet = area_nums.keySet();

		// 行业
		Map<String, Integer> trade_nums = new HashMap<String, Integer>();
		Set<String> tradeSet = trade_nums.keySet();

		// 地域加含有
		Map<String, Integer> area_trade_nums = new HashMap<String, Integer>();
		Set<String> areatradeSet = area_trade_nums.keySet();

		for (BhiProWithBLOBs bhiProWithBLOBs2 : bhiProWithBLOBs) {
			// 地域
			String area = bhiProWithBLOBs2.getProArea().trim();
			if (areaSet.contains(area)) {
				area_nums.put(area, area_nums.get(area) + 1);
			} else {
				area_nums.put(area, 1);
			}
			// hanye
			String trade = bhiProWithBLOBs2.getProTrade().trim();

			if (tradeSet.contains(trade)) {
				trade_nums.put(trade, trade_nums.get(trade) + 1);
			} else {
				trade_nums.put(trade, 1);
			}
			// 地域 加hanye
			if (areatradeSet.contains(area + "&&&" + trade)) {
				area_trade_nums.put(area + "&&&" + trade,
						area_trade_nums.get(area + "&&&" + trade) + 1);
			} else {
				area_trade_nums.put(area + "&&&" + trade, 1);
			}
		}
		List<Entry<String, Integer>> areaNumList = new ArrayList<Map.Entry<String, Integer>>(
				area_nums.entrySet());

		List<Entry<String, Integer>> tradeNumList = new ArrayList<Map.Entry<String, Integer>>(
				trade_nums.entrySet());
		List<Entry<String, Integer>> area_trade_NumList = new ArrayList<Map.Entry<String, Integer>>(
				area_trade_nums.entrySet());

		Collections.sort(areaNumList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> mapping1,
							Map.Entry<String, Integer> mapping2) {
						return mapping2.getValue().compareTo(
								mapping1.getValue());
					}
				});
		Collections.sort(tradeNumList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> mapping1,
							Map.Entry<String, Integer> mapping2) {
						return mapping2.getValue().compareTo(
								mapping1.getValue());
					}
				});
		Collections.sort(area_trade_NumList,
				new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> mapping1,
							Map.Entry<String, Integer> mapping2) {
						return mapping2.getValue().compareTo(
								mapping1.getValue());
					}
				});
		for (Map.Entry<String, Integer> mapping : areaNumList) {
			System.out.println(mapping.getKey() + ":" + mapping.getValue());
		}
		for (Map.Entry<String, Integer> mapping : tradeNumList) {
			System.out.println(mapping.getKey() + ":" + mapping.getValue());
		}
		for (Map.Entry<String, Integer> mapping : area_trade_NumList) {
			System.out.println(mapping.getKey() + ":" + mapping.getValue());
		}

	}

}
