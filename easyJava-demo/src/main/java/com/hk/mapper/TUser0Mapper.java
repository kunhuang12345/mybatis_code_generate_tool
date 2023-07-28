package com.hk.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @Description:用户表Mapper
 * @author:AUTHOR
 * @date:2023/07/28
 */
public interface TUser0Mapper<T, P> extends BaseMapper<T, P> {

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

	/**
	 * 根据Gender查询
	 */
	T selectByGender(@Param("gender") Integer gender);

	/**
	 * 根据Gender更新
	 */
	Integer updateByGender(@Param("bean") T t,@Param("gender") Integer gender);

	/**
	 * 根据Gender删除
	 */
	Integer deleteByGender(@Param("gender") Integer gender);

	/**
	 * 根据NameAndAge查询
	 */
	T selectByNameAndAge(@Param("name") String name, @Param("age") Integer age);

	/**
	 * 根据NameAndAge更新
	 */
	Integer updateByNameAndAge(@Param("bean") T t,@Param("name") String name, @Param("age") Integer age);

	/**
	 * 根据NameAndAge删除
	 */
	Integer deleteByNameAndAge(@Param("name") String name, @Param("age") Integer age);


}