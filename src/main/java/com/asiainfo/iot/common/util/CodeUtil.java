package com.asiainfo.iot.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeUtil {

	
	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://10.1.228.240:2611/iot";  //
	String username = "iot";     //
	String password = "Iot@CentMysql132";   //
	String dbName = "iot";    //
	String tableName = "base_templet";  //
	
	String controllerTemplateFile = "C:\\controllerTemplate.java";  //mapper模板
	String mapperTemplateFile = "C:\\mapperTemplateFile.xml";  //mapper模板
	String outputpath = "C:\\"; //文件输出目录
	
	String controllerPackage="";
	String basePackageName = "com.asiainfo.iot.usercenter.client";  //
	String modelPackageName = basePackageName + ".model";
	String ServicePackageName = basePackageName + ".service";
	
	String mapperTemplate = "";
	String modelTemplate = "";
	String IserviceTemplate = "";
	String serviceTemplate = "";
	String tableComment="";
	String controllerTemplate = "";
	String mclassName = initcap(getFieldName(this.tableName));
	
	
	Map<String, String> types = new HashMap<String, String>();
	List<Entitys> entityslist = new ArrayList<Entitys>();;
	List<String> datetypes = new ArrayList<String>();
	Entitys pk = null;
	String enter = "\r\n";
	
	
	public static void main(String[] args) {
		new CodeUtil().generatorCode();
	}

	void generatorCode() {
		try {
			init();
			String sql = "SELECT lower(column_name) column_name,UPPER(data_type) data_type,column_comment,column_key,is_nullable ,character_maximum_length  ,(SELECT table_comment FROM information_schema.TABLES WHERE table_schema = '%s' AND table_name = '%s') table_comment        FROM information_schema.columns WHERE table_schema = '%s' AND table_name = '%s'" ;
			
			sql = String.format(sql, dbName, tableName,dbName, tableName);
			Class.forName(driver);
			Connection con = DriverManager.getConnection(url, username, password);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				String column_name = rs.getString("column_name");
				String data_type = rs.getString("data_type");
				String column_comment = rs.getString("column_comment");
				String column_key = rs.getString("column_key");
				String is_nullable = rs.getString("is_nullable");
				String character_maximum_length = rs.getString("character_maximum_length");
				tableComment = rs.getString("table_comment");
				
				Entitys en = new Entitys();
				en.setColumnName(column_name);
				en.setDataType(data_type);
				en.setComment(column_comment);
				if(character_maximum_length!=null && !"longblob".equalsIgnoreCase(data_type)){
					en.setMaxlength( Integer.parseInt(character_maximum_length) );
				}
				if ( column_key.contains("PRI")) {
					en.setPk(true);
					pk = en;
				}
				
				if("NO".equals(is_nullable)){
					en.setIsnullable(false);
				}
				
				entityslist.add(en);
				if (datetypes.contains(en.getDataType())) {
					en = new Entitys();
					en.setComment(column_comment + "_开始时间");
					en.setColumnName(column_name + "_start");
					en.setDataType(data_type);
					en.setIsadd(true);
					en.setOrgcolumnName(column_name);
					entityslist.add(en);
					en = new Entitys();
					en.setComment(column_comment + "_结束时间");
					en.setColumnName(column_name + "_end");
					en.setDataType(data_type);
					en.setOrgcolumnName(column_name);
					en.setIsadd(true);
					entityslist.add(en);
				}

				if ( en.getColumnName().endsWith("_enum") ) {
					en = new Entitys();
					en.setComment(column_comment + "_name");
					en.setColumnName(column_name + "_name");
					en.setDataType(data_type);
					en.setOrgcolumnName(column_name);
					en.setIsadd(true);
					entityslist.add(en);
				}
			}

			for (Entitys entitys : entityslist) {
				entitys.setFieldName(getFieldName(entitys.getColumnName()));
				entitys.setFieldType(getFieldType(entitys.getDataType()));
			}

			getModelStr();
			getMapperStr();
			getService();
			getServiceImpl();
			getController();
			
			PrintWriter pw = new PrintWriter(new File(outputpath+ this.mclassName + ".java"), "utf-8");
			pw.write(modelTemplate);
			pw.flush();
			
			pw = new PrintWriter(new File(outputpath + this.mclassName + "Mapper.xml"), "utf-8");
			pw.write(mapperTemplate);
			pw.flush();
			
			pw = new PrintWriter(new File(outputpath + "I"+this.mclassName + "Service.java"), "utf-8");
			pw.write(IserviceTemplate);
			pw.flush();
			
			pw = new PrintWriter(new File(outputpath +this.mclassName + "Service.java"), "utf-8");
			pw.write(serviceTemplate);
			pw.flush();
			
			pw = new PrintWriter(new File(outputpath +this.mclassName + "Controller.java"), "utf-8");
			pw.write(controllerTemplate);
			pw.flush();

			pw.close();

		} catch (Exception se) {
			se.printStackTrace();
		}

	}

	private void getController() {
		
		controllerTemplate = controllerTemplate.replaceAll("@controllerPackage@", controllerPackage);
		controllerTemplate = controllerTemplate.replaceAll("@modelComment@", tableComment);
		controllerTemplate = controllerTemplate.replaceAll("@modelName@", mclassName);
		controllerTemplate = controllerTemplate.replaceAll("@IService@", "I"+mclassName+"Service");
		controllerTemplate = controllerTemplate.replaceAll("@iService@", toLowerCaseFirstOne(mclassName+"Service"));
		
	}

	private void getServiceImpl() {
		StringBuilder service = new StringBuilder(); 
		service.append("package ");
		service.append(ServicePackageName).append(".impl;").append(enter).append(enter);
		
		service.append("import org.apache.log4j.Logger;").append(enter);;
		service.append("import org.springframework.stereotype.Service;").append(enter);;
		service.append("import org.springframework.transaction.annotation.Transactional;").append(enter);;
		
		service.append("import com.asiainfo.iot.common.service.BaseSevice;").append(enter);;
		service.append("import "+ServicePackageName+".I"+mclassName+"Service;").append(enter).append(enter);
		
		service.append("@Service").append(enter);;
		service.append("@Transactional").append(enter);;
		
		service.append("public class  ").append(mclassName).append("Service extends BaseSevice  implements I").append(mclassName).append("Service {").append(enter).append(enter)
		.append("\t  private static final Logger logger = Logger.getLogger("+mclassName+"Service.class);").append(enter).append(enter)
		.append("}");
		serviceTemplate = service.toString();
	}

	private void getService() {
		
		StringBuilder service = new StringBuilder(); 
		service.append("package ");
		service.append(ServicePackageName).append(enter).append(enter);
		service.append("import com.asiainfo.iot.common.service.IBaseSevice;").append(enter).append(enter);
		service.append("public interface I").append(mclassName).append("Service extends IBaseSevice{").append(enter).append(enter).append("}");
		IserviceTemplate = service.toString();
	}

	private void getMapperStr() {

		String BaseResultMapline = "\t\t<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" />";
		String BaseResultMapline2 = "\t\t<result column=\"%s\" property=\"%s\" jdbcType=\"%s\" javaType=\"byte[]\" typeHandler=\"org.apache.ibatis.type.BlobTypeHandler\" />";
		
		
		String whereline = "\t\t<if test=\"%s != null\"> \r\n " + "\t     and %s  \r\n" + "\t\t</if> ";
		String insertline = "\t\t#{%s,jdbcType=%s},";
		String updateline = "\t   %s = #{%s,jdbcType=%s},";
		String ByIdline = "where %s = #{%s,jdbcType=%s}";
		String ById = String.format(ByIdline, pk.getColumnName(), pk.getFieldName(), pk.getDataType());

		String modeclasspath = modelPackageName.concat(".").concat(this.mclassName);
		StringBuilder columns = new StringBuilder();
		StringBuilder BaseResultMapColumns = new StringBuilder();
		StringBuilder wherecolumns = new StringBuilder();
		StringBuilder insert = new StringBuilder();
		
		StringBuilder updateColumns = new StringBuilder();

		
		for (Entitys entitys : entityslist) {
			String columnName = entitys.getColumnName();
			String dataType = entitys.getDataType();
			String fieldName = entitys.getFieldName();
			String fieldType = entitys.getFieldType();
			String orgcolumnName = entitys.getOrgcolumnName();
			//
			String wherecolumnscompare = "=";

			if(!"Date".equals(fieldType)){
				String wcs3 = columnName + wherecolumnscompare + "#" + fieldName + "#";
				wherecolumns.append(String.format(whereline, fieldName, wcs3)).append(enter);
			}else{
				
				if ("Date".equals(fieldType) && (columnName.endsWith("_start"))) {
					wherecolumnscompare = " <![CDATA[ >= ]]> ";
				}
				if ("Date".equals(fieldType) && (columnName.endsWith("_end"))) {
					wherecolumnscompare = " <![CDATA[ <= ]]> ";
				}
				//"\t\t<if test=\"%s != null\"> \r\n " + "\t     and %s  \r\n" + "\t\t</if> ";
				String wcs3 = orgcolumnName + wherecolumnscompare + "#" + fieldName + "#";
				wherecolumns.append(String.format(whereline, fieldName, wcs3)).append(enter);
			}
			
			
			//
			if (!entitys.isadd){
				//
				if(!entitys.isIsadd()){
					columns.append(columnName).append(",");
				}
				//
				String basemapline = BaseResultMapline;
				if(entitys.getDataType().equals("BLOB")){
					basemapline =BaseResultMapline2;
				}
				BaseResultMapColumns.append(String.format(basemapline, columnName, fieldName, dataType)).append(enter);
				
				
				if("create_date".equalsIgnoreCase(entitys.getColumnName())){
					insert.append("\t\tnow(),").append(enter);
				}else if("ver".equalsIgnoreCase(entitys.getColumnName())){
					insert.append("\t\t1,").append(enter);
			    }else if("update_date".equalsIgnoreCase(entitys.getColumnName())){
			    	insert.append("\t\tnull,").append(enter);
			    }else{
			    	insert.append(String.format(insertline, fieldName, dataType)).append(enter);
			    }
				
				//
			    if("update_date".equalsIgnoreCase(entitys.getColumnName())){
			    	updateColumns.append("\t\tupdate_date = now(),").append(enter);
			    }else if("ver".equalsIgnoreCase(entitys.getColumnName())){
			    	updateColumns.append("\t\tver = #{ver}+1,").append(enter);
			    }else{
			    	updateColumns.append(String.format(updateline, columnName, fieldName, dataType)).append(enter);
			    }
			}
			
			
		}
		columns.deleteCharAt(columns.length() - 1);
		insert.deleteCharAt(insert.length() - 3);
		insert.append("\t\t)").append(enter);

		updateColumns.deleteCharAt(updateColumns.length() - 3);
		
		
		mapperTemplate = mapperTemplate.replaceAll("@ById@", ById);
		mapperTemplate = mapperTemplate.replaceAll("@updateColumns@", updateColumns.toString());
		mapperTemplate = mapperTemplate.replaceAll("@tableName@", tableName);
		mapperTemplate = mapperTemplate.replaceAll("@BaseResultMapColumns@", BaseResultMapColumns.toString());
		mapperTemplate = mapperTemplate.replaceAll("@columns@", columns.toString());
		mapperTemplate = mapperTemplate.replaceAll("@modeclasspath@", modeclasspath);
		mapperTemplate = mapperTemplate.replaceAll("@wherecolumns@", wherecolumns.toString());
		mapperTemplate = mapperTemplate.replaceAll("@insert@", insert.toString());

	}

	/**
	 * 功能：生成实体类主体代码
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private void getModelStr() {
		StringBuffer sb = new StringBuffer();
		// 生成package包路径
		sb.append("package " + this.modelPackageName + ";\r\n");
		sb.append("\r\n");
		sb.append("import javax.validation.constraints.NotEmpty;").append(enter);
		sb.append("import javax.validation.constraints.NotNull;").append(enter);
		sb.append("import javax.validation.constraints.Size;").append(enter);
		sb.append("import com.asiainfo.iot.common.model.Entity;").append(enter);
		
		// 注释部分
		sb.append(" /**\r\n");
		sb.append(" * @文件名称：" + getFieldName(this.tableName) + ".java\r\n");
		sb.append(" * @创建时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n");
		sb.append(" * @文件描述：" + this.tableName +" "+ this.tableComment + " \r\n");
		sb.append(" */ \r\n");
		// 实体部分
		//sb.append("\t@ApiModel(description = \""+tableComment+"\") \r\n");
		sb.append("public class " + this.mclassName + " extends Entity {\r\n");
		processAllAttrs(sb);
		processAllMethod(sb);
		sb.append("}\r\n");
		modelTemplate = sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (Entitys entitys : entityslist) {
			sb.append("\t/**\r\n");
			sb.append("\t* ").append(entitys.getComment()).append("\r\n");
			sb.append("\t*/ \r\n");
			if(!entitys.isnullable){
				sb.append("\t@NotNull(message =\""+entitys.getComment()+"不能为空"+"\") \r\n");
				sb.append("\t@NotEmpty(message =\""+entitys.getComment()+"不能为空"+"\") \r\n");
			}
			if(entitys.getMaxlength()!=null){
				sb.append("\t@Size(max="+entitys.getMaxlength()+" ,message =\""+entitys.getComment()+"最多"+entitys.getMaxlength()+"个字符\") \r\n");
			}
			//sb.append("\t@ApiModelProperty(value=\""+entitys.getComment()+",name=\""+entitys.getFieldName()+"\") \r\n");
			sb.append("\tprivate " + entitys.getFieldType() + " " + entitys.getFieldName() + ";\r\n\r\n");
		}
		
	}

	/**
	 * 功能：生成所有方法
	 */
	private void processAllMethod(StringBuffer sb) {
		for (Entitys entitys : entityslist) {
			String fieldName = entitys.getFieldName();
			String fieldType = entitys.getFieldType();
			sb.append("\tpublic void set" + initcap(fieldName) + "(" + fieldType + " " + fieldName + "){\r\n");
			sb.append("\t\tthis." + fieldName + "=" + fieldName + ";\r\n");
			sb.append("\t}\r\n\r\n");
			sb.append("\tpublic " + fieldType + " get" + initcap(fieldName) + "(){\r\n");
			sb.append("\t\treturn " + fieldName + ";\r\n");
			sb.append("\t}\r\n");
		}
		
		sb.append(enter);
		sb.append("\t@Override").append(enter);
		sb.append("\tpublic String toString() {").append(enter);
		sb.append("\t   return \""+this.mclassName+" [");
		for (Entitys entitys : entityslist) {
			sb.append(entitys.getFieldName()).append("=\" + ").append(entitys.getFieldName())
			.append("  + \",");
		}
		
		sb.deleteCharAt(sb.length()-1);
		sb.append(" ]\";").append(enter);
		
		sb.append("\t}").append(enter);
		
		
	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	
	//首字母转小写
	public  String toLowerCaseFirstOne(String s){
	  if(Character.isLowerCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	String getFieldName(String columnName) {
		if (columnName.indexOf("_") == -1) {
			return columnName;
		}
		String[] strs = columnName.split("_");
		for (int i = 0; i < strs.length; i++) {
			if (i > 0) {
				strs[i] = initcap(strs[i]);
			}
		}
		return String.join("", strs);
	}

	String getFieldType(String dataType) {
		return types.get(dataType);
	}

	void init() throws Exception {
		types.put("CHAR", "String");
		types.put("VARCHAR", "String");
		types.put("BIGINT", "Long");
		types.put("TINYINT", "Byte");
		types.put("DATE", "Date");
		types.put("DATETIME", "Date");
		types.put("DOUBLE", "Double");
		types.put("INT", "Integer");
		types.put("TIMESTAMP", "Date");
		types.put("DECIMAL", "BigDecimal");
		types.put("ENUM", "String");
		types.put("FLOAT", "Float");
		types.put("ENUM", "String");
		types.put("LONGVARCHAR", "String");
		types.put("BLOB", "byte[]");
		types.put("LONGBLOB", "byte[]");
		types.put("TEXT", "String");
		types.put("LONGVARBINARY", "byte[]");
		types.put("VARBINARY", "byte[]");
		datetypes.add("DATE");
		datetypes.add("DATETIME");
		datetypes.add("TIMESTAMP");

		StringBuilder sb = new StringBuilder("");
		BufferedReader bReader = new BufferedReader(new FileReader(new File(mapperTemplateFile)));
		String line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		mapperTemplate = sb.toString();
		
		bReader.close();
		
		sb.setLength(0);
		bReader = new BufferedReader(new FileReader(new File(controllerTemplateFile)));
		line = null;
		while ((line = bReader.readLine()) != null) {
			sb.append(line).append(enter);
		}
		controllerTemplate = sb.toString();

	}

	static class Entitys {
		String fieldName;
		String fieldType;
		String columnName;
		String orgcolumnName;
		String dataType;
		String comment;
		boolean isnullable = true;
		boolean isPk = false;
		boolean isadd = false;
		Integer maxlength ;

		
		public String getOrgcolumnName() {
			return orgcolumnName;
		}

		public void setOrgcolumnName(String orgcolumnName) {
			this.orgcolumnName = orgcolumnName;
		}

		public boolean isIsadd() {
			return isadd;
		}

		public void setIsadd(boolean isadd) {
			this.isadd = isadd;
		}

		public Integer getMaxlength() {
			return maxlength;
		}

		public void setMaxlength(Integer maxlength) {
			this.maxlength = maxlength;
		}

		public boolean isIsnullable() {
			return isnullable;
		}

		public void setIsnullable(boolean isnullable) {
			this.isnullable = isnullable;
		}

		public boolean isPk() {
			return isPk;
		}

		public void setPk(boolean isPk) {
			this.isPk = isPk;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			this.dataType = dataType;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getFieldName() {
			return fieldName;
		}

		public void setFieldName(String fieldName) {
			this.fieldName = fieldName;
		}

		public String getFieldType() {
			return fieldType;
		}

		public void setFieldType(String fieldType) {
			this.fieldType = fieldType;
		}


	}
}
