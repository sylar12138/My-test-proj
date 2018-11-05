package com.asiainfo.iot.common.service;

import java.util.HashMap;
import java.util.List;

import com.asiainfo.iot.common.param.ReqParam;
import com.asiainfo.iot.common.param.RespParam;
import com.asiainfo.iot.common.param.page.Page;

public interface IBaseSevice <E> {

	public int insert(ReqParam req, RespParam res);

	public int update(ReqParam req, RespParam res);

	public int updateById(ReqParam req, RespParam res);

	public int delete(ReqParam req, RespParam res);

	public int deleteById(ReqParam req, RespParam res);

	public int deleteByArrayIds(ReqParam req, RespParam res);

	public int findById(ReqParam req, RespParam res);

	public int findOne(ReqParam req, RespParam res);

	public int findList(ReqParam req, RespParam res);

	public int findListPage(ReqParam req, RespParam res);

	public int batchInsert(ReqParam req, RespParam res);

	public int batchUpdate(ReqParam req, RespParam res);

	public int insertByNativeSql(ReqParam req, RespParam res);

	public int updateByNativeSql(ReqParam req, RespParam res);

	public int deleteByNativeSql(ReqParam req, RespParam res);

	public int findListByNativeSql(ReqParam req, RespParam res);
	
	public int findListPageByNativeSql(ReqParam req, RespParam res);

	public int findOneByNativeSql(ReqParam req, RespParam res);

}
