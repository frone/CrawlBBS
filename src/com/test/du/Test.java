package com.test.du;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 有以下几种日期输入格式：
 * 发表于 前天 09:24
 * 发表于 昨天 15:54
 * 发表于 4 天前 
 * 发表于 3 小时前
 * 发表于 5 分钟前
 * 发表于 2013-10-12 15:39:50 
 * 
 * 将上述时间转化为标准日期格式  Date类型,例如 2013-11-01 12:38:00
 */

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Test t = new Test();
		t.formatTimeText("发表于 2013-10-12 16:14:12");
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
			System.out.println(longFormat.format(cal.getTime()));
		} else if (timeText.contains("昨天")) {
			dateDif = 1;
			String time = timeText.substring(7);
			String[] detail = time.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(detail[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(detail[1]));
			cal.add(Calendar.DAY_OF_YEAR, -2);
			System.out.println(longFormat.format(cal.getTime()));
		} else if (timeText.contains("天前")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
					System.out.println("come here:" + dateDif);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1 * dateDif);
			date = cal.getTime();
			System.out.println(longFormat.format(date));
		} else if (timeText.contains("小时")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
					System.out.println("come here:" + dateDif);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, -1 * dateDif);
			date = cal.getTime();
			System.out.println(longFormat.format(date));
		} else if (timeText.contains("分钟")) {
			String regex = "\\d*";
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(timeText);
			while (m.find()) {
				if (!"".equals(m.group())) {
					dateDif = Integer.parseInt(m.group());
					System.out.println("come here:" + dateDif);
				}
			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, -1 * dateDif);
			date = cal.getTime();
			System.out.println(longFormat.format(date));
		} else {
			String tmpDate = timeText.substring(4);
			try {
				date = longFormat.parse(tmpDate);
			} catch (ParseException e) {
				System.out.println("日期转换错误：" + e.getMessage());
			}
			System.out.println(longFormat.format(date));
		}
		return date;
	}
}
