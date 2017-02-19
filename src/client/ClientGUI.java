package client;


import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

public class ClientGUI implements Runnable{
	private Client client;

	private JFrame frame;
	private JTextField userName;
	private JTextField fileName;
	private JTextField message;
	private JPasswordField password;
	
	

	public JTextField getUserName() {
		return userName;
	}


	public void setUserName(JTextField userName) {
		this.userName = userName;
	}


	public JTextField getFileName() {
		return fileName;
	}


	public void setFileName(JTextField fileName) {
		this.fileName = fileName;
	}


	public JTextField getMessage() {
		return message;
	}


	public void setMessage(JTextField message) {
		this.message = message;
	}


	public JPasswordField getPassword() {
		return password;
	}


	public void setPassword(JPasswordField password) {
		this.password = password;
	}


	/**
	 * Launch the application.
	 */

			public void run() {
				try {
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}


	/**
	 * Create the application.
	 */
	public ClientGUI(Client client) {
		this.client = client;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		userName = new JTextField();
		userName.setBounds(113, 60, 200, 31);
		frame.getContentPane().add(userName);
		userName.setColumns(10);
		
		JLabel label = new JLabel("\u2014\u2014\u2014\u2014\u2014\u2014\u6587\u4EF6\u4F20\u8F93\u5BA2\u6237\u7AEF\u2014\u2014\u2014\u2014\u2014\u2014");
		label.setFont(new Font("宋体", Font.ITALIC, 20));
		label.setBounds(10, 10, 414, 40);
		frame.getContentPane().add(label);
		
		fileName = new JTextField();
		fileName.setColumns(10);
		fileName.setBounds(113, 142, 200, 31);
		frame.getContentPane().add(fileName);
		
		JLabel label_1 = new JLabel("\u7528\u6237\u540D");
		label_1.setBounds(20, 68, 54, 15);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u5BC6\u7801");
		label_2.setBounds(20, 109, 54, 15);
		frame.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("\u6587\u4EF6\u540D");
		label_3.setBounds(20, 150, 93, 15);
		frame.getContentPane().add(label_3);
		
		message = new JTextField();
		message.setEditable(false);
		message.setHorizontalAlignment(SwingConstants.CENTER);
		message.setFont(new Font("宋体", Font.ITALIC, 16));
		message.setText("\u63D0\u793A\u4FE1\u606F");
		message.setBackground(Color.LIGHT_GRAY);
		message.setBounds(10, 193, 414, 40);
		frame.getContentPane().add(message);
		message.setColumns(10);
		
		//注册按钮
		JButton button = new JButton("\u6CE8\u518C");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = getUserName().getText();
				String password = getPassword().getPassword().toString();
				if(userName == null || password == null || userName.trim().equals("") || password.trim().equals("") )
					JOptionPane.showMessageDialog(null, "请输入用户名和密码！");
				else 
					client.operationHandler("register");
			}
		});
		button.setBounds(10, 254, 93, 23);
		frame.getContentPane().add(button);
		
		//登录按钮
		JButton button_1 = new JButton("\u767B\u5F55");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = getUserName().getText();
				String password = new String(getPassword().getPassword());
				if(userName == null || password == null || userName.trim().equals("") || password.trim().equals("") )
					JOptionPane.showMessageDialog(null, "请输入用户名和密码！");
				else 
					client.operationHandler("login");
			}
		});
		button_1.setBounds(111, 254, 93, 23);
		frame.getContentPane().add(button_1);
		
		//上传按钮
		JButton button_2 = new JButton("\u4E0A\u4F20");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileName.getText()==null || fileName.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "请输入文件名！");
				else 
					client.operationHandler("upload");
			}
		});
		button_2.setBounds(214, 254, 93, 23);
		frame.getContentPane().add(button_2);
		
		//下载按钮
		JButton button_3 = new JButton("\u4E0B\u8F7D");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileName.getText()==null || fileName.getText().trim().equals(""))
					JOptionPane.showMessageDialog(null, "请输入文件名！");
				else 
					client.operationHandler("download");
			}
		});
		button_3.setBounds(317, 254, 93, 23);
		frame.getContentPane().add(button_3);
		
		password = new JPasswordField();
		password.setBounds(113, 101, 200, 31);
		frame.getContentPane().add(password);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){   				
				client.operationHandler("quit");
				}   
		});
	}
}
