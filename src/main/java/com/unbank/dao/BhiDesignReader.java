package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiDesignMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiDesign;
import com.unbank.mybatis.vo.BhiDesignExample;

public class BhiDesignReader {
	public List<BhiDesign> readBhiDesign(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiDesign> bhiDesigns = new ArrayList<BhiDesign>();
		try {
			BhiDesignMapper bhiDesignMapper = sqlSession
					.getMapper(BhiDesignMapper.class);
			BhiDesignExample bhiDesignExample = new BhiDesignExample();
			bhiDesignExample.or().andIdIn(values);
			bhiDesigns = bhiDesignMapper.selectByExample(bhiDesignExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiDesigns;
	}
}
