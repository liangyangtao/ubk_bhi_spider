package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiFeasibilityStudyMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiFeasibilityStudy;
import com.unbank.mybatis.vo.BhiFeasibilityStudyExample;

public class BhiFeasibilityStudyReader {

	public List<BhiFeasibilityStudy> readBhiFeasibilityStudy(
			List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiFeasibilityStudy> bhiFeasibilityStudys = new ArrayList<BhiFeasibilityStudy>();
		try {
			BhiFeasibilityStudyMapper bhiFeasibilityStudyMapper = sqlSession
					.getMapper(BhiFeasibilityStudyMapper.class);
			BhiFeasibilityStudyExample bhiFeasibilityStudyExample = new BhiFeasibilityStudyExample();
			bhiFeasibilityStudyExample.or().andIdIn(values);
			bhiFeasibilityStudys = bhiFeasibilityStudyMapper
					.selectByExample(bhiFeasibilityStudyExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiFeasibilityStudys;
	}

}
