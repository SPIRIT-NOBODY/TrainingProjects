package part_three.task_five.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AnyFunction extends Remote{
	double f(double x) throws RemoteException;	
	public void printFunction(String interval);
}
