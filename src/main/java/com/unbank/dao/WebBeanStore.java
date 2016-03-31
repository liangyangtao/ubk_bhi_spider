package com.unbank.dao;

import java.util.Map;

import com.unbank.paser.entity.WebBean;

public class WebBeanStore extends BaseDao {

	public void saveWebBean(Map<String, Object> colums, WebBean webBean) {
		if (colums == null || colums.size() == 0) {
			return;
		}
		String sql = "insert into " + webBean.getTableName();
		executeMapSQL(sql, colums);

	}

}
