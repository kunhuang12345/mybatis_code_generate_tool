package com.hk.service;

import java.util.List;
import com.hk.entity.po.TUser2;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser2Query;

/**
 * @Description:用户表Service
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser2Service {

	/**
	 * 根据条件查询列表
	 */
	List<TUser2> findListByParam(TUser2Query param);

	/**
	 * 根据条件查询数量
	 */
	Integer findCountByParam(TUser2Query param);

	/**
	 * 分页查询
	 */
	PaginationResultVO<TUser2> findListByPage(TUser2Query param);

	/**
	 * 新增
	 */
	Integer add(TUser2 bean);

	/**
	 * 批量新增
	 */

	Integer addBatch(List<TUser2> beanList);

	/**
	 * 批量新增或修改
	 */
	Integer addOrUpdateBatch(List<TUser2> beanList);

	/**
	 * 根据Id查询
	 */
	TUser2 getTUser2ById(Long id);

	/**
	 * 根据Id更新
	 */
	Integer updateTUser2ById(TUser2 tUser2, Long id);

	/**
	 * 根据Id删除
	 */
	Integer deleteTUser2ById(Long id);

}