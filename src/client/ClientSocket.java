package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import domain.Response;
import util.FileHandler;

public class ClientSocket implements Runnable{
	public static final String IP_ADDR = "localhost";//服务器地址 
	public static final int PORT = 54321;//服务器端口号  
	
	private Client client;
	
	
	public ClientSocket(Client client) {
		super();
		this.client = client;
	}


	public void run() {
		 System.out.println("客户端启动...");  
		 
	       	Socket socket = null;
	        	try {
	        		//创建一个流套接字并将其连接到指定主机上的指定端口号
		        	socket = new Socket(IP_ADDR, PORT);  
		             
		            //向服务器端发送数据  
		            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
		            
		            out.writeObject(client.getUser());
		            
		            //读取服务器端数据  
		            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
		            
		            Response response = (Response)in.readObject();
		            
		            if(client.getUser().getState().equals("download")){
		            	if(!response.getMessage().equals("文件不存在"))
		    			FileHandler.download("./ClientFiles/"+client.getUser().getFilename(), response);
		            }
		            
		            client.getGui().getMessage().setText(response.getMessage());
		          	        
		            in.close();
		            out.close();
	        	} catch (Exception e) {
	        		System.out.println("客户端异常:" + e.getMessage()); 
	        	} finally {
	        		if (socket != null) {
	        			try {
							socket.close();
						} catch (IOException e) {
							socket = null; 
							System.out.println("客户端 finally 异常:" + e.getMessage()); 
						}
	        		}
	        	}
		
	}


	
    }  

