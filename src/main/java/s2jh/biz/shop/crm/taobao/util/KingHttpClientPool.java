package s2jh.biz.shop.crm.taobao.util;

import org.apache.http.HttpHost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;


/**
 *
 * @author zlp
 *
 */
public class KingHttpClientPool {

	private static PoolingHttpClientConnectionManager cm;
	
    static {
    	ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
    	LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
    	
    	Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
    			 .register("http", plainsf)
    			 .register("https", sslsf)
    			 .build();
    	
    	cm = new PoolingHttpClientConnectionManager(r);
    	// Increase max total connection to 200
    	cm.setMaxTotal(2000);
    	// Increase default max connection per route to 200
    	cm.setDefaultMaxPerRoute(200);
    	// Increase max connections for localhost:80 to 50
    	HttpHost localhost = new HttpHost("locahost", 800);
    	cm.setMaxPerRoute(new HttpRoute(localhost), 500);
    }

    public static CloseableHttpClient getHttpClient() {
    	CloseableHttpClient httpClient = HttpClients.custom()
    			.setConnectionManager(cm)
    			.build();
    	return httpClient;
    }
}
