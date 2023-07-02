import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;

public class SignUpPage extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	public SignUpPage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(197, 70, 165, 33);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(197, 144, 165, 33);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setBounds(43, 78, 84, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(43, 152, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Sign up");
		lblNewLabel_2.setBounds(175, 16, 61, 16);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.setBounds(148, 204, 117, 29);
		contentPane.add(btnNewButton);
		setVisible(true);
		
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				try {
					String name = textField.getText();
					String pass = textField_1.getText();
					String full = "\n"+name+";"+pass;
					Boolean contain = false;
					Scanner sc = new Scanner(new File("/Users/zimopeng/Desktop/WhiteBoard/src/password.txt"));
					while(sc.hasNext()) {
						String data = sc.nextLine();
						String[] val = data.split(";");
						if ((val[0].compareTo(name) == 0)) {
							contain = true;
							break;
						}
					}
					if (contain) {
						JOptionPane.showMessageDialog(null, "Usename already exists");
					}
					else {
						File f1 = new File("/Users/zimopeng/eclipse-workspace/WhiteBoard/src/password.txt");
						FileWriter fileWriter = new FileWriter(f1.getName(),true);
						BufferedWriter bw = new BufferedWriter(fileWriter);
						bw.write(full);
						bw.close();
						setVisible(false);
					}
					
			        
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
	}
}
