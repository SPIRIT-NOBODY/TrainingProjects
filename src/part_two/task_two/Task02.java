package part_two.task_two;

import java.util.Arrays;

/**
 * Написать приложение, осуществляющее перемножение двух матриц. Организовать
 * несколько потоков по количеству строк первой матрицы. Cij = ∑k Aik * Bkj
 * Отображать номера работающих потоков.
 */

public class Task02 {

	public static void main(String[] args) throws Exception {

		Matrix matrix = new Matrix();

		int threadCount = matrix.getRowFirst();

		Thread[] threads = new Thread[threadCount];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				System.out.println(Thread.currentThread().getName());
				try {
					matrix.multiply();
				} catch (Exception e) {
					System.out.println(e);
				}
			}, "Thread " + (i + 1));
		}

		for (int i = 0; i < threads.length; i++) {
			threads[i].start();
		}
		for (int i = 0; i < threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println(matrix.getResult());

	}

}
