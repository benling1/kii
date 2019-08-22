package s2jh.biz.shop.crm.payment.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import lab.s2jh.core.util.DateUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import s2jh.biz.shop.utils.UploadPathUtil;
import s2jh.biz.shop.utils.ZxingUtils;

import com.alipay.api.response.AlipayTradePrecreateResponse;

public class PayUtils {
	private static final Log log = LogFactory.getLog(PayUtils.class);
	public static final String TRADE_FINISHED = "TRADE_FINISHED";//交易完成
	public static final String TRADE_SUCCESS = "TRADE_SUCCESS";//支付成功
	public static final String TRADE_CLOSED = "TRADE_CLOSED";//支付关闭
	
	//logo图片路径
	private static String logoPath = "";
	
    /**
     * 获取LogoPath
     * @param request 
     */
    public static String createLogoPath(HttpServletRequest request) {
    	try {
			if(null == logoPath || "".equals(logoPath)){
				logoPath = request.getServletContext().getRealPath("")+File.separator+"crm"+File.separator+"images"+File.separator+"QRlogo.png";
				log.info("*********************************logo路径:"+logoPath);
			}
		} catch (Exception e) {
			logoPath = "";
		}
		return logoPath;
	}

	/**
     * 创建文件夹
     * @param day 
     * @return
     */
    public static String createFolder(String day) throws Exception {
    	String imagePath = UploadPathUtil.QRCODE_IMAGE_PATH+day;
  		File fileP = new File(imagePath);
  		if (!fileP.exists()){
  			fileP.mkdirs();
  		}
		return imagePath;
	}
    
    
    /**
     * 校验短信条数
     */
    public static boolean checkoutSmsAndMoney(Double totalAmount,Integer rechargeNum){
    	boolean flag = false;
    	Integer money = null;
		if(rechargeNum<=3637){
			money=getMoney((rechargeNum*55));
		}else if(rechargeNum>3637&&rechargeNum<=20000){
			money=getMoney((rechargeNum*50));
		}else if(rechargeNum>20000&&rechargeNum<=31250){
			money=getMoney((rechargeNum*48));
		}else if(rechargeNum>31250&&rechargeNum<=53192){
			money=getMoney((rechargeNum*47));
		}else if(rechargeNum>53192&&rechargeNum<=97827){
			money=getMoney((rechargeNum*46));
		}else if(rechargeNum>97827&&rechargeNum<=222223){
			money=getMoney((rechargeNum*45));
		}else if(rechargeNum>222223&&rechargeNum<=500000){
			money=getMoney((rechargeNum*40));
		}else if(rechargeNum>500000&&rechargeNum<=1052632){
			money=getMoney((rechargeNum*38));
		}else if(rechargeNum>1052632){
			money=getMoney((rechargeNum*37));
		}
		if(null != money){
			if(money.intValue() == totalAmount.intValue()){
				flag = true;
			}
		}
		return flag;
    }
    public static Integer getMoney(int num){
    	try {
    		Integer averagePrice = num/1000;
    		return averagePrice;
		} catch (Exception e) {
			return null;
		}
    }
    
    
    /**
     * 获取单价
     */
    public static String univalence(Integer rechargeNum){
    	String price = "0.055";
		if(rechargeNum<=3637){
			price = "0.055";
		}else if(rechargeNum>3637&&rechargeNum<=20000){
			price = "0.050";
		}else if(rechargeNum>20000&&rechargeNum<=31250){
			price = "0.048";
		}else if(rechargeNum>31250&&rechargeNum<=53192){
			price = "0.047";
		}else if(rechargeNum>53192&&rechargeNum<=97827){
			price = "0.046";
		}else if(rechargeNum>97827&&rechargeNum<=222223){
			price = "0.045";
		}else if(rechargeNum>222223&&rechargeNum<=500000){
			price = "0.040";
		}else if(rechargeNum>500000&&rechargeNum<=1052632){
			price = "0.038";
		}else if(rechargeNum>1052632){
			price = "0.037";
		}
		return price;
    }
    
    /**
     * 校验单价
     */
	public static boolean comparePrice(Double total_amount, Double rechargePrice) {
		try {
			return total_amount.intValue()==rechargePrice.intValue();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 获取标题
	 */
	public static String getSubject(Integer rechargeNum, Double totalAmount) {
		return "自定义充值短信"+rechargeNum+"条("+totalAmount+"元)";
	}

	/**
	 * 生成二维码图片返回路径 
	 */
	public static String createQrCodeImage(HttpServletRequest request,
			AlipayTradePrecreateResponse response) {
			try {
				String day = DateUtils.dateToString(new Date(), DateUtils.FORMAT_YYYYMMDD)+"/";
				// 需要修改为运行机器上的路径
				String imageName = String.format("%s.png", response.getOutTradeNo());
				String filePath = PayUtils.createFolder(day)+ imageName;
				//获取logo路径
				String logoPath = PayUtils.createLogoPath(request);
				//生成二维码图片
				BufferedImage image = ZxingUtils.createImage(response.getQrCode(), logoPath, true);
				ImageIO.write(image, "png", new File(filePath));
				log.info("*********************************扫码支付路径:"+day+imageName);
				return day+imageName;
			} catch (Exception e) {
				log.error("*********************************生成二维码错误:"+e.getMessage());
			}
		return "";
	}
}
