package part_three.task_five;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import part_three.task_five.client.ClientCalculate;

/**
 * Сервер решает уравнения методом деления отрезка пополам. Реализовать по
 * RMI-технологии. Сервер в функциональности не ограничивать.
 */

public class Task05 {

	public static void main(String[] args) throws RemoteException, NotBoundException {
		ClientCalculate client = new ClientCalculate(args.length > 0 ? args[0]: null);
	}

}
