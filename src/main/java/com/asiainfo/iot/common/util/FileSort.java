package com.asiainfo.iot.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 文件排序
 */
public class FileSort {

	public static List<Map<String, String>> orderFileByType(String type,List<Map<String, String>> list ) {
		if(type.equals("1")){
			//按时间排序
			Collections.sort(list, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
					Date date1 = null;
					Date date2 = null;
					try {
						date1 = sdf.parse(o1.get("modifyDate"));
						date2 = sdf.parse(o2.get("modifyDate"));
					} catch (Exception e) {

					}
					int value = 0;
					if (date1.getTime() - date2.getTime() < 0)
						value = 1;
					else
						value = -1;
					return value;
				}

			});
		}else if(type.equals("2")){
			//按名称
			Collections.sort(list, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					int result=0;
					if (o1.get("isDir").equals("1") && o2.get("isDir").equals("0"))
						result= -1;
					if (o1.get("isDir").equals("0") && o2.get("isDir").equals("1"))
						result= 1;
					if (o1.get("isDir").equals("1") && o2.get("isDir").equals("1"))
						result= o1.get("dirName").compareTo(o2.get("dirName"));
					if (o1.get("isDir").equals("0") && o2.get("isDir").equals("0"))
						result= o1.get("fileName").compareTo(o2.get("fileName"));
					return result;
				}
			   });
		}else if(type.equals("3")){
			//按大小
			Collections.sort(list, new Comparator<Map<String, String>>() {
				public int compare(Map<String, String> o1, Map<String, String> o2) {
					long diff = Long.valueOf(o1.get("fileLen")) - Long.valueOf(o2.get("fileLen")) ;
					if (diff > 0)
					  return 1;
					else if (diff == 0)
					  return 0;
					else
					  return -1;
				}
			});
		}
		return list;
	}
}
