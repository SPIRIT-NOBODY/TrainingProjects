package part_three.task_five.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;

import part_three.task_five.functions.*;
import part_three.task_five.interfaces.AnyFunctionSerr;
import part_three.task_five.interfaces.CalculateRemote;

public class ClientCalculate {

	private int port = 1099;
	private String host = "127.0.0.1";

	public ClientCalculate(String port) throws RemoteException, NotBoundException {
		System.setProperty("java.security.policy","file:src\\part_three\\task_five\\client\\security.policy");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			this.port = Integer.parseInt(port);
		} catch (Exception e) {
			// TODO: handle exception
		}

		Registry registry = LocateRegistry.getRegistry(port);
		CalculateRemote stub = (CalculateRemote) registry.lookup("RemoteFunction");
		AnyFunctionSerr func1 = new FunctioOne();

		double res = stub.calculate(func1, 0.5, 2.5, 0.01);
		func1.printFunction(" on [0.5 , 2.5] eps 0.01");
		System.out.println(res);
		
		AnyFunctionSerr func2 = new FunctionTwo();		
		func2.printFunction(" on [0.5 , 2.5] eps 0.01");
		res = stub.calculate(func2, 0.5, 2.5, 0.01);
		System.out.println(res);
		

	}

}
