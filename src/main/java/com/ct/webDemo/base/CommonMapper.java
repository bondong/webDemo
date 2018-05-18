package com.ct.webDemo.base;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertListMapper;

public interface CommonMapper<T> extends Mapper<T>,InsertListMapper<T>,IdsMapper<T>{
}