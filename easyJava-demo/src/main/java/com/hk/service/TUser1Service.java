package com.hk.service;

import java.util.List;
import com.hk.entity.po.TUser1;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser1Query;

/**
 * @Description:用户表Service
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser1Service {

	/**
	 * 根据条件查询列表
	 */
	List<TUser1> findListByParam(TUser1Query param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(TUser1Query param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<TUser1> findListByPage(TUser1Query param);

	/**
	 * 新增
	 */
	Integer add(TUser1 bean);

	/**
	 * 批量新增
	 */

	Integer addBatch(List<TUser1> beanList);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<TUser1> beanList);

	/**
	 * 根据Id查询
	 */
	TUser1 getTUser1ById(Long id);

	/**
	 * 根据Id更新
	 */
	Integer updateTUser1ById(TUser1 tUser1, Long id);

	/**
	 * 根据Id删除
	 */
	Integer deleteTUser1ById(Long id);

}