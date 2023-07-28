package com.hk.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:用户表Mapper
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser3Mapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据IdAndUserId查询
	 */
	T selectByIdAndUserId(@Param("id") Long id, @Param("user_id") Long userId);

	/**
	 * 根据IdAndUserId更新
	 */
	Integer updateByIdAndUserId(@Param("bean") T t,@Param("id") Long id, @Param("user_id") Long userId);

	/**
	 * 根据IdAndUserId删除
	 */
	Integer deleteByIdAndUserId(@Param("id") Long id, @Param("user_id") Long userId);


}