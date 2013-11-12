package com.crawler.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class TestMySQLConnection {
	public static final Logger log = Logger.getLogger(TestMySQLConnection.class);
	private static Integer counter = 0;

	public static void main(String[] args) {
//		testOne();
		ArticleDao artOp = new ArticleDao();
		String title = "开天辟地第一帖";
		String result = "不存在";
		if(artOp.checkCrwaledByTitle(title)){
			result = "存在";
		}
		System.out.println(result);
	}

	public static void testOne() {
		Connection conn = null;
		try {
			conn = DBUtils.getConnection();
			conn.prepareStatement("select * from article");
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void testOnes() {
		for (int i = 1; i <= 5; i++) {
			new Thread(new Runnable() {
				public void run() {
					Connection conn = null;
					try {
						conn = DBUtils.getConnection();
						synchronized (counter) {
							System.out.print(Thread.currentThread().getName());
							System.out.print("    counter = " + counter++
									+ "  conn = " + conn);
							System.out.println();
							conn.prepareStatement("select * from article");
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
