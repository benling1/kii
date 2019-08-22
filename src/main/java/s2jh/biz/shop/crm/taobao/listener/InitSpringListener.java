package s2jh.biz.shop.crm.taobao.listener;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import s2jh.biz.shop.crm.buyers.queue.MemberInfoQueue;
import s2jh.biz.shop.crm.manage.service.ImportTradeQueueService;
import s2jh.biz.shop.crm.manage.service.LogAccessQueueService;
import s2jh.biz.shop.crm.manage.service.SyncEffectQueueService;
import s2jh.biz.shop.crm.manage.service.TradeQueueService;
import s2jh.biz.shop.crm.manage.service.VipMemberQueueService;
import s2jh.biz.shop.crm.manage.util.InitDataManageUtil;
import s2jh.biz.shop.crm.order.queue.TransactionQueue;
import s2jh.biz.shop.crm.schedule.job.auto.InitAutoOneScheduleRunnable;
import s2jh.biz.shop.crm.taobao.GetTaobaoMSG;
import s2jh.biz.shop.crm.taobao.util.TmcThreadUtil;
import s2jh.biz.shop.crm.tmc.taobao.ObtainTaoBaoTmc;

//监听类
public class InitSpringListener{
	@Autowired
	private InitAutoOneScheduleRunnable initAutoOneScheduleRunnable;
	@Autowired
	private GetTaobaoMSG getTaobaoMSG;
	@Autowired
	private TmcThreadUtil  tmcThread;
	@Autowired
	private TransactionQueue transactionQueue;
    @Autowired
	private MemberInfoQueue memberProcessor;
	@Autowired
	private InitDataManageUtil initDataManageUtil;
	@Autowired
	private TradeQueueService tradeQueueService;
	@Autowired
	private VipMemberQueueService vipMemberQueueService;
	@Autowired
	private ImportTradeQueueService importTradeQueueService;
	@Autowired
	private SyncEffectQueueService syncEffectQueueService;
	@Autowired
	private ObtainTaoBaoTmc obtainTaoBaoTmc;
    @Autowired
    private LogAccessQueueService  logAccessQueueService ;
	
	Logger logger = org.slf4j.LoggerFactory
			.getLogger(InitSpringListener.class);

	public void contextInitialized(){
		logger.info("**************** Servlet 初始化加载淘宝消息监听服务**************");
//		tmcThread.handleTMCData();
//		new Thread(obtainTaoBaoTmc).start();
//		旧版tmc new Thread(getTaobaoMSG).start();
//		logger.info("******************* Servlet 一次性定时任务 *********************");
//		new Thread(initAutoOneScheduleRunnable).start();
		vipMemberQueueService.handleTMCData();
		//异步日志处理队列
		logAccessQueueService.handleLogAccessData();
	}
	

}
