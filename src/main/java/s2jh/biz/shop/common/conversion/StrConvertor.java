package s2jh.biz.shop.common.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
* @ClassName: StrConvertor
* @Description: (转换器:String字符串 将两端空格直接trim(),如果为"   "直接返回null)
* @author:jackstraw_yu
* @date 2017年08月04日
*
*/
public class StrConvertor  implements Converter<String, String>  {

	private static Logger logger = LoggerFactory.getLogger(StrConvertor.class);
	
	@Override
	public String convert(String source) {
		try {
			// "  ",""等字符串全部返回null
			if(null == source || "".equals(source.trim()))
				return null;
			return source.trim();
		} catch (Exception e) {
			logger.error("转换器工作失败!!");
		}
		return null;
	}
}
