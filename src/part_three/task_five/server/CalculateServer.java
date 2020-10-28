package part_three.task_five.server;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObjectInvocationHandler;
import java.rmi.server.UnicastRemoteObject;
import java.security.Permission;
import java.util.Arrays;

import part_three.task_five.interfaces.*;

public class CalculateServer implements CalculateRemote {

	private static int port = 1099;
	private static String host = "127.0.0.1";

	public static void main(String[] args) throws RemoteException {
		System.setProperty("java.security.policy","file:src\\part_three\\task_five\\server\\security.policy");
		if (System.getSecurityManager() == null) {
		    System.setSecurityManager(new SecurityManager());
		}
		CalculateServer server = new CalculateServer(args.length > 0 ? args[0] : null);
		CalculateRemote stub = (CalculateRemote) UnicastRemoteObject.exportObject(server, 0);

		Registry registry;
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			registry = LocateRegistry.getRegistry(host, port);
		}
		
		registry.rebind("RemoteFunction", stub);
		System.out.println("server ready");
	}

	public CalculateServer(String port) {

		if (port != null) {
			try {
				this.port = Integer.parseInt(port);
			} catch (Exception e) {
				System.out.println("ports set to default value= " + this.port);
			}
		}

	}

	@Override
	public double calculate(Remote func, double a, double b, double eps) throws RemoteException {
		AnyFunctionSerr function = (AnyFunctionSerr) func;
		/*
		 * x = (a + b)/2 Если f(a)f(x) < 0, то b = x, иначе если f(x)f(b) < 0, то a = x.
		 * Если |b - a| > 2ε, то идти к 1. x = (a + b)/2.
		 */
		//return 0;
		double y1 = function.f(a);
		double x = (a + b) / 2d;
		double y = function.f(x);
		if(y == 0) {
			return x;
		} else if(Math.abs(b - a) < eps ) {
			return x;
		} else if( y * y1 < 0) {
			return calculate(func, a, x , eps);
		} else {
			return calculate(func, x ,b , eps);
		}		

	}
	

}
