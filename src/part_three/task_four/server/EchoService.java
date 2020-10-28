package part_three.task_four.server;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class EchoService implements EchoRemote,Serializable {

	private static String host = "localhost";
	private static int port = 6666;

	public EchoService() throws RemoteException {
	}

	public static void main(String[] args) throws AlreadyBoundException {
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (Exception e) {
			}
		}
		System.out.println(port);
		try {
//			String localhost    = "127.0.0.1";
//	        String RMI_HOSTNAME = "java.rmi.server.hostname";
//			System.setProperty(RMI_HOSTNAME, localhost);
			EchoService service = new EchoService();
			EchoRemote stub = (EchoRemote) UnicastRemoteObject.exportObject(service, 0);
			Registry registry;
			try {
				 registry = LocateRegistry.createRegistry(port);
			} catch (Exception e) {
				registry = LocateRegistry.getRegistry(port);
			}			
			registry.bind("EchoService", stub);
			System.out.println("Server ready");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public String sayEcho(String msg) {
		return msg;

	}

	@Override
	public String sayEchoRevers(String msg) {
		StringBuilder builder = new StringBuilder();
		return builder.append(msg).reverse().toString();
	}

}
