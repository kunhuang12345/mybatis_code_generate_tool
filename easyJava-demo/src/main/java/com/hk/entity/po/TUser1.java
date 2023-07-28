package com.hk.entity.po;

import java.io.Serializable;


/**
 * @Description:用户表
 * @author:AUTHOR
 * @date:2023/07/28
 */
public class TUser1 implements Serializable {

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

	@Override
	public String toString() {
		return "自增ID:" + (id == null ? "空" : id) + "," + 
				"用户ID（主键）:" + (userId == null ? "空" : userId) + "," + 
				"姓名:" + (name == null ? "空" : name) + "," + 
				"年龄:" + (age == null ? "空" : age) + "," + 
				"性别，0：女 1：男:" + (gender == null ? "空" : gender);
	}
}