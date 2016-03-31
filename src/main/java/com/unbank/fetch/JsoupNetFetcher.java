package com.unbank.fetch;

import java.io.EOFException;
import java.io.IOException;
import java.net.SocketTimeoutException;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupNetFetcher {
	private final static Logger logger = Logger
			.getLogger(JsoupNetFetcher.class);

	private static final int TIMEOUT = 5 * 1000;
	private static final short RETRYTIMES = 2;

	public Document fetchText(String url) {
		Document doc = getDocumentByJsoup(url);
		return doc;
	}

	public Document getDocumentByJsoup(String url) {
		Document doc = null;
		Connection conn = null;
		conn = Jsoup.connect(url);
		fillConnection(url, conn);
		// GET方式获取内容
		try {
			doc = conn.ignoreContentType(true).timeout(TIMEOUT).get();
		} catch (IOException e) {
			if (e instanceof SocketTimeoutException
					|| e instanceof java.net.UnknownHostException) {
				for (int i = 0; i < RETRYTIMES; i++) {
					try {
						doc = conn.timeout(TIMEOUT + (int) (Math.random() * 3))
								.get();
					} catch (IOException e1) {

					} finally {
						if (doc != null) {
							break;
						}
					}
				}
			} else if (e instanceof EOFException) {

			} else {
				logger.info(url + "       ", e);
				return null;
			}

		} catch (Exception e) {
			logger.info(url + "        ", e);
		}
		// GET方式获取内容失败，尝试POST方式获取
		if (doc == null) {
			try {
				doc = conn.timeout(TIMEOUT).post();
			} catch (IOException e) {
				if (e instanceof SocketTimeoutException
						|| e instanceof java.net.UnknownHostException) {
					for (int i = 0; i < RETRYTIMES; i++) {
						try {
							doc = conn.timeout(
									TIMEOUT + (int) (Math.random() * 3)).post();
						} catch (IOException e1) {

						} finally {
							if (doc != null) {
								break;
							}
						}
					}
				} else {
					logger.info(url + "         ", e);
					return null;
				}

			} catch (Exception e) {
				logger.info(url + "         ", e);
			}
		}
		return doc;
	}

	private void fillConnection(String url, Connection conn) {
		conn.header("User-Agent",
				"Mozilla/5.0 (Windows NT 5.1; rv:29.0) Gecko/20100101 Firefox/29.0");
		conn.header("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		conn.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		conn.header("Accept-Encoding", "gzip, deflate");
		conn.header("Connection", "keep-alive");
		conn.header("Referer", url);
		conn.header("Cache-Control", "max-age=0");
	}

	public static void main(String[] args) {
		System.out
				.println(new JsoupNetFetcher()
						.fetchText("http://club.kdnet.net/dispbbs.asp?boardid=1&id=10583050"));
	}

}
