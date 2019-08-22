package s2jh.biz.shop.crm.taobao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import s2jh.biz.shop.crm.taobao.util.TmcThreadUtil;
@Controller
public class TmcClockController {

	@RequestMapping("/close/tmcClock")
	@ResponseBody
	public Map<String, Object> closeTmcClock(){
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if(TmcThreadUtil.tmcClock){
			TmcThreadUtil.tmcClock = false;
			resultmap.put("flag", false);
		}
		resultmap.put("tmcSize", TmcThreadUtil.queue.size());
		return resultmap;
	}
	@RequestMapping("/open/tmcClock")
	@ResponseBody
	public Map<String, Object> openTmcClock(){
		Map<String, Object> resultmap = new HashMap<String, Object>();
		if(TmcThreadUtil.tmcClock==false){
			TmcThreadUtil.tmcClock = true;
			resultmap.put("flag", true);
		}
		resultmap.put("tmcSize", TmcThreadUtil.queue.size());
		return resultmap;
	}
}
