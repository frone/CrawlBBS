package com.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.crawler.dao.ArticleDao;

public class ThreadTest {
	public static final Logger log = Logger.getLogger(ThreadTest.class);
	public static void main(String[] args) {
//			(1)�̶���С���̳߳�
//			����һ�������ù̶��߳������̳߳�
		ExecutorService pool = Executors.newFixedThreadPool(3);
//			(2)�������̳߳�
//			����һ��ʹ�õ��� worker �̵߳� Executor�����޽���з�ʽ�����и��̡߳� 	
//        ExecutorService pool = Executors.newSingleThreadExecutor(); 
//			(3)�ɱ�ߴ���̳߳�
//			����һ���ɸ�����Ҫ�������̵߳��̳߳أ���������ǰ������߳̿���ʱ���������ǡ� 
//        ExecutorService pool = Executors.newCachedThreadPool(); 
//			(4)�ӳ����ӳ� 
//			���ɰ����ڸ����ӳٺ�����������߶��ڵ�ִ��
//		  ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		
		// ����ʵ����Runnable�ӿڶ���Thread����ȻҲʵ����Runnable�ӿ�
		Runnable t1 = new Auv("1");
		Runnable t2 = new Auv("2");
		Runnable t3 = new Auv("3");
		Runnable t4 = new Auv("4");
		Runnable t5 = new Auv("5");
		// ���̷߳�����н���ִ��
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// �ر��̳߳�
		pool.shutdown();
	}
}

class MyThread extends Thread {
	public static final Logger log = Logger.getLogger(MyThread.class);
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "����ִ�С�����");
	}
}
class Auv implements Runnable{
	public static final Logger log = Logger.getLogger(Auv.class);
	private String title;
	public Auv(String title){
		this.title = title;
	}
	@Override
	public void run() {
		log.error(Thread.currentThread().getName() +"\t"+ title +" ������");
		log.info("done");
	}
	
}
