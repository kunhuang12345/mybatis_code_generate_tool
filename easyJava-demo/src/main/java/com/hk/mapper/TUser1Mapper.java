package com.hk.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:用户表Mapper
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser1Mapper<T, P> extends BaseMapper<T, P> {

	/**
	 * 根据Id查询
	 */
	T selectById(@Param("id") Long id);

	/**
	 * 根据Id更新
	 */
	Integer updateById(@Param("bean") T t,@Param("id") Long id);

	/**
	 * 根据Id删除
	 */
	Integer deleteById(@Param("id") Long id);


}