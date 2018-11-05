package com.asiainfo.iot.common.model;

/**
 * 外部sql
 * 
 * 
 */
public class NativeSql extends Entity {

	private String sql;

	public NativeSql() {
	}

	public NativeSql(String sql) {
		this.sql = sql;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

}
