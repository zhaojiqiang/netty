package com.netty.nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class NioServer {

	private static Map<String, SocketChannel> clientMap = new HashMap<>();

	public static void main(String[] args) throws Exception {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		// false = 非阻塞
		serverSocketChannel.configureBlocking(false);
		ServerSocket serverSocket = serverSocketChannel.socket();
		serverSocket.bind(new InetSocketAddress("127.0.0.1",8899));
		Selector selector = Selector.open();
		// 将serverSocketChannel 注册到 selector 选择器 关注的人连接事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			// 一直阻塞在这，知道他关心的SelectionKey 事件的发生
			selector.select();
			// 之前注册所有的时间集合
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				final SocketChannel client;

				if (selectionKey.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
					// 表示连接真正的被接受了
					client = server.accept();
					client.configureBlocking(false);
					// 关注的是read事件 表示客户端和服务器端已经建立好连接了 因为 serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

					client.register(selector, SelectionKey.OP_READ);
					String key = "【" + UUID.randomUUID().toString() + "】";
					clientMap.put(key, client);
					iterator.remove();
				} else if (selectionKey.isReadable()) {
					// 1.获取到与之关联的channel对象
					client = (SocketChannel) selectionKey.channel();
					// 2.读取数据
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					int read = client.read(readBuffer);
					if (read > 0) {
						// 写
						readBuffer.flip();
						Charset charset = Charset.forName("UTF-8");
						String recMessage = String.valueOf(charset.decode(readBuffer).array());
						System.out.println(client + ":" + recMessage);

						String sendKey = null;
						for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
							if (client == entry.getValue()) {
								sendKey = entry.getKey();
								break;
							}
						}
						for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
							SocketChannel socketChannel = entry.getValue();
							ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
							writeBuffer.put((sendKey + ":" + recMessage).getBytes());
							writeBuffer.flip();
							socketChannel.write(writeBuffer);
						}
					}
					iterator.remove();
				}
			}
		}

	}

}
