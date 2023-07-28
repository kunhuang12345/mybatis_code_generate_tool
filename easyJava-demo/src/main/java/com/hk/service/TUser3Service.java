package com.hk.service;

import java.util.List;
import com.hk.entity.po.TUser3;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser3Query;

/**
 * @Description:用户表Service
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser3Service {

	/**
	 * 根据条件查询列表
	 */
	List<TUser3> findListByParam(TUser3Query param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(TUser3Query param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<TUser3> findListByPage(TUser3Query param);

	/**
	 * 新增
	 */
	Integer add(TUser3 bean);

	/**
	 * 批量新增
	 */

	Integer addBatch(List<TUser3> beanList);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<TUser3> beanList);

	/**
	 * 根据IdAndUserId查询
	 */
	TUser3 getTUser3ByIdAndUserId(Long id, Long userId);

	/**
	 * 根据IdAndUserId更新
	 */
	Integer updateTUser3ByIdAndUserId(TUser3 tUser3, Long id, Long userId);

	/**
	 * 根据IdAndUserId删除
	 */
	Integer deleteTUser3ByIdAndUserId(Long id, Long userId);

}