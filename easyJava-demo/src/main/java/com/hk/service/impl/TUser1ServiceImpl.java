package com.hk.service.impl;

import java.util.List;
import com.hk.service.TUser1Service;
import com.hk.entity.po.TUser1;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser1Query;
import com.hk.mapper.TUser1Mapper;
import com.hk.entity.query.SimplePage;
import com.hk.entity.enums.PageSize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:用户表ServiceImpl
 * @author:AUTHOR
 * @date:2023/07/28
 */
@Service("tUser1Service")
public class TUser1ServiceImpl implements TUser1Service {

	@Resource
	private TUser1Mapper<TUser1, TUser1Query> tUser1Mapper;

	/**
	 * 根据条件查询列表
	 */
	public List<TUser1> findListByParam(TUser1Query query) {
		return this.tUser1Mapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(TUser1Query query) {
		return this.tUser1Mapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<TUser1> findListByPage(TUser1Query query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<TUser1> list = this.findListByParam(query);
		PaginationResultVO<TUser1> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(TUser1 bean) {
		return this.tUser1Mapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<TUser1> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser1Mapper.insertBatch(beanList);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<TUser1> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser1Mapper.insertOrUpdateBatch(beanList);
	}

	/**
	 * 根据Id查询
	 */
	public TUser1 getTUser1ById(Long id) {
		return this.tUser1Mapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateTUser1ById(TUser1 tUser1, Long id) {
		return this.tUser1Mapper.updateById(tUser1, id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteTUser1ById(Long id) {
		return this.tUser1Mapper.deleteById(id);
	}

}