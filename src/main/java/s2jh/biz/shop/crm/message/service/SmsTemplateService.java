package s2jh.biz.shop.crm.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.message.dao.SmsHistoryTemplateDao;
import s2jh.biz.shop.crm.message.dao.SmsTemplateDao;
import s2jh.biz.shop.crm.message.entity.SmsHistoryTemplate;
import s2jh.biz.shop.crm.message.entity.SmsTemplate;
import s2jh.biz.shop.crm.message.templateVo.SmsTemplateVo;
import s2jh.biz.shop.crm.user.dao.UserOperationLogDao;
import s2jh.biz.shop.crm.user.entity.UserOperationLog;
import s2jh.biz.shop.utils.pagination.Pagination;

@Service
@Transactional
public class SmsTemplateService extends BaseService<SmsTemplate, Long>{
	@Autowired
	private SmsTemplateDao smsTemplateDao;
	
	@Autowired
	private MyBatisDao mybatisDao;
	
	@Autowired
	private UserOperationLogDao userOperationLogDao;
	
	@Autowired
	private SmsHistoryTemplateDao smsHistoryTemplateDao;
	
	@Autowired
	private SmsHistoryTemplateService smsHistoryTemplateService;
	
	@Override
	protected BaseDao<SmsTemplate, Long> getEntityDao() {
		return smsTemplateDao;
	}
	
	/**
	 * 根据条件获取所有的短信模板
	 * @param type
	 * @param pageNo
	 * @param contextPath
	 * @return
	 */
	public Pagination findAllSmsTemplate(Integer pageNo,Map<String, Object> map){
		//map.put("type", type);
		//设置起始页
		if(pageNo==null){
			pageNo =1;	
		}
		//先设置每页显示的条数为5条
		Integer currentRows = 5;
		//计算出起始行数
		Integer startRows = (pageNo-1)*currentRows;		
		
		//计算出总页数
		Integer totalCount= countSmsTemplate(map);
		
		map.put("startRows", startRows);
		map.put("currentRows", currentRows);
		List<SmsTemplate> list = mybatisDao.findList(SmsTemplate.class.getName(), "findAll", map);
		//使用工具类分页=====pageNo:前段页面的第几页,currentRows:每页显示的条数,totalCount:根据条件查询的数据总条数
		//smsSendRecordList:每页显示的list集合或者当前页显示的list集合
		Pagination pagination = new Pagination(pageNo, currentRows, totalCount, list);
		
		return pagination;
	}
	
	/**
	 * 根据条件获取短信模板的总数
	 * @param map
	 * @return
	 */
	public int countSmsTemplate(Map<String,Object> map){
		int count = mybatisDao.findBy(SmsTemplate.class.getName(), "findSmsTemplateCount", map);
		return count;
	}
	
	/**
	 * 保存短信模板
	 */
	public void saveSmsTemplate(UserOperationLog log,SmsTemplate template){
		try {
			smsTemplateDao.save(template);
			log.setState("成功");
		} catch (Exception e) {
			e.printStackTrace();
			log.setState("失败");
		}
		userOperationLogDao.save(log);
	}
	/**
	 * 根据条件查询短信模板（不分页）
	 */
	public List<SmsTemplate> findAllSmsTemplates(Map<String, Object> map){
		List<SmsTemplate> findList = mybatisDao.findList(SmsTemplate.class.getName(), "findTemplateByType", map);
		return findList;
	}
	
	/**
	 * 根据type和userNick查询短信模板==中差评安抚==我的模板
	 * helei 2017年1月5日上午11:32:26
	 */
	public List<SmsTemplate> findTemplateByTypeAnduserNick(Map<String, Object> map){
		List<SmsTemplate> findList = mybatisDao.findList(SmsTemplate.class.getName(), "findTemplateByTypeAnduserNick", map);
		return findList;
	}
	
	/**
	 * 根据模板id将该条模板添加或更新到历史使用记录中
	 * ZTK2017年2月24日下午2:59:27
	 */
	public void addHistoryTemp(Long id,String userId){
		SmsHistoryTemplate smsHisTemp = smsHistoryTemplateService.findTemplateBytemplateId(userId, id+"","订单短信群发");
		if(smsHisTemp != null && !"".equals(smsHisTemp)){
			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("id", smsHisTemp.getId());
			hashMap.put("userId", userId);
			hashMap.put("templateId", id+"");
			hashMap.put("type", "订单短信群发");
			hashMap.put("lastModifiedDate", new Date());
			mybatisDao.execute(SmsHistoryTemplate.class.getName(), "updateHistoryTempByTempid", hashMap);
		}else{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",id);
			SmsTemplate smsTemplate = mybatisDao.findBy(SmsTemplate.class.getName(), "findSmsTempById", map);
			if(smsTemplate != null && !"".equals(smsTemplate)){
				SmsHistoryTemplate smsHistoryTemplate = new SmsHistoryTemplate();
				smsHistoryTemplate.setContent(smsTemplate.getContent());
				smsHistoryTemplate.setUserId(userId);
				smsHistoryTemplate.setTemplateId(id+"");
				smsHistoryTemplate.setCreatedBy(userId);
				smsHistoryTemplate.setCreatedDate(new Date());
				smsHistoryTemplate.setLastModifiedBy(userId);
				smsHistoryTemplate.setLastModifiedDate(new Date());
				smsHistoryTemplate.setType("订单短信群发");
				smsHistoryTemplateDao.save(smsHistoryTemplate);
			}
		}
	}
	
	/**
	 * Gg
	 * 系统短语库查询
	 * @param temVo
	 * @return
	 * Gg
	 */
	public List<SmsTemplate> findSmsTem(SmsTemplateVo temVo){
		Map<String,Object> map = new HashMap<String, Object>();
		if(temVo.getType()!=null){
			if(temVo.getType().equals("1")){
				map.put("type", "下单关怀");
			}
			if(temVo.getType().equals("2")){
				map.put("type", "常规催付");
			}
			if(temVo.getType().equals("3")){
				map.put("type", "二次催付");
			}
			if(temVo.getType().equals("4")){
				map.put("type", "聚划算催付");
				
			}
			if(temVo.getType().equals("5")){
				map.put("type", "预售催付");
			}
			if(temVo.getType().equals("6")){
				map.put("type", "发货提醒");
			}
			if(temVo.getType().equals("7")){
				map.put("type","到达同城提醒");
			}
			if(temVo.getType().equals("8")){
				map.put("type", "派件提醒");
			}
			if(temVo.getType().equals("9")){
				map.put("type", "签收提醒");
			}
			if(temVo.getType().equals("10")){
				map.put("type", "疑难件提醒");
			}
			if(temVo.getType().equals("11")){
				map.put("type", "延时发货提醒");
			}
			if(temVo.getType().equals("12")){
				map.put("type", "宝贝关怀");
			}
			if(temVo.getType().equals("13")){
				map.put("type", "付款关怀");
			}
			if(temVo.getType().equals("14")){
				map.put("type", "回款提醒");
			}
			if(temVo.getType().equals("16")){
				map.put("type", "自动评价");
			}
			if(temVo.getType().equals("20")){
				map.put("type", "中差评监控");
			}
			if(temVo.getType().equals("21")){
				map.put("type", "中差评安抚");
			}
			if(temVo.getType().equals("29")){
				map.put("type", "退款关怀");
			}
			if(temVo.getType().equals("30")){
				map.put("type", "退款成功");
			}
			if(temVo.getType().equals("31")){
				map.put("type", "同意退货申请");
			}
			if(temVo.getType().equals("32")){
				map.put("type", "拒绝退款");
			}
			if(temVo.getType().equals("37")){
				map.put("type", "好评提醒");
			}
		}
		List<SmsTemplate> smsTemList = mybatisDao.findList(SmsTemplate.class.getName(), "findSmsTem", map);
		return smsTemList;
	}
	
	/**
	 * Gg
	 * 自定义短语库查询
	 * @param temVo
	 * @return
	 * Gg
	 */
	public List<SmsTemplate> findSmsTemZdy(SmsTemplateVo temVo){
		Map<String,Object> map = new HashMap<String, Object>();
		if(temVo.getType()!=null){
			if(temVo.getType().equals("1")){
				map.put("type", "下单关怀");
			}
			if(temVo.getType().equals("2")){
				map.put("type", "常规催付");
			}
			if(temVo.getType().equals("3")){
				map.put("type", "二次催付");
			}
			if(temVo.getType().equals("4")){
				map.put("type", "聚划算催付");
				
			}
			if(temVo.getType().equals("5")){
				map.put("type", "预售催付");
			}
			if(temVo.getType().equals("6")){
				map.put("type", "发货提醒");
			}
			if(temVo.getType().equals("7")){
				map.put("type","到达同城提醒");
			}
			if(temVo.getType().equals("8")){
				map.put("type", "派件提醒");
			}
			if(temVo.getType().equals("9")){
				map.put("type", "签收提醒");
			}
			if(temVo.getType().equals("10")){
				map.put("type", "疑难件提醒");
			}
			if(temVo.getType().equals("11")){
				map.put("type", "延时发货提醒");
			}
			if(temVo.getType().equals("12")){
				map.put("type", "宝贝关怀");
			}
			if(temVo.getType().equals("13")){
				map.put("type", "付款关怀");
			}
			if(temVo.getType().equals("14")){
				map.put("type", "回款提醒");
			}
			if(temVo.getType().equals("16")){
				map.put("type", "自动评价");
			}
			if(temVo.getType().equals("20")){
				map.put("type", "中差评监控");
			}
			if(temVo.getType().equals("21")){
				map.put("type", "中差评安抚");
			}
			if(temVo.getType().equals("29")){
				map.put("type", "退款关怀");
			}
			if(temVo.getType().equals("30")){
				map.put("type", "退款成功");
			}
			if(temVo.getType().equals("31")){
				map.put("type", "同意退货申请");
			}
			if(temVo.getType().equals("32")){
				map.put("type", "拒绝退款");
			}
			if(temVo.getType().equals("37")){
				map.put("type", "好评提醒");
			}
		}
		map.put("userNick", temVo.getUserId());
		List<SmsTemplate> smsTemZdyList = mybatisDao.findList(SmsTemplate.class.getName(), "findCustomSmsTem", map);
		return smsTemZdyList;
	}
	
	/**
	 * Gg
	 * 设置页保存短信模板
	 * @param temVo
	 * Gg
	 */
	public void saveSmsTemContent(SmsTemplateVo temVo){
		SmsTemplate smsTemplate=new SmsTemplate();
		smsTemplate.setUserNick(temVo.getUserId());
		smsTemplate.setContent(temVo.getContent());
		smsTemplate.setName(temVo.getName());
		if(temVo.getType().equals("1")){
			smsTemplate.setType("下单关怀");
		}
		if(temVo.getType().equals("2")){
			smsTemplate.setType("常规催付");
		}
		if(temVo.getType().equals("3")){
			smsTemplate.setType("二次催付");
		}
		if(temVo.getType().equals("4")){
			smsTemplate.setType("聚划算催付");
		}
		if(temVo.getType().equals("5")){
			smsTemplate.setType("预售催付");
		}
		if(temVo.getType().equals("6")){
			smsTemplate.setType("发货提醒");
		}
		if(temVo.getType().equals("7")){
			smsTemplate.setType("到达同城提醒");
		}
		if(temVo.getType().equals("8")){
			smsTemplate.setType("派件提醒");
		}
		if(temVo.getType().equals("9")){
			smsTemplate.setType("签收提醒");
		}
		if(temVo.getType().equals("10")){
			smsTemplate.setType("疑难件提醒");
		}
		if(temVo.getType().equals("11")){
			smsTemplate.setType("延时发货提醒");
		}
		if(temVo.getType().equals("12")){
			smsTemplate.setType("宝贝关怀");
		}
		if(temVo.getType().equals("13")){
			smsTemplate.setType("付款关怀");
		}
		if(temVo.getType().equals("14")){
			smsTemplate.setType("回款提醒");
		}
		if(temVo.getType().equals("16")){
			smsTemplate.setType("自动评价");
		}
		if(temVo.getType().equals("20")){
			smsTemplate.setType("中差评监控");
		}
		if(temVo.getType().equals("21")){
			smsTemplate.setType("中差评安抚");
		}
		if(temVo.getType().equals("29")){
			smsTemplate.setType("退款关怀");
		}
		if(temVo.getType().equals("30")){
			smsTemplate.setType("退款成功");
		}
		if(temVo.getType().equals("31")){
			smsTemplate.setType("同意退货申请");
		}
		if(temVo.getType().equals("32")){
			smsTemplate.setType("拒绝退款");
		}
		if(temVo.getType().equals("37")){
			smsTemplate.setType("好评提醒");
		}
		//smsTemplate.setCreateDate(new Date());
		smsTemplate.setCreatedDate(new Date());
		smsTemplateDao.save(smsTemplate);
	}
	
	/**
	 * Gg
	 * 删除自定义短语库
	 * @param temVo
	 * Gg
	 */
	public void deleteTemZdy(SmsTemplateVo temVo){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userNick", temVo.getUserId());
		map.put("id", temVo.getId());
		mybatisDao.execute(SmsTemplate.class.getName(), "deleteTemZdy", map);
	}
	
	/**
	 * Gg
	 * 另存为短语库，查询模板名称是否重复
	 * @param temVo
	 * @return
	 * Gg
	 */
	public int findSmsTemName(SmsTemplateVo temVo){
		Map<String,Object> map = new HashMap<String, Object>();
		if(temVo!=null){
			if(temVo.getType().equals("1")){
				map.put("type", "下单关怀");
			}
			if(temVo.getType().equals("2")){
				map.put("type", "常规催付");
			}
			if(temVo.getType().equals("3")){
				map.put("type", "二次催付");
			}
			if(temVo.getType().equals("4")){
				map.put("type", "聚划算催付");
			}
			if(temVo.getType().equals("5")){
				map.put("type", "预售催付");
			}
			if(temVo.getType().equals("6")){
				map.put("type", "发货提醒");
			}
			if(temVo.getType().equals("7")){
				map.put("type", "到达同城提醒");
			}
			if(temVo.getType().equals("8")){
				map.put("type", "派件提醒");
			}
			if(temVo.getType().equals("9")){
				map.put("type", "签收提醒");
			}
			if(temVo.getType().equals("10")){
				map.put("type", "疑难件提醒");
			}
			if(temVo.getType().equals("11")){
				map.put("type", "延时发货提醒");
			}
			if(temVo.getType().equals("12")){
				map.put("type", "宝贝关怀");
			}
			if(temVo.getType().equals("13")){
				map.put("type", "付款关怀");
			}
			if(temVo.getType().equals("14")){
				map.put("type", "回款提醒");
			}
			if(temVo.getType().equals("16")){
				map.put("type", "自动评价");
			}
			if(temVo.getType().equals("20")){
				map.put("type", "中差评监控");
			}
			if(temVo.getType().equals("21")){
				map.put("type", "中差评安抚");
			}
			if(temVo.getType().equals("29")){
				map.put("type", "退款关怀");
			}
			if(temVo.getType().equals("30")){
				map.put("type", "退款成功");
			}
			if(temVo.getType().equals("31")){
				map.put("type", "同意退货申请");
			}
			if(temVo.getType().equals("32")){
				map.put("type", "拒绝退款");
			}
			if(temVo.getType().equals("37")){
				map.put("type", "好评提醒");
			}
			map.put("name", temVo.getName());
			map.put("userNick", temVo.getUserId());
		}
		int count = mybatisDao.findBy(SmsTemplate.class.getName(), "findTemNameIsExist", map);
		return count;
	}
}
