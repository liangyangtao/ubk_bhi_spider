package com.unbank.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiOwnerMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiOwner;
import com.unbank.mybatis.vo.BhiOwnerExample;

public class BhiOwnerReader {

	public List<BhiOwner> readBhiOwner(List<Integer> values) {
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		List<BhiOwner> bhiOwners = new ArrayList<BhiOwner>();
		try {
			BhiOwnerMapper bhiOwnerMapper = sqlSession
					.getMapper(BhiOwnerMapper.class);
			BhiOwnerExample bhiOwnerExample = new BhiOwnerExample();
			bhiOwnerExample.or().andIdIn(values);
			bhiOwners = bhiOwnerMapper.selectByExample(bhiOwnerExample);
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiOwners;
	}

}
