package com.hk.controller;

import java.util.List;
import java.util.Date;

import com.hk.utils.DateUtils;
import com.hk.entity.enums.DateTimePatternEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.hk.service.TUser0Service;
import com.hk.entity.po.TUser0;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.vo.ResponseVO;
import com.hk.entity.query.TUser0Query;
import com.hk.mapper.TUser0Mapper;
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
@RequestMapping("/tUser0")
public class TUser0Controller extends ABaseController {

	@Resource
	private TUser0Service tUser0Service;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(TUser0Query query) {
		return getSuccessResponseVO(tUser0Service.findListByPage(query));
	}

	@RequestMapping("/add")
	/**
	 * 新增
	 */
	public ResponseVO add(TUser0 bean) {
		return getSuccessResponseVO(this.tUser0Service.add(bean));
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<TUser0> beanList) {
		return getSuccessResponseVO(this.tUser0Service.addBatch(beanList));
	}

	/**
	 * 批量新增或修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<TUser0> beanList) {
		return getSuccessResponseVO(this.tUser0Service.addOrUpdateBatch(beanList));
	}

	/**
	 * 根据Id查询
	 */
	@RequestMapping("/getTUser0ById")
	public ResponseVO getTUser0ById(Long id) {
		return getSuccessResponseVO(this.tUser0Service.getTUser0ById(id));
	}

	/**
	 * 根据Id更新
	 */
	@RequestMapping("/updateTUser0ById")
	public ResponseVO updateTUser0ById(TUser0 tUser0, Long id) {
		return getSuccessResponseVO(this.tUser0Service.updateTUser0ById(tUser0, id));
	}

	/**
	 * 根据Id删除
	 */
	@RequestMapping("/deleteTUser0ById")
	public ResponseVO deleteTUser0ById(Long id) {
		return getSuccessResponseVO(this.tUser0Service.deleteTUser0ById(id));
	}

	/**
	 * 根据Gender查询
	 */
	@RequestMapping("/getTUser0ByGender")
	public ResponseVO getTUser0ByGender(Integer gender) {
		return getSuccessResponseVO(this.tUser0Service.getTUser0ByGender(gender));
	}

	/**
	 * 根据Gender更新
	 */
	@RequestMapping("/updateTUser0ByGender")
	public ResponseVO updateTUser0ByGender(TUser0 tUser0, Integer gender) {
		return getSuccessResponseVO(this.tUser0Service.updateTUser0ByGender(tUser0, gender));
	}

	/**
	 * 根据Gender删除
	 */
	@RequestMapping("/deleteTUser0ByGender")
	public ResponseVO deleteTUser0ByGender(Integer gender) {
		return getSuccessResponseVO(this.tUser0Service.deleteTUser0ByGender(gender));
	}

	/**
	 * 根据NameAndAge查询
	 */
	@RequestMapping("/getTUser0ByNameAndAge")
	public ResponseVO getTUser0ByNameAndAge(String name, Integer age) {
		return getSuccessResponseVO(this.tUser0Service.getTUser0ByNameAndAge(name, age));
	}

	/**
	 * 根据NameAndAge更新
	 */
	@RequestMapping("/updateTUser0ByNameAndAge")
	public ResponseVO updateTUser0ByNameAndAge(TUser0 tUser0, String name, Integer age) {
		return getSuccessResponseVO(this.tUser0Service.updateTUser0ByNameAndAge(tUser0, name, age));
	}

	/**
	 * 根据NameAndAge删除
	 */
	@RequestMapping("/deleteTUser0ByNameAndAge")
	public ResponseVO deleteTUser0ByNameAndAge(String name, Integer age) {
		return getSuccessResponseVO(this.tUser0Service.deleteTUser0ByNameAndAge(name, age));
	}

}