package com.unbank.dao;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiProDataMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiProDataWithBLOBs;

public class BhiProDataStore {

	public int saveBhiPro(BhiProDataWithBLOBs bhiPro) {
		int bhiProid = 0;
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			BhiProDataMapper bhiProMapper = sqlSession
					.getMapper(BhiProDataMapper.class);
			bhiProMapper.insertSelective(bhiPro);
			bhiProid = bhiPro.getId();
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(true);
		} finally {
			sqlSession.close();
		}
		return bhiProid;
	}

}
