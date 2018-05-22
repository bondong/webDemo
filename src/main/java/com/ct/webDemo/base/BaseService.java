package com.ct.webDemo.base;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */


import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 通用接口
 * 
 * @author Lichee
 */
public interface BaseService<T, PK extends Serializable> {

	List<T> getAll(T record);

	List<T> getAll();
	
	T get(PK id);

	T get(T record);

	int getCount(T record);

	int save(T record) throws RuntimeException;

	int saveDefault(T record) throws RuntimeException;

	int update(T record) throws RuntimeException;

	int updateAll(T record) throws RuntimeException;

	int delete(T record) throws RuntimeException;

	int delete(PK id) throws RuntimeException;

	List<T> getByExample(Object example);
	
	List<T> getByColumn(String column, String value);

	int getCountByExample(Object example);

	int updateByExample(T record, Object example) throws RuntimeException;

	int deleteByExample(Object example) throws RuntimeException;

	PageInfo<T> page(Integer pageNum, Integer pageSize);

	PageInfo<T> page(Integer pageNum, Integer pageSize, T record);

	PageInfo<T> pageExample(Integer pageNum, Integer pageSize, Object example);


}
