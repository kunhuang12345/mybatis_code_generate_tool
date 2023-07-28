package com.hk.service;

import java.util.List;
import java.util.Date;

import com.hk.utils.DateUtils;
import com.hk.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.hk.entity.po.TUser0;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser0Query;

/**
 * @Description:用户表Service
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser0Service {

	/**
	 * 根据条件查询列表
	 */
	List<TUser0> findListByParam(TUser0Query param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(TUser0Query param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<TUser0> findListByPage(TUser0Query param);

	/**
	 * 新增
	 */
	Integer add(TUser0 bean);

	/**
	 * 批量新增
	 */

	Integer addBatch(List<TUser0> beanList);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<TUser0> beanList);

	/**
	 * 根据Id查询
	 */
	TUser0 getTUser0ById(Long id);

	/**
	 * 根据Id更新
	 */
	Integer updateTUser0ById(TUser0 tUser0, Long id);

	/**
	 * 根据Id删除
	 */
	Integer deleteTUser0ById(Long id);

	/**
	 * 根据Gender查询
	 */
	TUser0 getTUser0ByGender(Integer gender);

	/**
	 * 根据Gender更新
	 */
	Integer updateTUser0ByGender(TUser0 tUser0, Integer gender);

	/**
	 * 根据Gender删除
	 */
	Integer deleteTUser0ByGender(Integer gender);

	/**
	 * 根据NameAndAge查询
	 */
	TUser0 getTUser0ByNameAndAge(String name, Integer age);

	/**
	 * 根据NameAndAge更新
	 */
	Integer updateTUser0ByNameAndAge(TUser0 tUser0, String name, Integer age);

	/**
	 * 根据NameAndAge删除
	 */
	Integer deleteTUser0ByNameAndAge(String name, Integer age);

}