package com.netty.fifth;

import java.net.InetSocketAddress;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class MyServer {
	public static void main(String[] args) throws InterruptedException {
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//
//		try {
//			ServerBootstrap serverBootstrap = new ServerBootstrap();
//			serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
//					.handler(new LoggingHandler(LogLevel.INFO)).childHandler
//					(new WebSockerChannelInitialzer());
//			ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(8899)).sync();
//			channelFuture.channel().closeFuture().sync();
//		} finally {
//			bossGroup.shutdownGracefully();
//			workerGroup.shutdownGracefully();
//		}
		int [] arr = new int[1024111];
		System.gc();
	}
}
