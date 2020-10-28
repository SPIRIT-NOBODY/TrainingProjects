package part_three.task_five.functions;

import java.rmi.RemoteException;

import part_three.task_five.interfaces.AnyFunctionSerr;

public class FunctioOne extends AnyFunctionSerr{
	public FunctioOne() throws RemoteException {
	}
	@Override
	public double f(double x){		
		return x * x + 2.0;
	}
	@Override
	public void printFunction(String interval) {
		System.out.println(interval);
		System.out.print("x^2 + 2 = ");
	}	
}