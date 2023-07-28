package com.hk.entity.query;

import java.util.Date;



/**
 * @Description:用户表查询对象
 * @author:AUTHOR
 * @date:2023/07/28
 */
public class TUser0Query extends BaseQuery {

	/**
	 * 自增ID
	 */
	private Long id;

	/**
	 * 用户ID（主键）
	 */
	private Long userId;

	/**
	 * 姓名
	 */
	private String name;

	private String nameFuzzy;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 性别，0：女 1：男
	 */
	private Integer gender;

	/**
	 * 
	 */
	private Date dateTest;

	private String dateTestStart;

	private String dateTestEnd;

	/**
	 * 
	 */
	private Date dateTimeTest;

	private String dateTimeTestStart;

	private String dateTimeTestEnd;

	/**
	 * 
	 */
	private Long bigNum;

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getGender() {
		return this.gender;
	}

	public void setDateTest(Date dateTest) {
		this.dateTest = dateTest;
	}

	public Date getDateTest() {
		return this.dateTest;
	}

	public void setDateTimeTest(Date dateTimeTest) {
		this.dateTimeTest = dateTimeTest;
	}

	public Date getDateTimeTest() {
		return this.dateTimeTest;
	}

	public void setBigNum(Long bigNum) {
		this.bigNum = bigNum;
	}

	public Long getBigNum() {
		return this.bigNum;
	}

	public void setNameFuzzy(String nameFuzzy) {
		this.nameFuzzy = nameFuzzy;
	}

	public String getNameFuzzy() {
		return this.nameFuzzy;
	}

	public void setDateTestStart(String dateTestStart) {
		this.dateTestStart = dateTestStart;
	}

	public String getDateTestStart() {
		return this.dateTestStart;
	}

	public void setDateTestEnd(String dateTestEnd) {
		this.dateTestEnd = dateTestEnd;
	}

	public String getDateTestEnd() {
		return this.dateTestEnd;
	}

	public void setDateTimeTestStart(String dateTimeTestStart) {
		this.dateTimeTestStart = dateTimeTestStart;
	}

	public String getDateTimeTestStart() {
		return this.dateTimeTestStart;
	}

	public void setDateTimeTestEnd(String dateTimeTestEnd) {
		this.dateTimeTestEnd = dateTimeTestEnd;
	}

	public String getDateTimeTestEnd() {
		return this.dateTimeTestEnd;
	}

}