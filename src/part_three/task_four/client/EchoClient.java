package part_three.task_four.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import part_three.task_four.server.EchoRemote;

public class EchoClient {
	
	public EchoClient(String port) {
		String host = "localhost";
		int portTmp = 6666;
		if(port != null) {
			try {
				portTmp = Integer.parseInt(port);
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		String consoleEncoding = System.getProperty("sun.stdout.encoding");
		if (consoleEncoding == null) {
			consoleEncoding = "UTF-8";
		}
		Scanner in = new Scanner(System.in, consoleEncoding);
        try {
            Registry registry = LocateRegistry.getRegistry(portTmp);
            EchoRemote stub = (EchoRemote) registry.lookup("EchoService");
            System.out.println("Please, write message:\n");
            while(in.hasNextLine()) {
            	String msg = in.nextLine();
            	if(msg.equals("/exit")) {
            		System.out.println("client exit");
            		break;
            	}
            	System.out.println(stub.sayEcho(msg));
            	System.out.println(stub.sayEchoRevers(msg));            	
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }        
	}

}
