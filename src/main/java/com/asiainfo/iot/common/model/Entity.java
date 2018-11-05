package com.asiainfo.iot.common.model;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 实体支持序列化
 * 
 * 
 */
public class Entity implements Serializable {

	@NotEmpty
	@NotNull
	@Size
	private int pageIndex = 1;
	private int pageSize = 10;
	private int totalCount = 0;

	private int pageOffset = 0;
	private int pageLimit = 10;
	private boolean pageIsCount = false;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageOffset() {
		return pageOffset;
	}

	public void setPageOffset(int pageOffset) {
		this.pageOffset = pageOffset;
	}

	public int getPageLimit() {
		return pageLimit;
	}

	public void setPageLimit(int pageLimit) {
		this.pageLimit = pageLimit;
	}

	public boolean isPageIsCount() {
		return pageIsCount;
	}

	public void setPageIsCount(boolean pageIsCount) {
		this.pageIsCount = pageIsCount;
	}

}
