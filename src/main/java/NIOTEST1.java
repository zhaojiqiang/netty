import java.io.FileInputStream;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class NIOTEST1 {

	public static void main(String[] args) throws Exception {

		
		FileInputStream fileInputStream = new FileInputStream("");
		FileChannel channel = fileInputStream.getChannel();
		IntBuffer allocate = IntBuffer.allocate(88);
		int[] array = allocate.array();
		
		
	}

}
