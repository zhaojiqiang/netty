package com.netty;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class Snippet {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			Thread thread = new Thread(new YO());
			thread.start();
		}
		
	}
	
	private static class YO implements Runnable {
		
		public void run() {
			try {
				Looo.lock();
				//完成业务流程, 释放锁
				mutex.release();
//				//关闭客户端
				client.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	static RetryPolicy retryPolicy = null;
	static CuratorFramework client = null;
	static InterProcessMutex mutex = null;
	static {
		retryPolicy = new ExponentialBackoffRetry(1000, 3);
		
		client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
		
		client.start();
		
		//创建分布式锁, 锁空间的根节点路径为/curator/lock
		
		mutex = new InterProcessMutex(client, "/curator/lock");
	}
	
	
	
	private static class Looo{
		public static void lock() throws Exception{
			
			//创建zookeeper的客户端
			
			
			
			mutex.acquire();
			
			//获得了锁, 进行业务流程
			
			System.out.println("Enter mutex");
			
			
		}
	}
}

