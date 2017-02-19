package domain;

import java.io.Serializable;

public class Response implements FileContainer,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	private byte[] fileContent;
	
	
	public Response(String message) {
		super();
		this.message = message;
	}
	
	public Response() {
		super();
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public byte[] getFileContent() {
		return fileContent;
	}
	
	@Override
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	
	

}
