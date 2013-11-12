package com.crawler.biz;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.crawler.dao.ArticleDao;
import com.crawler.dao.ReplyDao;

import com.crawler.entity.*;

public class CrawlDetail {
	static String url = "http://bbs.51credit.com/thread-1348796-1-12.html";
	static String title = "[求助] 麻烦高手帮我分析下我的个人信用报告好吗？";
	public static final Logger log = Logger.getLogger(CrawlDetail.class);
	int currentPage = 1;
	int totalPages = 1;
	int lastPageReplies;
	boolean isLastPage = false;

	private ArticleDao art_op;
	private ReplyDao rep_op;

	public CrawlDetail() {
		this.art_op = new ArticleDao();
		this.rep_op = new ReplyDao();
	}

	public static void main(String[] args) throws IOException {
		Article art = new Article();
		art.setUrl(url);
		art.setTitle(title);
		CrawlDetail cd = new CrawlDetail();
		log.info("fetching... " + url);
		cd.crawlInfo(art);
	}

	public void crawlInfo(Article art) throws IOException {
		Document doc = Jsoup
				.connect(art.getUrl())
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; rv:22.0) Gecko/20100101 Firefox/22.0")
				.ignoreContentType(true).timeout(50000).post();
		// test privilege
		// 当没有权限访问该版块时，不继续执行抓取
		Elements pri = doc.select("#messagetext");
		String msg = pri.text().trim();
		if (!(msg.contains("没有权限") || msg.contains("权限高于") || msg
				.contains("没有找到帖子"))) {
			// 当帖子为第一页时
			// 查看数与回复数
			if (currentPage == 1) {
				Elements replies = doc.select(".xi1");
				int rSize = replies.size();
				String[] counts = new String[rSize];
				int i = 0;
				for (Element reply : replies) {
					counts[i] = reply.text();
					i++;
				}
				art.setViewCounts(Integer.parseInt(counts[0]));
				art.setReplyCounts(Integer.parseInt(counts[1]));
				totalPages = (int) Math.floor(art.getReplyCounts() / 20 + 1);
				lastPageReplies = art.getReplyCounts() - (totalPages - 1) * 20
						+ 1;
				log.info("总共" + totalPages + "页");
				log.info("最后一页有" + lastPageReplies + "条回复");
			}
			if ((currentPage) == totalPages) {
				this.isLastPage = true;
			}
			// 发/回帖人姓名
			Elements authors = doc.select("a[href][class=xw1]");
			String[] authorName = null;
//			canceled to make sure no  ArrayIndexOutOfBoundsException 
//			if (isLastPage) {
//				authorName = new String[lastPageReplies];
//			} else {
//				authorName = new String[20];
//			}
//			
			authorName = new String[20];
			int j = 0;

			for (Element author : authors) {
				if (!author.text().endsWith(".jpg")) {
					authorName[j] = author.text();
//					log.error(author.text());
					j++;
				}
			}

			// 内容
			Elements divcontents = doc.select("td[id^=postmessage]");
			String[] contents = null;
//			canceled to make sure no  ArrayIndexOutOfBoundsException 		
//			if (isLastPage) {
//				contents = new String[lastPageReplies];
//			} else {
//				contents = new String[20];
//			}
			contents = new String[20];
			int k = 0;
			for (Element content : divcontents) {
				contents[k] = content.text();
				k++;
			}
			List<Reply> reps = new LinkedList<Reply>();

			// 时间
			Elements divdates = doc.select("em[id^=authorposton]");
			Date[] dates = null;
//			canceled to make sure no  ArrayIndexOutOfBoundsException 
//			if (isLastPage) {
//				dates = new Date[lastPageReplies];
//			} else {
//				dates = new Date[20];
//			}
			dates = new Date[20];
			int l = 0;
			for (Element divdate : divdates) {
				String dateText;
				dateText = divdate.text();
				if(dateText.length()==3){
					if(l==0){
						dates[l] = dates[l+1];
					}else{
						dates[l] = dates[l-1];
					}
				}else{
					dates[l] = formatTimeText(dateText);	
				}
				l++;
			}
//			canceled to make sure no  ArrayIndexOutOfBoundsException 
			// 赋值给实体类并储存到数据库
//			if (currentPage == 1 && !isLastPage) {
//				art.setAuthorName(authorName[0]);
//				art.setContent(contents[0]);
//				art.setPostTime(dates[0]);
//				int artID = art_op.insertArticleReturnID(art);
//				art.setArticleId(artID);
//				for (int c = 0; c < 19; c++) {
//					Reply rep = new Reply();
//					rep.setReplierName(authorName[c + 1]);
//					rep.setContent(contents[c + 1]);
//					rep.setArticleId(art.getArticleId());
//					rep.setPostTime(dates[c + 1]);
//					reps.add(rep);
//				}
//			} else if (currentPage == 1 && isLastPage) {
//				art.setAuthorName(authorName[0]);
//				art.setContent(contents[0]);
//				art.setPostTime(dates[0]);
//				art.setArticleId(art_op.insertArticleReturnID(art));
//				for (int c = 0; c < lastPageReplies - 1; c++) {
//					Reply rep = new Reply();
//					rep.setReplierName(authorName[c + 1]);
//					rep.setContent(contents[c + 1]);
//					rep.setArticleId(art.getArticleId());
//					rep.setPostTime(dates[c + 1]);
//					reps.add(rep);
//				}
//			} else if (currentPage != 1 && isLastPage) {
//				for (int c = 0; c < lastPageReplies; c++) {
//					Reply rep = new Reply();
//					rep.setReplierName(authorName[c]);
//					rep.setContent(contents[c]);
//					rep.setArticleId(art.getArticleId());
//					rep.setPostTime(dates[c]);
//					reps.add(rep);
//				}
//			} else {
//				for (int c = 0; c < 20; c++) {
//					Reply rep = new Reply();
//					rep.setReplierName(authorName[c]);
//					rep.setContent(contents[c]);
//					rep.setArticleId(art.getArticleId());
//					rep.setPostTime(dates[c]);
//					reps.add(rep);
//				}
//			}
			
			if (currentPage == 1) {
				art.setAuthorName(authorName[0]);
				art.setContent(contents[0]);
				art.setPostTime(dates[0]);
				int artID = art_op.insertArticleReturnID(art);
				art.setArticleId(artID);
				for (int c = 0; c < 19; c++) {
					Reply rep = new Reply();
					rep.setReplierName(authorName[c + 1]);
					rep.setContent(contents[c + 1]);
					rep.setArticleId(art.getArticleId());
					rep.setPostTime(dates[c + 1]);
					reps.add(rep);
				}
			}else {
				for (int c = 0; c < 20; c++) {
					Reply rep = new Reply();
					rep.setReplierName(authorName[c]);
					rep.setContent(contents[c]);
					rep.setArticleId(art.getArticleId());
					rep.setPostTime(dates[c]);
					reps.add(rep);
				}
			}
//			log.info("FETCHING REPLIES (" + reps.size() + ") PAGE "
//					+ currentPage + " NOW");
			log.info("FETCHING REPLIES "
					+ currentPage + " NOW");
//			put results into DB
			rep_op.insertReplys(reps);

			currentPage++;
			String[] tt = art.getUrl().split("-");
			if (currentPage <= totalPages) {
				String newURL = tt[0] + "-" + tt[1] + "-" + currentPage + "-"
						+ tt[3];
				log.info("next page is " + newURL);
				art.setUrl(newURL);
				crawlInfo(art);
			} else {
				log.info("this is the end of article!");
			}
		} else {
			log.info(msg);
		}
	}

	/**
	 * Format a string containing date info to date
	 * 
	 * @param timeText
	 *            The String to be formatted
	 */
	public Date formatTimeText(String timeText) {
		int dateDif = 1;
		SimpleDateFormat longFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		timeText = timeText.trim();
		if (timeText.contains("前天")) {
			dateDif = 2;
			String time = timeText.substring(7);
			String[] detail = time.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(detail[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(detail[1]));
			cal.add(Calendar.DAY_OF_YEAR, -2);
		} else if (timeText.contains("昨天")) {
			dateDif = 1;
			String time = timeText.substring(7);
			String[] detail = time.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(detail[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(detail[1]));
			cal.add(Calendar.DAY_OF_YEAR, -2);
		} else if (timeText.contains("天前")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1 * dateDif);
			date = cal.getTime();
		} else if (timeText.contains("小时")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -1 * dateDif);
			date = cal.getTime();
		} else if (timeText.contains("分钟")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -1 * dateDif);
			date = cal.getTime();
		} else if (timeText.contains("秒前")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.SECOND, -1 * dateDif);
			date = cal.getTime();
		} else {
			String tmpDate = timeText.substring(4);
			try {
				date = longFormat.parse(tmpDate);
			} catch (ParseException e) {
				log.error("日期转换错误");
				log.error(e.getMessage());
			}
		}
		return date;
	}

	public String formatText(String text) {
		String formatted = "";
		formatted = text.replace("”", "&quot;").replace("“", "&quot;").replace(
				"（", ");").replace("）", "(");
		return formatted;
	}
}
