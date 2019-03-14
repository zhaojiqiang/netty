package com.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import com.netty.protobuf.DataInfo.Student;

public class TestProtoBuf {

	public static void main(String[] args) throws InvalidProtocolBufferException {

//		DataInfo.Student student = DataInfo.Student.newBuilder().setName("张三").setAge(20).setAddress("北京").build();
//		byte[] stu2BytaArray = student.toByteArray();
//		Student student2 = DataInfo.Student.parseFrom(stu2BytaArray);
//		System.out.println(student2.getAddress());
//		System.out.println(student2.getAge());
//		System.out.println(student2.getName());
		
		
		System.out.println("只是不是我");
		
		try {
			System.out.println(0x7fffffff);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
