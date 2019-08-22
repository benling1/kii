package s2jh.biz.shop.crm.feedback.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.thoughtworks.xstream.core.util.Base64Encoder;

import s2jh.biz.shop.crm.feedback.entity.FeedBack;
import s2jh.biz.shop.crm.feedback.service.FeedBackService;
import s2jh.biz.shop.crm.manage.base.BaseComponent;
import s2jh.biz.shop.utils.PinYin4jUtil;
import s2jh.biz.shop.utils.UploadPathUtil;

@Controller
public class FeedBackController extends BaseComponent{
	private static final Log logger = LogFactory.getLog(FeedBackController.class);
	
	@Autowired
	private FeedBackService feedBackService;

	@RequestMapping(value="/crm/feedBackMessage",method = RequestMethod.POST)
	public void feedBackMessage(String[] fileImages, String feedbackContent,
			String contactMode,HttpServletRequest request,
			HttpServletResponse response) {
		//反馈信息 true-反馈成功，false-反馈失败
		boolean flagFile = true;
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		if(userId !=null && !"".equals(userId)){
			try {
				if (null==fileImages && null==feedbackContent && null==contactMode) {
					StringBuilder sb = gainRequestData(request);
					String strImage = this.splitString(sb.toString(), "fileImages=");
					if(null!=strImage){
						fileImages = strImage.split(",");
					}
					feedbackContent = this.splitString(sb.toString(), "feedbackContent=");
					contactMode = this.splitString(sb.toString(), "contactMode=");
				}
				
				//保存图片，返回图片名称
				String feedbackImage = saveImages(fileImages);
				FeedBack feedBack = new FeedBack();
				feedBack.setUserId(userId);
				feedBack.setFeedbackContent(feedbackContent);
				feedBack.setFeedbackImage(feedbackImage);
				feedBack.setContactMode(contactMode);
				feedBack.setFeedbackRead(false);
				
				//保存反馈信息
				feedBackService.insertFeedBack(feedBack);
			} catch (Exception e) {
				e.printStackTrace();
				flagFile = false;
			}
		}else{
			flagFile = false;
		}
		try {
			JSONObject json = new JSONObject();
			json.putOnce("message", flagFile);
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	/**
	 * 循环将base64编码的图片进行保存
	 * @param fileImages
	 * @return
	 * @throws IOException
	 */
	private String saveImages(String[] fileImages) throws Exception {
		String filePath = createFolder();
		String feedbackImage = "";
		if(null != fileImages && fileImages.length>0){
			for (int i = 0; i < fileImages.length; i++) {
				String ImageName = gainImageName();
				String imgFilePath = filePath+ImageName;
				boolean generateImage = generateImage(fileImages[i], imgFilePath);
				if(generateImage){
					logger.info("图片保存完成.........."+imgFilePath);
					feedbackImage+=ImageName+",";
				}
			}
		}
		if(!"".equals(feedbackImage)){
			feedbackImage = feedbackImage.substring(0, feedbackImage.length()-1);
		}
		return feedbackImage;
	}




	/**
	 * 获取请求参数
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private StringBuilder gainRequestData(HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		char[] buff = new char[1024*1024];
		int len;
		while ((len = reader.read(buff)) != -1) {
			sb.append(buff, 0, len);
		}
		return sb;
	}

	/**
	 * 截取指定参数的内容，进行解码返回
	 * @param str
	 * @param temp
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private String splitString(String str, String temp) throws Exception {
		String result = null;
		if (str.indexOf(temp) != -1) {
			if (str.substring(str.indexOf(temp)).indexOf("&") != -1) {
				result = str.substring(str.indexOf(temp)).substring(str.substring(str.indexOf(temp)).indexOf("=") + 1,
						str.substring(str.indexOf(temp)).indexOf("&"));
			} else {
				result = str.substring(str.indexOf(temp)).substring(str.substring(str.indexOf(temp)).indexOf("=") + 1);
			}
		}
		if(null !=result){
			result=URLDecoder.decode(result,"UTF-8");
		}
		return result;
	}

	
	/**
	 * base64字符串转化成图片  
	 * 对字节数组字符串进行Base64解码并生成图片  
	 * @param imgStr
	 * @return
	 */
	private boolean generateImage(String imgStr, String imgFilePath) throws Exception {
    	//图像数据为空
        if (imgStr == null){
        	return false;  
        }
        Base64Encoder decoder = new Base64Encoder();  
        try{  
            //Base64解码  
            byte[] b = decoder.decode(imgStr);
            for(int i=0;i<b.length;++i) {
                if(b[i]<0){//调整异常数据 
                    b[i]+=256;  
                }  
            }  
            OutputStream out = new FileOutputStream(imgFilePath);      
            out.write(b);  
            out.flush();  
            out.close();  
            return true;  
        }catch (Exception e){
        	e.printStackTrace();
            return false;  
        }  
    }  
	
    /**
     * 创建文件夹
     * @return
     */
    private String createFolder() throws Exception {
    	String imagePath = UploadPathUtil.FEEDBACK_IMAGE_PATH;
  		File fileP = new File(imagePath);
  		if (!fileP.exists()){
  			fileP.mkdirs();
  		}
		return imagePath;
	}
  		
  		
	/**
	 * 创建图片名字，
	 * @return
	 * @throws IOException 
	 */
	private String gainImageName() throws Exception {
		UUID randomUUID = UUID.randomUUID();
		String strUUID = randomUUID.toString().split("-")[0];
		Date date = new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		String formatStr = strUUID+"_"+format.format(date);
		String imageName = formatStr+".jpg";
		return imageName;
	}
}
