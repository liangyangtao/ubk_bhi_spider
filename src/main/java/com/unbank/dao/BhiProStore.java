package com.unbank.dao;

import org.apache.ibatis.session.SqlSession;

import com.unbank.mybatis.client.BhiProMapper;
import com.unbank.mybatis.factory.DynamicConnectionFactory;
import com.unbank.mybatis.vo.BhiProWithBLOBs;

public class BhiProStore {

	public int saveBhiPro(BhiProWithBLOBs bhiPro) {
		int bhiProid = 0;
		SqlSession sqlSession = DynamicConnectionFactory
				.getInstanceSessionFactory("development").openSession();
		try {
			BhiProMapper bhiProMapper = sqlSession
					.getMapper(BhiProMapper.class);
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
