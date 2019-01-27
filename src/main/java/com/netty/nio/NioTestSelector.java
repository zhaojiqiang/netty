package com.netty.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioTestSelector {
	/**
	 * 
	 * 多路传输 可选择的channel对象
	 * 
	 * 可以通过调用Selector.open() 创建实例
	 * 
	 * open 通过系统底层的选择器提供者创建出来的
	 * 
	 * SelectionKey channel----》Selector 通过SelectionKey建立联系，SelectionKey标示了一些事件
	 * 
	 * keySet channel----》Selector 所有的key
	 * 
	 * keySet 全局的 SelectionKeySet（关注的）是keySet子集 cancelledkeySet（关注完放弃的） 也是是keySet子集
	 * 
	 * channel.refister这个方法添加selectionKey
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

	public static void main(String[] args) throws Exception {

		int[] ports = { 5000, 5001, 5002, 5003, 5004 };

		Selector selector = Selector.open();

		for (int i = 0; i < ports.length; i++) {
			java.nio.channels.ServerSocketChannel open = java.nio.channels.ServerSocketChannel.open();
			open.configureBlocking(false);
			ServerSocket socket = open.socket();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(ports[i]);
			socket.bind(inetSocketAddress);
			open.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("监听端口： " + ports[i]);
		}

		while (true) {
			int select = selector.select();
			System.out.println("selector.select : " + select);
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			System.out.println("selectedKeys:" + selectedKeys);

			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if (selectionKey.isAcceptable()) {
					ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
					SocketChannel socketChannel = channel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ);
					iterator.remove();
					System.out.println("获得客户端连接：" + channel);
				} else if (selectionKey.isReadable()) {
					SocketChannel channel = (SocketChannel) selectionKey.channel();
					int byteread = 0;
					while (true) {
						ByteBuffer buffer = ByteBuffer.allocate(512);
						buffer.clear();
						int read = channel.read(buffer);
						if (read <= 0) {
							break;
						}
						buffer.flip();
						channel.write(buffer);
						byteread += read;
					}
					System.out.println("读取：" + byteread + ",来自于：" + channel);
					iterator.remove();

				}

			}
		}

	}
}
