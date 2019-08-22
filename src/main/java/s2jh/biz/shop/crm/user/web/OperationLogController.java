package s2jh.biz.shop.crm.user.web;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lab.s2jh.module.sys.entity.DataDict;
import lab.s2jh.module.sys.service.DataDictService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import s2jh.biz.shop.crm.user.service.UserOperationLogService;
import s2jh.biz.shop.utils.ConstantUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

/**
 * 用户操作日志
 *
 * @param model
 * @return
 */
@Controller
@RequestMapping(value = "/")
public class OperationLogController {
	
	@Autowired
	private UserOperationLogService userOperationLogService;
	
	@Autowired
	private DataDictService dataDictService;
	
	@RequestMapping(value="/crms/storeData/operationLog"/*,method = RequestMethod.GET*/)
	public String operationLog(Model model,String type,String functionGens,String beginTime,String endTime,
			HttpServletRequest request,@RequestParam(value = "pageNo", required = false,defaultValue ="1") Integer pageNo){
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		//String userId="crzzyboy";
		Map<String, Object> params = new HashMap<String, Object>();
		//params.put("functionGens", functionGens);
		//所属功能类型
		List<DataDict> functionGens1 =  dataDictService.findChildrenByPrimaryKey(ConstantUtils.FUNCTIONGENS);
		params.put("functionGens1", functionGens1);
		model.addAttribute("params", params);
		Date bTime = null;//起始时间
		Date eTime = null;//结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(beginTime!=null && !"".equals(beginTime)){
			try {
				bTime = dateFormat.parse(beginTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(endTime!=null && !"".equals(endTime)){
			try {
				eTime = dateFormat.parse(endTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String contextPath = request.getContextPath();
		Pagination pagination = userOperationLogService.findoperationLog(contextPath,functionGens, beginTime, endTime, pageNo,userId);
		model.addAttribute("pagination", pagination);
		//此处type无效   只用作了回填  
		model.addAttribute("functionGens", functionGens);
		model.addAttribute("beginTime", bTime);
		model.addAttribute("endTime", eTime);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("type", type);
		return "crms/storeData/operationLog";
	}

}
