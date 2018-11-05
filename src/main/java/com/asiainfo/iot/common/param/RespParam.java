package com.asiainfo.iot.common.param;

import java.io.Serializable;

import com.asiainfo.iot.common.param.page.Page;

public class RespParam<T> implements Serializable {
    protected T data;
    protected T entity;
    protected T entityList;
    protected Page page;

    public RespParam() {
        super();
    }

    public RespParam(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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
    
}
