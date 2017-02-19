package server;

import java.util.HashMap;

import domain.User;

public class Server {
	private HashMap<String, Boolean> loginState;

	public Server() {
		super();
		loginState = new HashMap<String, Boolean>();
		new Thread(new SocketConnector(this)).start();
	}
	
	boolean isLogin(User user){
		return loginState.containsKey(user.getName()) && loginState.get(user.getName());
	}
	
	void setLogin(User user, boolean login){
		loginState.put(user.getName(), login);
	}

	public static void main(String[] args) {
		new Server();
	}
}
