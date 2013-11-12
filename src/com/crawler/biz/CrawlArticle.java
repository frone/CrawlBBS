package com.crawler.biz;

import org.apache.log4j.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.crawler.entity.*;
import com.crawler.dao.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Example program to list links from a URL.
 */
public class CrawlArticle implements Runnable {
	public static final Logger log = Logger.getLogger(CrawlArticle.class);
	final static String BASEURL = "http://bbs.51credit.com/";
	// page offset considering the articles placed top
	final static int PAGECOUNTFIX = 9;
	int currentPage = 1;
	int totalPages = 1;
	private int[] targetArray;
	private Article art;
	private ArticleDao ad;

	public Article getArt() {
		return art;
	}

	public void setArt(Article art) {
		this.art = art;
	}

	public CrawlArticle() {
		ad = new ArticleDao();
	}

	public CrawlArticle(int[] array) {
		this.targetArray = array;
		ad = new ArticleDao();
	}

	public CrawlArticle(Article art) {
		this.art = art;
		ad = new ArticleDao();
	}

	public static void main(String[] args) throws IOException {
		CrawlArticle ca = new CrawlArticle();
		ca.goCrawlingByThreadPool();
		// ca.goCrawling();
	}

	/**
	 * using thread pool to crawl BBS
	 */
	public void goCrawlingByThreadPool() {
		ExecutorService pool = Executors.newFixedThreadPool(5);
//		int[] forums1 = { 130, 151, 3, 4 };
//		int[] forums2 = { 6, 7, 8, 9, 11, 231 };
//		int[] forums3 = { 152, 153, 169, 5 };
		int[] forums1 = {3};
		int[] forums2 = {151};
		int[] forums3 = {4};
		int[] forums4 = { 178, 180, 181, 231 };
		int[] forums5 = { 216, 218, 219, 226, 234, 232 };

		// int[] forums1 = {218};
		// int[] forums2 = {219};
		Runnable test1 = new CrawlArticle(forums1);
		Runnable test2 = new CrawlArticle(forums2);
		Runnable test3 = new CrawlArticle(forums3);
		Runnable test4 = new CrawlArticle(forums4);
		Runnable test5 = new CrawlArticle(forums5);
		pool.execute(test1);
		pool.execute(test2);
		pool.execute(test3);
		pool.execute(test4);
		pool.execute(test5);
		pool.shutdown();
	}

	// �����̳߳�ǰ�ĳ������
	public void goCrawling() {
		// 130 ���ÿ��ٿ�,137 ��ū�뿨��,152 ����ָ��,151 ���ü�¼,153 �ÿ��ĵ�,169 ����թ�뷴Υ��
		// 178 �������˵�,180����֧���뻹�� ,181 �Ż��̻�,231 ���ÿ�ר��
		// 232 �Ŵ�ר�� ,3 ���п�ר�� ,4 ũ�п�ר��,5 ���п�ר�� ,6 ���п�ר��,7 ���п�ר�� ,8 ���п�ר��,9 ���ſ�ר��
		// 11 �㷢��ר��, 216 ���ô��� , 218 ��������, 219 ˽����ҵ������, 226 ���ɷ���, 234 ���
		// 205,203,212,16,96,133
		// int[] forums = { 130, 137, 151, 152, 153, 169, 178, 180, 181, 231,
		// 232,
		// 3, 4, 5, 6, 7, 8, 9, 11, 216, 218, 219, 226, 234 };
		int[] forums = { 226, 234 };
		for (int i = 0; i < forums.length; i++) {
			String fullURL = BASEURL + "forum-" + forums[i] + "-1.html";
			log.info("Fetching Forum " + fullURL + "...");
			crwalForums(fullURL);
		}
	}

	/**
	 * ʹ��jsoup�����ĵ����� ��ȡĿ���������ڵ�Ŀ��� ���Ŀ��������div��table��tr�ȵ�
	 */
	public String getDivContentByJsoup(String content) {
		String divContent = "";
		Document doc = Jsoup.parse(content);
		// Elements divs = doc.getElementsByClass("xst");
		Elements contents = doc.select(".xst");
		divContent = contents.toString();
		return divContent;
	}

	/**
	 * ����jsoup������ȡhtmlContent ����򵥵�ʱ���¼
	 * 
	 * @throws IOException
	 */
	public void crwalForums(String url) {
		String content = "";
		String divContent = "";
		try {
			Document doc = Jsoup
					.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
					.ignoreContentType(true).timeout(50000).post();
			content = doc.toString();// ��ȡ��վ��Դ��html����
			// Elements divs = doc.getElementsByClass("xst");
			doc = Jsoup.parse(content);
			Elements contents = doc.select(".xst");
			// get last page number
			if (currentPage == 1) {
				// Element count = doc.select("a[class=last]").first();
				// lastForumPageNum = Integer.parseInt(count.text().substring(3)
				// .trim());

				Elements totalTopics = doc.select("strong[class=xi1]");
				int topicCounts = Integer.parseInt(totalTopics.get(1).text());
				log.info("Topic Counts: " + topicCounts);
				totalPages = (int) Math.floor(PAGECOUNTFIX + topicCounts) / 50 + 1;
				log.info("Forum �ܹ�" + totalPages + "ҳ");
			}
			// form the link of each page
			if (currentPage <= totalPages) {
				String[] url_char = url.split("-");
				divContent = contents.toString();
				crawAllLinks(divContent);

				// û��ҳ��������һҳʱ
				if (currentPage < totalPages) {
					currentPage++;
					String newURL = url_char[0] + "-" + url_char[1] + "-"
							+ currentPage + ".html";
					crwalForums(newURL);
				} else {
					currentPage = 1;
					log.info("CURRENT FORUM ENDS");
				}
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ץȡ��ǰ�����������������
	 */
	public void crawAllLinks(String divContent) {
		Document doc = Jsoup.parse(divContent, BASEURL);
		Elements linkStrs = doc.select("a[href]");
		log.info("=====��" + currentPage + "ҳ���� [" + linkStrs.size()
				+ "] ������=====");
		if (linkStrs.size() != 0) {
			for (Element linkStr : linkStrs) {
				String url = linkStr.attr("abs:href");
				String title = linkStr.text();

				if (!ad.checkCrwaledByURL(url)) {
					Article art = new Article();
					art.setTitle(title);
					art.setUrl(url);
					this.setArt(art);
					log.info("URL: " + art.getUrl());
					try {
						CrawlDetail cd = new CrawlDetail();
						cd.crawlInfo(art);
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				} else {
					log.info(title + "  [��ץȡ]");
				}

			}
		}
	}

	@Override
	public void run() {
		for (int i = 0; i < targetArray.length; i++) {
			String fullURL = BASEURL + "forum-" + targetArray[i] + "-1.html";
			log.info("Fetching Forum " + fullURL + "...");
			crwalForums(fullURL);
		}
	}
}