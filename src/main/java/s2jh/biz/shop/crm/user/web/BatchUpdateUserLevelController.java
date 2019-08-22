package s2jh.biz.shop.crm.user.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.user.entity.UserInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;
import s2jh.biz.shop.utils.GetUserLevel;

@Controller
@RequestMapping("user")
public class BatchUpdateUserLevelController {
	private static final Log logger = LogFactory.getLog(BatchUpdateUserLevelController.class);
	private static boolean open = false;
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	  * @Description: 批量修改用户等级 
	  * @author Mr.H
	  * @date 2017年12月4日 下午5:09:50
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserLevel",produces = "application/json;charset=utf-8")
	public String UpdateUserLevel(){
			if(open){
				open = false;
				List<UserInfo> list = userInfoService.findUserInfoToken();
				List<UserInfo> updateList = new ArrayList<UserInfo>();
				Integer num = 0;
				Integer i = 0;
				for (UserInfo userInfo : list) {
					Long level = GetUserLevel.getSingleUserLevel(userInfo
							.getAccess_token());
					if (level != null && userInfo.getTaobaoUserNick() != null
							&& !"".equals(userInfo.getTaobaoUserNick())) {
						userInfo.setLevel(level);
						updateList.add(userInfo);
						i++;
					}
					if (i != 0 && i % 500 == 0) {
						userInfoService.batchUpdateUserLevel(updateList);
						logger.info("批量更新用户等级list：" + updateList.size());
						num += updateList.size();
						updateList = new ArrayList<UserInfo>();
					}
	
				}
				if(updateList !=null && updateList.size()>0){
					userInfoService.batchUpdateUserLevel(updateList);
					logger.info("批量更新用户等级list："+updateList.size());
					num+=updateList.size();
				}
				return "批量修改用户等级，处理完成("+num+")条";
			}else{
				return "请打开访问开关！！！";
			}
		}
	
	/**
	  * @Description: 批量修改用户等级访问开关，1-开，
	  * @author Mr.H
	  * @date 2017年12月4日 下午5:10:56
	 */
	@ResponseBody
	@RequestMapping(value = "/updateUserLevelOpen",produces = "application/json;charset=utf-8")
	public String UpdateUserLevelOpen(String str){
		if("1".equals(str)){
			open = true;
		}else{
			open = false;
		}
		return "是否开启："+open;
	}
}
