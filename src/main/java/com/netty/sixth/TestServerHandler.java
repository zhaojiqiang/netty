package com.netty.sixth;

import com.netty.sixth.MyDataInfo.Cat;
import com.netty.sixth.MyDataInfo.Dog;
import com.netty.sixth.MyDataInfo.MyMessage;
import com.netty.sixth.MyDataInfo.MyMessage.DataType;
import com.netty.sixth.MyDataInfo.Person;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, MyMessage msg) throws Exception {
		DataType dataType = msg.getDataType();
		if (dataType == MyDataInfo.MyMessage.DataType.PersonType) {
			Person person = msg.getPerson();
			System.out.println(person.getAddress());
			System.out.println(person.getAge());
			System.out.println(person.getName());
		} else if (dataType == MyDataInfo.MyMessage.DataType.DogType) {
			Dog dog = msg.getDog();
			System.out.println(dog.getAge());
			System.out.println(dog.getName());
		} else if (dataType == MyDataInfo.MyMessage.DataType.CatType) {
			Cat cat = msg.getCat();
			System.out.println(cat.getCity());
			System.out.println(cat.getName());
		}
	}

}
