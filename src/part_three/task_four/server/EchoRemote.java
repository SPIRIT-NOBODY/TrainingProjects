package part_three.task_four.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoRemote extends Remote {

	String sayEcho(String msg) throws RemoteException;

	String sayEchoRevers(String msg) throws RemoteException;

}
