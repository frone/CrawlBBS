package com.crawler.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.crawler.biz.CrawlDetail;
import com.mchange.v2.c3p0.DataSources;

public class DBUtils {
	public static final Logger log = Logger.getLogger(DBUtils.class);
	private static String url = null;
	private static String username = null;
	private static String pwd = null;
	private static String poolSize = null;
	private static DataSource ds_pooled;
	/**
	 * �������ݿ����ӵ������ļ�������
	 */
	static {
		FileInputStream fis = null;
		Properties env = new Properties();
		String basedir = System.getProperty("user.dir");
		try {
			fis = new FileInputStream(basedir+"\\src\\ds.properties");
			// ���������ļ��е����ݿ�������Ϣ
			// ��=�����Ϊkeyֵ���ұ���Ϊvalueֵ
			env.load(fis);

			// 1. ����������
			Class.forName(env.getProperty("driverClassName"));

			url = env.getProperty("url");
			username = env.getProperty("username");
			pwd = env.getProperty("password");
			poolSize = env.getProperty("poolSize");
			// �����������ݿ��������Ϣ
			DataSource ds_unpooled = DataSources.unpooledDataSource(url,
					username, pwd);

			Map<String, Object> pool_conf = new HashMap<String, Object>();
			// �������������
			pool_conf.put("maxPoolSize", poolSize);
			ds_pooled = DataSources.pooledDataSource(ds_unpooled, pool_conf);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ��ȡ���Ӷ���
	 */
	public static Connection getConnection() throws SQLException {
		// 2. �������ӵ�url,username,pwd
		// return DriverManager.getConnection(url, username, pwd);
		return ds_pooled.getConnection();
	}

	/**
	 * �ͷ����ӳ���Դ
	 */
	public static void clearup() {
		if (ds_pooled != null) {
			try {
				DataSources.destroy(ds_pooled);
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
	}

	/**
	 * ��Դ�ر�
	 * 
	 * @param rs
	 * @param ps
	 * @param conn
	 */
	public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
	}
}
