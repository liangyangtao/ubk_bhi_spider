package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiPlantMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiPlant;
import com.unbank.mybatis.vo.BhiPlantExample;

public class BhiPlantReader {

	public List<BhiPlant> readBhiPlant(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiPlant> bhiPlants = new ArrayList<BhiPlant>();
		try {
			BhiPlantMapper bhiPlantMapper = sqlSession
					.getMapper(BhiPlantMapper.class);
			BhiPlantExample bhiPlantExample = new BhiPlantExample();
			bhiPlantExample.or().andIdIn(values);
			bhiPlants = bhiPlantMapper.selectByExample(bhiPlantExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiPlants;
	}

}
