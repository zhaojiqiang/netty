import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

/**
 * 
 * io缓冲区操作流程 用户空间（jvm） 请求 内核空间（操作系统） 磁盘控制器 磁盘
 * 
 * 内核空间有特殊权限直接通过磁盘控制器 读取磁盘数据（可以预读取） 读到内核缓冲区
 * 
 * 用户空间直接通过内核空间拷贝进来进行操作。如果内核空间没有数据，进程被挂起，内核把数据读进内存
 * 
 * 
 * 
 * Q：为什么不让磁盘控制器直接把数据送到用户空间的内存缓冲区呢》 首先硬件设备不能直接访问用户空间，其次，因为像磁盘这种基于跨存储的硬件设备操作是固定大小的数据块 而用户空间请求的可能是任意大小的非对齐的数据块，
 * 内核负责分解、在组合的工作，充当中间人的角色
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 缓冲区Buffer 一个buffer对象是固定数量的数据容器，。其作用是一个储存器，或者分段运输区，在这里的数据可以被储存并在之后进行检索，对于每个费布尔类型的数据类型都有一个缓冲区类，
 * 尽管缓冲区作用于他们储存的原始数据类型。但缓冲区十分倾向于处理字节，费字节缓冲区可以再从字节或者到字节的转换，
 * 
 * 
 * 
 */

public class BufferFillDrain {
	private static int		index	= 0;
	private static String[]	strings	= { "A random string value", "The product of an infinite number of monkeys",
			"Hey hey we're the Monkees", "Opening act for the Monkees: Jimi Hendrix",
			"'Scuse me while I kiss this fly" };																// Sorry
																												// Jimi
																												// ;-)
																												// "Help
																												// Me!
																												// Help
																												// Me!",
																												// }; }

	public static void main(String[] argv) throws Exception {
		CharBuffer buffer = CharBuffer.allocate(100);
		while (fillBuffer(buffer)) {
			buffer.flip();
			drainBuffer(buffer);
			buffer.clear();
		}
	}
	private static void drainBuffer(CharBuffer buffer) {
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println("");
	}
	private static boolean fillBuffer(CharBuffer buffer) {
		if (index >= strings.length) {
			return (false);
		}
		String string = strings[index++];
		for (int i = 0; i < string.length(); i++) {
			buffer.put(string.charAt(i));
		}
		return (true);
	}
}
