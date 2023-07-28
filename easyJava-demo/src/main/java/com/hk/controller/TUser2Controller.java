package com.hk.controller;

import java.util.List;
import com.hk.service.TUser2Service;
import com.hk.entity.po.TUser2;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.vo.ResponseVO;
import com.hk.entity.query.TUser2Query;
import com.hk.mapper.TUser2Mapper;
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
@RequestMapping("/tUser2")
public class TUser2Controller extends ABaseController {

	@Resource
	private TUser2Service tUser2Service;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(TUser2Query query) {
		return getSuccessResponseVO(tUser2Service.findListByPage(query));
	}

	@RequestMapping("/add")
	/**
	 * 新增
	 */
	public ResponseVO add(TUser2 bean) {
		return getSuccessResponseVO(this.tUser2Service.add(bean));
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<TUser2> beanList) {
		return getSuccessResponseVO(this.tUser2Service.addBatch(beanList));
	}

	/**
	 * 批量新增或修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<TUser2> beanList) {
		return getSuccessResponseVO(this.tUser2Service.addOrUpdateBatch(beanList));
	}

	/**
	 * 根据Id查询
	 */
	@RequestMapping("/getTUser2ById")
	public ResponseVO getTUser2ById(Long id) {
		return getSuccessResponseVO(this.tUser2Service.getTUser2ById(id));
	}

	/**
	 * 根据Id更新
	 */
	@RequestMapping("/updateTUser2ById")
	public ResponseVO updateTUser2ById(TUser2 tUser2, Long id) {
		return getSuccessResponseVO(this.tUser2Service.updateTUser2ById(tUser2, id));
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteTUser2ById")
	public ResponseVO deleteTUser2ById(Long id) {
		return getSuccessResponseVO(this.tUser2Service.deleteTUser2ById(id));
	}

}