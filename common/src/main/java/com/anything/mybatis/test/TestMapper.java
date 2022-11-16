package com.anything.mybatis.test;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

	public int selectTest();
}
