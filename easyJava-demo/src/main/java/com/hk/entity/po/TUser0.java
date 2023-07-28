package com.hk.entity.po;

import java.io.Serializable;
import java.util.Date;

import com.hk.utils.DateUtils;
import com.hk.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @Description:用户表
 * @author:AUTHOR
 * @date:2023/07/28
 */
public class TUser0 implements Serializable {

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

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateTest;

	/**
	 * 
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateTimeTest;

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

	@Override
	public String toString() {
		return "自增ID:" + (id == null ? "空" : id) + "," + 
				"用户ID（主键）:" + (userId == null ? "空" : userId) + "," + 
				"姓名:" + (name == null ? "空" : name) + "," + 
				"年龄:" + (age == null ? "空" : age) + "," + 
				"性别，0：女 1：男:" + (gender == null ? "空" : gender) + "," + 
				"无注释:" + (dateTest == null ? "空" : DateUtils.format(dateTest, DateTimePatternEnum.YYYY_MM_DD.getPattern())) + "," + 
				"无注释:" + (dateTimeTest == null ? "空" : DateUtils.format(dateTimeTest, DateTimePatternEnum.YYYY_MM_DD_HH_SS.getPattern())) + "," + 
				"无注释:" + (bigNum == null ? "空" : bigNum);
	}
}