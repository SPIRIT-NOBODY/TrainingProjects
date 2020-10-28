package part_three.task_five.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObjectInvocationHandler;

public interface CalculateRemote extends Remote{
	double calculate( Remote func, double a, double b, double eps) throws RemoteException;
}
