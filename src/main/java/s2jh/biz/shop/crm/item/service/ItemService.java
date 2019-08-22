package s2jh.biz.shop.crm.item.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import lab.s2jh.core.dao.jpa.BaseDao;
import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import lab.s2jh.core.service.BaseService;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import s2jh.biz.shop.crm.goods.vo.CommodityArtifact;
import s2jh.biz.shop.crm.item.dao.ItemDao;
import s2jh.biz.shop.crm.item.dao.SkusDao;
import s2jh.biz.shop.crm.item.entity.Item;
import s2jh.biz.shop.crm.item.itemThread.ItemThread;
import s2jh.biz.shop.crm.item.itemThread.OverallSituationItemThread;
import s2jh.biz.shop.crm.item.pojo.TbItem;
import s2jh.biz.shop.crm.item.vo.ItemVO;
import s2jh.biz.shop.crm.other.entity.TaskNode;
import s2jh.biz.shop.crm.schedule.threadpool.ItemThreadPool;
import s2jh.biz.shop.crm.schedule.threadpool.OverallSituationItemThreadPool;
import s2jh.biz.shop.crm.taobao.listener.InitSpringListener;
import s2jh.biz.shop.utils.ConstantUtils;

import com.taobao.api.ApiException;
import com.taobao.api.internal.util.TaobaoUtils;
import com.taobao.api.response.ItemAddResponse;

@Service
//@Transactional
public class ItemService extends BaseService<Item, Long>{
	
	private static final Integer PAGE_SIZE = 5;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private SkusDao skusDao;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MyBatisDaoT myBatisDaoT;
	
	@Autowired
	private ItemImportService itemImportService;

	@Override
	protected BaseDao<Item, Long> getEntityDao() {
		return itemDao;
	}

	/**
	 * 通过userId查询出该用户下的商品
	 * 通过条件查询
	 *  1.userId --- 从域中获取
	 * 	2.commodityId --商品ID
	 * 	3.name -- 商品名称(关键字)
	 *  4.status -- 状态 1-有效 2-锁定
	 */
	public List<Item> queryItem(String commodityId, String name,
			String status, String userNick) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userNick", userNick); 
		map.put("commodityId", commodityId);
		map.put("status", status);
		map.put("name", name);		
		List<Item> itemList = myBatisDao.findList(Item.class.getName(), "findListToItem", map);
//		if(null != name && !"".equals(name)){
//			List<Item> itemImportList = itemImportService.findListToItemImport(map);
//			itemList.addAll(itemImportList);
//		}
		return itemList;
	}
	
	/**
	  * 创建人：邱洋
	  * @Title: 
	  * @date 2017年1月6日--下午12:11:19 
	  * @return int
	  * @throws
	 */
	public int addItem(Map<String,Object> map){
		int status = myBatisDao.execute(Item.class.getName(), "", map);
		return status;
	}
	
	
	//查询numIid 是否存在
	public Boolean isExitItem(Long numIid){
		Map<String ,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("numIid", numIid);
		Item item = myBatisDao.findBy(Item.class.getName(), "findNumIid", hashMap);
		if(item!=null){
			return true;
		}
		return false;
		
	}
	
	//查询Sku 表 iid是否存在
	/*public Boolean isExitSkus(Long numIid){
		Map<String,Object> hashMap = new HashMap<String,Object>();
		hashMap.put("numIid", numIid);
		List<Skus> skus = myBatisDao.findList(Skus.class.getName(), "findSkuIid", hashMap);
		if(skus!=null && skus.size()>0){
			return true;
		}
		return false;
	}*/
	
	
	//item更新
	public void updateItem(Item item){
		Map<String,Object> hashMap = new HashMap<String, Object>();
		hashMap.put("numIid", item.getNumIid());
		hashMap.put("nick", item.getNick());
		hashMap.put("approveStatus", item.getApproveStatus());
		hashMap.put("cid", item.getCid());
		hashMap.put("hasShowcase", item.getHasShowcase());
		hashMap.put("hasDiscount", item.getHasDiscount());
		hashMap.put("created", item.getCreated());
		hashMap.put("modified", item.getModified());
		hashMap.put("sellerCids", item.getSellerCids());
		hashMap.put("props", item.getProps());
		hashMap.put("inputPids", item.getInputPids());
		hashMap.put("inputStr", item.getInputStr());
		hashMap.put("num", item.getNum());
		hashMap.put("listTime", item.getListTime());
		hashMap.put("delistTime", item.getDelistTime());
		hashMap.put("stuffStatus", item.getStuffStatus());
		hashMap.put("zip", item.getZip());
		hashMap.put("address", item.getAddress());
		hashMap.put("city", item.getCity());
		hashMap.put("state", item.getState());
		hashMap.put("country", item.getCountry());
		hashMap.put("district", item.getDistrict());
		hashMap.put("freightPayer", item.getFreightPayer());
		hashMap.put("url", item.getUrl());
		hashMap.put("is3D", item.getIs3D());
		hashMap.put("score", item.getScore());
		hashMap.put("sellPromise", item.getSellPromise());
		hashMap.put("title", item.getTitle());
		hashMap.put("type", item.getType());
		hashMap.put("properties", item.getProperties());
		hashMap.put("quantity", item.getQuantity());
		hashMap.put("withHoldQuantity", item.getWithHoldQuantity());
		hashMap.put("changeProp", item.getChangeProp());
		hashMap.put("propsName", item.getPropsName());
		hashMap.put("promotedService", item.getPromotedService());
		hashMap.put("isLightningConsignment", item.getIsLightningConsignment());
		hashMap.put("isFenxiao", item.getIsFenxiao());
		hashMap.put("auctionPoint", item.getAuctionPoint());
		hashMap.put("propertyAlias", item.getPropertyAlias());
		hashMap.put("templateId", item.getTemplateId());
		hashMap.put("afterSaleId", item.getAfterSaleId());
		hashMap.put("isXinpin", item.getIsXinpin());
		hashMap.put("subStock", item.getSubStock());
		hashMap.put("features", item.getFeatures());
		hashMap.put("itemWeight", item.getItemWeight());
		hashMap.put("itemSize", item.getItemSize());
		hashMap.put("sellPoint", item.getSellPoint());
		hashMap.put("validThru", item.getValidThru());
		hashMap.put("outerId", item.getOuterId());
		hashMap.put("autofill", item.getAutofill());
		hashMap.put("descModules", item.getDescModules());
		hashMap.put("customMadeTypeId", item.getCustomMadeTypeId());
		hashMap.put("wirelessDesc", item.getWirelessDesc());
		hashMap.put("barcode", item.getBarcode());
		hashMap.put("newprepay", item.getNewprepay());
		hashMap.put("price", item.getPrice());
		hashMap.put("postFee", item.getPostFee());
		hashMap.put("expressFee", item.getExpressFee());
		hashMap.put("emsFee", item.getEmsFee());
		hashMap.put("globalStockType", item.getGlobalStockType());
		hashMap.put("globalStockCountry", item.getGlobalStockCountry());
		hashMap.put("largeScreenImageUrl", item.getLargeScreenImageUrl());
		myBatisDao.execute(Item.class.getName(), "updateItem", hashMap);
	}
	
	//skus信息表更新
	/*public void updateSkus(Skus skus){
		Map<String,Object> hashMap = new HashMap<String,Object>();
		try {
			hashMap = MyBeanUtils.describe(skus);
		} catch (IllegalAccessException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		hashMap.put("gmtModified", skus.getGmtModified());
		hashMap.put("modified", skus.getModified());
		myBatisDao.execute(Skus.class.getName(), "updateSkus", hashMap);
	}*/
	
	/**
	 * 定时更新商品 Item信息
	 */
   public void saveItems(){
	  Logger logger = org.slf4j.LoggerFactory
				.getLogger(InitSpringListener.class);
	   logger.info("定时任务开始执行！！！！");

	    List<Item> itemList = new ArrayList<Item>();
		List<TbItem> tbItemList = myBatisDaoT.findList(TbItem.class.getName(), "findTbItem", null);
		//System.out.println("*****************************"+tbItemList.size()+"条数据可以更新"+"**************************");
		if(tbItemList != null && tbItemList.size()>0){
			for(TbItem i : tbItemList){
				if(i.getJdpresponse()!=null && !"".equals(i.getJdpresponse())){
					String jdpresponse = i.getJdpresponse();
					ItemAddResponse rsp =null;
					try {
						rsp= TaobaoUtils.parseResponse(jdpresponse, ItemAddResponse.class);
					} catch (Exception e) {
						e.printStackTrace();
					}
					
						//商品信息获取
						Item item = null;
						if(rsp.getItem()!=null){
							   item = new Item();
							   item.setNumIid(rsp.getItem().getNumIid());
							   item.setNick(rsp.getItem().getNick());
							   item.setApproveStatus(rsp.getItem().getApproveStatus());
							   if(rsp.getItem().getCid()!=null){
								   item.setCid(rsp.getItem().getCid().toString());
							   }
							   if(rsp.getItem().getHasShowcase()!=null){
								   item.setHasShowcase(rsp.getItem().getHasShowcase().toString());
							   }
							   if(rsp.getItem().getHasDiscount()!=null){
								   item.setHasDiscount(rsp.getItem().getHasDiscount().toString());
							   }
							   item.setCreated(rsp.getItem().getCreated());
							   item.setModified(rsp.getItem().getModified());
							   item.setSellerCids(rsp.getItem().getSellerCids());
							   item.setProps(rsp.getItem().getProps());
							   item.setInputPids(rsp.getItem().getInputPids());
							   item.setInputStr(rsp.getItem().getInputStr());
							   if(rsp.getItem().getNum()!=null){
								   String num = rsp.getItem().getNum().toString();
								   int nums = Integer.parseInt(num);
								   item.setNum(nums);
							   }
							   item.setListTime(rsp.getItem().getListTime());
							   item.setDelistTime(rsp.getItem().getDelistTime());
							   item.setStuffStatus(rsp.getItem().getStuffStatus());
							   if(rsp.getItem().getLocation()!=null){
								   item.setZip(rsp.getItem().getLocation().getZip());
								   item.setAddress(rsp.getItem().getLocation().getAddress());
								   item.setCity(rsp.getItem().getLocation().getCity());
								   item.setState(rsp.getItem().getLocation().getState());
								   item.setCountry(rsp.getItem().getLocation().getCountry());
								   item.setDistrict(rsp.getItem().getLocation().getDistrict());
							   }
							   item.setFreightPayer(rsp.getItem().getFreightPayer());
							   item.setIs3D(rsp.getItem().getIs3D());
							   if(rsp.getItem().getScore()!=null){
								   String score = rsp.getItem().getScore().toString();
								   int scores = Integer.parseInt(score);
								   item.setScore(scores);
							   }
							   item.setSellPromise(rsp.getItem().getSellPromise());
							   item.setTitle(rsp.getItem().getTitle().toString());
							   item.setType(rsp.getItem().getType());
							   item.setItem_desc(rsp.getItem().getDesc());
							   item.setPropsName(rsp.getItem().getPropsName());
							   item.setPromotedService(rsp.getItem().getPromotedService());
							   item.setIsLightningConsignment(rsp.getItem().getIsLightningConsignment());
							   if(rsp.getItem().getIsFenxiao()!=null){
								   String isFenxiao = rsp.getItem().getIsFenxiao().toString();
								   int isFenXiaos = Integer.parseInt(isFenxiao);
								   item.setIsFenxiao(isFenXiaos);
							   }
							   item.setTemplateId(rsp.getItem().getTemplateId());
							   if(rsp.getItem().getAuctionPoint()!=null){
								   String auctionPoint = rsp.getItem().getAuctionPoint().toString();
								   int auctionPoints = Integer.parseInt(auctionPoint);
								   item.setAuctionPoint(auctionPoints);
							   }
							   item.setPropertyAlias(rsp.getItem().getPropertyAlias());
							   item.setAfterSaleId(rsp.getItem().getAfterSaleId());
							   item.setIsXinpin(rsp.getItem().getIsXinpin());
							   if(rsp.getItem().getSubStock()!=null){
								   String subStock = rsp.getItem().getSubStock().toString();
								   int subStocks = Integer.parseInt(subStock);
								   item.setSubStock(subStocks);
							   }
							   item.setFeatures(rsp.getItem().getFeatures());
							   if(rsp.getItem().getWithHoldQuantity()!=null){
								   String withHoldQuantity = rsp.getItem().getWithHoldQuantity().toString();
								   int withHoldQuantitys = Integer.parseInt(withHoldQuantity);
								   item.setWith_hold_quantity(withHoldQuantitys);
							   }
							   item.setSellPoint(rsp.getItem().getSellPoint());
							   if(rsp.getItem().getValidThru()!=null){
								   String validThru = rsp.getItem().getValidThru().toString();
								   int validThrus = Integer.parseInt(validThru);
								   item.setValidThru(validThrus);
							   }
							   item.setOuterId(rsp.getItem().getOuterId());
							   item.setDescModules(rsp.getItem().getDescModules());
							   item.setCustomMadeTypeId(rsp.getItem().getCustomMadeTypeId());
							   item.setWirelessDesc(rsp.getItem().getWirelessDesc());
							   item.setBarcode(rsp.getItem().getBarcode());
							   item.setNewprepay(rsp.getItem().getNewprepay());
							   item.setPrice(rsp.getItem().getPrice());
							   item.setPostFee(rsp.getItem().getPostFee());
							   item.setEmsFee(rsp.getItem().getEmsFee());
							   item.setGlobalStockType(rsp.getItem().getGlobalStockType());
							   item.setGlobalStockCountry(rsp.getItem().getGlobalStockCountry());
							   item.setLargeScreenImageUrl(rsp.getItem().getLargeScreenImageUrl());
							   if(rsp.getItem().getItemImgs()!=null){
								   item.setUrl(rsp.getItem().getItemImgs().get(0).getUrl());
							   }
							   item.setLastModifiedDate(new Date());
						}
						itemList.add(item);
						//System.out.println("********itemList*********"+"一共转换"+itemList.size()+"条数据***********************");
				}
			}
		}
		//储存商品信息
		if(itemList.size()>0){
			
			Iterator<Item> iterator =itemList.iterator();
			//logger.info("!!!!!!!!!!!!!!!", iterator);
			while(iterator.hasNext()){
				Item i = iterator.next();
				boolean exists = isExitItem(i.getNumIid());
				if(exists){
					updateItem(i);
					//System.out.println("************修改i**************"+i+"****条数据****************");
					iterator.remove();
				}
			}
			if(itemList.size()>0){
				itemDao.save(itemList);
				//System.out.println("*******成功保存*********"+itemList.size()+"条数据************************");
			}
		}
		logger.info("商品更新执行完毕！！！！");
	}
	
   
   /**
    * 根据查询条件查询所有商品（订单短信群发商品id的选择）
    * ZTK2017年3月8日上午11:21:45
    */
   public List<Item> findItemByCondition(String userNick,String name,Integer pageIndex,Integer pageSize){
	   Map<String, Object> map = new HashMap<String, Object>();
	   map.put("userNick", userNick);
	   map.put("name", name);
	   map.put("pageIndex", pageIndex * pageSize);
	   map.put("pageSize",pageSize);
	   map.put("approveStatus", "onsale");
	   List<Item> list = myBatisDao.findList(Item.class.getName(), "queryItemByCondition", map);
	   return list;
   }
   
   /**
    * 根据查询条件查询所有商品（订单短信群发商品id的选择）
    * ZTK2017年3月9日上午11:21:45
    */
   public int findItemByConditionCount(String userNick,String name){
	   Map<String, Object> map = new HashMap<String, Object>();
	   map.put("userNick", userNick);
	   map.put("name", name);
	   map.put("approveStatus", "onsale");
	   int totalCount = myBatisDao.findBy(Item.class.getName(), "queryItemByConditionCount", map);
	   return totalCount;
   }
   
	   
 //------------------------------------------商品更新多綫程-----------------------------------------------
	   
	   public void synchronizeItem(){
		   //记录节点
		   long taskCounts= myBatisDao.findBy(Item.class.getName(), "findTaskCounts", null);
		    // 构建5分钟之前的内容
			Calendar calendar = Calendar.getInstance();
			//Date endTime = calendar.getTime();
			//查询定时任务最后执行时间
			TaskNode taskNode = myBatisDao.findBy(TaskNode.class.getName(), "findTaskLastTime", "items");
			Date endTime =taskNode.getTaskEndTime() ;
			calendar.add(Calendar.MINUTE, -5);
			//Date beginTime = calendar.getTime();
			Date beginTime = new Date(endTime .getTime() - 300000);
			// 1查询出聚石塔内数据(当前时间的5分钟之前的总条数)
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("beginTime", beginTime);
			hashMap.put("endTime", endTime);
			Long itemCount = myBatisDaoT.findBy(TbItem.class.getName(), "findFivememinute", hashMap);
			
			// 每页显示的条数
			//Long pageSize = ConstantUtils.RSP_PAGE_SIZE;
			Long pageSize = ConstantUtils.PROCESS_PAGE_SIZE_MIDDLE;
			//总页数
			Long pageNum = (itemCount+pageSize)/pageSize;
			hashMap.put("pageSize", pageSize);
			
			List<TbItem> tbItem = null;
			ExecutorService threadPool = ItemThreadPool.getItemThreadPool();
			int start = 0;
			List<ItemThread> itemThreadList = new  ArrayList<ItemThread>();
			while(start < pageNum){
				System.out.println("******************更新商品第："+(start+1)+"次分发或者追加数据！*****************************");
				hashMap.put("startRows", start * pageSize);
				//根据分页查询淘宝推送库数据
				tbItem=myBatisDaoT.findList(TbItem.class.getName(), "findTbFivememinute", hashMap);
				if(tbItem ==null || tbItem.size()==0) break;
				//调用线程池
				ItemThread itemThread = null;
				if (Math.floor(start * 1.0 / 10) == 0) {
					itemThread = new ItemThread();
				} else {
					itemThread = itemThreadList.get(start % 10);
				}
				start++;
				//taskCounts +=pageSize;
				taskCounts +=1000 ;
				itemThread.appendtbItemList(tbItem);
				itemThreadList.add(itemThread);
			}
			for (ItemThread i : itemThreadList) {
				threadPool.execute(i);
			}
			
			//将节点保存到数据库
		   hashMap.put("taskCounts", taskCounts);
		   hashMap.put("endTime", new Date());
		   try {
			  myBatisDao.execute(Item.class.getName(), "updateTaskCounts", hashMap);
		   } catch (Exception e) {
			 // TODO: handle exception
		   }
	   }
	   
	   
	   
	   /**
	    *商品数据开始同步
	    *
	    */
	   public void saveItemsys(List<TbItem> tbItems){
		   List<Item> itemlist = new ArrayList<Item>();
		   if(tbItems!=null && tbItems.size()>0){
			   itemlist = new ArrayList<Item>();
			   String jdpresponse = null;
			   for(TbItem tbItem : tbItems){
				   if(tbItem.getJdpresponse()!=null && !"".equals(tbItem.getJdpresponse())){
					   jdpresponse = tbItem.getJdpresponse();
					   ItemAddResponse rsp = null;
					   try {
						rsp= TaobaoUtils.parseResponse(jdpresponse, ItemAddResponse.class);
					   } catch (ApiException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					   }
					   Item item = null;
					   if(rsp.getItem()!=null){
						   item = new Item();
						   item.setNumIid(rsp.getItem().getNumIid());
						   item.setNick(rsp.getItem().getNick());
						   item.setApproveStatus(rsp.getItem().getApproveStatus());
						   if(rsp.getItem().getCid()!=null){
							   item.setCid(rsp.getItem().getCid().toString());
						   }
						   if(rsp.getItem().getHasShowcase()!=null){
							   item.setHasShowcase(rsp.getItem().getHasShowcase().toString());
						   }
						   if(rsp.getItem().getHasDiscount()!=null){
							   item.setHasDiscount(rsp.getItem().getHasDiscount().toString());
						   }
						   item.setCreated(rsp.getItem().getCreated());
						   item.setModified(rsp.getItem().getModified());
						   item.setSellerCids(rsp.getItem().getSellerCids());
						   item.setProps(rsp.getItem().getProps());
						   item.setInputPids(rsp.getItem().getInputPids());
						   item.setInputStr(rsp.getItem().getInputStr());
						   if(rsp.getItem().getNum()!=null){
							   String num = rsp.getItem().getNum().toString();
							   int nums = Integer.parseInt(num);
							   item.setNum(nums);
						   }
						   item.setListTime(rsp.getItem().getListTime());
						   item.setDelistTime(rsp.getItem().getDelistTime());
						   item.setStuffStatus(rsp.getItem().getStuffStatus());
						   if(rsp.getItem().getLocation()!=null){
							   item.setZip(rsp.getItem().getLocation().getZip());
							   item.setAddress(rsp.getItem().getLocation().getAddress());
							   item.setCity(rsp.getItem().getLocation().getCity());
							   item.setState(rsp.getItem().getLocation().getState());
							   item.setCountry(rsp.getItem().getLocation().getCountry());
							   item.setDistrict(rsp.getItem().getLocation().getDistrict());
						   }
						   item.setFreightPayer(rsp.getItem().getFreightPayer());
						   item.setIs3D(rsp.getItem().getIs3D());
						   if(rsp.getItem().getScore()!=null){
							   String score = rsp.getItem().getScore().toString();
							   int scores = Integer.parseInt(score);
							   item.setScore(scores);
						   }
						   item.setSellPromise(rsp.getItem().getSellPromise());
						   item.setTitle(rsp.getItem().getTitle().toString());
						   item.setType(rsp.getItem().getType());
						   item.setItem_desc(rsp.getItem().getDesc());
						   item.setPropsName(rsp.getItem().getPropsName());
						   item.setPromotedService(rsp.getItem().getPromotedService());
						   item.setIsLightningConsignment(rsp.getItem().getIsLightningConsignment());
						   if(rsp.getItem().getIsFenxiao()!=null){
							   String isFenxiao = rsp.getItem().getIsFenxiao().toString();
							   int isFenXiaos = Integer.parseInt(isFenxiao);
							   item.setIsFenxiao(isFenXiaos);
						   }
						   item.setTemplateId(rsp.getItem().getTemplateId());
						   if(rsp.getItem().getAuctionPoint()!=null){
							   String auctionPoint = rsp.getItem().getAuctionPoint().toString();
							   int auctionPoints = Integer.parseInt(auctionPoint);
							   item.setAuctionPoint(auctionPoints);
						   }
						   item.setPropertyAlias(rsp.getItem().getPropertyAlias());
						   item.setAfterSaleId(rsp.getItem().getAfterSaleId());
						   item.setIsXinpin(rsp.getItem().getIsXinpin());
						   if(rsp.getItem().getSubStock()!=null){
							   String subStock = rsp.getItem().getSubStock().toString();
							   int subStocks = Integer.parseInt(subStock);
							   item.setSubStock(subStocks);
						   }
						   item.setFeatures(rsp.getItem().getFeatures());
						   if(rsp.getItem().getWithHoldQuantity()!=null){
							   String withHoldQuantity = rsp.getItem().getWithHoldQuantity().toString();
							   int withHoldQuantitys = Integer.parseInt(withHoldQuantity);
							   item.setWith_hold_quantity(withHoldQuantitys);
						   }
						   item.setSellPoint(rsp.getItem().getSellPoint());
						   if(rsp.getItem().getValidThru()!=null){
							   String validThru = rsp.getItem().getValidThru().toString();
							   int validThrus = Integer.parseInt(validThru);
							   item.setValidThru(validThrus);
						   }
						   item.setOuterId(rsp.getItem().getOuterId());
						   item.setDescModules(rsp.getItem().getDescModules());
						   item.setCustomMadeTypeId(rsp.getItem().getCustomMadeTypeId());
						   item.setWirelessDesc(rsp.getItem().getWirelessDesc());
						   item.setBarcode(rsp.getItem().getBarcode());
						   item.setNewprepay(rsp.getItem().getNewprepay());
						   item.setPrice(rsp.getItem().getPrice());
						   item.setPostFee(rsp.getItem().getPostFee());
						   item.setEmsFee(rsp.getItem().getEmsFee());
						   item.setGlobalStockType(rsp.getItem().getGlobalStockType());
						   item.setGlobalStockCountry(rsp.getItem().getGlobalStockCountry());
						   item.setLargeScreenImageUrl(rsp.getItem().getLargeScreenImageUrl());
						   if(rsp.getItem().getItemImgs()!=null){
							   item.setUrl(rsp.getItem().getItemImgs().get(0).getUrl());
						   }
						   item.setLastModifiedDate(new Date());
					  }
					  itemlist.add(item);
				   }
			   }
		   }
		   //保存商品信息
		   if(itemlist.size()>0){
			   try {
				   saveOrUpItem(itemlist);
			   } catch (Exception e) {
				// TODO: handle exception
			   }
		   }
	   }
	 
	   
	   //批量保存或更新商品信息
	   public void saveOrUpItem(List<Item> itemlist){
		   if(itemlist!=null && itemlist.size()>0){
			   List<Item> updateItems = new ArrayList<Item>();
			   Iterator<Item> iterator = itemlist.iterator();
			   Boolean exists = false;
			   while(iterator.hasNext()){
				   Item item = iterator.next();
				   try {
					   //存在为真，不存在为假
					    exists = isExitItem(item.getNumIid());
				   } catch (Exception e) {
					    exists= true;
				   }
				   if(exists){
					   updateItems.add(item);
					   iterator.remove();
				   }
				   //boolean exists = isExitItem(i.getNumIid());
			   }
			   //批量保存
			   if(itemlist.size()>0){
				   saveItemGx(itemlist);
			   }
			   //批量更新
			   if(updateItems.size()>0){
				   batchUpdatesItem(updateItems);
			   }
		   }
	   }
	   
	   
	   //批量保存商品信息
	   public void saveItemGx(List<Item> itemlist){
		   for(Item item:itemlist){
			   try {
				   itemDao.save(item);
			   } catch (Exception e) {
				   e.printStackTrace();
			   }
		   }
	   }
	   
	  //批量更新商品
	   public void batchUpdatesItem(List<Item> updateItems){
		   /*Map<String, Object> hashMap = new HashMap<String, Object>();*/
		   for(Item item : updateItems){
			   try {
				  item.setModified(new Date());
				 /* hashMap.put("item", item);*/
				  myBatisDao.execute(Item.class.getName(), "updateItem", item);
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		   }
	   }
//------------------------------------------------商品多线程-------------------------------------------------------	   
	   
	   
//===============================================商品全局跑找丢失数据========================================================
	   /**
	    * 全局定时任务找回丢失数据
	    * 每次找回，处理 1万条数据
	    * 定时执行时间（每10分钟一次）
	    */
	   public void overallSituationItemThread(){
		   //查询出节点执行数据行
		   long taskCount= myBatisDao.findBy(Item.class.getName(), "findTaskCount", null);
		   HashMap<String, Object> hashMap = new HashMap<String, Object>();
		   hashMap.put("pageSize", 200);
		   List<TbItem> tbItem = null;
		   ExecutorService threadPool = OverallSituationItemThreadPool.quanItemThreadPool();
		   int start = 0;
		   List<OverallSituationItemThread> quanitemThreadList = new  ArrayList<OverallSituationItemThread>();
		   while(start<50){
			   System.out.println("********************商品更新"+(start+1)+"次处理数据******************");
			   hashMap.put("taskCount", taskCount);
			   tbItem=myBatisDaoT.findList(TbItem.class.getName(), "findTbItemDValue", hashMap);
			   if(null == tbItem || tbItem.size()==0) break;
			   OverallSituationItemThread quanItemThread=null;
			   if(Math.floor(start * 1.0 / 20)==0){
				   quanItemThread = new OverallSituationItemThread();
			   }else{
				   quanItemThread = quanitemThreadList.get(start % 20);
			   }
			   quanItemThread.appendtbItemList(tbItem);
			   quanitemThreadList.add(quanItemThread);
			   start ++;
			   taskCount +=200;
		   }
		   for(OverallSituationItemThread i : quanitemThreadList){
			   threadPool.execute(i);
		   }
		   //将节点保存到数据库
		   hashMap.put("taskCount", taskCount);
		   hashMap.put("endTime", new Date());
		   try {
			  myBatisDao.execute(Item.class.getName(), "updateTaskCount", hashMap);
		   } catch (Exception e) {
			 // TODO: handle exception
		   }
	   }
	   
//===============================================商品全局跑找丢失数据========================================================
	   
	   /**
	   * 创建人：邱洋
	   * @Title: getOnsaleNumber 
	   * @Description: TODO(查询正在出售中的商品数据) 
	   * @param @param userId
	   * @param @return    设定文件 
	   * @return int    返回类型 
	   * @throws
	    */
	   public int getOnsaleNumber(String userId){
		   return myBatisDao.execute(TbItem.class.getName(), "getItemOnsaleNumber", userId);
	   }
	   
	   /**
	    * Gg
	    * 创建分组 查询商品信息
	    * @param userId
	    * @param numIidList
	    * @return
	    * Gg
	    */
	   public Item findItem(String userId,String numIid){
		   Map<String, Object> map = new HashMap<String, Object>();
		   map.put("nick", userId);
		   map.put("numIid", numIid);
		   Item item = myBatisDao.findBy(Item.class.getName(), "findGroupItem", map);
		   return item;
	   }
	   
	   
	   /**
	    * Gg
	    * 商品分组，查询商品总数
	    * @param userId
	    * @return
	    * Gg
	    */
	   public Long findtotalCount(CommodityArtifact artifact){
		   Map<String,Object> map = new HashMap<String, Object>();
		   map.put("nick", artifact.getUserId());
		   if(artifact.getQueryNumIid()!=null){
			   map.put("numIid", artifact.getQueryNumIid());
		   }
		   if(artifact.getTitle()!=null && !"".equals(artifact.getTitle())){
			   map.put("title", artifact.getTitle());
		   }
		   if(artifact.getQueryIshow()!=null && !"".equals(artifact.getQueryIshow())){
			   map.put("approveStatus", artifact.getQueryIshow());
		   }
		   Long totalCount = myBatisDao.findBy(Item.class.getName(), "findItemCount", map);
		   return totalCount;
	   }
	   
	   /**
	    * 查询商品分组左侧数据
	    * 分页：
	    * 
	    * */
	   public Map<String,Object> findItemPagination(CommodityArtifact artifact){
		  //1,根据条件查询出总条数
		   Long totalCount = findtotalCount(artifact);
		  /***
		   * 计算出起始行数
		   */
		  //设置每页显示5条数据
		  Integer currentRows =5;
		  //计算出起始行数
		  Integer startRows = (artifact.getPageNo()==null?1:artifact.getPageNo()-1)*currentRows;
		  //计算出总页数
		  Integer totalPage = (int) Math.ceil(1.0*totalCount/currentRows);
		  //Long totalPage = Long.valueOf((int) Math.ceil(1.0*totalCount/currentRows));
		  Map<String,Object> map = new HashMap<String, Object>();
		   map.put("nick", artifact.getUserId());
		   map.put("startRows",startRows);
		   map.put("currentRows", currentRows);
		   if(artifact.getQueryNumIid()!=null){
			   map.put("numIid", artifact.getQueryNumIid());
		   }
		   if(artifact.getTitle()!=null && !"".equals(artifact.getTitle())){
			   map.put("title", artifact.getTitle());
		   }
		   if(artifact.getQueryIshow()!=null && !"".equals(artifact.getQueryIshow())){
			   map.put("approveStatus", artifact.getQueryIshow());
		   }
		   List<Item> itemList = myBatisDao.findLimitList(Item.class.getName(), "findItem", map, currentRows);
		   Map<String,Object> result = null;
		   if(itemList!=null && !itemList.isEmpty()){
			   result = new HashMap<String, Object>();
			   result.put("data", itemList);
			   result.put("pageNo", artifact.getPageNo());
			   result.put("totalCount", totalCount);
			   result.put("totalPage", totalPage);
		   }
		   return result;
	   }
	   
	   /**
	    * 设置页
	    * numIid不为空时，查询右侧数据
	    * @param comm
	    * @return
	    */
	   public List<Item> findSetupItem(CommodityArtifact comm){
		   Map<String,Object> map = new HashMap<String, Object>();
		   map.put("numIid", comm.getNumIid());
		   map.put("nick", comm.getUserId());
		   List<Item> itemList = myBatisDao.findList(Item.class.getName(), "findsetupCommodity", map);
		   return itemList;
	   }

	public Map<String, Long> insertAndFindItenList(List<Item> itemList,String userNick) {
		Map<String, Long>  map   = new HashMap<String, Long>();
		if(null!=itemList&&itemList.size()>0){
			myBatisDao.execute(Item.class.getName(), "insertbatchItemList", itemList);
		}
		List<Item> queryItem = queryItem("","","",userNick);
		if(null!=queryItem&&queryItem.size()>0){
			for (Item item : queryItem) {
				map.put(item.getTitle(), item.getNumIid());
			}
		}
		return map;
	}
	
	
	/**
	 * @Description: 查询用户所以商品描述及商品id
	 * @author HL
	 * @date 2017年11月17日 下午4:08:55
	 */
	public Map<String, Long> findItemTitleAndItemid(String userId) {
		Map<String, Long> map = new HashMap<String, Long>();
		List<Item> list = myBatisDao.findList(Item.class.getName(), "findItemTitleAndItemid", userId);
		if(null != list){
			for (Item item : list) {
				map.put(item.getTitle().trim(), item.getNumIid());
			}
		}
		return map;
	}
	
	
	/**
	 * 根据商品名称查询商品分页（订单中心商品缩写）
	 * @Title: listItemByTitle 
	 * @param @param userId
	 * @param @param title
	 * @param @param pageNo
	 * @param @return 设定文件 
	 * @return List<Item> 返回类型 
	 * @throws
	 */
	public List<Item> listItemByTitle(ItemVO itemVO){
		if(itemVO == null || itemVO.getUserId() == null || "".equals(itemVO.getUserId())){
			return null;
		}
		if(itemVO.getPageNo() == null){
			itemVO.setPageNo(1);
		}
		Map<String, Object> queryMap = new HashMap<>();
		Integer startRows = (itemVO.getPageNo() - 1) * PAGE_SIZE;
		queryMap.put("startRows", startRows);
		queryMap.put("pageSize", PAGE_SIZE);
		queryMap.put("userId", itemVO.getUserId());
		queryMap.put("numIid", itemVO.getItemId());
		queryMap.put("title", itemVO.getTitle());
		queryMap.put("groupId", itemVO.getGroupId());
		if(itemVO.getStatus() == null || itemVO.getStatus() == 0){
			queryMap.put("approveStatus", null);
		}else{
			queryMap.put("approveStatus", "onsale");
		}
		List<Item> itemList = myBatisDao.findList(Item.class.getName(), "listItemByTitle", queryMap);
		return itemList;
	}
	
	/**
	 * 根据商品名称查询商品总数（订单中心商品缩写）
	 * @Title: countItemByTitle 
	 * @param @param userId
	 * @param @param title
	 * @param @return 设定文件 
	 * @return Integer 返回类型 
	 * @throws
	 */
	public Integer countItemByTitle(ItemVO itemVO){
		if(itemVO == null || itemVO.getUserId() == null || "".equals(itemVO.getUserId())){
			return 0;
		}
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("userId", itemVO.getUserId());
		queryMap.put("numIid", itemVO.getItemId());
		queryMap.put("title", itemVO.getTitle());
		queryMap.put("groupId", itemVO.getGroupId());
		if(itemVO.getStatus() == null || itemVO.getStatus() == 0){
			queryMap.put("approveStatus", null);
		}else{
			queryMap.put("approveStatus", "onsale");
		}
		Integer itemCount = myBatisDao.findBy(Item.class.getName(), "countItemByTitle", queryMap);
		return itemCount;
	}
	
	/**
	 * 更改缩写
	 * @Title: updateSubtitleById 
	 * @param @param itemId
	 * @param @param subtitle 设定文件 
	 * @return void 返回类型 
	 * @throws
	 */
	public void updateSubtitleById(Long itemId,String subtitle){
		Map<String, Object> queryMap = new HashMap<>();
		queryMap.put("itemId", itemId);
		queryMap.put("subtitle", subtitle);
		myBatisDao.execute(Item.class.getName(), "updateItemSubtitle", queryMap);
	}
	
	/**
	 * 根据商品id查询商品缩写
	 * @Title: findSubtitleById 
	 * @param @param itemId
	 * @param @return 设定文件 
	 * @return String 返回类型 
	 * @throws
	 */
	public String findSubtitleById(Long itemId){
		if(null == itemId){
			return null;
		}
		Item item = myBatisDao.findBy(Item.class.getName(), "findSubtitleById", itemId);
		if(item != null){
			String subtitle = item.getSubtitle();
			if(subtitle == null || "".equals(subtitle)){
				return item.getTitle();
			}
			return subtitle;
		}
		return null;
	}
}	


