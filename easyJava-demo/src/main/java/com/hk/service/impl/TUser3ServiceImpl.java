package com.hk.service.impl;

import java.util.List;
import com.hk.service.TUser3Service;
import com.hk.entity.po.TUser3;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser3Query;
import com.hk.mapper.TUser3Mapper;
import com.hk.entity.query.SimplePage;
import com.hk.entity.enums.PageSize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:用户表ServiceImpl
 * @author:AUTHOR
 * @date:2023/07/28
 */
@Service("tUser3Service")
public class TUser3ServiceImpl implements TUser3Service {

	@Resource
	private TUser3Mapper<TUser3, TUser3Query> tUser3Mapper;

	/**
	 * 根据条件查询列表
	 */
	public List<TUser3> findListByParam(TUser3Query query) {
		return this.tUser3Mapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(TUser3Query query) {
		return this.tUser3Mapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<TUser3> findListByPage(TUser3Query query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<TUser3> list = this.findListByParam(query);
		PaginationResultVO<TUser3> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(TUser3 bean) {
		return this.tUser3Mapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<TUser3> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser3Mapper.insertBatch(beanList);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<TUser3> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser3Mapper.insertOrUpdateBatch(beanList);
	}

	/**
	 * 根据IdAndUserId查询
	 */
	public TUser3 getTUser3ByIdAndUserId(Long id, Long userId) {
		return this.tUser3Mapper.selectByIdAndUserId(id, userId);
	}

	/**
	 * 根据IdAndUserId更新
	 */
	public Integer updateTUser3ByIdAndUserId(TUser3 tUser3, Long id, Long userId) {
		return this.tUser3Mapper.updateByIdAndUserId(tUser3, id, userId);
	}

	/**
	 * 根据IdAndUserId删除
	 */
	public Integer deleteTUser3ByIdAndUserId(Long id, Long userId) {
		return this.tUser3Mapper.deleteByIdAndUserId(id, userId);
	}

}