package com.asiainfo.iot.common.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * 用于判断是否是图片
 * 注意：实际上还应该进行格式判断，但是格式应该在调用该工具之前就应该判断。所以这里就没有图片格式判断了
 * @author ZhangLiang
 * 
 * 2014/12/02 创建该类，适用于 jpg、bmp、gif、png、jpeg 格式
 */
public class ImageUtil {
	/**
	 * 通过路径，判断是否是图片
	 * @author ZhangLiang
	 * @param filePath
	 * @return
	 * @throws IOException 
	 */
	public static boolean isImagePath(String filePath) throws IOException{
		if(filePath == null || "".equals(filePath)){
			return false;
		}
		
		InputStream is = new FileInputStream(filePath);
		byte[] b = new byte[20];
		is.read(b);
		is.close();
		
		return isImage(b);
	}
	
	/**
	 * 通过流，判断是否是图片
	 * @author ZhangLiang
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	public static boolean isImageStream(InputStream is) throws IOException{
		if(is == null){
			return false;
		}
		
		byte[] b = new byte[20];
		is.read(b);
		is.close();
		
		return isImage(b);
	}
	
	/**
	 * 用于图片格式判断
	 * @author ZhangLiang
	 * @param picName
	 * @return
	 */
	public static boolean picPattern(String picName){
		if(picName == null || "".equals(picName)){
			return false;
		}
		
		if (".jpg".equalsIgnoreCase(picName)
				|| ".bmp".equalsIgnoreCase(picName)
				|| ".gif".equalsIgnoreCase(picName)
				|| ".png".equalsIgnoreCase(picName)
				|| ".jpeg".equalsIgnoreCase(picName)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 获取格式判断
	 * @author ZhangLiang
	 * @param b
	 * @return
	 */
	private static boolean isImage(byte[] b){
		if((char)b[1] == 'P' && (char)b[2] == 'N' && (char)b[3] == 'G'){
			return true;	// png
		}
		if((char)b[0] == 'G' && (char)b[1] == 'I' && (char)b[2] == 'F'){
			return true;	// gif
		}
		if((char)b[6] == 'J' && (char)b[7] == 'F' && (char)b[8] == 'I' && (char)b[9] == 'F'){
			return true;	// jpg, jpeg
		}
		if((char)b[0] == 'B' && (char)b[1] == 'M'){
			return true;	// bmp
		}
		return false;
	}
	
	
	/**
	 * 截取字符串用于已发布商品详情页面
	 * @param str
	 * @return
	 */
	public static String subUrlStr(String str){
		String str1 = str.toLowerCase();
		if(str1.contains(".jpg")){
			str = str.substring(0, str1.lastIndexOf(".jpg")+4);
		}else if(str1.contains(".bmp")){
			str = str.substring(0, str1.lastIndexOf(".bmp")+4);
		}else if(str1.contains(".gif")){
			str = str.substring(0, str1.lastIndexOf(".gif")+4);
		}else if(str1.contains(".png")){
			str = str.substring(0, str1.lastIndexOf(".png")+4);
		}else if(str1.contains(".jpeg")){
			str = str.substring(0, str1.lastIndexOf(".jpeg")+4);
		}
		return str;
	}
	
	/**
	 * 判断是否包含图片信息
	 * @param picName
	 * @return
	 */
	public static boolean isContainPic(String picName){
		if(picName == null || "".equals(picName)){
			return false;
		}
		
		if(picName.toLowerCase().contains(".jpg") || picName.toLowerCase().contains(".bmp") 
				|| picName.toLowerCase().contains(".gif") || picName.toLowerCase().contains(".png")
				|| picName.toLowerCase().contains(".jpeg")){
			return true;
		}
		
		return false;
	}
	
	 /**
     * 缩放图像（按比例缩放）
     */
     public static byte[] scale(byte[] btyes, float scale) throws IOException{
   	  InputStream in = new ByteArrayInputStream(btyes);
   	  ImageInputStream iis = ImageIO.createImageInputStream(in);
   	  Iterator iterator = ImageIO.getImageReaders(iis);
   	  // Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
   	  ImageReader reader = (ImageReader) iterator.next();
   	  reader.setInput(iis, true);
   	  BufferedImage srcImg = reader.read(0);

   	  int width = srcImg.getWidth(); 	// 得到源图宽
   	  int height = srcImg.getHeight(); 	// 得到源图长

   	  // 计算新的宽度和高度
   	  width = Integer.valueOf(new Float(width * scale).intValue());
   	  height = Integer.valueOf(new Float(height * scale).intValue());

   	  Image image = srcImg.getScaledInstance(width, height, Image.SCALE_DEFAULT);
   	  BufferedImage destImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
   	  Graphics g = destImg.getGraphics();
   	  g.drawImage(image, 0, 0, null); // 绘制缩小后的图
   	  g.dispose();

   	  ByteArrayOutputStream out = new ByteArrayOutputStream();
   	  ImageIO.write(destImg, "jpg", out);
   	  return out.toByteArray();
     }
}
