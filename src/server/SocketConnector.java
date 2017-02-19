package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dao.UserAccess;
import domain.Response;
import domain.User;
import util.FileHandler;

public class SocketConnector implements Runnable {

	private Server server;

	public SocketConnector(Server server) {
		super();
		this.server = server;
		System.out.println("服务器启动");
	}

	ServerSocket serverSocket;

	public static final int PORT = 54321;// 监听的端口号

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				// 监听client终端请求
				Socket client = serverSocket.accept();
				// 新建线程为链接服务
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
			try {
				serverSocket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class HandlerThread implements Runnable {
		private Socket socket;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {		
		
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

				// 读取客户端数据
				User user = (User) in.readObject();
				System.out.println("读取客户端数据: " + user);
				
				Response response = new Response();
				switch (user.getState()){
				case "register":
					User targetUser1 = UserAccess.findUser(user);
					if (targetUser1 != null) {
						response.setMessage("帐号已存在");
						break;
						}
					UserAccess.addUser(user);
					server.setLogin(user, true);
					response.setMessage("注册成功");
					break;
				case "login":					
						User targetUser = UserAccess.findUser(user);
						if (targetUser == null) {
							response.setMessage("帐号不存在");
							}
						else if (!(user.getPassword().equals(targetUser.getPassword()))) {
							response.setMessage("密码错误");
							}
						else response.setMessage("登录成功");
						server.setLogin(user, true);
						break;
					
				case "quit":
					server.setLogin(user, false);
					response.setMessage("已退出");
					break;
				case "download":
					if(!server.isLogin(user)){
						response.setMessage("未登录");
						break;
					}
					try{
						FileHandler.upload("./ServerFiles/"+user.getFilename(), response);
						response.setMessage("下载完成");

					}catch (FileNotFoundException e) {
						response.setMessage("文件不存在");
					}
					break;
				case "upload":
					if(!server.isLogin(user)){
						response.setMessage("未登录");
						break;
					}
					FileHandler.download("./ServerFiles/"+user.getFilename(), user);
					response.setMessage("上传成功");
				}
				
				
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(response);
				
				in.close();
				out.close();
				
			} catch (Exception e) {
				System.out.println("服务器 run 异常: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("服务端 finally 异常:" + e.getMessage());
					}
				}
			}
		}
	}

}
