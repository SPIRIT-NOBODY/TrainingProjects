package part_two.task_two;

import java.util.Arrays;

public class Matrix {

	private int rowFirst = 5;
	private int columntFirst = 4;
	private int columnSecond = 6;

	volatile private static int m1ColLength;
	volatile private static int m2RowLength;
	volatile private static int mResultRowLength;
	volatile private static int mResultColLength;

	volatile private static int[][] matrixA;
	volatile private static int[][] matrixB;
	volatile private static int[][] matrixC = null;

	public Matrix() throws Exception {
		matrixA = new int[rowFirst][columntFirst];
		fillMatrix(matrixA);
		matrixB = new int[columntFirst][columnSecond];
		fillMatrix(matrixB);
		initResultMatrix();
	}

	protected void initResultMatrix() throws Exception {

		m1ColLength = matrixA[0].length;
		m2RowLength = matrixB.length;
		if (m1ColLength != m2RowLength) {
			throw new Exception("matrix multiplication is not possible");
		}
		mResultRowLength = matrixA.length;
		mResultColLength = matrixB[0].length;
		matrixC = new int[mResultRowLength][mResultColLength];

	}

	public void multiply() {
		for (int i = 0; i < mResultRowLength; i++) {
			for (int j = 0; j < mResultColLength; j++) {
				for (int k = 0; k < m1ColLength; k++) {
					matrixC[i][j] += matrixA[i][k] * matrixB[k][j];
				}
			}
		}
	}

	public int getRowFirst() {
		return rowFirst;
	}

	protected void fillMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				matrix[i][j] = (int) (Math.random() * 9) + 1;
				// matrix[i][j] = i + j + 1;
			}
		}
		System.out.println(Arrays.deepToString(matrix).replace("], ", "],\n"));
	}

	public String getResult() {
		return Arrays.deepToString(matrixC).replace("], ", "],\n");
	}

}
