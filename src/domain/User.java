package domain;

import java.io.Serializable;

public class User implements FileContainer, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String state;
	private String name;
	private String password;	
	private String filename;
	private byte[] fileContent;
	
	
	
	public byte[] getFileContent() {
		return fileContent;
	}



	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}



	public String getFilename() {
		return filename;
	}



	public void setFilename(String filename) {
		this.filename = filename;
	}



	public User() {
		super();
	}


	
	public User(String state, String name, String password) {
		super();
		this.state = state;
		this.name = name;
		this.password = password;
	}



	public String getState() {
		return state;
	}



	public void setState(String state) {
		this.state = state;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}



	@Override
	public String toString() {
		return "User [state=" + state + ", name=" + name + ", password=" + password + "]";
	}

	
	
	
	
}
