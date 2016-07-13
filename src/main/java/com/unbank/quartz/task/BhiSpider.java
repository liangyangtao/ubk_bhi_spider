package com.unbank.quartz.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
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
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.unbank.fetch.HttpClientBuilder;
import com.unbank.paser.detail.BHIDetailPaser;
import com.unbank.tools.SimpleTools;

public class BhiSpider {

	// private static HttpHost proxy = new HttpHost(Values.v.PROXYIP,
	// Integer.parseInt(Values.v.PROXYPORT));
	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(30000).setConnectTimeout(30000)
			.setStaleConnectionCheckEnabled(true)
			.setCircularRedirectsAllowed(true)
			// .setProxy(proxy)
			.setMaxRedirects(50).build();

	private static BasicCookieStore cookieStore = new BasicCookieStore();

	private static String headerCookie = "";

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
		List<String> urls = getVipProjectsUrls(startTime, endTime, httpClient,
				headerCookie);
		List<String> urls2 = getProjectsUrls(startTime, endTime, httpClient,
				headerCookie);
		loginbhi(httpClient);

		System.out.println(headerCookie);
		// headerCookie
		// ="LogUser=gsJGLZmi92LaFq5nxFn4vgBP/a8k7h90uPB54g+TXeY=; ASP.NET_SessionId=sblx2xjbn1m2kel4uv2bxakw; Hm_lvt_8d994d177d2158b74a6011c3839d1a20=1467711069; Hm_lpvt_8d994d177d2158b74a6011c3839d1a20=1467711778";
		if (headerCookie.contains("LogUser")) {
			System.out.println("登陆成功");
		} else {
			System.out.println("登陆失败");
			return;
		}
		getRecodPage(httpClient,
				"http://projectinfo.bhi.com.cn/Projects/ProjectNList.aspx");
		// vip
		//
		analyzerVipPaper(urls, httpClient, headerCookie);
		// 普通

		analyzerPaper(urls2, httpClient, headerCookie);

	}

	private static void getRecodPage(CloseableHttpClient httpClient, String a) {
		String url = "http://projectinfo.bhi.com.cn/Login/RecordPage.ashx";

		Map<String, String> aaa = new HashMap<String, String>();
		aaa.put("url", a);
		System.out.println(post(httpClient, url, aaa, "utf-8", headerCookie));

	}

	private static void loginbhi(CloseableHttpClient httpClient) {
		// http://www.bhi.com.cn/Login/login.aspx
		String loginurl = "http://www.bhi.com.cn/Login/login.aspx";
		// http://www.bhi.com.cn/Login/login.aspx
		String html = getHtml(httpClient, loginurl, "utf-8", getCookiesString());
		printCookies();
		Document loginDocument = Jsoup.parse(html, loginurl);
		Element checkElement = loginDocument.select("#loginsafecode").first();
		String codeurl = checkElement.absUrl("src");
		// http://www.bhi.com.cn/Public/IsValid.ashx?num=537744831
		// http://www.bhi.com.cn/Public/IsValid.ashx?num=40452815
		// String codeurl = "http://www.bhi.com.cn/Public/IsValid.ashx";
		getHtml(httpClient, codeurl, "utf-8", getCookiesString());
		printCookies();
		String checkCode = getCheckCode();

		String checkcodeurl = "http://www.bhi.com.cn/Public/IsExistValid.aspx?_="
				+ new Date().getTime();
		getHtml(httpClient, checkcodeurl, "utf-8", getCookiesString());
		printCookies();

		String tourl = "http://www.bhi.com.cn/Login/login.ashx?prev=";
		Map<String, String> params = new HashMap<String, String>();
		params.put("name", "unbank");
		params.put("pwd", "");
		params.put("code", checkCode);
		params.put("method", "popup");
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
		headerCookie = getCookiesString();
	}

	public static List<String> getVipProjectsUrls(String startTime,
			String endTime, CloseableHttpClient httpClient, String cookie) {
		int i = 1;
		List<String> urls = new ArrayList<String>();
		while (true) {
			try {
				String url = "http://projectinfo.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=30&solr_rsort=0&solr_keywords=&solr_area=&solr_industry=&solr_date=-366&solr_cbcolumns=1&solr_currentPage="
						+ i
						+ "&solr_fund=&solr_jinzhan=&solr_xmxingzhi=&solr_qyxingzhi=&solr_leibie=&solr_fenlei=";

				i++;
				String html = getHtml(httpClient, url, "utf-8", "");
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
					String tempURL = "http://projectinfo.bhi.com.cn" + href;
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
				String html = getHtml(httpClient, url, "utf-8", cookie);
				if (html == null || html.isEmpty()) {
					continue;
				}
				new BHIDetailPaser().analyzerVipPaper(html, url);
				getRecodPage(httpClient, url);
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
				String url = "http://projectinfo.bhi.com.cn/solr/SolrProject.ashx?solr_core=0&solr_rows=30&solr_rsort=0&solr_keywords=&solr_area=&solr_industry=&solr_date=-366&solr_cbcolumns=0&solr_currentPage="
						+ i
						+ "&solr_fund=&solr_jinzhan=&solr_xmxingzhi=&solr_qyxingzhi=&solr_leibie=&solr_fenlei=";
				i++;
				String html = getHtml(httpClient, url, "utf-8", "");
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
					String tempURL = "http://projectinfo.bhi.com.cn" + href;
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
				String html = getHtml(httpClient, url, "utf-8", cookie);
				if (html == null || html.isEmpty()) {
					continue;
				}
				new BHIDetailPaser().analyzerPaper(html, url);
				getRecodPage(httpClient, url);
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
		httpPost.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.2; rv:23.0) Gecko/20100101 Firefox/23.0");
		httpPost.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpPost.setHeader("Accept-Language",
				"zh-CN,zh;q=0.8,en-us;q=0.8,en;q=0.6");
		httpPost.setHeader("Accept-Encoding", "gzip, deflate,sdch");
		httpPost.setHeader("Host", getDomain(url));
		httpPost.setHeader("DHT", "1");
		httpPost.setHeader("Connection", "keep-alive");
		httpPost.setHeader("Referer",
				"http://projectinfo.bhi.com.cn/Projects/ProjectNList.aspx");
		httpPost.setHeader("Cache-Control", "private");
		httpPost.setHeader("Cookie", cookie);

	}

	private static void fillGetHeader(String url, HttpGet httpGet, String cookie) {

		httpGet.setHeader("User-Agent",
				"Mozilla/5.0 (Windows NT 5.2; rv:23.0) Gecko/20100101 Firefox/23.0");
		httpGet.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		httpGet.setHeader("Accept-Language",
				"zh-CN,zh;q=0.8,en-us;q=0.8,en;q=0.6");
		httpGet.setHeader("Accept-Encoding", "gzip, deflate,sdch");
		httpGet.setHeader("Host", getDomain(url));
		httpGet.setHeader("DHT", "1");
		httpGet.setHeader("Connection", "keep-alive");
		httpGet.setHeader("Referer",
				"http://projectinfo.bhi.com.cn/Projects/ProjectNList.aspx");
		httpGet.setHeader("Cache-Control", "private");
		httpGet.setHeader("Cookie", cookie);
	}

	private static String getDomain(String url) {
		String domain = "";
		try {
			URL u = new URL(url);
			domain = u.getHost();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return domain;
	}

}
