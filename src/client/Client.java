package client;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JOptionPane;

import domain.User;
import util.FileHandler;

public class Client {
	private User user;
	private ClientGUI gui;

	public Client() {
		super();
		user = new User();
		gui = new ClientGUI(this);
		new Thread(gui).start();
	}
	
	
	
	public User getUser() {
		return user;
	}



	public ClientGUI getGui() {
		return gui;
	}



	void operationHandler(String s){
		switch (s) {
		case "register":
			user.setName(gui.getUserName().getText());
			user.setPassword(new String(gui.getPassword().getPassword()));
			user.setIP(gui.getIP().getText());
			user.setState(s);
			break;
		case "login":
			user.setName(gui.getUserName().getText());
			user.setPassword(new String(gui.getPassword().getPassword()));
			user.setIP(gui.getIP().getText());
			user.setState(s);
			break;
		case "upload":
			try {
				FileHandler.upload("./ClientFiles/"+gui.getFileName().getText(), user);
			}catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "文件不存在");
				return;
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setFilename(gui.getFileName().getText());
			user.setState(s);
			break;
		case "download":
			user.setFilename(gui.getFileName().getText());
			user.setState(s);
//			FileHandler.download("./ClientFiles/"+gui.getFileName(), response);
			break;
		case "quit":
			user.setState(s);
			
		}
		new Thread(new ClientSocket(this)).start();

	}
	public static void main(String[] args) {
		new Client();
	}

}
