package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiPPPMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiPPP;
import com.unbank.mybatis.vo.BhiPPPExample;

public class BhiPppReader {
	public List<BhiPPP> readBhiPppReader() {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiPPP> bhiPPPs = new ArrayList<BhiPPP>();
		try {
			BhiPPPMapper bhiPPPMapper = sqlSession
					.getMapper(BhiPPPMapper.class);
			BhiPPPExample bhiPppExample = new BhiPPPExample();
			bhiPPPs = bhiPPPMapper.selectByExampleWithBLOBs(bhiPppExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiPPPs;

	}

}
