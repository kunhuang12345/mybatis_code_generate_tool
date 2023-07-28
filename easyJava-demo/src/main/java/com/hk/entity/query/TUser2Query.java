package com.hk.entity.query;



/**
 * @Description:用户表查询对象
 * @author:AUTHOR
 * @date:2023/07/28
 */
public class TUser2Query extends BaseQuery {

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

	public void setNameFuzzy(String nameFuzzy) {
		this.nameFuzzy = nameFuzzy;
	}

	public String getNameFuzzy() {
		return this.nameFuzzy;
	}

}