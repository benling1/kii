package s2jh.biz.shop.crm.autoJob;


import lab.s2jh.core.dao.mybatis.MyBatisDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import s2jh.biz.shop.crm.product.entity.Product;
import s2jh.biz.shop.crm.product.entity.ProductExtraInfos;
import s2jh.biz.shop.crm.product.entity.ProductImgs;
import s2jh.biz.shop.crm.product.entity.ProductPropImgs;
import s2jh.biz.shop.crm.product.service.ProductExtraInfosService;
import s2jh.biz.shop.crm.product.service.ProductImgsService;
import s2jh.biz.shop.crm.product.service.ProductPropImgsService;
import s2jh.biz.shop.crm.product.service.ProductService;
import s2jh.biz.shop.crm.taobao.taobaoInfo;
import s2jh.biz.shop.crm.user.service.UserInfoService;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.ProductGetRequest;
import com.taobao.api.response.ProductGetResponse;

@Service
@Transactional
public class AutoProductInformationService {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ProductPropImgsService productPropImgsService;
	
	@Autowired
	private ProductImgsService productImgsService;
	
	@Autowired
	private ProductExtraInfosService productExtraInfosService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private MyBatisDao myBatisDao;
	
	public void productInformation() throws ApiException{
		TaobaoClient client =new DefaultTaobaoClient(taobaoInfo.url,taobaoInfo.appKey,taobaoInfo.appSecret);
		ProductGetRequest req = new ProductGetRequest();
		req.setFields("productId,name,binds,saleProps,price,desc,picUrl,modified+"
				+ "status,level,picPath,rateNum,saleNum,shopPrice,standardPrice+"
				+ "verticalMarket,customerProps,propertyAlias,outerId,created,+"
				+ "tsc,cid,catName,props,propsStr,bindsStr,salePropsStr,collectNum+"
				+ "sellPt,cspuFeature,templateId,isSuiteEffective,suiteItemsStr+"
				+ "barcodeStr");
		ProductGetResponse rsp = client.execute(req);
		
		if(rsp.getProduct()!=null){
			  
			Product pd =new Product();
			
			if(rsp.getProduct().getProductId()!=null){
				pd.setProductId(Long.toString(rsp.getProduct().getProductId()));
			}
			pd.setName(rsp.getProduct().getName());
			pd.setBinds(rsp.getProduct().getBinds());
			pd.setSaleProps(rsp.getProduct().getSaleProps());
			pd.setPrice(rsp.getProduct().getPrice());
			pd.setDescribe(rsp.getProduct().getDesc());
			pd.setPicUrl(rsp.getProduct().getPicUrl());
			pd.setModified(rsp.getProduct().getModified());
			if(rsp.getProduct().getStatus()!=null){
				pd.setStatus(Long.toString(rsp.getProduct().getStatus()));
			}
			if(rsp.getProduct().getLevel()!=null){
				pd.setLevel(Long.toString(rsp.getProduct().getLevel()));
			}
			pd.setPicPath(rsp.getProduct().getPicPath());
			if(rsp.getProduct().getRateNum()!=null){
				pd.setRateNum(Long.toString(rsp.getProduct().getRateNum()));
			}
			if(rsp.getProduct().getSaleNum()!=null){
				pd.setSaleNum(Long.toString(rsp.getProduct().getSaleNum()));
			}
			pd.setShopPrice(rsp.getProduct().getShopPrice());
			pd.setStandardPrice(rsp.getProduct().getStandardPrice());
			if(rsp.getProduct().getVerticalMarket()!=null){
				pd.setVerticalMarket(Long.toString(rsp.getProduct().getVerticalMarket()));
			}
			pd.setCustomerProps(rsp.getProduct().getCustomerProps());
			pd.setPropertyAlias(rsp.getProduct().getPropertyAlias());
			pd.setOuterId(rsp.getProduct().getOuterId());
			pd.setCreated(rsp.getProduct().getCreated());
			pd.setTsc(rsp.getProduct().getTsc());
			pd.setCid(rsp.getProduct().getCid());
			pd.setCatName(rsp.getProduct().getCatName());
			pd.setProps(rsp.getProduct().getProps());
			pd.setPropsStr(rsp.getProduct().getPropsStr());
			pd.setBindsStr(rsp.getProduct().getBindsStr());
			pd.setSalePropsStr(rsp.getProduct().getSalePropsStr());
			pd.setCollectNum(Long.toString(rsp.getProduct().getCollectNum()));
			pd.setSellPt(rsp.getProduct().getSellPt());
			pd.setCspuFeature(rsp.getProduct().getCspuFeature());
			pd.setTemplateId(rsp.getProduct().getTemplateId());
			pd.setIsSuiteEffective(rsp.getProduct().getIsSuiteEffective());
			pd.setSuiteItemsStr(rsp.getProduct().getSuiteItemsStr());
			pd.setBarcodeStr(rsp.getProduct().getBarcodeStr());
			pd.setCommodityId(rsp.getProduct().getCommodityId());
			productService.save(pd);
	}else{
		  System.out.println("没有数据！！");
	  }
		
		
		//获取产品的属性图片
		if(rsp.getProduct()!=null&&rsp.getProduct().getProductPropImgs()!=null){
			for(int i=0;i<rsp.getProduct().getProductPropImgs().size();i++){
				
				ProductPropImgs pi = new ProductPropImgs();
				pi.setProductId(rsp.getProduct().getProductId());
				pi.setProps(rsp.getProduct().getProductPropImgs().get(i).getProps());
				pi.setUrl(rsp.getProduct().getProductPropImgs().get(i).getUrl());
				pi.setPosition(Long.toString(rsp.getProduct().getProductPropImgs().get(i).getPosition()));
				productPropImgsService.save(pi);
			}
			
		}else{
			System.out.println("应该是没有数据！！！");
			
		}
		
		//产品子图片
		if(rsp.getProduct().getProductImgs()!=null){
			
			for(int i=0;i<rsp.getProduct().getProductImgs().size();i++){
				ProductImgs pm=new ProductImgs();
				pm.setProductId(rsp.getProduct().getProductId());
				pm.setUrl(rsp.getProduct().getProductImgs().get(i).getUrl());
				if(rsp.getProduct().getProductImgs().get(i).getPosition()!=null){
				pm.setPosition(Long.toString(rsp.getProduct().getProductImgs().get(i).getPosition()));
				}
				pm.setCreated(rsp.getProduct().getProductImgs().get(i).getCreated());
				pm.setModified(rsp.getProduct().getProductImgs().get(i).getModified());
				productImgsService.save(pm);
			}
			
		}else{
			System.out.println("产品子图片没有数据！！");
		}
		
		//产品扩展信息
		if(rsp.getProduct().getProductExtraInfos()!=null){
			for(int i=0;i<rsp.getProduct().getProductExtraInfos().size();i++){
				
				ProductExtraInfos pf =new ProductExtraInfos();
				pf.setProductId(rsp.getProduct().getProductId());
				pf.setFieldKey(rsp.getProduct().getProductExtraInfos().get(i).getFieldKey());
				pf.setFieldName(rsp.getProduct().getProductExtraInfos().get(i).getFieldName());
				pf.setFieldValue(rsp.getProduct().getProductExtraInfos().get(i).getFieldValue());
				productExtraInfosService.save(pf);
				
			}
		}else{
			System.out.println("产品扩展信息没有数据！！");
		}
		
			
  }	
}
