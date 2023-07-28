package com.hk.service.impl;

import java.util.List;
import java.util.Date;

import com.hk.utils.DateUtils;
import com.hk.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.hk.service.TUser0Service;
import com.hk.entity.po.TUser0;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.query.TUser0Query;
import com.hk.mapper.TUser0Mapper;
import com.hk.entity.query.SimplePage;
import com.hk.entity.enums.PageSize;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Description:用户表ServiceImpl
 * @author:AUTHOR
 * @date:2023/07/28
 */
@Service("tUser0Service")
public class TUser0ServiceImpl implements TUser0Service {

	@Resource
	private TUser0Mapper<TUser0, TUser0Query> tUser0Mapper;

	/**
	 * 根据条件查询列表
	 */
	public List<TUser0> findListByParam(TUser0Query query) {
		return this.tUser0Mapper.selectList(query);
	}

	/**
	 * 根据条件查询数量
	 */
	public Integer findCountByParam(TUser0Query query) {
		return this.tUser0Mapper.selectCount(query);
	}

	/**
	 * 分页查询
	 */
	public PaginationResultVO<TUser0> findListByPage(TUser0Query query) {
		Integer count = this.findCountByParam(query);
		Integer pageSize = query.getPageSize() == null ? PageSize.SIZE15.getSize() : query.getPageSize();
		SimplePage page = new SimplePage(query.getPageNo(), count, pageSize);
		query.setSimplePage(page);
		List<TUser0> list = this.findListByParam(query);
		PaginationResultVO<TUser0> result = new PaginationResultVO<>(count, page.getPageSize(), page.getPageNo(), page.getPageTotal(), list);
		return result;
	}

	/**
	 * 新增
	 */
	public Integer add(TUser0 bean) {
		return this.tUser0Mapper.insert(bean);
	}

	/**
	 * 批量新增
	 */
	public Integer addBatch(List<TUser0> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser0Mapper.insertBatch(beanList);
	}

	/**
	 * 批量新增或修改
	 */
	public Integer addOrUpdateBatch(List<TUser0> beanList) {
		if (beanList == null || beanList.isEmpty()) {
			return 0;
		}
		return this.tUser0Mapper.insertOrUpdateBatch(beanList);
	}

	/**
	 * 根据Id查询
	 */
	public TUser0 getTUser0ById(Long id) {
		return this.tUser0Mapper.selectById(id);
	}

	/**
	 * 根据Id更新
	 */
	public Integer updateTUser0ById(TUser0 tUser0, Long id) {
		return this.tUser0Mapper.updateById(tUser0, id);
	}

	/**
	 * 根据Id删除
	 */
	public Integer deleteTUser0ById(Long id) {
		return this.tUser0Mapper.deleteById(id);
	}

	/**
	 * 根据Gender查询
	 */
	public TUser0 getTUser0ByGender(Integer gender) {
		return this.tUser0Mapper.selectByGender(gender);
	}

	/**
	 * 根据Gender更新
	 */
	public Integer updateTUser0ByGender(TUser0 tUser0, Integer gender) {
		return this.tUser0Mapper.updateByGender(tUser0, gender);
	}

	/**
	 * 根据Gender删除
	 */
	public Integer deleteTUser0ByGender(Integer gender) {
		return this.tUser0Mapper.deleteByGender(gender);
	}

	/**
	 * 根据NameAndAge查询
	 */
	public TUser0 getTUser0ByNameAndAge(String name, Integer age) {
		return this.tUser0Mapper.selectByNameAndAge(name, age);
	}

	/**
	 * 根据NameAndAge更新
	 */
	public Integer updateTUser0ByNameAndAge(TUser0 tUser0, String name, Integer age) {
		return this.tUser0Mapper.updateByNameAndAge(tUser0, name, age);
	}

	/**
	 * 根据NameAndAge删除
	 */
	public Integer deleteTUser0ByNameAndAge(String name, Integer age) {
		return this.tUser0Mapper.deleteByNameAndAge(name, age);
	}

}