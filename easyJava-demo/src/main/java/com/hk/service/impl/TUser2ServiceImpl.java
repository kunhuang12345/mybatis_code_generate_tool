package com.hk.service.impl;

import java.util.List;
import com.hk.service.TUser2Service;
import com.hk.entity.po.TUser2;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser2Query;
import com.hk.mapper.TUser2Mapper;
import com.hk.entity.query.SimplePage;
import com.hk.entity.enums.PageSize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:用户表ServiceImpl
 * @author:AUTHOR
 * @date:2023/07/28
 */
@Service("tUser2Service")
public class TUser2ServiceImpl implements TUser2Service {

	@Resource
	private TUser2Mapper<TUser2, TUser2Query> tUser2Mapper;

	/**
	 * 根据条件查询列表
	 */
	public List<TUser2> findListByParam(TUser2Query query) {
		return this.tUser2Mapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(TUser2Query query) {
		return this.tUser2Mapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<TUser2> findListByPage(TUser2Query query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<TUser2> list = this.findListByParam(query);
		PaginationResultVO<TUser2> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(TUser2 bean) {
		return this.tUser2Mapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<TUser2> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser2Mapper.insertBatch(beanList);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<TUser2> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser2Mapper.insertOrUpdateBatch(beanList);
	}

	/**
	 * 根据Id查询
	 */
	public TUser2 getTUser2ById(Long id) {
		return this.tUser2Mapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateTUser2ById(TUser2 tUser2, Long id) {
		return this.tUser2Mapper.updateById(tUser2, id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteTUser2ById(Long id) {
		return this.tUser2Mapper.deleteById(id);
	}

}