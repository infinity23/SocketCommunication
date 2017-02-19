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
		//���ļ���װ��������int��Χ��֪��������ļ�����Ϊ1G��ʵ���������������ҪС�ܶ�
		byte[] buff = new byte[(int) new File(path).length()];
		in.read(buff);
		fc.setFileContent(buff);
		in.close();
	}

}
