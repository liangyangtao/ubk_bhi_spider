package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiConstructionMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiConstruction;
import com.unbank.mybatis.vo.BhiConstructionExample;

public class BhiConstructionReader {

	public List<BhiConstruction> readBhiConstruction(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiConstruction> bhiConstructions = new ArrayList<BhiConstruction>();
		try {
			BhiConstructionMapper bhiConstructionMapper = sqlSession
					.getMapper(BhiConstructionMapper.class);
			BhiConstructionExample bhiConstructionExample = new BhiConstructionExample();
			bhiConstructionExample.or().andIdIn(values);
			bhiConstructions = bhiConstructionMapper.selectByExample(bhiConstructionExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiConstructions;
	}

}
