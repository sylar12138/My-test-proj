package com.asiainfo.iot.common.util;

import java.io.IOException;
import java.util.Properties;
/**
 * 读取hdfs的properties配置文件
 * @author ht
 *
 */
public class ReadHdfsPropertiesUtil {
	public static String hdfsUri="";
	/**
	 * 读取HdfsUri
	 * @return
	 */
	public static String getHdfsUri() {
		Properties props = new Properties();
		try {
			props.load(ReadHdfsPropertiesUtil.class
					.getResourceAsStream("/config/hdfs/hdfs.properties"));
			hdfsUri = props.getProperty("hdfsUri");
		} catch (IOException e) {
			hdfsUri = null;
			e.printStackTrace();
		}
		return hdfsUri;
	}
	/**
	 * 根据key值获取值
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value="";
		Properties props = new Properties();
		try {
			props.load(ReadHdfsPropertiesUtil.class
					.getResourceAsStream("/config/hdfs/hdfs.properties"));
			value = props.getProperty(key);
		} catch (IOException e) {
			value = null;
			e.printStackTrace();
		}
		return value;
	}
	public static void main(String[] args) {
	String a=	ReadHdfsPropertiesUtil.getHdfsUri();
	System.out.println(a);
	}
}
