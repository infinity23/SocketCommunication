package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import domain.FileContainer;

public class FileHandler {
	public static void download(String path, FileContainer fc) throws IOException{
		FileOutputStream out = new FileOutputStream(path);
		byte[] buff = fc.getFileContent();
		out.write(buff);
		out.close();
	}
	
	public static void upload(String path, FileContainer fc) throws IOException{
		FileInputStream in = new FileInputStream(path);
		//将文件包装进对象，由int范围可知理论最大文件长度为1G，实际由于虚拟机限制要小很多
		byte[] buff = new byte[(int) new File(path).length()];
		in.read(buff);
		fc.setFileContent(buff);
		in.close();
	}

}
