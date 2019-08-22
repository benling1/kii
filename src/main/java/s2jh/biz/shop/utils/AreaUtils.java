package s2jh.biz.shop.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**   
 * @ClassName:  AreaUtils   
 * @Description:(创建一个提取出地区的/省,直辖市的工具)   
 * @author: jackstraw_yu
 * @date:   2017年4月6日 下午5:03:14   
 *     
 */ 
public class AreaUtils {
	
	private static List<String> areas = new ArrayList<String>(){
		private static final long serialVersionUID = 1613293972981552262L;
		{
			add("北京");add("天津");add("上海");add("重庆");
			add("北京市");add("天津市");add("上海市");add("重庆市");
			
			add("河北省");add("山西省");add("辽宁省");add("吉林省");
			add("黑龙江省");add("江苏省");add("浙江省");add("安徽省");add("福建省");
			add("江西省");add("山东省");add("河南省");add("湖北省");add("湖南省");
			add("广东省");add("海南省");add("四川省");add("贵州省");
			add("云南省");add("陕西省");add("甘肃省");add("青海省");
			
			add("内蒙古自治区");add("广西壮族自治区");add("西藏自治区");
			add("宁夏回族自治区");add("新疆维吾尔自治区");
			add("香港特别行政区");add("澳门特别行政区");
			add("台湾省");
		}
	};
	
	
	/**   
	 * @Title: getArea   
	 * @Description: (外部调用时 通过对详细地址进行split后的所得集合作为入参)   
	 * @param:citys
	 * @return: String      
	 * @date:   2017年4月6日 下午5:31:00 
	 * @author: jackstraw_yu  
	 */  
	public static String getArea(Collection<String> citys){
		/*示例:
		 * 北京 北京市 朝阳区 四惠东泰禾文化大厦b座510(100011)
		 * 广东省 云浮市 郁南县 都城镇柳城路41号首层1.2卡 （既都城市场对面菲尔国际名车中心）(527100)
		 * 
		 * **/
		String area = null;
		 if(citys!=null && citys.size()>0){
			 out:for (String s: citys) {
				if(areas.contains(s)){
					if(s.contains("市")){
						area = s.substring(0,s.length()-1);
					}else{
						area = s;
					}
					break;	
				}else{
					for (String state : areas) {
						if(state.equals(s) || state.contains(s)){
							area = state;
							break out;
						}
					}
				}
			}
		 }
		return area;
	}
	
	public static String getArea(String city){
		String area = null;
		if(city !=null && !"".equals(city)){
			for (String s : areas) {
				if(s.equals(city) || s.contains(city)){
					if(s.contains("市")){
						area = s.substring(0,s.length()-1);
					}else{
						area = s;
					}
				}
			}
		}
		return area;
	}
}
