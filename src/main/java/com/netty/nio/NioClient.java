package com.netty.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {

	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		Selector selector = Selector.open();
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));

		while (true) {
			selector.select();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey selectionKey = iterator.next();
				if (selectionKey.isConnectable()) {
					SocketChannel client = (SocketChannel) selectionKey.channel();
					if (client.isConnectionPending()) {
						client.finishConnect();
						ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
						writeBuffer.put((LocalDateTime.now() + "连接成功").getBytes());
						writeBuffer.flip();
						client.write(writeBuffer);
						ExecutorService executorService = Executors.newSingleThreadExecutor();
						executorService.submit(() -> {
							while (true) {
								writeBuffer.clear();
								InputStreamReader input = new InputStreamReader(System.in);
								BufferedReader br = new BufferedReader(input);
								String sendMsg = br.readLine();
								writeBuffer.put(sendMsg.getBytes());
								writeBuffer.flip();
								client.write(writeBuffer);
							}
						});
					}
					client.register(selector, SelectionKey.OP_READ);
				} else if (selectionKey.isReadable()) {
					SocketChannel client = (SocketChannel) selectionKey.channel();
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					int read = client.read(readBuffer);
					if (read > 0) {
						String msg = new String(readBuffer.array(), 0, read);
						System.out.println(msg);
					}
				}
			}
			selectedKeys.clear();

		}
	}

}
