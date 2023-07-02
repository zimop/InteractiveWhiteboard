import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import java.awt.Point;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class OptionsFrame extends JPanel{
	//private JFrame window;
	private JPanel message;
	private WhiteBoardPanel wp;
	private JSlider red, blue, green;
	private int redVal, blueVal, greenVal;
	private JButton square, oval, circle, line, rectangle, text;
	private JPanel colorPanel;
	private JTextArea users;
	private JLabel label;
	private JLabel onlineUsers;
	private JLabel chatLabel;
	private JTextArea chat;
	private JTextField textBox;
	private JButton submit;
	private JButton kick;
	private String username;
	private Boolean isManager;
	private IRemoteDraw drawObj;
	
	private final int WIDTH= 300;
	
	private Timer timer = new Timer(500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				chatUpdate(wp.getChat(), wp.getUserForChat());
		}
	
	});
	
	public OptionsFrame(WhiteBoardPanel wp, String username, boolean isManager, IRemoteDraw drawObj){
		this.wp = wp;
		this.username = username;
		this.drawObj = drawObj;
		redVal = 0;
		blueVal = 0;
		greenVal = 0;
		initialize();
		addSlider();
		if(isManager) {
			addKick();
		}
		addShapeButtons();
		addTextButton();
		addOnlineUsers();
		addChat();
		addToPanel();
		createWindow();
		timer.start();
	}
	
	public void initialize() {
		this.setLayout(null);
		colorPanel = new JPanel();
		label = new JLabel("Shapes:");
		red = new JSlider(0,255);
		blue = new JSlider(0,255);
		green = new JSlider(0,255);
		square = new JButton("Square");
		oval = new JButton("Oval");
		circle = new JButton("Circle");
		line = new JButton("Line");
		rectangle = new JButton("Rectangle");
		text = new JButton("Add Text");
		onlineUsers = new JLabel("Online Users:");
		users = new JTextArea();
		users.setEditable(false);
		chatLabel = new JLabel("Chat");
		chat = new JTextArea();
		chat.setEditable(false);
		textBox = new JTextField();
		submit = new JButton("Submit");
		kick = new JButton("Kick user");
	}
	
	public void createWindow() {
		
		red.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				redVal = red.getValue();
				resetColour();
			}
			
		});
		blue.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				blueVal = blue.getValue();
				resetColour();
			}
			
		});
		green.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				greenVal = green.getValue();
				resetColour();
			}
			
		});
	    
	    JTextField textMessage = new JTextField(5);
	    
	    message = new JPanel();
	    message.add(new JLabel("Text"));
	    message.add(textMessage);
	    
	    
	    square.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Click on white board to place your Square and resize");
				wp.placeShape("Square");
			}
			
		});
	    
	    
		circle.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Click on white board to place your Circle and resize");
				wp.placeShape("Circle");
			}
			
		});
		
	    
		oval.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Click on white board to place your Oval and resize");
				wp.placeShape("Oval");
			}
		});
		
		line.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 JOptionPane.showMessageDialog(null, "Click on white board to place your rectangle and resize");
				 wp.placeShape("Line");
				 
			}
		});
		
		rectangle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JOptionPane.showMessageDialog(null, "Click on white board to place your rectangle and resize");
				wp.placeShape("Rectangle");
			}
		});
		
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, message, "Please enter text", JOptionPane.OK_CANCEL_OPTION);
				 if (result == JOptionPane.OK_OPTION) {
					 String t = textMessage.getText();
					 JOptionPane.showMessageDialog(null, "Click on white board to place your text field");
					 wp.placeText(t);
				 }
			}
			
		});
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String t = textBox.getText();
				textBox.setText("");
				wp.addChat(t, username);
			}
		});
		
		kick.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, message, "Please enter user to kick", JOptionPane.OK_CANCEL_OPTION);
				 if (result == JOptionPane.OK_OPTION) {
					 String t = textMessage.getText();
					 try {
						boolean found = false;
						for(int i = 0; i < drawObj.getAllUsers().size(); i++) {
							 if (t.compareTo(drawObj.getAllUsers().get(i)) == 0) {
								 if (drawObj.isManager(t)) {
									 drawObj.clearUsers();
								 }
								 else {
									 drawObj.clearUser(t);
								 }
								 found = true;
								 break;
							 }
						 }
						if (!found) {
							JOptionPane.showMessageDialog(null, "Username not found");
						}else {
							JOptionPane.showMessageDialog(null, "Successful Removal");
						}
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				 }
			}
		});
		
	}
	
	public void resetColour() {
		Color color = new Color(redVal, greenVal, blueVal);
		colorPanel.setBackground(color);
		wp.changeColor(color);
	}
	
	public void addKick() {
		kick.setBounds(0, 860, 75, 35);
		this.add(kick);
	}
	
	public void addSlider() {
		red.setBorder(BorderFactory.createTitledBorder("Red"));
		red.setMajorTickSpacing(50);
		red.setPaintTicks(true);
		red.setValue(0);
		red.setBounds(0, 0, WIDTH, 75);
	
		blue.setBorder(BorderFactory.createTitledBorder("Blue"));
		blue.setMajorTickSpacing(50);
		blue.setPaintTicks(true);
		blue.setValue(0);
		blue.setBounds(0, 75, WIDTH, 75);
		
		green.setBorder(BorderFactory.createTitledBorder("Green"));
		green.setMajorTickSpacing(50);
		green.setPaintTicks(true);
		green.setValue(0);
		green.setBounds(0, 150, WIDTH, 75);
		
		red.setPaintLabels(true);
		blue.setPaintLabels(true);
		green.setPaintLabels(true);
		
		colorPanel.setBounds(0, 225, WIDTH, 50);
		colorPanel.setBackground(Color.BLACK);
	}
	
	public void addShapeButtons() {
		label.setBounds(10, 275, 50, 25);
		square.setBounds(0, 300, WIDTH/2, 50);
		circle.setBounds(WIDTH/2,300, WIDTH/2, 50);
		line.setBounds(0,350, WIDTH/3, 50);
		oval.setBounds(WIDTH/3,350, WIDTH/3, 50);
		rectangle.setBounds((WIDTH/3)*2, 350, WIDTH/3, 50);
	}
	
	public void addTextButton() {
		text.setBounds(0, 400, 100, 50);
	}
	
	public void addOnlineUsers() {
		onlineUsers.setBounds(0, 460, 100 , 25);
		users.setBounds(0, 500, 300, 50);
	}
	
	
	public void addAllUsers(ArrayList<String> u) {
		users.setText(null);
		for (int i = 0; i < u.size(); i++) {
			users.append(u.get(i)+"\n");
		}
	}
	
	public void addChat() {
		chatLabel.setBounds(0, 575, 100, 25);
		chat.setBounds(0, 600, 300, 200);
		textBox.setBounds(0, 815, 300, 35);
		submit.setBounds(100, 860, 100, 35);
	}
	
	public void chatUpdate(ArrayList<String> c, ArrayList<String> u) {
		chat.setText("");
		
		for (int i = 0; i < c.size(); i++) {
			chat.append(u.get(i)+":  ");
			chat.append(c.get(i)+"\n");
		}
	}
	
	public void addToPanel() {
		this.add(red);
		this.add(blue);
		this.add(green);
		this.add(square);
		this.add(circle);
		this.add(oval);
		this.add(line);
		this.add(colorPanel);
		this.add(label);
		this.add(rectangle);
		this.add(text);
		this.add(onlineUsers);
		this.add(users);
		this.add(chatLabel);
		this.add(chat);
		this.add(textBox);
		this.add(submit);
	}
}
