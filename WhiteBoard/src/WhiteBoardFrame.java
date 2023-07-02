import java.awt.EventQueue;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.awt.event.WindowAdapter;
import java.awt.Container;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

public class WhiteBoardFrame extends JFrame implements WindowListener{

	private static JFrame window;
	private static OptionsFrame options;
	private static WhiteBoardPanel panel;
	private static JPanel container;
	private IRemoteDraw drawObj;
	public static final int HEIGHT = 700;
	public static final int WIDTH = 700;
	public static final int INITIAL_X = 400;
	public static final int INITIAL_Y = 400;
	private String username;
	
	/**
	 * Launch the application.
	 */
	/**
	 * Create the frame.
	 */
	private Timer timer = new Timer(500, new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			try {
				options.addAllUsers(drawObj.getAllUsers());
				if (drawObj.getAllUsers().size() == 0) {
					JOptionPane.showMessageDialog(null, "Manager has ended the whiteboard");
					System.exit(0);
				}
				
				boolean notRemoved = false;
				for(int i = 0; i < drawObj.getAllUsers().size(); i++) {
					 if (username.compareTo(drawObj.getAllUsers().get(i)) == 0) {
						 notRemoved = true;
						 break;
					 }
				 }
				if(!notRemoved) {
					JOptionPane.showMessageDialog(null, "You have either been kicked out or the manager has left the whiteboard");
					System.exit(0);
				}
				
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				System.out.println("remote");
			}
		}
	
	});
	
	public WhiteBoardFrame(IRemoteDraw drawObj, String username){
		
		try {
			this.drawObj = drawObj;
			this.username = username;
		
			window = new JFrame("Drawing");
			window.addWindowListener(this);
			container = new JPanel();
			container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
		
			panel = new WhiteBoardPanel(drawObj);
			options = new OptionsFrame(panel, username, drawObj.isManager(username), drawObj);
			options.addAllUsers(drawObj.getAllUsers());
			timer.start();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void initialize() {
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(WIDTH, HEIGHT);
		window.setLocation(INITIAL_X, INITIAL_Y);
		window.setResizable(true);
		
		Dimension dim = new Dimension(700, 700);
		panel.setPreferredSize(dim);
		
		container.add(options);
		container.add(panel);
		
		window.add(container);
		panel.setLayout(null);
		options.setLayout(null);
		
		window.setVisible(true);
	}
	
	public static Point getLoc() {
		return window.getLocation();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		try {
			if (drawObj.isManager(username)) {
				drawObj.clearUsers();	        		
			}
			else {
				drawObj.clearUser(username);
			}
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
