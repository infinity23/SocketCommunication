package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import domain.Response;
import util.FileHandler;

public class ClientSocket implements Runnable{
	public static final String IP_ADDR = "localhost";//��������ַ 
	public static final int PORT = 54321;//�������˿ں�  
	
	private Client client;
	
	
	public ClientSocket(Client client) {
		super();
		this.client = client;
	}


	public void run() {
		 System.out.println("�ͻ�������...");  
		 
	       	Socket socket = null;
	        	try {
	        		//����һ�����׽��ֲ��������ӵ�ָ�������ϵ�ָ���˿ں�
		        	socket = new Socket(IP_ADDR, PORT);  
		             
		            //��������˷�������  
		            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream()); 
		            
		            out.writeObject(client.getUser());
		            
		            //��ȡ������������  
		            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
		            
		            Response response = (Response)in.readObject();
		            
		            if(client.getUser().getState().equals("download")){
		            	if(!response.getMessage().equals("�ļ�������"))
		    			FileHandler.download("./ClientFiles/"+client.getUser().getFilename(), response);
		            }
		            
		            client.getGui().getMessage().setText(response.getMessage());
		          	        
		            in.close();
		            out.close();
	        	} catch (Exception e) {
	        		System.out.println("�ͻ����쳣:" + e.getMessage()); 
	        	} finally {
	        		if (socket != null) {
	        			try {
							socket.close();
						} catch (IOException e) {
							socket = null; 
							System.out.println("�ͻ��� finally �쳣:" + e.getMessage()); 
						}
	        		}
	        	}
		
	}


	
    }  

