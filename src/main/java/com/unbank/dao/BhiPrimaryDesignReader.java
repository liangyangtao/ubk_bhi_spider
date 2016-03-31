package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiPrimaryDesignMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiPrimaryDesign;
import com.unbank.mybatis.vo.BhiPrimaryDesignExample;

public class BhiPrimaryDesignReader {

	public List<BhiPrimaryDesign> readBhiPrimaryDesign(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiPrimaryDesign> bhiPrimaryDesigns = new ArrayList<BhiPrimaryDesign>();
		try {
			BhiPrimaryDesignMapper bhiPrimaryDesignMapper = sqlSession
					.getMapper(BhiPrimaryDesignMapper.class);
			BhiPrimaryDesignExample bhiPrimaryDesignExample = new BhiPrimaryDesignExample();
			bhiPrimaryDesignExample.or().andIdIn(values);
			bhiPrimaryDesigns = bhiPrimaryDesignMapper
					.selectByExample(bhiPrimaryDesignExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiPrimaryDesigns;
	}

}
