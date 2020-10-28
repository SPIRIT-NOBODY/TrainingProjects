package part_three.task_five.interfaces;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public abstract class AnyFunctionSerr  implements AnyFunction,Serializable{

	public AnyFunctionSerr() throws RemoteException {}
	public abstract double f(double x) throws RemoteException;	
}
