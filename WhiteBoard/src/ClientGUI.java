import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField txt1;
	private JTextField txt2;
	private static String username;
	private static String password;
	private static Boolean valid;
	private static Boolean isManager;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public ClientGUI(IRemoteDraw obj, String role) {
		try {
			if (role.compareTo("manager") == 0) {
				boolean manage;
				manage = obj.existManager();
				
				if (manage) {
					JOptionPane.showMessageDialog(null, "Manager already exists, please join the whiteboard");
					System.exit(0);
				}
				else {
					isManager = true;
					obj.clearWhiteBoard();
					initialize(obj);
				}
			}
			
			else {
				boolean val = obj.existManager();
				if (!val) {
					JOptionPane.showMessageDialog(null, "No whiteboard has been created yet");
					System.exit(0);
				}
				else {
					isManager = false;
					initialize(obj);
				}
			}
		}
		catch(RemoteException e1) {
			System.out.println("problem with remote object");
		}
		
		
	
	}
	
	public void setVis() {
		this.setVisible(false);
	}
	
	public void initialize(IRemoteDraw obj) {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txt1 = new JTextField();
		txt1.setBounds(202, 78, 187, 39);
		contentPane.add(txt1);
		txt1.setColumns(10);
		
		txt2 = new JTextField();
		txt2.setBounds(202, 139, 187, 39);
		contentPane.add(txt2);
		txt2.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setBounds(47, 89, 112, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(47, 150, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Login");
		lblNewLabel_2.setBounds(171, 22, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.setBounds(147, 207, 149, 39);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton2 = new JButton("Sign up");
		btnNewButton2.setBounds(300, 207, 130, 39);
		contentPane.add(btnNewButton2);
		
		btnNewButton2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SignUpPage();
			}
			
		});
		
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				username = txt1.getText();
				password = txt2.getText();
				try {
					valid = obj.check(username, password);
					if (valid) {
						setVis();
						obj.addParticipants(username);
						if (isManager) {
							obj.changeManager(username);
						}
						WhiteBoardFrame wbf = new WhiteBoardFrame(obj, username);
						wbf.initialize();
						System.out.println("I initialized");
					}
					else {
						JOptionPane.showMessageDialog(null, "Password or username unrecognized, or user is already connected");
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					System.out.println("error connecting to remote object");
				}
			}
			
		});
	}
	
}
