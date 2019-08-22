package s2jh.biz.shop.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 获得分钟、小时或天数的时间
 * @author Administrator
 *
 */
public class DateUtils {
	
	// 精确的时间格式化
	public static final String DETAIL_DATE_FORMAT = "yyyyMMddHHmmss";

	/**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(DETAIL_DATE_FORMAT);
		return df.format(date);
	}
	/**
	 * 获得某个时间几个月后或几个月前的时间(返回的是字符串型的时间，输入的是String day, int x)
	 * @param day	当前时间字符串类型
	 * @param x		正数为x个月之后，负数为x个月之前
	 * @return
	 */
	public static String getTimeByMoth(String day, int x){   
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
	    //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
	
	    Date date = null;   
	    try {   
	        date = format.parse(day);   
	    } catch (Exception ex) {   
	        ex.printStackTrace();   
	    }   
	    if (date == null)   
	        return "";   
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(date);   
	    cal.add(Calendar.MONTH, x);//24小时制   
	    date = cal.getTime();   
	    cal = null;   
	    return format.format(date);   
	    } 
	
	/**
	 * 获得某个时间几分钟后或几分钟前的时间(返回的是字符串型的时间，输入的是String day, int x)
	 * @param day
	 * @param x
	 * @return
	 */
	public static String getTimeByMinut(String day, int x){   
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
	    //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
	
	    Date date = null;   
	    try {   
	        date = format.parse(day);   
	    } catch (Exception ex) {   
	        ex.printStackTrace();   
	    }   
	    if (date == null)   
	        return "";   
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(date);   
	    cal.add(Calendar.MINUTE, x);//24小时制   
	    date = cal.getTime();   
	    cal = null;   
	    return format.format(date);   
	    } 
	/**
	 * 获得某个时间几小时后或几小时前的时间(返回的是字符串型的时间，输入的是String day, int x)
	 * @param day
	 * @param x
	 * @return
	 */
	public static String getTimeByHour(String day, int x){   
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
	    //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
	
	    Date date = null;   
	    try {   
	        date = format.parse(day);   
	    } catch (Exception ex) {   
	        ex.printStackTrace();   
	    }   
	    if (date == null)   
	        return "";   
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(date);   
	    cal.add(Calendar.HOUR_OF_DAY, x);//24小时制   
	    date = cal.getTime();   
	    cal = null;   
	    return format.format(date);   
    } 
	
	/**
	 * 获得某个时间几天后或几天前的时间(返回的是字符串型的时间，输入的是String day, int x)
	 * @param day
	 * @param x
	 * @return
	 */
	public static String getTimeByDay(String day, int x){   
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制  
	    //引号里面个格式也可以是 HH:mm:ss或者HH:mm等等，很随意的，不过在主函数调用时，要和输入的变量day格式一致
	
	    Date date = null;   
	    try {   
	        date = format.parse(day);   
	    } catch (Exception ex) {   
	        ex.printStackTrace();   
	    }   
	    if (date == null)   
	        return "";   
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(date);   
	    cal.add(Calendar.DATE, x);//24小时制   
	    date = cal.getTime();   
	    cal = null;   
	    return format.format(date);   
    } 
	/**
	 * 获得某个时间几天后或几天前的时间(返回的是字符串型的时间，输入的是Date day, int x)
	 * @param day
	 * @param x
	 * @return
	 */
	public static Date getTimeByDay(Date day, int x){   
	    if (day == null)
	        return null;   
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(day);   
	    cal.add(Calendar.DATE, x);//24小时制   
	    day = cal.getTime();   
	    cal = null;   
	    return day;   
    } 
	/**
	 * 定义一个日期的工具转换类("yyyy-MM-dd HH:mm:ss")
	 * @param time
	 * @return
	 */
	public static Date convertDate(String time){
		
		Date Ctime = null;//结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(time!=null && !"".equals(time)){
			try {
				Ctime = dateFormat.parse(time);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return Ctime;
		
	}
	 /**
	  * 获取现在时间
	  * 
	  * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	  */
	public static Date getNowDate(){
		  Date currentTime = new Date();
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  String dateString = formatter.format(currentTime);
		  Date parse = null;
		  try {
			parse = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return parse;
	}
	/**
	 * 将yyyy-MM-dd格式的string转成date
	 * @param date
	 * @return
	 */
	public static Date convertStringToDate(String date){
		Date d = null;
		
		if(date!= null && !date.equals("")){
			try {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
				d = f.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d;
	}
	
	/**
	 * 将yyyy-MM-dd格式的string转成date
	 * @param date
	 * @return
	 */
	public static Date convertStringToDateNoSS(String date){
		Date d = null;
		
		if(date!= null && !date.equals("")){
			try {
				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				d = f.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return d;
	}
	
	
	/**
	    *@Title:getDiffDay
	    *@Description:获取日期相差天数
	    *@param:@param beginDate Date类型开始日期
	    *@param:@param endDate   Date类型结束日期
	    *@param:@return
	    *@return:Long            相差天数
	    *@author: 谢
	    *@thorws:
	    */
	    public static Long getDiffDay(Date beginDate, Date endDate) {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        String strBeginDate = format.format(beginDate);

	        String strEndDate = format.format(endDate);
	        return getDiffDay(strBeginDate, strEndDate);
	    }

	    /**
	     * N天之后
	     * @param n
	     * @param date
	     * @return
	     */
	    public static Date nDaysAfter(Integer n, Date date) {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + n);
	        return cal.getTime();
	    }
	    
	    /**
	     *@Title:getDiffDay
	     *@Description:获取日期相差天数
	     *@param:@param beginDate  字符串类型开始日期
	     *@param:@param endDate    字符串类型结束日期
	     *@param:@return
	     *@return:Long             日期相差天数
	     *@author:谢
	     *@thorws:
	     */
	    public static Long getDiffDay(String beginDate, String endDate) {
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	        Long checkday = 0l;
	        //开始结束相差天数
	        try {
	            checkday = (formatter.parse(endDate).getTime() - formatter.parse(beginDate).getTime()) / (1000 * 24 * 60 * 60);
	        } catch (ParseException e) {

	            e.printStackTrace();
	            checkday = null;
	        }
	        return checkday;
	    }
	    
	    /**
	     * 判断当前时间是否在某个时间段
	     * @param strDateBegin
	     * @param strDateEnd
	     * @return
	     */
	    public static boolean isInDate(String strDateBegin,String strDateEnd) {  
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);  
	    	String strDate = sdf.format(new Date());//当前时间
	    	// 截取当前时间时分秒 转成整型
	    	int  tempDate=Integer.parseInt(strDate.substring(11, 13)+strDate.substring(14, 16)+strDate.substring(17, 19));  
	    	// 截取开始时间时分秒  转成整型
	    	int  tempDateBegin=Integer.parseInt(strDateBegin.substring(0, 2)+strDateBegin.substring(3, 5)+strDateBegin.substring(6, 8));  
	    	// 截取结束时间时分秒  转成整型
	    	int  tempDateEnd=Integer.parseInt(strDateEnd.substring(0, 2)+strDateEnd.substring(3, 5)+strDateEnd.substring(6, 8));

	    	if((tempDate >= tempDateBegin && tempDate <= tempDateEnd)){  
	    		return true;  
	    	}else{  
	    		return false;  
	    	}  
    	}
	    
	    /**
	     * 获得某天0点
	     * ZTK2017年7月3日下午3:15:38
	     */
	    public static Date getStartTimeOfDay(Date date){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
	    	Calendar day = Calendar.getInstance();
	    	day.setTime(date);
	    	day.set(Calendar.HOUR_OF_DAY,0);
	    	day.set(Calendar.MINUTE, 0);
	    	day.set(Calendar.SECOND, 0);
	    	day.set(Calendar.MILLISECOND, 0);
	    	return day.getTime();
	    }
	    
	    /**
	     * 获得某天23:59:59:000点
	     * ZTK2017年7月3日下午3:15:38
	     */
	    public static Date getEndTimeOfDay(Date date){
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);
	    	Calendar day = Calendar.getInstance();
	    	day.setTime(date);
	    	day.set(Calendar.HOUR_OF_DAY,23);
	    	day.set(Calendar.MINUTE, 59);
	    	day.set(Calendar.SECOND, 59);
	    	day.set(Calendar.MILLISECOND, 0);
	    	return day.getTime();
	    }
	    
	    /**
	     * 时间转成string类型(yyyy-MM-dd HH:mm:ss)
	     * ZTK2017年7月14日上午11:59:44
	     */
	    public static String dateToStringHMS(Date date){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 24小时制  
	    	if(date != null){
	    		String dateStr = format.format(date);
	    		return dateStr;
	    	}
	    	return null;
	    }
	    
	    /**
	     * 时间转成string类型(yyyy-MM-dd)
	     * ZTK2017年7月14日上午11:59:44
	     */
	    public static String dateToString(Date date){
	    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 无小时制  
	    	if(date != null){
	    		String dateStr = format.format(date);
	    		return dateStr;
	    	}
	    	return null;
	    }
	    
	    
		/**
		 * 获得本月第一天0点时间
		 * ZTK2017年7月24日下午5:16:24
		 */
		public static Date getTimesMonthmorning() {
		    Calendar cal = Calendar.getInstance();
		    cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		    return  cal.getTime();
		}
		
	      
	    /** 
	     * 获取当年的第一天 
	     * @param year 
	     * @return 
	     */  
	    public static Date getCurrYearFirst(){  
	        Calendar currCal=Calendar.getInstance();    
	        int currentYear = currCal.get(Calendar.YEAR);  
	        return getYearFirst(currentYear);  
	    }  
	      
	    /** 
	     * 获取当年的最后一天 
	     * @param year 
	     * @return 
	     */  
	    public static Date getCurrYearLast(){  
	        Calendar currCal=Calendar.getInstance();    
	        int currentYear = currCal.get(Calendar.YEAR);  
	        return getYearLast(currentYear);  
	    }  
	      
	    /** 
	     * 获取某年第一天日期 
	     * @param year 年份 
	     * @return Date 
	     */  
	    public static Date getYearFirst(int year){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.clear();  
	        calendar.set(Calendar.YEAR, year);  
	        Date currYearFirst = calendar.getTime();  
	        return currYearFirst;  
	    }  
	      
	    /** 
	     * 获取某年最后一天日期 
	     * @param year 年份 
	     * @return Date 
	     */  
	    public static Date getYearLast(int year){  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.clear();  
	        calendar.set(Calendar.YEAR, year);  
	        calendar.roll(Calendar.DAY_OF_YEAR, -1);  
	        Date currYearLast = calendar.getTime();  
	          
	        return currYearLast;  
	    } 
	    
	    /**
	    * 获得该月第一天
	    * @param year
	    * @param month
	    * @return
	    */
	    public static Date getFirstDayOfMonth(int year,int month){
            Calendar cal = Calendar.getInstance();
            //设置年份
            cal.set(Calendar.YEAR,year);
            //设置月份
            cal.set(Calendar.MONTH, month-1);
            //获取某月最小天数
            int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
            //设置日历中月份的最小天数
            cal.set(Calendar.DAY_OF_MONTH, firstDay);
            //格式化日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date firstDayOfMonth = cal.getTime();
            return firstDayOfMonth;
        }
	    
	    /** 
	     * 获取某月的最后一天 
	     * @Title:getLastDayOfMonth 
	     * @Description: 
	     * @param:@param year 
	     * @param:@param month 
	     * @param:@return 
	     * @return:String 
	     * @throws 
	     */  
	    public static Date getLastDayOfMonth(int year,int month){  
	        Calendar cal = Calendar.getInstance();  
	        //设置年份  
	        cal.set(Calendar.YEAR,year);  
	        //设置月份  
	        cal.set(Calendar.MONTH, month-1);  
	        //获取某月最大天数  
	        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);  
	        //设置日历中月份的最大天数  
	        cal.set(Calendar.DAY_OF_MONTH, lastDay);  
	        //格式化日期  
	        Date lastDayOfMonth = cal.getTime();  
	          
	        return lastDayOfMonth;  
	    }  
}
