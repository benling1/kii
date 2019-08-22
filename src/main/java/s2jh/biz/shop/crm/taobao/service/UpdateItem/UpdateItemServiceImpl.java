package s2jh.biz.shop.crm.taobao.service.UpdateItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lab.s2jh.core.dao.mybatis.MyBatisDao;
import lab.s2jh.core.dao.mybatis.MyBatisDaoT;
import net.sf.json.JSONObject;
import s2jh.biz.shop.crm.item.dao.ItemDao;
import s2jh.biz.shop.crm.item.dao.SkusDao;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.taobao.service.judgment.appraise.JudgeAppraiseMainUtil;

@Service("updateItemService")
@Transactional
@Deprecated 
public class UpdateItemServiceImpl implements UpdateItemService {

	@Autowired
	private JudgeAppraiseMainUtil judgeAppraiseMainUtil;
	@Autowired
	private MyBatisDao myBatisDao;
	
	@Autowired
	private MyBatisDaoT myBatisDaoT;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private SkusDao skusDao;
	
	@Override
	public  void updateItem(JSONObject parseJSON) throws Exception {
		//获取到 nick
		/*String taobaoUserNick = parseJSON.getString("nick");
//		String taobaoUserNick = "小白你什么都没看见哦";
		//获取到 num_iid
		String numIid = parseJSON.getString("num_iid");
//		String numIid = "544146687282";		
		long numiid=Long.parseLong(numIid);
		//获取商品的 title
		String title = parseJSON.getString("title");
		
		//获取 sku_id
		String skuid = parseJSON.getString("sku_id");
		long skuId = Long.parseLong(skuid);
		
		//通过淘宝推送 的 numiid 是否 和 skus 的 iid相同
		
		if(numIid!=null && taobaoUserNick !=null ){
			
			synchronized(numIid){
				//判断 nick 是否为空
				if(taobaoUserNick!=null && taobaoUserNick !="" ){
				   Map<String,Object> map = new HashMap<String,Object>();
				   map.put("taobaoUserNick", taobaoUserNick);
				   //从UserInfo 获取 用户的 access_token
				   String accessToken = myBatisDao.findBy(UserInfo.class.getName(), "findUserInfoTokens", map);
				   //查询淘宝推送 的 num_iid 是否存在 Item表中
				   map.put("numiid", numiid);
				   Item item = null;
				   item = myBatisDao.findBy(Item.class.getName(), "findItemNumIid", map);
				   //判断 用户的access_token 是否为空
				   if(accessToken!=null){
					   //调用 接口
					   TaobaoClient client = new DefaultTaobaoClient(taobaoInfo.url, taobaoInfo.appKey, taobaoInfo.appSecret);
					   ItemSellerGetRequest req = new ItemSellerGetRequest();
					   req.setFields("num_iid,nick,approve_status,cid,has_showcase,has_discount,created,modified+"
					   		+ "seller_cids,props,input_pids,input_str,num,list_time,delist_time,stuff_status,zip+"
					   		+ "address,city,state,country,district,freight_payer,is_3D,score,sell_promise,title+"
					   		+ "type,item_desc,props_name,promoted_service,is_lightning_consignment,is_fenxiao,template_id+"
					   		+ "auction_point,property_alias,after_sale_id,is_xinpin,sub_stock,features,with_hold_quantity+"
					   		+ "sell_point,valid_thru,outer_id,auto_fill,desc_modules,custom_made_type_id,wireless_desc+"
					   		+ "barcode,newprepay,price,post_fee,express_fee,ems_fee,global_stock_type,global_stock_country+"
					   		+ "large_screen_image_url,url,location,item_img,sku");
					   req.setNumIid(numiid);
					   ItemSellerGetResponse rsp = client.execute(req, accessToken);
				   if(rsp!=null){
					   if(rsp.getErrorCode()==null && rsp.getItem()!=null && rsp.getItem().getNumIid()!=null){
						   if(item!=null){
							   Map<String,Object> hashMap = new HashMap<String, Object>();
							   hashMap.put("numiid",numiid);
							   hashMap.put("nick", rsp.getItem().getNick());
							   hashMap.put("approveStatus", rsp.getItem().getApproveStatus());
							   hashMap.put("cid", rsp.getItem().getCid());
							   hashMap.put("hasShowcase", rsp.getItem().getHasShowcase());
							   hashMap.put("hasDiscount", rsp.getItem().getHasDiscount());
							   hashMap.put("created", rsp.getItem().getCreated());
							   if(rsp.getItem().getModified()!=null){
								   hashMap.put("modified", rsp.getItem().getModified());
							   }else{
								   hashMap.put("modified",new Date());
							   }
							   hashMap.put("sellerCids", rsp.getItem().getSellerCids());
							   hashMap.put("props", rsp.getItem().getProps());
							   hashMap.put("inputPids", rsp.getItem().getInputPids());
							   hashMap.put("inputStr", rsp.getItem().getInputStr());
							   hashMap.put("num", rsp.getItem().getNum());
							   hashMap.put("listTime", rsp.getItem().getListTime());
							   hashMap.put("delistTime", rsp.getItem().getDelistTime());
							   hashMap.put("stuffStatus", rsp.getItem().getStuffStatus());  
							   if(rsp.getItem().getLocation()!=null){
								   hashMap.put("zip", rsp.getItem().getLocation().getZip());
								   hashMap.put("address", rsp.getItem().getLocation().getAddress());
								   hashMap.put("city", rsp.getItem().getLocation().getCity());
								   hashMap.put("state", rsp.getItem().getLocation().getState());
								   hashMap.put("country", rsp.getItem().getLocation().getCountry());
								   hashMap.put("district", rsp.getItem().getLocation().getDistrict());
							   }
							   hashMap.put("freightPayer", rsp.getItem().getFreightPayer());
							   if(rsp.getItem().getItemImgs()!=null){
								   hashMap.put("url", rsp.getItem().getItemImgs().get(0).getUrl());
							   }
							   hashMap.put("is3D", rsp.getItem().getIs3D());
							   hashMap.put("score", rsp.getItem().getScore());
							   hashMap.put("sellPromise", rsp.getItem().getSellPromise());
							   hashMap.put("title", rsp.getItem().getTitle());
							   hashMap.put("type", rsp.getItem().getType());
							   hashMap.put("itemDesc", rsp.getItem().getDesc());
							   hashMap.put("changeProp", rsp.getItem().getChangeProp());
							   hashMap.put("propsName", rsp.getItem().getPropsName());
							   hashMap.put("promotedService", rsp.getItem().getPromotedService());
							   hashMap.put("isLightningConsignment", rsp.getItem().getIsLightningConsignment());
							   hashMap.put("isFenxiao", rsp.getItem().getIsFenxiao());
							   hashMap.put("auctionPoint", rsp.getItem().getAuctionPoint());
							   hashMap.put("propertyAlias", rsp.getItem().getPropertyAlias());
							   hashMap.put("templateId", rsp.getItem().getTemplateId());
							   hashMap.put("afterSaleId", rsp.getItem().getAfterSaleId());
							   hashMap.put("isXinpin", rsp.getItem().getIsXinpin());
							   hashMap.put("subStock", rsp.getItem().getSubStock());
							   hashMap.put("features", rsp.getItem().getFeatures());
							   hashMap.put("itemWeight", rsp.getItem().getItemWeight());
							   hashMap.put("itemSize", rsp.getItem().getItemSize());
							   hashMap.put("with_hold_quantity", rsp.getItem().getWithHoldQuantity());
							   hashMap.put("sellPoint", rsp.getItem().getSellPoint());
							   hashMap.put("validThru", rsp.getItem().getValidThru());
							   hashMap.put("outerId", rsp.getItem().getOuterId());
							   hashMap.put("autofill", rsp.getItem().getAutoFill());
							   hashMap.put("descModules", rsp.getItem().getDescModules());
							   hashMap.put("customMadeTypeId", rsp.getItem().getCustomMadeTypeId());
							   hashMap.put("wirelessDesc", rsp.getItem().getWirelessDesc());
							   hashMap.put("barcode", rsp.getItem().getBarcode());
							   hashMap.put("newprepay", rsp.getItem().getNewprepay());
							   hashMap.put("price", rsp.getItem().getPrice());
							   hashMap.put("postFee", rsp.getItem().getPostFee());
							   hashMap.put("expressFee", rsp.getItem().getExpressFee());
							   hashMap.put("emsFee", rsp.getItem().getEmsFee());
							   hashMap.put("globalStockType", rsp.getItem().getGlobalStockType());
							   hashMap.put("globalStockCountry", rsp.getItem().getGlobalStockCountry());
							   hashMap.put("largeScreenImageUrl", rsp.getItem().getLargeScreenImageUrl());
							   myBatisDao.execute(Item.class.getName(), "updateItem", hashMap);
						   }else{
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
							   item.setTitle(rsp.getItem().getTitle());
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
							   itemDao.save(item);
						   }
					   }
					   
					}
//					   System.out.println(rsp.getBody()+"更新商品监听已执行!!!!!!!!!!!"+new Date());
				   }
				}
				
			}
		}else{
			return;
		}*/
	}

}
