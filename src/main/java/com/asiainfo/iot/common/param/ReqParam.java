package com.asiainfo.iot.common.param;

import java.io.Serializable;

import com.asiainfo.iot.common.param.page.Page;

public class ReqParam<T> implements Serializable {
    protected T param;
    
    protected T entity;
    
    protected T entityList;
    
    protected Page page;
    
    protected T[] arrayIds;

    public ReqParam() {
        super();
    }

    public ReqParam(T param) {
        this.param = param;
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T getEntityList() {
		return entityList;
	}

	public void setEntityList(T entityList) {
		this.entityList = entityList;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public T[] getArrayIds() {
		return arrayIds;
	}

	public void setArrayIds(T[] arrayIds) {
		this.arrayIds = arrayIds;
	}

}
