package s2jh.biz.shop.crm.historyOrder.web;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import s2jh.biz.shop.crm.historyOrder.entity.OrderHistoryImport;
import s2jh.biz.shop.crm.historyOrder.service.ImportThread;
import s2jh.biz.shop.crm.historyOrder.service.OrderHistoryImportService;
import s2jh.biz.shop.crm.historyOrder.util.ImportUtils;
import s2jh.biz.shop.crm.item.entity.ItemImport;
import s2jh.biz.shop.crm.item.service.ItemImportService;
import s2jh.biz.shop.crm.item.service.ItemService;
import s2jh.biz.shop.crm.manage.base.BaseController;
import s2jh.biz.shop.crm.schedule.threadpool.MyFixedThreadPool;
import s2jh.biz.shop.utils.CsvReaderUtils;
import s2jh.biz.shop.utils.OrderHeaderUtil;
import s2jh.biz.shop.utils.PinYin4jUtil;
import s2jh.biz.shop.utils.UploadPathUtil;
import s2jh.biz.shop.utils.ZipUtils;
import s2jh.biz.shop.utils.pagination.Pagination;

@Controller
@RequestMapping(value = "/OrderHistoryImport")
public class OrderHistoryImportController extends BaseController {
	private static final Log logger = LogFactory.getLog(OrderHistoryImportController.class);

	//设置每个线程默认处理10000条数量--可以手动访问设置
	private static int threadNum = 10000;
	//设置切割商品list默认处理5000条数量--可以手动访问设置
	private static int subItemListNum = 5000;
	
	@Autowired
	private OrderHistoryImportService orderHistoryImportService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemImportService itemImportService;
	
	public static Map<String, String> table = new Hashtable<String, String>();


	/**
	 * 手动设置每个线程处理数量
	 */
	@ResponseBody
	@RequestMapping("/setThreadNum")
	public int setThreadNum(Integer num){
		if(null != num && num >=5000){
			threadNum = num;
		}
		return threadNum;
	}
	/**
	 * 手动设置切割商品list数据
	 */
	@ResponseBody
	@RequestMapping("/subItemListNum")
	public int subItemListNum(Integer num){
		if(null != num && num >=5000 && num<=10000){
			subItemListNum = num;
		}
		return subItemListNum;
	}
	
	
	/*
	 *  设置历史订单导入页面跳转,并查询分页数据返回页面
	 */
	@RequestMapping(value = "/showLoadHistoryOrder")
	public String showLoadHistoryOrder(Model model, String beginTime,
			String endTime, Integer pageNo, String orderName,
			HttpServletRequest request) {

		// 从session中获取卖家id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		// userId = "crzzyboy";

		// 将前台传入的字符串日期转换成Date类型
		Date bTime = null;// 起始时间
		Date eTime = null;// 结束时间
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (beginTime != null && !"".equals(beginTime)) {
			try {
				bTime = dateFormat.parse(beginTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (endTime != null && !"".equals(endTime)) {
			try {
				eTime = dateFormat.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		// 使用分页工具进行分页列表查询
		String contextPath = request.getContextPath();
		Pagination pagination = orderHistoryImportService
				.findPaginationByCondition(contextPath, bTime, eTime, pageNo,
						orderName, userId);
		model.addAttribute("paginationOrderImport", pagination);

		// 页面查询添加回现数据
		model.addAttribute("beginTime", beginTime);
		model.addAttribute("endTime", endTime);
		model.addAttribute("orderName", orderName);

		return "crms/storeData/loadHistoryOrder";
	}
	
	
	/*
	 *  查询导入中的数据改变页面展示页面进度
	 */
	@ResponseBody
	@RequestMapping(value = "/findImportData")
	public String findImportData(String id) {
		if(null != id && !"".equals(id)){
			List<OrderHistoryImport> data = orderHistoryImportService.findImportDataById(id);
			return rsMap(100, "操作成功").put("data",data).toJson();
		}
		return null;
	}
	

	// 根据卖家编号查询历史订单导入信息
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String findAllOrderHistoryImport(Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderHistoryImport> list = orderHistoryImportService
				.findAllOrderHistoryImport(map);
		if (list != null && list.size() > 0) {
			model.addAttribute("orderHistoryImportList", list);
		}
		return "";
	}

	// 根据卖家查询条件查询历史订单导入信息
	@RequestMapping(value = "/queryOrderHistoryImport", method = RequestMethod.GET)
	public String queryOrderHistoryImport(Model model,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		List<OrderHistoryImport> list = orderHistoryImportService
				.queryOrderHistoryImport(map);
		if (list != null && list.size() > 0) {
			model.addAttribute("orderHistoryImportList", list);
		}
		return "";
	}


	private Map<String, Integer> makeHeaderMap(String[] headerArr) {
		Map<String, Integer> headerMap = new HashMap<String, Integer>();
		for (int i = 0; i < headerArr.length; i++) {
			headerMap.put(headerArr[i].trim(), i);
		}
		logger.info("成功---表头信息封装数量"+headerMap.size());
		return headerMap;
	}

	/**
	 * 历史订单导入 helei 2017年2月8日下午12:10:48
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "/uploadOrderFile", method = RequestMethod.POST)
	public String uploadOrderFile(@RequestParam MultipartFile file,
			HttpServletResponse response, HttpServletRequest request)
			{
		// 定义标识位返回个页面 0---导入成功 1---失败导入 2---导入文件过大 3---导入的文件不符合订单文件要求 4---确保数据准确，同时只能上传一个文件
		String flagFile = "0";
		
		// 获取用户id
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("taobao_user_nick");
		
		if(table.containsValue(userId)){
			flagFile = "4";
		} else {
			table.put(Thread.currentThread().getName(), userId);
			
			
			// 创建csv接收对象
			CsvReaderUtils r = null;
			try {

				// 获取导入文件名称
				String fileName = file.getOriginalFilename();

				// 获取文件流
				InputStream inputStream = file.getInputStream();

				// 生成CsvReader对象，以，为分隔符，GBK编码方式
				r = new CsvReaderUtils(inputStream, ',', Charset.forName("GBK"));

				// 获取文件大小
				long size = file.getSize();
				if (size > 52428800) {
					flagFile = "2";
				} else {
					// 读取表头
					r.readHeaders();

					String[] header = r.getHeaders();
					// 获取每个头对应的索引
					Map<String, Integer> headerMap = this.makeHeaderMap(header);

					// 判断文件导入的表头数据是否符合订单要求
					if (headerMap.keySet().containsAll(
							OrderHeaderUtil.getHeader())) {

						// 获取每一行数组放入List
						List<String[]> datasList = new ArrayList<String[]>();
						while (r.readRecord()) {
							datasList.add(r.getValues());
						}
						logger.info("成功---获取表头下的每一行数据，封装行数：" + datasList.size());

						// 创建导入记录对象
						Long orderImportId = createOrderImport(userId,
								fileName, datasList);

						if (orderImportId != null) {
							logger.info("成功---创建导入记录对象返回id：" + orderImportId);
							// 调用线程处理数据
							this.startThread(datasList, headerMap, userId,
									orderImportId);
						} else {
							logger.error("失败---创建导入记录对象失败：" + orderImportId);
							flagFile = "1";
						}

						// 保存文件
						this.saveFile(file, fileName);
					} else {
						flagFile = "3";
					}
				}
			} catch (Exception e) {
				flagFile = "1";
				logger.error("失败---处理数据失败:" + e.getMessage());
			} finally {
				r.close();
				table.remove(Thread.currentThread().getName());
			}
		}
		
		try {
			// 创建对象封装参数,输出到前台
			JSONObject json = new JSONObject();
			json.putOnce("error", flagFile);
			response.setContentType("application/json; charset=utf-8");
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			logger.error("失败---返回json失败:" + e.getMessage());
		}
		return null;
	}
	

	private Long createOrderImport(String userId,
			String fileName, List<String[]> datasList) {
		OrderHistoryImport orderImport = new OrderHistoryImport();
		orderImport.setUserId(userId);// 用户编号
		orderImport.setOrderName(fileName);// 导入的订单文件名称
		orderImport.setCreatedDate(new Date());// 导入时间
		orderImport.setState("1");
		orderImport.setSumNumber(datasList.size());// 总行数
		orderImport.setCompleteNumber(0);// 处理完成行数
		orderImport.setCompletedQuantity(0);// 已完成数量
		orderImport.setErrorNumber(0);// 报错总行数
		orderImport.setRepetitionNumber(0);//重复数量
		// 执行保存返回id
		Long historyImportId = orderHistoryImportService
				.insertOrderHistoryImport(orderImport);
		return historyImportId;
	}

	/**
	 * 保存导入的订单文件到指定的目录下
	 */
	private void saveFile(MultipartFile file, String fileName) {
		try {
			// 创建时间，并格式
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String formatStr = format.format(date);

			// 获取保存路径
			String filePath = UploadPathUtil.ORDER_FILE_PATH;
			StringBuffer fileBuffer = new StringBuffer(filePath);
			fileBuffer.append(PinYin4jUtil.hanyu2pinyin(fileName.substring(0,
					fileName.lastIndexOf("."))));
			fileBuffer.append("_" + formatStr);
			fileBuffer.append(fileName.substring(fileName.lastIndexOf("."),
					fileName.length()));

			// 是否创建文件
			File fileP = new File(filePath);
			if (!fileP.exists()) {
				fileP.mkdirs();
			}
			// 上传文件
			file.transferTo(new File(fileBuffer.toString()));
			logger.info("成功---文件保存成功，路径：" + fileBuffer.toString());
			// 将文件打包为zip
			String sourcePath = fileBuffer.toString();
			String zipPath = sourcePath.substring(0,
					sourcePath.lastIndexOf("."))
					+ ".zip";
			boolean createZip = ZipUtils.createZip(sourcePath, zipPath);
			// 判断是否创建zip,如果创建成功就删除原文件
			if (createZip) {
				File delFile = new File(sourcePath);
				delFile.delete();
				logger.info("成功---文件压缩成功，路径：" + zipPath);
			}

		} catch (Exception e) {
			logger.error("失败---保存文件异常:" + e.getMessage());
		}
	}

	/**
	 * 通过id删除导入记录 helei 2017年2月10日下午4:25:11
	 */
	@RequestMapping(value = "/deleteOrderById")
	public String deleteOrderById(String id, HttpServletRequest request) {
		String userId = (String) request.getSession().getAttribute("taobao_user_nick");
		// 删除数据
		orderHistoryImportService.deleteOrderById(id);
		removeTable(userId);
		return "redirect:/OrderHistoryImport/showLoadHistoryOrder";
	}

	/**
	 * 删除重复上传文件标识
	 */
	@RequestMapping("/removeTable")
	@ResponseBody
	public String removeTable(String userId){
		if(null != userId && !"".equals(userId)){
			try {
				Set<Entry<String,String>> addSet = new HashSet<Entry<String,String>>(table.entrySet());
				for (Entry<String, String> entry : addSet) {
					if(entry.getValue().equals(userId)){
						table.remove(entry.getKey());
					}
				}
				return "success";
			} catch (Exception e) {
				return "error";
			}
		}else{
			return "error";
		}
	}
	
	/**
	 * 使用线程处理数据
	 */
	public void startThread(final List<String[]> datasList,
			final Map<String, Integer> headerMap, final String userId,
			final Long orderImportId) {
		new Thread() {
			public void run() {
				table.put(Thread.currentThread().getName(), userId);
				ExecutorService executor = MyFixedThreadPool.getMyFixedThreadPool();
				List<ImportThread> threadList = new ArrayList<ImportThread>();
				ImportThread importThread =null;
				
				//将导入的订单做商品拆分，返回商品编号
				Map<String,List<String>> itemTitleMap = extractItemData(headerMap, datasList,userId);
				
				//设置每个线程处理10000条数据
				int threadNumber = threadNum;
				
				for (int i = 0; i < Math.ceil((datasList.size() / threadNumber)+((datasList.size() % threadNumber==0)?0:1)); i++) {
					List<String[]> subDataList = datasList.subList(i * threadNumber,((i + 1) * threadNumber<datasList.size())?(i + 1) * threadNumber:datasList.size());
					//封装数据service
					importThread = new ImportThread(subDataList, headerMap, orderImportId, userId,itemTitleMap);
					//赋值数据
					threadList.add(importThread);
					
				}
				for (ImportThread t: threadList) {
					executor.execute(t);
					try {
						Thread.sleep(1000);
						logger.info("成功---线程休眠中..."+new Date());
					} catch (InterruptedException e) {
					}
				}
				logger.info("成功---使用线程个数："+threadList.size());
				table.remove(Thread.currentThread().getName());
			}
		}.start();
	}
	
	/**
	 * @Description: 提取商品信息，保存并返回 
	 * @author HL
	 * @date 2017年11月21日 上午10:47:37
	 */
	private Map<String, List<String>> extractItemData(Map<String, Integer> headerMap,
			List<String[]> datasList, String userId) {
		//查询出当前用户所以商品标题及商品id---（导入及同步商品）
		Map<String, Long> itemList = itemImportService.findItemTitleAndItemid(userId);
		//封装所有导入的商品标题
		Map<String, List<String>> ImportTitles = ImportUtils.getImportTitles(headerMap, datasList);
		//将该用户的商品和当前导入的比对 --key需要与待导入的订单比对，value--需要将数据保存到新建商品表
		Map<Map<String, List<String>>, List<ItemImport>> map = ImportUtils.comparisonItemTitle(itemList,ImportTitles,userId);
		//获取map中的key 数据进行订单比对
		Map<String,List<String>> itemTitleMap = ImportUtils.getMapKey(map);
		//获取map中的value数据，将订单拆分的商品数据保存
		List<ItemImport>  mapValue = ImportUtils.getMapValue(map);
		
		//设置商品list切割数量
		int subNumber = subItemListNum;
		List<List<ItemImport>> subList = new ArrayList<List<ItemImport>>();
		
		try {
			if(null != mapValue && mapValue.size()>0){
				for (int i = 0; i < Math.ceil((mapValue.size() / subNumber)+((mapValue.size() % subNumber==0)?0:1)); i++) {
					List<ItemImport> subDataList = mapValue.subList(i * subNumber,((i + 1) * subNumber<mapValue.size())?(i + 1) * subNumber:mapValue.size());
					subList.add(subDataList);
				}
				for (List<ItemImport> list : subList) {
					itemImportService.insertItemImportList(list);
				}
			}
		} catch (Exception e) {
			logger.error("失败---商品拆分保存异常："+e.getMessage());
		}
		return itemTitleMap;
	}
}
