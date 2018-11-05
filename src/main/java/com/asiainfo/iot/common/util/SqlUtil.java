package com.asiainfo.iot.common.util;

import java.util.List;

/**
 * sql工具类
 * @author wangyy
 */
public class SqlUtil {
	
	/** 
	 * 把传入的字符串 转化成where x in ('x','y','z') 的形式，
	 * @param str  1,2,3,4
	 * @return	('1','2','3','4')
	 * @return String  
	 */  
	public static String getSqlWhereIn(String str){
		if (str == null || "".equals(str)) return "('')";
		
		if (str.indexOf(",") > 0){
			String[] list= str.split(",");
			StringBuilder sb = new StringBuilder("(");
			for (int i=0, j=list.length; i<j; i++){
				sb.append("'"+list[i]+"'");
				if (i != list.length - 1){
					sb.append(",");
				}
			}
			sb.append(")");
			return sb.toString();
		}else{
			return "('"+str+"')";
		}
	}
	
	/** 
	 * 把传入的List 转化成where x in ('x','y','z') 的形式
	 * @param str  [1,2,3,4]
	 * @return	('1','2','3','4')
	 * @return String  
	 */  
	public static String getSqlWhereIn(List list){
		if (list == null || list.size() < 1) return "('')";
		
		if (list.size() > 1){
			StringBuilder sb = new StringBuilder("(");
			for (int i=0, j=list.size(); i<j; i++){
				sb.append("'"+list.get(i)+"'");
				if (i != list.size() - 1){
					sb.append(",");
				}
			}
			sb.append(")");
			return sb.toString();
		}else{
			return "('"+list.get(0)+"')";
		}
	}
	
	/** 
	 * 把传入的List 转化成where FIND_IN_SET ('x','x,y,z') 的形式
	 * @param str  [1,2,3,4]
	 * @return	x,y,z
	 * @return String  
	 */  
	public static String findInSet(List list){
		if (list == null || list.size() < 1) return "";
		
		if (list.size() > 1){
			StringBuilder sb = new StringBuilder();
			for (int i=0, j=list.size(); i<j; i++){
				sb.append(list.get(i));
				if (i != list.size() - 1){
					sb.append(",");
				}
			}
			return sb.toString();
		}else{
			return list.get(0)+"";
		}
	}
	
	/** 
	 * 把传入的List 转化成where FIND_IN_SET ('x','x,y,z') 的形式
	 * @param str  [1,2,3,4]
	 * @return	'x,y,z'
	 * @return String  
	 */  
	public static String findInSetStr(List list){
		if (list == null || list.size() < 1) return "''";
		
		if (list.size() > 1){
			StringBuilder sb = new StringBuilder("'");
			for (int i=0, j=list.size(); i<j; i++){
				sb.append(list.get(i));
				if (i != list.size() - 1){
					sb.append(",");
				}
			}
			sb.append("'");
			return sb.toString();
		}else{
			return "'"+list.get(0)+"'";
		}
	}

	/**
	 * 返回yyyy-MM-dd格式
	 * @param column 要格式化的列名
	 * @return
	 */
	public static String formatyyyyMMddWithBar(String column){
		try {
			if(isMysql()) {
				return "date_format(" + column +", '%Y-%m-%d')";
			} else {
				return "convert(varchar(10), " + column + ", 23)";
			}
		} catch (Exception e) {
			return "date_format(" + column +", '%Y-%m-%d')";
		}
	}

	/**
	 * 返回yyyy-MM格式
	 * @param column 要格式化的列名
	 * @return
	 */
	public static String formatyyyyMMWithBar(String column){
		try {
			if(isMysql()) {
				return "date_format(" + column +", '%Y-%m')";
			} else {
				return "convert(varchar(7), " + column + ", 23)";
			}
		} catch (Exception e) {
			return "date_format(" + column +", '%Y-%m')";
		}
	}

	/**
	 * 返回指定日期减去时间间隔后的日期
	 * @param column 要格式化的列名
	 * @return
	 */
	public static String dateAfterSub(String days){
		try {
			if(isMysql()) {
				return "date_sub(now(), interval "+days+" day)";
			} else {
				return " DATEADD(day,-"+days+",getdate())";
			}
		} catch (Exception e) {
			return "date_sub(now(), interval "+days+" day)";
		}
	}
	
	/**
	 * 字符串转化成日期类型
	 * @param column 要格式化的列名
	 * @return
	 */
	public static String strToDate(String column){
		try {
			if(isMysql()) {
				return "str_to_date("+column+",'%Y-%m-%d %H:%i:%s')";
			} else {
				return " cast("+column+" as datetime)";
			}
		} catch (Exception e) {
			return "str_to_date("+column+",'%Y-%m-%d %H:%i:%s')";
		}
	}
	
	/**
	 * 返回字符串长度
	 * @param column
	 * @return
	 */
	public static String getStrLength(String column){
		try {
			if(isMysql()) {
				return " length("+column+")";
			} else {
				return " len("+column+")";
			}
		} catch (Exception e) {
			return " length("+column+")";
		}
	}
	
	/**
	 * 判断是否mysql
	 * @return
	 */
	public static boolean isMysql(){
		return true;
	}
}
	
