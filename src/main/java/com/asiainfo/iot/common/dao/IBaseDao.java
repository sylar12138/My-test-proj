package com.asiainfo.iot.common.dao;

import java.util.HashMap;
import java.util.List;

import com.asiainfo.iot.common.param.page.Page;

public interface IBaseDao<E> {

	public int insert(String mapperId, E entity);

	public int update(String mapperId, E entity);

	public int updateById(String mapperId, E entity);

	public int delete(String mapperId, E entity);

	public int deleteById(String mapperId, E entity);

	public int deleteByArrayIds(String mapperId, E entity, E[] arrayIds);

	public E findById(String mapperId, E entity);

	public E findOne(String mapperId, E entity);

	public List<E> findList(String mapperId, E entity);

	public List<E> findListPage(String mapperId, E entity, Page page);

	public int batchInsert(String mapperId, List<E> list);

	public int batchUpdate(String mapperId, List<E> list);

	public int insertByNativeSql(String sql);

	public int updateByNativeSql(String sql);

	public int deleteByNativeSql(String sql);

	public List<HashMap> findListByNativeSql(String sql);
	
	public List<HashMap> findListPageByNativeSql(String sql, Page page);

	public HashMap findOneByNativeSql(String sql);

}
