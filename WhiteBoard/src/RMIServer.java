import java.rmi.AlreadyBoundException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

	public static void main(String[] args){
		// TODO Auto-generated method stub
		try {
			if (args.length == 0) {
				System.out.println("Please enter a port number and an ip address");
			}
			else {
				int port = Integer.parseInt(args[0]);
				IRemoteDraw remoteDraw = new RemoteDraw();
				Registry registry = LocateRegistry.createRegistry(port);
				registry.rebind("rmi://localhost"+":"+port+"/DrawServer", remoteDraw);
				System.out.println("Draw server is ready to be connected to on port: "+port);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			System.out.println("Error creating the server with current port");
			
		}
	}
}
