package dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import domain.User;

//����property�ļ�
public class UserAccess {
	public static void addUser(User user) {
		Properties properties = new Properties();
		//д���µ��û���Ϣ
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream("./users.property"), "UTF-8");
			properties.load(in);
			properties.setProperty(user.getName(), user.getPassword());
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("./users.property"), "UTF-8");
			properties.store(out, null);
			out.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static User findUser(User user) {
		Properties properties = new Properties();
		//��ȡ�û���Ϣ������Ҳ����򷵻�null
		try {
			InputStreamReader in = new InputStreamReader(new FileInputStream("./users.property"), "UTF-8");
			properties.load(in);
			if(!properties.containsKey(user.getName())){
				return null;
			}
			User user2 = new User(null, user.getName(), properties.getProperty(user.getName()));
			return user2;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}
