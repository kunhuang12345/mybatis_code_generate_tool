package com.hk.controller;

import java.util.List;
import com.hk.service.TUser1Service;
import com.hk.entity.po.TUser1;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.vo.ResponseVO;
import com.hk.entity.query.TUser1Query;
import com.hk.mapper.TUser1Mapper;
import com.hk.entity.query.SimplePage;
import com.hk.entity.enums.PageSize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @Description:用户表Controller
 * @author:AUTHOR
 * @date:2023/07/28
 */
@RestController
@RequestMapping("/tUser1")
public class TUser1Controller extends ABaseController {

	@Resource
	private TUser1Service tUser1Service;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(TUser1Query query) {
		return getSuccessResponseVO(tUser1Service.findListByPage(query));
	}

	@RequestMapping("/add")
	/**
	 * 新增
	 */
	public ResponseVO add(TUser1 bean) {
		return getSuccessResponseVO(this.tUser1Service.add(bean));
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<TUser1> beanList) {
		return getSuccessResponseVO(this.tUser1Service.addBatch(beanList));
	}

	/**
	 * 批量新增或修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<TUser1> beanList) {
		return getSuccessResponseVO(this.tUser1Service.addOrUpdateBatch(beanList));
	}

	/**
	 * 根据Id查询
	 */
	@RequestMapping("/getTUser1ById")
	public ResponseVO getTUser1ById(Long id) {
		return getSuccessResponseVO(this.tUser1Service.getTUser1ById(id));
	}

	/**
	 * 根据Id更新
	 */
	@RequestMapping("/updateTUser1ById")
	public ResponseVO updateTUser1ById(TUser1 tUser1, Long id) {
		return getSuccessResponseVO(this.tUser1Service.updateTUser1ById(tUser1, id));
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteTUser1ById")
	public ResponseVO deleteTUser1ById(Long id) {
		return getSuccessResponseVO(this.tUser1Service.deleteTUser1ById(id));
	}

}