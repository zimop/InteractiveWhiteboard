import java.awt.Point;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IRemoteDraw extends Remote{
	public void addDraw(DrawObject draw) throws RemoteException;
	public ArrayList<DrawObject> getDraw() throws RemoteException;
	public void addShape(ShapeObject shape) throws RemoteException;
	public ArrayList<ShapeObject> getShape() throws RemoteException;
	public void addText(Texts text) throws RemoteException;
	public ArrayList<Texts> getText() throws RemoteException;
	public Boolean check(String username, String password) throws RemoteException;
	public void addParticipants(String user) throws RemoteException;
	public boolean changeManager(String user) throws RemoteException;
	public boolean existManager() throws RemoteException;
	public void clearUsers() throws RemoteException;
	public void clearUser(String username) throws RemoteException;
	public void clearWhiteBoard() throws RemoteException;
	public boolean isManager(String username) throws RemoteException;
	public boolean checkNewUser() throws RemoteException;
	public String getLatestUser() throws RemoteException;
	public ArrayList<String> getAllUsers() throws RemoteException;
	public void addChat(String chat, String username) throws RemoteException;
	public ArrayList<String> getChat() throws RemoteException;
	public ArrayList<String> getUserForChat() throws RemoteException;
}
