package com.asiainfo.iot.common.dao;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asiainfo.iot.common.model.Entity;
import com.asiainfo.iot.common.model.NativeSql;
import com.asiainfo.iot.common.param.page.Page;
import com.asiainfo.iot.common.util.Constants;

@Repository
@Transactional
public class BaseDao<E> extends SqlSessionDaoSupport implements IBaseDao {

	private static final Logger logger = Logger.getLogger(BaseDao.class);

	@Resource(name = "sqlSessionFactory")
	private SqlSessionFactory sqlSessionFactory;

	@PostConstruct
	public void SqlSessionFactory() {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	@Override
	public int insert(String mapperId, Object entity) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_INSERT;
		}
		i = sqlSession.insert(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int update(String mapperId, Object entity) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_UPDATE;
		}
		i = sqlSession.update(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int updateById(String mapperId, Object entity) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_UPDATEBYID;
		}
		i = sqlSession.update(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int delete(String mapperId, Object entity) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_DELETE;
		}
		i = sqlSession.delete(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int deleteById(String mapperId, Object entity) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_DELETEBYID;
		}
		i = sqlSession.delete(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int deleteByArrayIds(String mapperId, Object entity, Object arrayIds[]) {
		int i = 0;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_DELETEBYARRAYID;
		}
		i = sqlSession.delete(entity.getClass().getName() + "." + mapperId, arrayIds);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public Object findById(String mapperId, Object entity) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		E model = null;
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_FINDBYID;
		}
		model = sqlSession.selectOne(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return model;
	}

	@Override
	public List findList(String mapperId, Object entity) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<E> list = null;
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_FINDLIST;
		}
		list = sqlSession.selectList(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return list;
	}

	@Override
	public List findListPage(String mapperId, Object entity, Page page) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<E> list = null;
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_FINDLISTPAGE;
		}
		Entity entityPage = (Entity)entity;
		entityPage.setPageOffset((page.getPageIndex()-1)*10);
		entityPage.setPageLimit(page.getPageSize());
		entityPage.setPageIsCount(true);
		Entity entityPageCount = sqlSession.selectOne(entity.getClass().getName() + "." + mapperId, entity);
		page.setTotalCount(entityPageCount.getTotalCount());
		entityPage.setPageIsCount(false);
		list = sqlSession.selectList(entity.getClass().getName() + "." + mapperId, entity);
		page.setDataList(list);		
		sqlSession.flushStatements();
		sqlSession.close();
		return list;
	}

	@Override
	public Object findOne(String mapperId, Object entity) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		E model = null;
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_FINDLIST;
		}
		model = sqlSession.selectOne(entity.getClass().getName() + "." + mapperId, entity);
		sqlSession.flushStatements();
		sqlSession.close();
		return model;
	}

	@Override
	public int batchInsert(String mapperId, List list) {
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		int i = 0;
		if (StringUtils.isBlank(mapperId)) {
			mapperId = Constants.MAPPER_ID_INSERT;
		}
		for (int cnt = list.size(); i < cnt; i++) {
			sqlSession.insert(list.get(i).getClass().getName() + "." + mapperId, list.get(i));
		}
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int batchUpdate(String mapperId, List list) {
		SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
		int i = 0;
		for (int cnt = list.size(); i < cnt; i++) {
			sqlSession.update(list.get(i).getClass().getName() + "." + mapperId, list.get(i));
		}
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int insertByNativeSql(String sql) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int i = 0;
		i = sqlSession.insert("com.asiainfo.iot.common.model.NativeSql.insertNativeSql", nativeSql);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;

	}

	@Override
	public int updateByNativeSql(String sql) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int i = 0;
		i = sqlSession.update("com.asiainfo.iot.common.model.NativeSql.updateNativeSql", nativeSql);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}

	@Override
	public int deleteByNativeSql(String sql) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int i = 0;
		i = sqlSession.delete("com.asiainfo.iot.common.model.NativeSql.deleteNativeSql", nativeSql);
		sqlSession.flushStatements();
		sqlSession.close();
		return i;
	}
	
	@Override
	public List<HashMap> findListByNativeSql(String sql) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<HashMap> list = null;
		list = sqlSession.selectList("com.asiainfo.iot.common.model.NativeSql.findListNativeSql", nativeSql);
		sqlSession.flushStatements();
		sqlSession.close();
		return list;
	}
	
	@Override
	public List<HashMap> findListPageByNativeSql(String sql, Page page) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<HashMap> list = null;
		nativeSql.setPageOffset((page.getPageIndex()-1)*10);
		nativeSql.setPageLimit(page.getPageSize());
		nativeSql.setPageIsCount(true);
		Entity entityPageCount = sqlSession.selectOne("com.asiainfo.iot.common.model.NativeSql.findListPageNativeSql", nativeSql);
		page.setTotalCount(entityPageCount.getTotalCount());
		nativeSql.setPageIsCount(false);
		list = sqlSession.selectList("com.asiainfo.iot.common.model.NativeSql.findListPageNativeSql", nativeSql);
		page.setDataList(list);		
		sqlSession.flushStatements();
		sqlSession.close();
		return list;
	}

	@Override
	public HashMap findOneByNativeSql(String sql) {
		NativeSql nativeSql = new NativeSql(sql);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		HashMap model = null;
		model = sqlSession.selectOne("com.asiainfo.iot.common.model.NativeSql.selectNativeSql", nativeSql);
		sqlSession.flushStatements();
		sqlSession.close();
		return model;
	}


}