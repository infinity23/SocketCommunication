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
		System.out.println("����������");
	}

	ServerSocket serverSocket;

	public static final int PORT = 54321;// �����Ķ˿ں�

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				// ����client�ն�����
				Socket client = serverSocket.accept();
				// �½��߳�Ϊ���ӷ���
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("�������쳣: " + e.getMessage());
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

				// ��ȡ�ͻ�������
				User user = (User) in.readObject();
				System.out.println("��ȡ�ͻ�������: " + user);
				
				Response response = new Response();
				switch (user.getState()){
				case "register":
					User targetUser1 = UserAccess.findUser(user);
					if (targetUser1 != null) {
						response.setMessage("�ʺ��Ѵ���");
						break;
						}
					UserAccess.addUser(user);
					server.setLogin(user, true);
					response.setMessage("ע��ɹ�");
					break;
				case "login":					
						User targetUser = UserAccess.findUser(user);
						if (targetUser == null) {
							response.setMessage("�ʺŲ�����");
							}
						else if (!(user.getPassword().equals(targetUser.getPassword()))) {
							response.setMessage("�������");
							}
						else response.setMessage("��¼�ɹ�");
						server.setLogin(user, true);
						break;
					
				case "quit":
					server.setLogin(user, false);
					response.setMessage("���˳�");
					break;
				case "download":
					if(!server.isLogin(user)){
						response.setMessage("δ��¼");
						break;
					}
					try{
						FileHandler.upload("./ServerFiles/"+user.getFilename(), response);
						response.setMessage("�������");

					}catch (FileNotFoundException e) {
						response.setMessage("�ļ�������");
					}
					break;
				case "upload":
					if(!server.isLogin(user)){
						response.setMessage("δ��¼");
						break;
					}
					FileHandler.download("./ServerFiles/"+user.getFilename(), user);
					response.setMessage("�ϴ��ɹ�");
				}
				
				
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				out.writeObject(response);
				
				in.close();
				out.close();
				
			} catch (Exception e) {
				System.out.println("������ run �쳣: " + e.getMessage());
			} finally {
				if (socket != null) {
					try {
						socket.close();
					} catch (Exception e) {
						socket = null;
						System.out.println("����� finally �쳣:" + e.getMessage());
					}
				}
			}
		}
	}

}
