package com.crawler.dao;


import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

public class ConnectionPool {
	public static final Logger log = Logger.getLogger(ConnectionPool.class);
    private Vector<Connection> pool;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    /**
     * ���ӳصĴ�С��Ҳ�������ӳ����ж��ٸ����ݿ����ӡ�
     */
    private int poolSize = 8;
    private static ConnectionPool instance = null;
    /**
     * ˽�еĹ��췽������ֹ�ⲿ��������Ķ���Ҫ���ñ���Ķ���ͨ��<code>getIstance</code>������
     * ʹ�������ģʽ�еĵ���ģʽ��
     */
    private ConnectionPool() {
        init();
    }
    /**
     * ���ӳس�ʼ����������ȡ�����ļ������� �������ӳ��еĳ�ʼ����
     */
    private void init() {
        pool = new Vector<Connection>(poolSize);
        readConfig();
        addConnection();
    }
    /**
     * �������ӵ����ӳ���
     */
    public synchronized void release(Connection conn) {
        pool.add(conn);
    }
    /**
     * �ر����ӳ��е��������ݿ�����
     */
    public synchronized void closePool() {
        for (int i = 0; i < pool.size(); i++) {
            try {
                ((Connection) pool.get(i)).close();
            } catch (SQLException e) {
               
            }
            pool.remove(i);
        }
    }
    /**
     * ���ص�ǰ���ӳص�һ������
     */

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
    /**
     * �������ӳ��е�һ�����ݿ�����
     */
    public synchronized Connection getConnection() { 
        if (pool.size() > 0) {
            Connection conn = pool.get(0);
            pool.remove(conn);
            return conn;
        } else {
            return null;
        }
    }

    /**
     * �����ӳ��д�����ʼ���õĵ����ݿ�����
     */
    private void addConnection() {
        Connection conn = null;
        for (int i = 0; i < poolSize; i++) {
            try {
                Class.forName(driverClassName);
                conn = java.sql.DriverManager.getConnection(url, username, password);
                pool.add(conn);
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage());
            } catch (SQLException e) {
            	log.error(e.getMessage());
            }
        }
    }
    /**
     * ��ȡ�������ӳص������ļ�
     */
    private void readConfig() {
        try {
            String path = System.getProperty("user.dir") + "\\ds.properties";
            FileInputStream is = new FileInputStream(path);
            Properties props = new Properties();
            props.load(is);
            this.driverClassName = props.getProperty("driverClassName");
            this.username = props.getProperty("username");
            this.password = props.getProperty("password");
            this.url = props.getProperty("url");
            this.poolSize = Integer.parseInt(props.getProperty("poolSize"));

        } catch (Exception e) {
        	log.error("��ȡ�����ļ�����");
        	log.error(e.getMessage());      
        }
    }
}
