package s2jh.biz.shop.utils;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * 一个简单的HTTP客户端，发送HTTP请求，模拟浏览器 可打印服务器发送过来的HTTP消息
 * 
 * File: SimpleHttpClient.java<br/> Description: <br/>
 * 
 * Copyright: Copyright (c) 2014 ecbox.com<br/> Company: ECBOX,Inc.<br/>
 * 
 * @author chenxiaochun
 * @date Oct 27, 2014
 * @version 1.0
 */
public class SimpleHttpClient {

	public SimpleHttpClient() {
	}

	/**
	 * 发送短信
	 * 
	 * @param userCode
	 * @param password
	 * @param mobile
	 * @param content
	 * 
	 * @return
	 */
	public static String send(String url,String username, String password, String mobile,
			String content, String taskid, String extnum) {
		String result = "";
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();

			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");

			post = new PostMethod(url);

			NameValuePair name = new NameValuePair("uid", username);
			NameValuePair pass = new NameValuePair("pwd", MD5Utils
					.md5(password));
			NameValuePair RecvNum = new NameValuePair("content", content);
			NameValuePair Msg = new NameValuePair("mobile", mobile);
			NameValuePair charset = new NameValuePair("extnum", extnum);
			NameValuePair taskidss = new NameValuePair("taskid", taskid);

			post.setRequestBody(new NameValuePair[] { name, pass, RecvNum, Msg,
					charset, taskidss });
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
				post = null;
			}
		}

		return result;
	}

	/**
	 * 获取状态报告
	 * 
	 * @param userCode
	 * @param password
	 * @param mobile
	 * @param content
	 * 
	 * @return
	 */
	public static String report(String url,String username, String password) {
		String result = "";
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();

			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");

			post = new PostMethod(url);

			NameValuePair name = new NameValuePair("uid", username);
			NameValuePair pass = new NameValuePair("pwd", MD5Utils
					.md5(password));

			post.setRequestBody(new NameValuePair[] { name, pass });
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
				post = null;
			}
		}

		return result;
	}

	/**
	 * 获取短信上行
	 * 
	 * @param userCode
	 * @param password
	 * @param mobile
	 * @param content
	 * 
	 * @return
	 */
	public static String reply(String url,String username, String password) {
		String result = "";
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();

			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");

			post = new PostMethod(url);

			NameValuePair name = new NameValuePair("uid", username);
			NameValuePair pass = new NameValuePair("pwd", MD5Utils
					.md5(password));

			post.setRequestBody(new NameValuePair[] { name, pass });
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
				post = null;
			}
		}

		return result;
	}

	/**
	 * 获取查询余额
	 * 
	 * @param userCode
	 * @param password
	 * @param mobile
	 * @param content
	 * 
	 * @return
	 */
	public static String balance(String url,String username, String password) {
		String result = "";
		PostMethod post = null;
		try {
			HttpClient client = new HttpClient();

			client.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "GBK");

			post = new PostMethod(url);

			NameValuePair name = new NameValuePair("uid", username);
			NameValuePair pass = new NameValuePair("pwd", MD5Utils
					.md5(password));

			post.setRequestBody(new NameValuePair[] { name, pass });
			client.executeMethod(post);
			result = post.getResponseBodyAsString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (post != null) {
				post.releaseConnection();
				post = null;
			}
		}

		return result;
	}
}
