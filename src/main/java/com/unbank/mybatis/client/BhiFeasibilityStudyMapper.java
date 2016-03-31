package com.unbank.mybatis.client;

import com.unbank.mybatis.vo.BhiFeasibilityStudy;
import com.unbank.mybatis.vo.BhiFeasibilityStudyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BhiFeasibilityStudyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int countByExample(BhiFeasibilityStudyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int deleteByExample(BhiFeasibilityStudyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int insert(BhiFeasibilityStudy record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int insertSelective(BhiFeasibilityStudy record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	List<BhiFeasibilityStudy> selectByExample(BhiFeasibilityStudyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int updateByExampleSelective(@Param("record") BhiFeasibilityStudy record,
			@Param("example") BhiFeasibilityStudyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_feasibility_study
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	int updateByExample(@Param("record") BhiFeasibilityStudy record,
			@Param("example") BhiFeasibilityStudyExample example);
}