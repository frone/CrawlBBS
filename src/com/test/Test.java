package com.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

/*
 * �����¼������������ʽ��
 * ������ ǰ�� 09:24
 * ������ ���� 15:54
 * ������ 4 ��ǰ 
 * ������ 3 Сʱǰ
 * ������ 5 ����ǰ
 * ������ 56 ��ǰ
 * ������ 2013-10-12 15:39:50 
 * 
 * ������ʱ��ת��Ϊ��׼���ڸ�ʽ  Date����,���� 2013-11-01 12:38:00
 */

public class Test {
//ö�ٲ���
	public enum Coin {
		penny(1), nickel(5), dime(10), quarter(25);
		Coin(int value) {
			this.value = value;
		}
		private final int value;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Test t = new Test();
//		t.formatTimeText("������ 10��ǰ");
		System.out.println((int) Math.floor(9 + 52) / 50 + 1);
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
		if (timeText.contains("ǰ��")) {
			dateDif = 2;
			String time = timeText.substring(7);
			String[] detail = time.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(detail[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(detail[1]));
			cal.add(Calendar.DAY_OF_YEAR, -2);
			System.out.println(longFormat.format(cal.getTime()));
		} else if (timeText.contains("����")) {
			dateDif = 1;
			String time = timeText.substring(7);
			String[] detail = time.split(":");
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(detail[0]));
			cal.set(Calendar.MINUTE, Integer.parseInt(detail[1]));
			cal.add(Calendar.DAY_OF_YEAR, -2);
			System.out.println(longFormat.format(cal.getTime()));
		} else if (timeText.contains("��ǰ")) {
//TO DO:
			dateDif = Integer.parseInt(timeText.replaceAll("\\D", ""));
//			String regex = "\\d*";
//			Pattern p = Pattern.compile(regex);
//			Matcher m = p.matcher(timeText);
//			while (m.find()) {
//				if (!"".equals(m.group())) {
//					dateDif = Integer.parseInt(m.group());
//					System.out.println("come here:" + dateDif);
//				}
//			}
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, -1 * dateDif);
			date = cal.getTime();
			System.out.println(longFormat.format(date));
		} else if (timeText.contains("Сʱ")) {
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
		} else if (timeText.contains("����")) {
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
		}else if (timeText.contains("��ǰ")) {
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
			cal.add(Calendar.SECOND, -1 * dateDif);
			date = cal.getTime();
			System.out.println(longFormat.format(date));
		}else {
			String tmpDate = timeText.substring(4);
			try {
				date = longFormat.parse(tmpDate);
			} catch (ParseException e) {
				System.out.println("����ת������" + e.getMessage());
			}
			System.out.println(longFormat.format(date));
		}
		return date;
	}
}
