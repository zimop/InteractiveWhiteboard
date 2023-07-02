import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.Timer;

public class RemoteDraw extends UnicastRemoteObject implements IRemoteDraw {
	private ArrayList<DrawObject> drawing = new ArrayList<DrawObject>();
	private ArrayList<ShapeObject> shapes = new ArrayList<ShapeObject>();
	private ArrayList<Texts> texts = new ArrayList<Texts>();
	private ArrayList<String> users = new ArrayList<String>();
	private ArrayList<String> chats = new ArrayList<String>();
	private ArrayList<String> userForChat = new ArrayList<String>();
	private int currNumUsers = 0;
	private String userManager = "";
	public RemoteDraw() throws RemoteException{
		
	}
	
	public synchronized void addDraw(DrawObject draw) throws RemoteException {
		drawing.add(draw);
	}
	
	public synchronized ArrayList<DrawObject> getDraw() {
		return drawing;
	}
	
	public synchronized void addShape(ShapeObject shape) throws RemoteException {
		shapes.add(shape);
	}
	
	public synchronized ArrayList<ShapeObject> getShape() {
		return shapes;
	}
	
	public synchronized void addText(Texts text) throws RemoteException {
		texts.add(text);
	}
	
	public synchronized void addChat(String chat, String username) throws RemoteException {
		chats.add(chat);
		userForChat.add(username);
	}
	
	public synchronized ArrayList<String> getChat() throws RemoteException{
		return chats;
	}
	
	public synchronized ArrayList<String> getUserForChat() throws RemoteException{
		return userForChat;
	}
	
	public synchronized ArrayList<Texts> getText() throws RemoteException{
		return texts;
	}
	
	public synchronized Boolean check(String username, String password) throws RemoteException{
		try {
			Scanner sc = new Scanner(new File("/Users/zimopeng/Desktop/WhiteBoard/src/password.txt"));
			while(sc.hasNext()) {
				String data = sc.nextLine();
				String[] val = data.split(";");
				if ((val[0].compareTo(username) == 0) && (val[1].compareTo(password) == 0)) {
					if (users.contains(username)){
						return false;
					}
					return true;
				}
			}
			return false;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public synchronized void addParticipants(String user) throws RemoteException {
		// TODO Auto-generated method stub
		users.add(user);
	}
	
	public synchronized boolean changeManager(String user) throws RemoteException {
		if (userManager.compareTo("") == 0) {
			userManager = user;
			return true;
		}else {
			return false;
		}
	}
	
	public synchronized boolean existManager() throws RemoteException {
		return (userManager.compareTo("") != 0);
	}
	
	public synchronized void clearUsers() throws RemoteException {
		userManager = "";
		users.clear();
	}
	public synchronized void clearUser(String username) throws RemoteException {
		for (int i = 0; i < users.size(); i++) {
			String u = users.get(i);
			if (u.compareTo(username) == 0) {
				users.remove(i);
			}
		}
	}
	public synchronized void clearWhiteBoard() throws RemoteException {
		drawing.clear();
		shapes.clear();
		texts.clear();
		users.clear();
		chats.clear();
	}
	
	public synchronized boolean isManager(String username) throws RemoteException {
		return username.compareTo(userManager) == 0;
	}
	
	public synchronized boolean checkNewUser() throws RemoteException {
		boolean check = users.size() > currNumUsers;
		if (check) {
			currNumUsers = users.size();
		}
		return check;
	}
	public synchronized String getLatestUser() throws RemoteException {
		int size = users.size();
		return users.get(size - 1);
				
	}
	public synchronized ArrayList<String> getAllUsers() throws RemoteException {
		return users;
	}
	
}
