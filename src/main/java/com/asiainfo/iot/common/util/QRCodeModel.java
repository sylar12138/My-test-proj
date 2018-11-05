package com.asiainfo.iot.common.util;

import java.io.File;
/**
 * 二维码实体类
 * @Package: kq_pmcs com.ailk.utils
 * @ClassName: QRCodeModel
 * @Description:TODO(描述这个类的作用) 
 * @see:<引用此类的类classpath>
 * @Author: 41620
 * @Date: 2017-7-12 下午2:37:43
 * @Modified By 
 * @Modified By：   <修改人中文名或拼音缩写>                                         
 * @Modified Date:  <修改日期，格式:YYYY-MM-DD>                                    
    * Why & What is modified  <修改原因描述>  
	* 需求名称 /jira编号  
 * Version:                  <版本号>     
 * see      对类、属性、方法的说明 参考转向，也就是相关主题 (用于说明与之相关的类，比如父类、子类)
 */
public class QRCodeModel {

	private String contents;
	private int width = 400;
	private int height = 400;
	private String format = "gif";
	private String character_set = "utf-8";
	private int fontSize = 15;
	private File logoFile;
	private float logoRatio = 0.20f;
	private String[] desc;
	private int whiteWidth=2;//白边的宽度
	private int[] bottomStart;//二维码最下边的开始坐标
	private int[] bottomEnd;//二维码最下边的结束坐标

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCharacter_set() {
		return character_set;
	}

	public void setCharacter_set(String character_set) {
		this.character_set = character_set;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	public File getLogoFile() {
		return logoFile;
	}

	public void setLogoFile(File logoFile) {
		this.logoFile = logoFile;
	}

	public float getLogoRatio() {
		return logoRatio;
	}

	public void setLogoRatio(float logoRatio) {
		this.logoRatio = logoRatio;
	}

	public String[] getDesc() {
		return desc;
	}

	public void setDesc(String[] desc) {
		this.desc = desc;
	}

	public int getWhiteWidth() {
		return whiteWidth;
	}

	public void setWhiteWidth(int whiteWidth) {
		this.whiteWidth = whiteWidth;
	}

	public int[] getBottomStart() {
		return bottomStart;
	}

	public void setBottomStart(int[] bottomStart) {
		this.bottomStart = bottomStart;
	}

	public int[] getBottomEnd() {
		return bottomEnd;
	}

	public void setBottomEnd(int[] bottomEnd) {
		this.bottomEnd = bottomEnd;
	}
}