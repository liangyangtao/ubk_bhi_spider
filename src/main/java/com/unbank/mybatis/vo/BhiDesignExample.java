package com.unbank.mybatis.vo;

import java.util.ArrayList;
import java.util.List;

public class BhiDesignExample {
    /**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	protected String orderByClause;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	protected boolean distinct;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	protected List<Criteria> oredCriteria;

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public BhiDesignExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public void setOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public String getOrderByClause() {
		return orderByClause;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public boolean isDistinct() {
		return distinct;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	protected abstract static class GeneratedCriteria {
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value,
				String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1,
				Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property
						+ " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andIdIsNull() {
			addCriterion("id is null");
			return (Criteria) this;
		}

		public Criteria andIdIsNotNull() {
			addCriterion("id is not null");
			return (Criteria) this;
		}

		public Criteria andIdEqualTo(Integer value) {
			addCriterion("id =", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotEqualTo(Integer value) {
			addCriterion("id <>", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThan(Integer value) {
			addCriterion("id >", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdGreaterThanOrEqualTo(Integer value) {
			addCriterion("id >=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThan(Integer value) {
			addCriterion("id <", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdLessThanOrEqualTo(Integer value) {
			addCriterion("id <=", value, "id");
			return (Criteria) this;
		}

		public Criteria andIdIn(List<Integer> values) {
			addCriterion("id in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotIn(List<Integer> values) {
			addCriterion("id not in", values, "id");
			return (Criteria) this;
		}

		public Criteria andIdBetween(Integer value1, Integer value2) {
			addCriterion("id between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andIdNotBetween(Integer value1, Integer value2) {
			addCriterion("id not between", value1, value2, "id");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentIsNull() {
			addCriterion("design_department is null");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentIsNotNull() {
			addCriterion("design_department is not null");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentEqualTo(String value) {
			addCriterion("design_department =", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentNotEqualTo(String value) {
			addCriterion("design_department <>", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentGreaterThan(String value) {
			addCriterion("design_department >", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentGreaterThanOrEqualTo(String value) {
			addCriterion("design_department >=", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentLessThan(String value) {
			addCriterion("design_department <", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentLessThanOrEqualTo(String value) {
			addCriterion("design_department <=", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentLike(String value) {
			addCriterion("design_department like", value, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentNotLike(String value) {
			addCriterion("design_department not like", value,
					"designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentIn(List<String> values) {
			addCriterion("design_department in", values, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentNotIn(List<String> values) {
			addCriterion("design_department not in", values, "designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentBetween(String value1, String value2) {
			addCriterion("design_department between", value1, value2,
					"designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignDepartmentNotBetween(String value1,
				String value2) {
			addCriterion("design_department not between", value1, value2,
					"designDepartment");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleIsNull() {
			addCriterion("design_people is null");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleIsNotNull() {
			addCriterion("design_people is not null");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleEqualTo(String value) {
			addCriterion("design_people =", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleNotEqualTo(String value) {
			addCriterion("design_people <>", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleGreaterThan(String value) {
			addCriterion("design_people >", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleGreaterThanOrEqualTo(String value) {
			addCriterion("design_people >=", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleLessThan(String value) {
			addCriterion("design_people <", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleLessThanOrEqualTo(String value) {
			addCriterion("design_people <=", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleLike(String value) {
			addCriterion("design_people like", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleNotLike(String value) {
			addCriterion("design_people not like", value, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleIn(List<String> values) {
			addCriterion("design_people in", values, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleNotIn(List<String> values) {
			addCriterion("design_people not in", values, "designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleBetween(String value1, String value2) {
			addCriterion("design_people between", value1, value2,
					"designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleNotBetween(String value1, String value2) {
			addCriterion("design_people not between", value1, value2,
					"designPeople");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobIsNull() {
			addCriterion("design_people_job is null");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobIsNotNull() {
			addCriterion("design_people_job is not null");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobEqualTo(String value) {
			addCriterion("design_people_job =", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobNotEqualTo(String value) {
			addCriterion("design_people_job <>", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobGreaterThan(String value) {
			addCriterion("design_people_job >", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobGreaterThanOrEqualTo(String value) {
			addCriterion("design_people_job >=", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobLessThan(String value) {
			addCriterion("design_people_job <", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobLessThanOrEqualTo(String value) {
			addCriterion("design_people_job <=", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobLike(String value) {
			addCriterion("design_people_job like", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobNotLike(String value) {
			addCriterion("design_people_job not like", value, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobIn(List<String> values) {
			addCriterion("design_people_job in", values, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobNotIn(List<String> values) {
			addCriterion("design_people_job not in", values, "designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobBetween(String value1, String value2) {
			addCriterion("design_people_job between", value1, value2,
					"designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignPeopleJobNotBetween(String value1,
				String value2) {
			addCriterion("design_people_job not between", value1, value2,
					"designPeopleJob");
			return (Criteria) this;
		}

		public Criteria andDesignTelIsNull() {
			addCriterion("design_tel is null");
			return (Criteria) this;
		}

		public Criteria andDesignTelIsNotNull() {
			addCriterion("design_tel is not null");
			return (Criteria) this;
		}

		public Criteria andDesignTelEqualTo(String value) {
			addCriterion("design_tel =", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelNotEqualTo(String value) {
			addCriterion("design_tel <>", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelGreaterThan(String value) {
			addCriterion("design_tel >", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelGreaterThanOrEqualTo(String value) {
			addCriterion("design_tel >=", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelLessThan(String value) {
			addCriterion("design_tel <", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelLessThanOrEqualTo(String value) {
			addCriterion("design_tel <=", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelLike(String value) {
			addCriterion("design_tel like", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelNotLike(String value) {
			addCriterion("design_tel not like", value, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelIn(List<String> values) {
			addCriterion("design_tel in", values, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelNotIn(List<String> values) {
			addCriterion("design_tel not in", values, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelBetween(String value1, String value2) {
			addCriterion("design_tel between", value1, value2, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignTelNotBetween(String value1, String value2) {
			addCriterion("design_tel not between", value1, value2, "designTel");
			return (Criteria) this;
		}

		public Criteria andDesignFaxIsNull() {
			addCriterion("design_fax is null");
			return (Criteria) this;
		}

		public Criteria andDesignFaxIsNotNull() {
			addCriterion("design_fax is not null");
			return (Criteria) this;
		}

		public Criteria andDesignFaxEqualTo(String value) {
			addCriterion("design_fax =", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxNotEqualTo(String value) {
			addCriterion("design_fax <>", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxGreaterThan(String value) {
			addCriterion("design_fax >", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxGreaterThanOrEqualTo(String value) {
			addCriterion("design_fax >=", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxLessThan(String value) {
			addCriterion("design_fax <", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxLessThanOrEqualTo(String value) {
			addCriterion("design_fax <=", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxLike(String value) {
			addCriterion("design_fax like", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxNotLike(String value) {
			addCriterion("design_fax not like", value, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxIn(List<String> values) {
			addCriterion("design_fax in", values, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxNotIn(List<String> values) {
			addCriterion("design_fax not in", values, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxBetween(String value1, String value2) {
			addCriterion("design_fax between", value1, value2, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignFaxNotBetween(String value1, String value2) {
			addCriterion("design_fax not between", value1, value2, "designFax");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeIsNull() {
			addCriterion("design_postcode is null");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeIsNotNull() {
			addCriterion("design_postcode is not null");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeEqualTo(String value) {
			addCriterion("design_postcode =", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeNotEqualTo(String value) {
			addCriterion("design_postcode <>", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeGreaterThan(String value) {
			addCriterion("design_postcode >", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeGreaterThanOrEqualTo(String value) {
			addCriterion("design_postcode >=", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeLessThan(String value) {
			addCriterion("design_postcode <", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeLessThanOrEqualTo(String value) {
			addCriterion("design_postcode <=", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeLike(String value) {
			addCriterion("design_postcode like", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeNotLike(String value) {
			addCriterion("design_postcode not like", value, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeIn(List<String> values) {
			addCriterion("design_postcode in", values, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeNotIn(List<String> values) {
			addCriterion("design_postcode not in", values, "designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeBetween(String value1, String value2) {
			addCriterion("design_postcode between", value1, value2,
					"designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignPostcodeNotBetween(String value1, String value2) {
			addCriterion("design_postcode not between", value1, value2,
					"designPostcode");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressIsNull() {
			addCriterion("design_detail_address is null");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressIsNotNull() {
			addCriterion("design_detail_address is not null");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressEqualTo(String value) {
			addCriterion("design_detail_address =", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressNotEqualTo(String value) {
			addCriterion("design_detail_address <>", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressGreaterThan(String value) {
			addCriterion("design_detail_address >", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressGreaterThanOrEqualTo(String value) {
			addCriterion("design_detail_address >=", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressLessThan(String value) {
			addCriterion("design_detail_address <", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressLessThanOrEqualTo(String value) {
			addCriterion("design_detail_address <=", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressLike(String value) {
			addCriterion("design_detail_address like", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressNotLike(String value) {
			addCriterion("design_detail_address not like", value,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressIn(List<String> values) {
			addCriterion("design_detail_address in", values,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressNotIn(List<String> values) {
			addCriterion("design_detail_address not in", values,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressBetween(String value1,
				String value2) {
			addCriterion("design_detail_address between", value1, value2,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignDetailAddressNotBetween(String value1,
				String value2) {
			addCriterion("design_detail_address not between", value1, value2,
					"designDetailAddress");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlIsNull() {
			addCriterion("design_weburl is null");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlIsNotNull() {
			addCriterion("design_weburl is not null");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlEqualTo(String value) {
			addCriterion("design_weburl =", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlNotEqualTo(String value) {
			addCriterion("design_weburl <>", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlGreaterThan(String value) {
			addCriterion("design_weburl >", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlGreaterThanOrEqualTo(String value) {
			addCriterion("design_weburl >=", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlLessThan(String value) {
			addCriterion("design_weburl <", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlLessThanOrEqualTo(String value) {
			addCriterion("design_weburl <=", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlLike(String value) {
			addCriterion("design_weburl like", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlNotLike(String value) {
			addCriterion("design_weburl not like", value, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlIn(List<String> values) {
			addCriterion("design_weburl in", values, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlNotIn(List<String> values) {
			addCriterion("design_weburl not in", values, "designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlBetween(String value1, String value2) {
			addCriterion("design_weburl between", value1, value2,
					"designWeburl");
			return (Criteria) this;
		}

		public Criteria andDesignWeburlNotBetween(String value1, String value2) {
			addCriterion("design_weburl not between", value1, value2,
					"designWeburl");
			return (Criteria) this;
		}
	}

	/**
	 * This class was generated by MyBatis Generator. This class corresponds to the database table bhi_design
	 * @mbggenerated  Tue Mar 31 15:11:14 GMT+08:00 2015
	 */
	public static class Criterion {
		private String condition;
		private Object value;
		private Object secondValue;
		private boolean noValue;
		private boolean singleValue;
		private boolean betweenValue;
		private boolean listValue;
		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue,
				String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table bhi_design
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 31 10:53:44 GMT+08:00 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}