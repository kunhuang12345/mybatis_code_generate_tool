package com.hk.controller;

import java.util.List;
import com.hk.service.TUser3Service;
import com.hk.entity.po.TUser3;
import com.hk.entity.vo.PaginationResultVO;
import com.hk.entity.vo.ResponseVO;
import com.hk.entity.query.TUser3Query;
import com.hk.mapper.TUser3Mapper;
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
@RequestMapping("/tUser3")
public class TUser3Controller extends ABaseController {

	@Resource
	private TUser3Service tUser3Service;

	/**
	 * 根据条件分页查询
	 */
	@RequestMapping("/loadDataList")
	public ResponseVO loadDataList(TUser3Query query) {
		return getSuccessResponseVO(tUser3Service.findListByPage(query));
	}

	@RequestMapping("/add")
	/**
	 * 新增
	 */
	public ResponseVO add(TUser3 bean) {
		return getSuccessResponseVO(this.tUser3Service.add(bean));
	}

	/**
	 * 批量新增
	 */
	@RequestMapping("/addBatch")
	public ResponseVO addBatch(@RequestBody List<TUser3> beanList) {
		return getSuccessResponseVO(this.tUser3Service.addBatch(beanList));
	}

	/**
	 * 批量新增或修改
	 */
	@RequestMapping("/addOrUpdateBatch")
	public ResponseVO addOrUpdateBatch(@RequestBody List<TUser3> beanList) {
		return getSuccessResponseVO(this.tUser3Service.addOrUpdateBatch(beanList));
	}

	/**
	 * 根据IdAndUserId查询
	 */
	@RequestMapping("/getTUser3ByIdAndUserId")
	public ResponseVO getTUser3ByIdAndUserId(Long id, Long userId) {
		return getSuccessResponseVO(this.tUser3Service.getTUser3ByIdAndUserId(id, userId));
	}

	/**
	 * 根据IdAndUserId更新
	 */
	@RequestMapping("/updateTUser3ByIdAndUserId")
	public ResponseVO updateTUser3ByIdAndUserId(TUser3 tUser3, Long id, Long userId) {
		return getSuccessResponseVO(this.tUser3Service.updateTUser3ByIdAndUserId(tUser3, id, userId));
	}

	/**
	 * 根据IdAndUserId删除
	 */
	@RequestMapping("/deleteTUser3ByIdAndUserId")
	public ResponseVO deleteTUser3ByIdAndUserId(Long id, Long userId) {
		return getSuccessResponseVO(this.tUser3Service.deleteTUser3ByIdAndUserId(id, userId));
	}

}