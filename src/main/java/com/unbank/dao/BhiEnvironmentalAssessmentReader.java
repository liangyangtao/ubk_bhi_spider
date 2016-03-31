package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiEnvironmentalAssessmentMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiEnvironmentalAssessment;
import com.unbank.mybatis.vo.BhiEnvironmentalAssessmentExample;

public class BhiEnvironmentalAssessmentReader {

	public List<BhiEnvironmentalAssessment> readBhiEnvironmentalAssessment(
			List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiEnvironmentalAssessment> bhiEnvironmentalAssessments = new ArrayList<BhiEnvironmentalAssessment>();
		try {
			BhiEnvironmentalAssessmentMapper bhiEnvironmentalAssessmentMapper = sqlSession
					.getMapper(BhiEnvironmentalAssessmentMapper.class);
			BhiEnvironmentalAssessmentExample bhiEnvironmentalAssessmentExample = new BhiEnvironmentalAssessmentExample();
			bhiEnvironmentalAssessmentExample.or().andIdIn(values);
			bhiEnvironmentalAssessments = bhiEnvironmentalAssessmentMapper
					.selectByExample(bhiEnvironmentalAssessmentExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiEnvironmentalAssessments;
	}

}
