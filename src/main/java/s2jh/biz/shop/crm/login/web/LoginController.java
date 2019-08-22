package s2jh.biz.shop.crm.login.web;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import s2jh.biz.shop.crm.login.service.LoginService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.taobao.service.util.ValidateUtil;

/** 
* @author wy
* @version 创建时间：2017年11月13日 下午1:57:44
*/
@Controller
public class LoginController {
    
    @Autowired
    private LoginService loginService;
    
    private  Logger logger = LoggerFactory.getLogger(LoginController.class);
    @RequestMapping("/test/login")
    public String loginByTaobao(HttpServletRequest request,String code, Model model){
        // 判断用户是否订购，没有则跳转到购买应用页面
        this.logger.info("卖家登陆，开始进行判断。code:"+code);
        if(ValidateUtil.isEmpty(code)){
            return taobaoInfo.payUrl;
        } 
        return this.loginService.getIndexUrl(request, code);
    }
}
