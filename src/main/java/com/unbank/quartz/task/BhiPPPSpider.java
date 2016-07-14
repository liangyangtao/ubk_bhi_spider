package com.unbank.quartz.task;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.unbank.dao.WebBeanStore;
import com.unbank.fetch.HttpClientBuilder;

public class BhiPPPSpider {

	private static RequestConfig requestConfig = RequestConfig.custom()
			.setSocketTimeout(30000).setConnectTimeout(30000)
			.setStaleConnectionCheckEnabled(true)
			.setCircularRedirectsAllowed(true)
			// .setProxy(proxy)
			.setMaxRedirects(50).build();

	private static BasicCookieStore cookieStore = new BasicCookieStore();

	private static String headerCookie = "";

	public static void main(String[] args) {

		PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
		HttpClientBuilder httpClientBuilder = new HttpClientBuilder(false,
				poolingHttpClientConnectionManager, cookieStore);
		CloseableHttpClient httpClient = httpClientBuilder.getHttpClient();
		headerCookie = "popuphref=http%3A%2F%2Fwww.bhi.com.cn; ASPSESSIONIDCABTRBQC=KOIFDMFCOIJHEJBLOGKOHLOG; ASP.NET_SessionId=uheied55jk02hgza41jljuix; CheckCode=64b3; LogUser=JRoT+lKgrTnCfXNDhssQGNlWpgvTqAecTxhwTD81sRw=; Hm_lvt_8d994d177d2158b74a6011c3839d1a20=1468234675,1468322765,1468365135,1468380619; Hm_lpvt_8d994d177d2158b74a6011c3839d1a20=1468410642; BHI_BROWSE_STATICS_WIDTH=1366; BHI_BROWSE_STATICS_HEIGHT=768";
		String industrys[] = new String[] { "%E7%8E%AF%E4%BF%9D",
				"%E4%BA%A4%E9%80%9A", "%E5%B8%82%E6%94%BF",
				"%E7%A4%BE%E4%BC%9A%E4%BA%8B%E4%B8%9A", "%E8%83%BD%E6%BA%90",
				"%E6%B0%B4%E5%88%A9", "%E5%8C%96%E5%B7%A5",
				"%E5%85%B6%E4%BB%96" };
		for (String industry : industrys) {
			int pageSize = 10;
			for (int i = 1; i <= pageSize; i++) {
				String url = "http://www.bhi.com.cn/project/Project.ashx?PageIndex="
						+ i
						+ "&PageSize=10&Type=1&Province=%E6%B2%B3%E5%8D%97&Industry="
						+ industry
						+ "&ProjectType=&GreaterArea=&isProgress=0&isLinkMan=0";
				pageSize = getInfo(httpClient, url);
			}
		}

	}

	private static int getInfo(CloseableHttpClient httpClient, String url) {
		String html = getHtml(httpClient, url, "utf-8", headerCookie);
		JSONObject bodyObject = JSONObject.fromObject(html);
		JSONArray list = bodyObject.getJSONArray("list");
		int pageSize = bodyObject.getInt("PageSize");
		int recordCount = bodyObject.getInt("RecordCount");
		int num = recordCount / pageSize + 1;
		for (Object object : list) {
			JSONObject proObject = JSONObject.fromObject(object);

			Map<String, Object> project = (Map<String, Object>) JSONObject
					.toBean(proObject, Map.class);
			int id = (int) project.get("Exportid");
			String detailurl = "http://www.bhi.com.cn/project/ProjectDetails.ashx?Exportid="
					+ id;
			String detailHtml = getHtml(httpClient, detailurl, "utf-8",
					headerCookie);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("info", proObject.toString());
			map.put("description", detailHtml);
			// JSONObject detailJsonObject = JSONObject.fromObject(detailHtml);
			// JSONArray descriptJsonArray = detailJsonObject
			// .getJSONArray("Description");
			// List<Map<String, Object>> descriptions = new
			// ArrayList<Map<String, Object>>();
			// for (Object object2 : descriptJsonArray) {
			// JSONObject jsonObject = JSONObject.fromObject(object2);
			// Map<String, Object> description = (Map<String, Object>)
			// JSONObject
			// .toBean(jsonObject, Map.class);
			// descriptions.add(description);
			// }
			// project.put("description", descriptions);

			saveProject(map);
		}
		return num;
	}

	private static void saveProject(Map<String, Object> project) {
		new WebBeanStore().executeMapSQL("insert into  bhi_ppp ", project);

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
		httpGet.setHeader("Referer", "http://www.bhi.com.cn");
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
