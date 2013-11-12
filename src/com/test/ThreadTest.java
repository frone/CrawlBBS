package com.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import org.apache.log4j.Logger;

import com.crawler.dao.ArticleDao;

public class ThreadTest {
	public static final Logger log = Logger.getLogger(ThreadTest.class);
	public static void main(String[] args) {
//			(1)固定大小的线程池
//			创建一个可重用固定线程数的线程池
		ExecutorService pool = Executors.newFixedThreadPool(3);
//			(2)单任务线程池
//			创建一个使用单个 worker 线程的 Executor，以无界队列方式来运行该线程。 	
//        ExecutorService pool = Executors.newSingleThreadExecutor(); 
//			(3)可变尺寸的线程池
//			创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。 
//        ExecutorService pool = Executors.newCachedThreadPool(); 
//			(4)延迟连接池 
//			它可安排在给定延迟后运行命令或者定期地执行
//		  ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
		
		// 创建实现了Runnable接口对象，Thread对象当然也实现了Runnable接口
		Runnable t1 = new Auv("1");
		Runnable t2 = new Auv("2");
		Runnable t3 = new Auv("3");
		Runnable t4 = new Auv("4");
		Runnable t5 = new Auv("5");
		// 将线程放入池中进行执行
		pool.execute(t1);
		pool.execute(t2);
		pool.execute(t3);
		pool.execute(t4);
		pool.execute(t5);
		// 关闭线程池
		pool.shutdown();
	}
}

class MyThread extends Thread {
	public static final Logger log = Logger.getLogger(MyThread.class);
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + "正在执行。。。");
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
		log.error(Thread.currentThread().getName() +"\t"+ title +" 。。。");
		log.info("done");
	}
	
}
