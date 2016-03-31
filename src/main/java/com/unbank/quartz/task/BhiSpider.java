package com.unbank.quartz.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.unbank.fetch.HttpClientBuilder;
import com.unbank.paser.detail.BHIDetailPaser;
import com.unbank.tools.SimpleTools;
import com.unbank.tools.Values;

public class BhiSpider {

	private static HttpHost proxy = new HttpHost(Values.v.PROXYIP,
			Integer.parseInt(Values.v.PROXYPORT));
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(30000).setConnectTimeout(30000)
			.setStaleConnectionCheckEnabled(true)
			.setCircularRedirectsAllowed(true).setProxy(proxy)
			.setMaxRedirects(50).build();

	private static BasicCookieStore cookieStore = new BasicCookieStore();

	public static void printCookies() {
		System.out.println("---查看当前Cookie---");
		List<Cookie> cookies = cookieStore.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				System.out.print(c.getName() + "         :" + c.getValue());
				System.out.print("  domain:" + c.getDomain());
				System.out.print("  expires:" + c.getExpiryDate());
				System.out.print("  path:" + c.getPath());
				System.out.println("    version:" + c.getVersion());
			}
		}
	}

	public static String getCookiesString() {
		List<Cookie> cookies = cookieStore.getCookies();
		StringBuffer sb = new StringBuffer();
		if (cookies != null) {
			for (Cookie c : cookies) {
				sb.append(c.getName() + "=" + c.getValue() + ";");
			}
		}
		return sb.toString();
	}

	public static String getCheckCode() {
		List<Cookie> cookies = cookieStore.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				System.out.print(c.getName() + "         :" + c.getValue());
				if (c.getName().trim().equals("CheckCode")) {
					return c.getValue();
				}
			}
		}
		return null;
	}

	public void BHIConsole(String startTime, String endTime) {
		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		loginbhi(httpClient);
		// vip
		List<String> urls = getVipProjectsUrls(startTime, endTime, httpClient,
				getCookiesString());
		analyzerVipPaper(urls, httpClient, getCookiesString());
		// 普通
		List<String> urls2 = getProjectsUrls(startTime, endTime, httpClient,
				getCookiesString());
		analyzerPaper(urls2, httpClient, getCookiesString());

	}

	private static void loginbhi(CloseableHttpClient httpClient) {
		String loginurl = "http://www.bhi.com.cn/Login/login.aspx";
		getHtml(httpClient, loginurl, "utf-8", getCookiesString());
		printCookies();
		String codeurl = "http://www.bhi.com.cn/Public/IsValid.ashx";
		getHtml(httpClient, codeurl, "utf-8", getCookiesString());
		printCookies();
		String checkCode = getCheckCode();
		String checkcodeurl = "http://www.bhi.com.cn/Public/IsExistValid.aspx";
		getHtml(httpClient, checkcodeurl, "utf-8", getCookiesString());
		printCookies();
		String tourl = "http://www.bhi.com.cn/Login/login.ashx?prev=";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "unbank");
		params.put("pwd", "chengxin163");
		params.put("code", checkCode);
		post(httpClient, tourl, params, "utf-8", getCookiesString());
		printCookies();
		String lourl = "http://www.bhi.com.cn/Login/login.ashx?prev=";
		post(httpClient, lourl, params, "utf-8", getCookiesString());
		printCookies();
		String lourl2 = "http://www.bhi.com.cn/Login/login.aspx";
		getHtml(httpClient, lourl2, "utf-8", getCookiesString());
		printCookies();
		String lourl3 = "http://www.bhi.com.cn/Login/showmsg.aspx?prev=";
		getHtml(httpClient, lourl3, "utf-8", getCookiesString());
		printCookies();
	}

	public static List<String> getVipProjectsUrls(String startTime,
			String endTime, CloseableHttpClient httpClient, String cookie) {
		int i = 1;
		List<String> urls = new ArrayList<String>();
		while (true) {
			try {
				String url = "http://www.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=30&solr_rsort=0&solr_keywords=%25u8BF7%25u8F93%25u5165%25u5173%25u952E%25u8BCD&solr_area=&solr_industry=&solr_date=0&solr_cbcolumns=,vip&solr_currentPage="
						+ i
						+ "&solr_fund=&solr_hezhun=&solr_shenpi=&solr_beian=&solr_xingzhi=&solr_laiyuan=&solr_suoyou=&_=1425973128841";
				i++;
				String html = getHtml(httpClient, url, "utf-8", getCookiesString());
				String cookieString  = getCookiesString();
				if(cookieString.contains("LogUser")){
					
				}else{
					Thread.sleep(10*1000);
					loginbhi(httpClient);
				}
				html = getHtml(httpClient, url, "utf-8", getCookiesString());
				JSONObject json = JSONObject.fromObject(html);
				JSONArray keywordJsonArray = json.getJSONArray("docs");
				boolean isbreak = false;
				for (Object object : keywordJsonArray) {
					JSONObject children = JSONObject.fromObject(object);
					System.out.println(children.get("adddate"));
					String adddate = (String) children.get("adddate");
					if (SimpleTools.stringToDate(adddate).after(
							SimpleTools.stringToDate(endTime))) {
						continue;
					} else if (SimpleTools.stringToDate(adddate).before(
							SimpleTools.stringToDate(startTime))) {
						isbreak = true;
						break;
					}
					String href = Jsoup.parse(children.get("title").toString())
							.select("a").first().attr("href");
					String tempURL = "http://www.bhi.com.cn" + href;
					System.out.println(tempURL);
					urls.add(tempURL);
				}
				if (isbreak) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
		return urls;
	}

	private static void analyzerVipPaper(List<String> urls,
			CloseableHttpClient httpClient, String cookie) {
		for (String url : urls) {
			System.out.println(url);
			try {
				String html = getHtml(httpClient, url, "utf-8",getCookiesString() );
				if (html == null || html.isEmpty()) {
					continue;
				}
				String cookieString  = getCookiesString();
				if(cookieString.contains("LogUser")){
					
				}else{
					Thread.sleep(10*1000);
					loginbhi(httpClient);
				}
			    html = getHtml(httpClient, url, "utf-8", getCookiesString());
				new BHIDetailPaser().analyzerVipPaper(html, url);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public static List<String> getProjectsUrls(String startTime,
			String endTime, CloseableHttpClient httpClient, String cookie) {
		int i = 1;
		List<String> urls = new ArrayList<String>();
		while (true) {
			try {
				String url = "http://www.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=30&solr_rsort=0&solr_keywords=%25u8BF7%25u8F93%25u5165%25u5173%25u952E%25u8BCD&solr_area=&solr_industry=&solr_date=0&solr_cbcolumns=%E6%8B%9F&solr_currentPage="
						+ i
						+ "&solr_fund=&solr_hezhun=&solr_shenpi=&solr_beian=&solr_xingzhi=&solr_laiyuan=&solr_suoyou=&_=1426669089669";
				i++;
				String html = getHtml(httpClient, url, "utf-8", getCookiesString());
				String cookieString  = getCookiesString();
				if(cookieString.contains("LogUser")){
					
				}else{
					Thread.sleep(10*1000);
					loginbhi(httpClient);
				}
				html = getHtml(httpClient, url, "utf-8", getCookiesString());
				JSONObject json = JSONObject.fromObject(html);
				JSONArray keywordJsonArray = json.getJSONArray("docs");
				boolean isbreak = false;
				for (Object object : keywordJsonArray) {
					JSONObject children = JSONObject.fromObject(object);
					System.out.println(children.get("adddate"));
					String adddate = (String) children.get("adddate");
					if (SimpleTools.stringToDate(adddate).after(
							SimpleTools.stringToDate(endTime))) {
						continue;
					} else if (SimpleTools.stringToDate(adddate).before(
							SimpleTools.stringToDate(startTime))) {
						isbreak = true;
						break;
					}
					String href = Jsoup.parse(children.get("title").toString())
							.select("a").first().attr("href");
					String tempURL = "http://www.bhi.com.cn" + href;
					System.out.println(tempURL);
					urls.add(tempURL);
				}
				if (isbreak) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

		}
		return urls;
	}

	private static void analyzerPaper(List<String> urls,
			CloseableHttpClient httpClient, String cookie) {
		for (String url : urls) {
			System.out.println(url);
			try {
				String html = getHtml(httpClient, url, "utf-8", getCookiesString());
				if (html == null || html.isEmpty()) {
					continue;
				}
				String cookieString  = getCookiesString();
				if(cookieString.contains("LogUser")){
					
				}else{
					Thread.sleep(10*1000);
					loginbhi(httpClient);
				}
				 html = getHtml(httpClient, url, "utf-8", getCookiesString());
				new BHIDetailPaser().analyzerPaper(html, url);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}

	}

	public static String getHtml(CloseableHttpClient httpClient, String url,
			String charset, String cookie) {
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);
		String useCharset = charset;
		HttpGet httpGet = new HttpGet(url);
		fillGetHeader(url, httpGet, cookie);
		httpGet.setConfig(requestConfig);
		try {
			CloseableHttpResponse response = httpClient.execute(httpGet,
					context);
			try {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, useCharset);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}

	public static String post(CloseableHttpClient httpClient, String url,
			Map<String, String> params, String charset, String cookie) {
		HttpClientContext context = HttpClientContext.create();
		context.setCookieStore(cookieStore);
		String useCharset = charset;
		if (charset == null) {
			useCharset = "utf-8";
		}
		try {
			HttpPost httpPost = new HttpPost(url);
			fillPostHeader(url, httpPost, cookie);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			if (params != null) {
				for (String key : params.keySet()) {
					nvps.add(new BasicNameValuePair(key, params.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
			}
			httpPost.setConfig(requestConfig);
			CloseableHttpResponse response = httpClient.execute(httpPost,
					context);
			try {
				HttpEntity entity = response.getEntity();
				return EntityUtils.toString(entity, useCharset);
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void fillPostHeader(String url, HttpPost httpPost,
			String cookie) {
		httpPost.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
		httpPost.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language",
				"zh-CN,zh;q=0.8,en-us;q=0.8,en;q=0.6");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate,sdch");
		httpPost.setHeader("Host", "www.bhi.com.cn");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Referer",
				"http://www.bhi.com.cn/projects/ItemList.aspx?a=1&p=t");
		httpPost.setHeader("Cache-Control", "max-age=0");
		httpPost.setHeader("Cookie", cookie);

	}

	private static void fillGetHeader(String url, HttpGet httpGet, String cookie) {
		httpGet.setHeader(
				"User-Agent",
				"Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.154 Safari/537.36");
		httpGet.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language",
				"zh-CN,zh;q=0.8,en-us;q=0.8,en;q=0.6");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate,sdch");
		httpGet.setHeader("Host", "www.bhi.com.cn");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Referer",
				"http://www.bhi.com.cn/projects/ItemList.aspx?a=1&p=t");
		httpGet.setHeader("Cache-Control", "max-age=0");
		httpGet.setHeader("Cookie", cookie);
	}
}
