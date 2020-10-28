package part_three.task_five.functions;

import java.rmi.RemoteException;

import part_three.task_five.interfaces.*;

public class FunctionTwo extends AnyFunctionSerr{
	public FunctionTwo() throws RemoteException {}

	@Override
	public double f(double x) throws RemoteException {
		// TODO Auto-generated method stub
		return x * x * x - 10;
	}
	@Override
	public void printFunction(String interval) {
		System.out.println(interval);
		System.out.print("x^3 - 10 = ");		
		
	}
}
