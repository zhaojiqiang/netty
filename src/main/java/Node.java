import java.util.concurrent.Callable;

public class Node {
	
	/**
	 * http:无状态的 请求响应的 
	 * 
	 * 一定是浏览器先发起的请求
	 * 
	 * 1.0 服务器返回 立刻断掉
	 * 1.1新增keepalive 持续连接， 在指定的时间内， 如果客户端在发起请求，不会重复建立连接
	 * 
	 * 
	 * 假的长连接：轮训检测
	 * 	问题1：不及时
	 * 	问题2：太多的无效请求 浪费资源， h
	 *	
	 *	head body   每次都要带头信息，占据内容大。
	 *
	 *	websocket 长连接，对等，互相发送数据  双向数据传递
	 *			只需发送双方数据本身，节省网络带宽
	 *			升级版http连接
	 *			
	 * 	Future所有的事情都是开发人员自己处理，而netty使用观察者模式 来回调
	 * 
	 * 
	 */

	
	public static void main(String[] args) {
		MPA mpb = new MPA();
		Thread a = new Thread(mpb);
		a.start();
	}
	
	static class MPA implements Runnable{

		@Override
		public void run() {
			
		}}
	static class MPB implements Callable<Integer>{

		@Override
		public Integer call() throws Exception {
			return 1;
		}
		
	}
}
