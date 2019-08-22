package s2jh.biz.shop.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberRegUtils {

	/**
	 * 判断字符串是否为数字
	 * @Title: isNumeric 
	 * @param @return 设定文件 
	 * @return boolean 返回类型 
	 * @throws
	 */
	public static boolean isNumeric(String str){
		if(str == null || "".equals(str)){
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher matcher = pattern.matcher(str);
		if(!matcher.matches()){
			return false;
		}
		return true;
	}
}
