package com.asiainfo.iot.common.service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.iot.common.dao.IBaseDao;
import com.asiainfo.iot.common.param.ReqParam;
import com.asiainfo.iot.common.param.RespParam;
import com.asiainfo.iot.common.param.page.Page;

@Service
@Transactional
public class BaseSevice implements IBaseSevice {

	private static final Logger logger = Logger.getLogger(BaseSevice.class);

	private IBaseDao baseDao;

	public IBaseDao getBaseDao() {
		return baseDao;
	}

	@Resource(name = "baseDao")
	public void setBaseDao(IBaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public int insert(ReqParam req, RespParam res) {
		int i = baseDao.insert(null, req.getEntity());
		res.setEntity(req.getEntity());
		return i;
	}

	@Override
	public int update(ReqParam req, RespParam res) {
		int i = baseDao.update(null, req.getEntity());
		res.setEntity(req.getEntity());
		return i;
	}

	@Override
	public int updateById(ReqParam req, RespParam res) {
		int i = baseDao.updateById(null, req.getEntity());
		res.setEntity(req.getEntity());
		return i;
	}

	@Override
	public int delete(ReqParam req, RespParam res) {
		return baseDao.delete(null, req.getEntity());
	}

	@Override
	public int deleteById(ReqParam req, RespParam res) {
		return baseDao.deleteById(null, req.getEntity());
	}

	@Override
	public int deleteByArrayIds(ReqParam req, RespParam res) {
		return baseDao.deleteByArrayIds(null, req.getEntity(), req.getArrayIds());
	}

	@Override
	public int findById(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findOne(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findList(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findListPage(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int batchInsert(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int batchUpdate(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int insertByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int updateByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int deleteByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findListByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findListPageByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

	@Override
	public int findOneByNativeSql(ReqParam req, RespParam res) {
		return 0;
	}

}
