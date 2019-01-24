package com.netty.sixth;

import java.util.Random;

import com.netty.sixth.MyDataInfo.Person;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Person msg) throws Exception {

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		int n = new Random().nextInt(3);
		MyDataInfo.MyMessage message = null;
		if (0 == n) {
			message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.PersonType)
					.setPerson(MyDataInfo.Person.newBuilder().setName("你强哥").setAddress("朝阳区").setAge(23).build())
					.build();
		} else if (1 == n) {
			message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.DogType)
					.setDog(MyDataInfo.Dog.newBuilder().setName("哈哈哈").setAge(23).build()).build();
		} else if (2 == n) {
			message = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.CatType)
					.setCat(MyDataInfo.Cat.newBuilder().setName("Tom").setCity("北京").build()).build();
		}
		ctx.writeAndFlush(message);
	}
}
