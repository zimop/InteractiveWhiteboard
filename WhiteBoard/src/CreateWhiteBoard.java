import java.net.ConnectException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class CreateWhiteBoard {
	public static void main(String args[]) {
		try {
			
			if (args.length == 0) {
				System.out.println("Please enter a port number and an ip address");
			}
			else if (args.length == 1) {
				System.out.println("Please enter an ip address");
			}
			else {
				int port = Integer.parseInt(args[0]);
				String ipAdd = args[1];
				Registry registry = LocateRegistry.getRegistry(ipAdd, port);
			
				IRemoteDraw localDraw = (IRemoteDraw) registry.lookup("rmi://"+ipAdd+":"+port+"/DrawServer");
				System.out.println("this client has connected");
			
				ClientGUI gui = new ClientGUI(localDraw, "manager");
				gui.setVisible(true);
			}
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("the connection has been refused, possibly due to invalid ip address or port");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid ip address or port");
		}
	}
	
	
}
