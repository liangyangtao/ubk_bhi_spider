package com.unbank.mybatis.mapper;

import com.unbank.mybatis.entity.SQLAdapter;

public interface SQLAdapterMapper {

	void executeSQL(SQLAdapter sqlAdapter);

	void executeMapSQL(SQLAdapter sqlAdapter);

}
