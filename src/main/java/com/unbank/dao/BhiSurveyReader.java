package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiSurveyMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiSurvey;
import com.unbank.mybatis.vo.BhiSurveyExample;

public class BhiSurveyReader {

	public List<BhiSurvey> readBhiSurvey(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiSurvey> bhiSurveys = new ArrayList<BhiSurvey>();
		try {
			BhiSurveyMapper bhiSurveyMapper = sqlSession
					.getMapper(BhiSurveyMapper.class);
			BhiSurveyExample bhiSurveyExample = new BhiSurveyExample();
			bhiSurveyExample.or().andIdIn(values);
			bhiSurveys = bhiSurveyMapper.selectByExample(bhiSurveyExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiSurveys;
	}

}
